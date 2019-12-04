/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年3月16日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.unionpay.acp.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.common.UnionPayConstant;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.UnionpayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.payrefund.utils.DateUtil;
import com.yxw.payrefund.utils.OrderUtil;
import com.yxw.utils.RandomUtil;

/**
 * 银联钱包SDK
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2017年3月7日
 */
public class UnionPayUtil {

	private static Logger logger = LoggerFactory.getLogger(UnionPayUtil.class);
	public static HttpClientUtil httpClient = HttpClientUtil.getInstance();

	/**
	 * 获取授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl) throws UnsupportedEncodingException {
		String oauth2Url = "";

		oauth2Url += UnionPayConstant.UNIONPAY_GATEWAY + "/s/open/html/oauth.html?responseType=code";
		oauth2Url += "&scope=upapi_userinfo&state=getUserInfo";
		oauth2Url += "&appId=" + appId;
		oauth2Url += "&redirectUri=" + URLEncoder.encode(redirectUrl, "utf-8");

		return oauth2Url;
	}

	/**
	 * 去内存获取FrontToken，有超时机制
	 * 
	 * @param appId
	 * @param secret
	 * @return FrontToken
	 */
	public static String getFrontToken(String appId, String secret) {
		String frontToken = "";
		JSONObject btJson = UnionPayConstant.unionPayFrontTokenMap.get(appId);
		if (btJson != null) {
			// 判断超时否
			long getTime = btJson.getLongValue("getTime");
			long expiresIn = 1000L * ( (Integer) JSONPath.eval(btJson, "$.params.expiresIn") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				frontToken = getFrontTokenByUnionPay(appId, secret);
			} else {
				// 没超时
				frontToken = (String) JSONPath.eval(btJson, "$.params.frontToken");
			}
		} else {
			frontToken = getFrontTokenByUnionPay(appId, secret);
		}
		return frontToken;
	}

	/**
	 * 去银联服务器获取FrontToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return FrontToken
	 * {"cmd":"frontToken","msg":"","params":{"expiresIn":7200,"frontToken":"aquXvuM/TNyVvGtxpP97Rg=="},"resp":"00","v":"1.0"}
	 */
	public static String getFrontTokenByUnionPay(String appId, String secret) {
		String frontToken = "";

		String url = UnionPayConstant.UNIONPAY_GATEWAY + "/open/access/1.0/frontToken";

		JSONObject params = new JSONObject();
		params.put("appId", appId);
		params.put("secret", secret);

		logger.info("getFrontTokenByUnionPay.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = httpClient.post(url, params.toJSONString());
		logger.info("frontToken.resp: {}", rs);

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {
				frontToken = (String) JSONPath.eval(jo, "$.params.frontToken");

				jo.put("getTime", System.currentTimeMillis() + "");

				UnionPayConstant.unionPayFrontTokenMap.put(appId, jo);
			}
		}

		logger.debug("reget unionpay front token : {}", frontToken);
		return frontToken;
	}

	/**
	 * 去内存获取BackendToken，有超时机制
	 * 
	 * @param appId
	 * @param secret
	 * @return BackendToken
	 */
	public static String getBackendToken(String appId, String secret) {
		String backendToken = "";
		JSONObject btJson = UnionPayConstant.unionPayBackendTokenMap.get(appId);
		if (btJson != null) {
			// 判断超时否
			long getTime = btJson.getLongValue("getTime");
			long expiresIn = 1000L * ( (Integer) JSONPath.eval(btJson, "$.params.expiresIn") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				backendToken = getBackendTokenByUnionPay(appId, secret);
			} else {
				// 没超时
				backendToken = (String) JSONPath.eval(btJson, "$.params.backendToken");
			}
		} else {
			backendToken = getBackendTokenByUnionPay(appId, secret);
		}
		return backendToken;
	}

