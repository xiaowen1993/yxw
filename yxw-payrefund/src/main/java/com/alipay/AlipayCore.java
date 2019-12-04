package com.alipay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {
    
    /**
     * 除去数组中的空值和签名参数
     * 
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        
        Map<String, String> result = new HashMap<String, String>();
        
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        
        return result;
    }
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        
        String prestr = "";
        
        String[] keys = new String[params.size()];
        params.keySet().toArray(keys);
        
        Arrays.sort(keys);
        
        for (String k : keys) {
            String value = params.get(k);
            if (StringUtils.isNotBlank(value)) {
                prestr += k + "=" + params.get(k) + "&";
            }
        }
        
        prestr = "".equals(prestr) ? prestr : StringUtils.stripEnd(prestr, "&");
        
        return prestr;
    }
    
    /**
     * 把数组所有元素按照固定参数排序，以“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params
     *            需要参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringNoSort(Map<String, String> params) {
        
        String prestr = "";
        
        prestr += "service=" + params.get("service");
        prestr += "&v=" + params.get("v");
        prestr += "&sec_id=" + params.get("sec_id");
        prestr += "&notify_data=" + params.get("notify_data");
        
        return prestr;
    }
    
}
