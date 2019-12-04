package com.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.common.AlipayConstant;
import com.yxw.payrefund.sign.RSA;


public class AlipayNotify {

    /**
     * 支付宝消息验证地址
     */
    private static final String HTTPS_VERIFY_URL = AlipayConstant.ALIPAY_MAPI_GATEWAY.concat("?service=notify_verify&");

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @param isSort 是否排序
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params, String pid, String privateKey, String alipayPublicKey, String charset, boolean isSort) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
    	String responseTxt = "true";
		if (StringUtils.isNotBlank(pid)) {
		    if(params.get("notify_id") != null) {
	            String notify_id = params.get("notify_id");
	            responseTxt = verifyResponse(notify_id, pid);
	        }
	        
		    String notifyData = params.get("notify_data");
	        if (notifyData != null) {
            	try {
            		if (!notifyData.matches("^<.*>$")) {
            			//解密
            			if(AlipayConstant.SIGN_TYPE_RSA_0001.equals(params.get("sec_id"))) {
            				notifyData = RSA.decrypt(notifyData, privateKey, charset);
            				params.put("notify_data", notifyData);
            			}
            		}
                	
                    //XML解析notify_data数据，获取notify_id
                    Document document = DocumentHelper.parseText(notifyData);
                    String notify_id = document.selectSingleNode( "//notify/notify_id" ).getText();
                    responseTxt = verifyResponse(notify_id, pid);
				} catch (Exception e) {
					responseTxt = e.toString();
				}
	        }
		}
		
		
	    String sign = "";
	    if(params.get("sign") != null) {sign = params.get("sign");}
	    boolean isSign = getRSASignVerify(params, sign, alipayPublicKey, charset, isSort);

	    System.out.println("isSign: " + isSign + ", responseTxt: " + responseTxt);
        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param params 通知返回来的参数数组
     * @param sign 比对的签名结果.
     * @param isSort 是否排序
     * @return 生成的签名结果
     */
	private static boolean getRSASignVerify(Map<String, String> params, String sign, String alipayPublicKey, String charset, boolean isSort) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = AlipayCore.paraFilter(params);
        //获取待签名字符串
        String preSignStr = "";
        if(isSort) {
            preSignStr = AlipayCore.createLinkString(sParaNew);
        } else {
            preSignStr = AlipayCore.createLinkStringNoSort(sParaNew);
        }
        System.out.println(preSignStr);
        
        //获得签名验证结果
        boolean isSign = RSA.verify(preSignStr, sign, alipayPublicKey, charset);
        
        return isSign;
    }
	
    /**
     * 解密
     * @param inputPara 要解密数据
     * @return 解密后结果
     */
    public static Map<String, String> decrypt(Map<String, String> inputPara, String privateKey, String charset) throws Exception {
        inputPara.put("notify_data", RSA.decrypt(inputPara.get("notify_data"), privateKey, charset));
        return inputPara;
    }

    /**
    * 获取远程服务器ATN结果,验证返回URL
    * @param notify_id 通知校验ID
    * @return 服务器ATN结果
    * 验证结果集：
    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
    * true 返回正确信息
    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
    */
    private static String verifyResponse(String notify_id, String pid) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + pid + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
    * 获取远程服务器ATN结果
    * @param urlvalue 指定URL路径地址
    * @return 服务器ATN结果
    * 验证结果集：
    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
    * true 返回正确信息
    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
    */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}