	/**
	 * 去银联服务器获取BackendToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return BackendToken
	 */
	public static String getBackendTokenByUnionPay(String appId, String secret) {
		String backendToken = "";

		String url = UnionPayConstant.UNIONPAY_GATEWAY + "/open/access/1.0/backendToken";

		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("secret", secret);
		
//		String nonceStr = RandomUtil.RandomString(16);
//		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//		params.put("nonceStr", nonceStr);
//		params.put("timestamp", timestamp);
//		params.put("signature", getUPSDKSignature(params));
//		params.remove("secret");
		
		logger.info("getBackendTokenByUnionPay.url: {}, params: {}", url, JSONObject.toJSONString(params));

		//		HttpResponse rs = httpClient.post(url, JSONObject.toJSONString(params), HttpConstants.HTML_TYPE, "utf-8");
		String rs = httpClient.post(url, JSONObject.toJSONString(params));
		logger.info("backendToken.resp: {}", rs);

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {
				backendToken = (String) JSONPath.eval(jo, "$.params.backendToken");

				jo.put("getTime", System.currentTimeMillis() + "");

				UnionPayConstant.unionPayBackendTokenMap.put(appId, jo);
			}
		}

		logger.debug("reget unionpay backend token : {}", backendToken);
		return backendToken;
	}

	/**
	 * 去银联服务器获取AccessToken
	 * 
	 * @param appId
	 * @param backendToken
	 * @param code - request.getParameter("code"); 
	 * 注意: 返回的code是通过encodeURIComponent经过URL编码的，接入方需要通过函数 decodeURIComponent 解码去获取 code.
	 * @return JSON 
	 * {
		"accessToken":"ACCESSTOKEN", 
		"expiresIn":"7200", 
		"refreshToken":"REFRESHTOKEN", 
		"openId":"OPENID", 
		"scope":"SCOPE"
	   }
	 */
	public static JSONObject getAccessTokenAndOpenIdByUnionPay(String appId, String backendToken, String code) {
		String url = UnionPayConstant.UNIONPAY_GATEWAY + "/open/access/1.0/token";

		JSONObject params = new JSONObject();
		params.put("appId", appId);
		params.put("backendToken", backendToken);
		params.put("code", code);
		params.put("grantType", UnionPayConstant.GRANT_TYPE);

		logger.info("getAccessTokenAndOpenIdByUnionPay.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			return jo.getJSONObject("params");
		}

		return null;
	}

	/**
	 * 银联钱包获取用户信息
	 * 
	 * @param appId
	 * @param openId
	 * @param accessToken
	 * @return JSON
	 */
	public static JSONObject getUserInfo(String appId, String openId, String accessToken) {
		String url = UnionPayConstant.UNIONPAY_GATEWAY + "/open/access/1.0/oauth.userInfo";

		JSONObject params = new JSONObject();
		params.put("accessToken", accessToken);
		params.put("openId", openId);
		params.put("appId", appId);

		logger.info("getUserInfo.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {

			}

			return jo.getJSONObject("params");
		}
		return null;
	}

	/**
	 * UNION PAY UPSDK 签名算法
	 * @param params
	 * 
	 * @return Signature
	 */
	public static String getUPSDKSignature(Map<String, String> params) {
		String signature = "";
		StringBuilder signatureBefore = new StringBuilder();

		String[] keys = new String[params.size()];
		params.keySet().toArray(keys);

		Arrays.sort(keys);

		for (String k : keys) {
			String value = params.get(k);
			if (StringUtils.isNotBlank(value)) {
				signatureBefore.append(k).append("=").append(params.get(k)).append("&");
			}
		}
		logger.info("signature before string: {}", signatureBefore.toString());
		signature = DigestUtils.sha256Hex(StringUtils.stripEnd(signatureBefore.toString(), "&"));

		return signature;
	}
	
	public static Map<String, String> genJSSDKParams(String url) {
		String nonceStr = RandomUtil.RandomString(16);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);


		Map<String, String> unionPayUPSDKParams = new HashMap<String, String>();

		unionPayUPSDKParams.put("fronttoken", UnionPayUtil.getFrontToken(UnionPayConstant.UNIONPAY_APPID, UnionPayConstant.UNIONPAY_SECRET));
		unionPayUPSDKParams.put("noncestr", nonceStr);
		unionPayUPSDKParams.put("timestamp", timestamp);
		unionPayUPSDKParams.put("url", url);

		logger.info("unionPayUPSDKParams: {}", unionPayUPSDKParams.toString());

		//生成签名
		String signature = UnionPayUtil.getUPSDKSignature(unionPayUPSDKParams);

		unionPayUPSDKParams.put("signature", signature);
		unionPayUPSDKParams.put("appid", UnionPayConstant.UNIONPAY_APPID);
		
		return unionPayUPSDKParams;
	}
	
	public static Map<String, String> pay(Unionpay pay, String merId, JSONObject attach, String certPath, String certPwd) {
		Map<String, String> contentData = new HashMap<String, String>();

		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", UnionPayConstant.version); //版本号，全渠道默认值
		contentData.put("encoding", UnionPayConstant.encoding); //字符集编码，可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "01"); //交易类型 ，01：消费
		contentData.put("txnSubType", "01"); //交易子类型， 01：自助消费
		contentData.put("bizType", "000201"); //业务类型，B2C网关支付，手机wap支付
		contentData.put("channelType", "08"); //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机

		/***商户接入参数***/
		contentData.put("merId", merId); //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		contentData.put("accessType", "0"); //接入类型，0：商户直连接入 1：收单机构接入 2：平台商户接入
		//contentData.put("orderId", UnionPayConstant.getOrderId()); //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("orderId", pay.getOrderNo());
		contentData.put("txnTime", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")); //订单发送时间，取系统时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		//contentData.put("accType", "01");					 	      //账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		contentData.put("currencyCode", "156"); //交易币种（境内商户一般是156 人民币）		
		contentData.put("txnAmt", pay.getTotalFee()); //交易金额，单位分，不要带小数点
		contentData.put("orderDesc", pay.getBody()); // 订单描述   
		
		contentData.put("reqReserved", attach.toJSONString()); //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		

		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//contentData.put("frontUrl", DemoBase.frontUrl);

		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:
		//    1.需设置为外网能访问，否则收不到通知    
		//    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", UnionPayConstant.UNIONPAY_NOTIFY_URL);

		// 订单超时时间。
		// 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
		// 此时间建议取支付时的北京时间加15分钟。
		// 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(pay.getAgtTimeout(), UnionPayConstant.UNIONPAY_ORDER_TIMEOUT));
		contentData.put("payTimeout", DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyyMMddHHmmss"));

		Map<String, String> reqData;
		if (StringUtils.isNotBlank(certPath) && StringUtils.isNotBlank(certPwd)) {
			//多证书签名： 
			reqData = AcpService.signByCertInfo(contentData, certPath, certPwd, UnionPayConstant.encoding);
		} else {
			reqData = AcpService.sign(contentData, UnionPayConstant.encoding); //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		}
		
		String requestBackUrl = SDKConfig.getConfig().getAppRequestUrl(); //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, UnionPayConstant.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			
		return rspData;
	}
	
	
	public static UnionpayRefundResponse refund(String merId, String refundOrderNo, String refundFee, String agtOrderNo, JSONObject attach, String certPath, String certPwd) {
		UnionpayRefundResponse response = new UnionpayRefundResponse();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", UnionPayConstant.version);               //版本号
		data.put("encoding", UnionPayConstant.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "04");                           //交易类型 04-退货		
		data.put("txnSubType", "00");                        //交易子类型  默认00		
		data.put("bizType", "000201");                       //业务类型
		data.put("channelType", "08");                       //渠道类型，07-PC，08-手机		
		
		/***商户接入参数***/
		data.put("merId", merId);                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改		
		data.put("orderId", refundOrderNo);          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效		
		data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）		
		data.put("txnAmt", refundFee);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额		
		data.put("backUrl", UnionPayConstant.UNIONPAY_REFUND_NOTIFY_URL);               //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", agtOrderNo);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
		//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
		//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
		
		data.put("reqReserved", attach.toJSONString());
		
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		Map<String, String> reqData;
		if (StringUtils.isNotBlank(certPath) && StringUtils.isNotBlank(certPwd)) {
			//多证书签名： 
			reqData = AcpService.signByCertInfo(data, certPath, certPwd, UnionPayConstant.encoding);
		} else {
			reqData = AcpService.sign(data, UnionPayConstant.encoding); //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		}
		
		
		String requestBackUrl = SDKConfig.getConfig().getAppRequestUrl();									//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, UnionPayConstant.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionPayConstant.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					response = new UnionpayRefundResponse(BizConstant.METHOD_INVOKE_SUCCESS, "OK");
					
					response.setRefundState(TradeConstant.REFUND_STATE_SUCCESS);
					response.setRefundSuccessTime(DateUtil.formatDate(rspData.get("txnTime"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
					response.setAgtRefundOrderNo(rspData.get("queryId"));
					response.setAgtOrderNo(rspData.get("origQryId"));
				} else if(("03").equals(respCode)||
						  ("04").equals(respCode)||
						  ("05").equals(respCode)){
					//后续需发起交易状态查询交易确定交易状态
					response = new UnionpayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, rspData.get("respCode").concat(" : ".concat(rspData.get("respMsg"))));
				} else{
					response = new UnionpayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, rspData.get("respCode").concat(" : ".concat(rspData.get("respMsg"))));
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				response = new UnionpayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, "验证签名失败");
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			
			response = new UnionpayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, "退费失败，RefundResponse is null");
		}
		
		return response;
	}
	
	public static UnionpayOrderQueryResponse orderQuery(UnionpayOrderQuery ordreQuery, String merId, String certPath, String certPwd) throws ParseException {
		UnionpayOrderQueryResponse response = new UnionpayOrderQueryResponse();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", UnionPayConstant.version);         //版本号
		data.put("encoding", UnionPayConstant.encoding);       //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "00");                             //交易类型 00-默认
		data.put("txnSubType", "00");                          //交易子类型  默认00
		data.put("bizType", "000201");                         //业务类型 
		
		/***商户接入参数***/
		data.put("merId", merId);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改
		
		/***要调通交易以下字段必须修改***/
		//****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		if (StringUtils.isNotBlank(ordreQuery.getOrderNo())) {
			data.put("orderId", ordreQuery.getOrderNo()); 
			data.put("txnTime", OrderNoGenerator.getDateTimeString(ordreQuery.getOrderNo()));
		} else if (StringUtils.isNotBlank(ordreQuery.getAgtOrderNo())) {
			data.put("queryId", ordreQuery.getAgtOrderNo()); 
		} else if (StringUtils.isNotBlank(ordreQuery.getRefundOrderNo())) {
			data.put("orderId", ordreQuery.getRefundOrderNo());
			data.put("txnTime", OrderNoGenerator.getDateTimeString(ordreQuery.getRefundOrderNo()));
		} else if (StringUtils.isNotBlank(ordreQuery.getAgtRefundOrderNo())) {
			data.put("queryId", ordreQuery.getAgtRefundOrderNo()); 
		}
		
		//这里需要传入订单的下单时间
		//data.put("txnTime", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));  //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		
		Map<String, String> reqData;
		if (StringUtils.isNotBlank(certPath) && StringUtils.isNotBlank(certPwd)) {
			//多证书签名： 
			reqData = AcpService.signByCertInfo(data, certPath, certPwd, UnionPayConstant.encoding);
		} else {
			reqData = AcpService.sign(data, UnionPayConstant.encoding); //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		}
		
		String url = SDKConfig.getConfig().getSingleQueryUrl();	//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
		Map<String, String> rspData = AcpService.post(reqData, url, UnionPayConstant.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionPayConstant.encoding)){
				LogUtil.writeLog("验证签名成功");
				if(("00").equals(rspData.get("respCode"))){//如果查询交易成功
					String origRespCode = rspData.get("origRespCode");
					if(("00").equals(origRespCode)){
						//交易成功，更新商户订单状态
						response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_SUCCESS, "OK");
						
						response.setTotalFee(rspData.get("settleAmt"));
						response.setTraceNo(rspData.get("traceNo"));
						response.setTradeTime(DateUtil.formatDate(rspData.get("txnTime"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
						
						//response.setQueryId(rspData.get("queryId"));
						//response.setTradeState(origRespCode);
						
						if (StringUtils.isNotBlank(ordreQuery.getOrderNo())) {
							response.setTradeState(TradeConstant.TRADE_STATE_SUCCESS);
							response.setOrderNo(rspData.get("orderId"));
							response.setAgtOrderNo(rspData.get("queryId"));
						} else if (StringUtils.isNotBlank(ordreQuery.getRefundOrderNo())) {
							response.setTradeState(TradeConstant.TRADE_STATE_REFUND);
							response.setRefundOrderNo(rspData.get("orderId"));
							response.setAgtRefundOrderNo(rspData.get("queryId"));
						}
						
					}else if(("03").equals(origRespCode)||
							 ("04").equals(origRespCode)||
							 ("05").equals(origRespCode)){
						//订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
						//若收到respCode为“03、04、05”的应答时，则间隔（5分、10分、30分、60分、120分）发起交易查询
						response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, origRespCode.concat(" : ".concat(rspData.get("origRespMsg"))));
					}else{
						//其他应答码为交易失败
						response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, origRespCode.concat(" : ".concat(rspData.get("origRespMsg"))));
					}
				}else if(("34").equals(rspData.get("respCode"))){
					//订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
					response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, "订单不存在");
				}else{
					//查询交易本身失败，如应答码10/11检查查询报文是否正确
					response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, rspData.get("respCode").concat(" : ".concat(rspData.get("respMsg"))));
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, "验证签名失败");
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, "查询失败，OrderQueryResponse is null");
		}
		
		return response;
	}
	
	public static Map<String, String> qrPay(Unionpay pay, String merId, JSONObject attach, String certPath, String certPwd) {
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", UnionPayConstant.version);            //版本号 全渠道默认值
		contentData.put("encoding", UnionPayConstant.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "01");              		 	//交易类型 01:消费
		contentData.put("txnSubType", "07");           		 	//交易子类 07：申请消费二维码
		contentData.put("bizType", "000000");          		 	//填写000000
		contentData.put("channelType", "08");          		 	//渠道类型 08手机

		/***商户接入参数***/
		contentData.put("merId", merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		contentData.put("orderId", pay.getOrderNo());        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));//订单发送时间，取系统时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("txnAmt", pay.getTotalFee());						//交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		contentData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		contentData.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
		
		contentData.put("reqReserved", attach.toJSONString());
					
		//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", UnionPayConstant.UNIONPAY_NOTIFY_URL);
		
		// 订单超时时间。
		// 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
		// 此时间建议取支付时的北京时间加15分钟。
		// 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(pay.getAgtTimeout(), UnionPayConstant.UNIONPAY_ORDER_TIMEOUT));
		contentData.put("payTimeout", DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyyMMddHHmmss"));
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData;
		if (StringUtils.isNotBlank(certPath) && StringUtils.isNotBlank(certPwd)) {
			//多证书签名： 
			reqData = AcpService.signByCertInfo(contentData, certPath, certPwd, UnionPayConstant.encoding);
		} else {
			reqData = AcpService.sign(contentData, UnionPayConstant.encoding); //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		}
		
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestAppUrl, UnionPayConstant.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		return rspData;
	}
	
	public static UnionpayAsynResponse map2UnionpayAsynResponse(Map<String, String> asynResponseMap) throws ParseException {
		UnionpayAsynResponse response = new UnionpayAsynResponse();
		
		String respCode = asynResponseMap.get("respCode");
		//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
		if ("00".equals(respCode) || "A6".equals(respCode)) {
			response = new UnionpayAsynResponse(BizConstant.METHOD_INVOKE_SUCCESS, asynResponseMap.get("respMsg"));
			
			response.setTradeState(TradeConstant.TRADE_STATE_SUCCESS);
			response.setAttach(asynResponseMap.get("reqReserved"));
			response.setMchId(asynResponseMap.get("merId"));
			response.setOrderNo(asynResponseMap.get("orderId"));
			response.setAgtOrderNo(asynResponseMap.get("queryId"));
			response.setTotalFee(asynResponseMap.get("txnAmt"));
			response.setTradeTime(DateUtil.formatDate(asynResponseMap.get("txnTime"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
			
			response.setSettleDate(asynResponseMap.get("settleDate"));
			response.setTraceNo(asynResponseMap.get("traceNo"));
			response.setTraceTime(asynResponseMap.get("traceTime"));
		} else {
			response = new UnionpayAsynResponse(BizConstant.METHOD_INVOKE_FAILURE, asynResponseMap.get("respMsg"));
		}
		
		return response;
	}
	
	public static void main(String[] args) {
		String appId = "1e4adaa4e8164aa894c115ac84b6c718";
		String secret= "2f06f4a9715149fcb34ef25fd9842474";
		System.out.println(getBackendTokenByUnionPay(appId, secret));
	}
	
}
