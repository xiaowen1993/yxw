package com.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.common.AlipayConstant;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.AlipayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.sign.RSA;
import com.yxw.payrefund.utils.DateUtil;
import com.yxw.payrefund.utils.OrderUtil;
import com.yxw.payrefund.utils.httpClient.HttpProtocolHandler;
import com.yxw.payrefund.utils.httpClient.HttpRequest;
import com.yxw.payrefund.utils.httpClient.HttpResponse;
import com.yxw.payrefund.utils.httpClient.HttpResultType;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年7月1日
 */
public class AlipayUtil {

	// 参数编码字符集
	public static final String CHARSET = "utf-8";

	private static String debugStr = AlipayUtil.class.getName();

	private static Logger logger = LoggerFactory.getLogger(AlipayUtil.class);

	/**
	 * 生成支付宝签名
	 * 
	 * @param params 需要签名的参数
	 * @param key 密钥
	 * @param charset 参数编码字符集
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5Sign(Map<String, Object> params, String key, String charset) throws UnsupportedEncodingException {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuffer sign = new StringBuffer();
		for (String str : keys) {
			String value = params.get(str).toString();
			if (value != null && !"".equals(value)) {
				sign.append(str).append("=").append(params.get(str)).append("&");
			}
		}
		sign.deleteCharAt(sign.length() - 1);
		sign.append(key);

		return DigestUtils.md5Hex(sign.toString().getBytes(charset));
	}

	/**
	 * 验证支付宝签名
	 * 
	 * @param params 需要验证签名的参数
	 * @param sign 支付宝传过来的签名
	 * @param key 密钥
	 * @param charset 参数编码字符集
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkMD5Sign(Map<String, Object> params, String key, String charset) throws UnsupportedEncodingException {
		String sign = String.valueOf(params.remove("sign"));
		params.remove("sign_type");
		return getMD5Sign(params, key, charset).equals(sign);
	}

	/**
	 * 支付宝验证 RSA 签名
	 * 1.将支付宝返回的POST参数(不包含sign参数)做字母排序，组成query类型的字符串
	 * 2.将签名参数使用base64解码为字节码串
	 * 3.使用OpenSSL的验签方法及支付宝公钥验证签名
	 * 
	 * @param params 支付宝 POST 参数
	 * @param alipayPublicKey 支付宝公钥
	 * @param charset 编码
	 */
	public static boolean checkRSASign(Map<String, String> params, String alipayPublicKey, String charset) {
		String sign = params.remove("sign");

		String content = AlipayCore.createLinkString(params);
		return RSA.verify(content, sign, alipayPublicKey, charset);
	}

	/**
	 * 调用支付宝查询订单接口
	 */
	public static Map<String, Object> getOrderQueryParams(String partner, String key, String tradeNo, String outTradeNo)
			throws UnsupportedEncodingException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("service", "single_trade_query");
		params.put("partner", partner);
		params.put("trade_no", tradeNo);
		params.put("out_trade_no", outTradeNo);
		params.put("_input_charset", CHARSET);
		params.put("sign", getMD5Sign(params, key, CHARSET));
		params.put("sign_type", "MD5");

		return params;
	}

	/**
	 * 获取授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @param scope auth_base/auth_userinfo
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl, String scope) throws UnsupportedEncodingException {
		StringBuilder oauth2Url = new StringBuilder();
		oauth2Url.append(AlipayConstant.ALIPAY_OPENAUTH_GATEWAY).append("/oauth2/publicAppAuthorize.htm?auth_skip=false&scope=")
				.append(scope).append("&app_id=").append(appId).append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "utf-8"));
		return oauth2Url.toString();
	}

	/**
	 * Alipay 授权跳转后获取账户信息
	 * 
	 * @param privateKey
	 *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
	 * @param authCode
	 *            授权跳转后用request.getParameter("auth_code")取到
	 * @return AlipayUserUserinfoShareResponse
	 */
	public static AlipayUserUserinfoShareResponse getUserInfo(String appId, String privateKey, String alipayPublicKey, String authCode) {
		logger.debug("{}.getUserInfo.authCode: {}", debugStr, authCode);
		AlipayUserUserinfoShareResponse userinfoShareResponse = null;
		try {
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType("authorization_code");
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			// logger.info(oauthTokenResponse.getBody());
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				logger.debug("{}.getUserInfo.AccessToken: {}", debugStr, oauthTokenResponse.getAccessToken());
				// 利用authToken获取用户信息
				AlipayUserUserinfoShareRequest userinfoShareRequest = new AlipayUserUserinfoShareRequest();
				userinfoShareResponse = alipayClient.execute(userinfoShareRequest, oauthTokenResponse.getAccessToken());
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		return userinfoShareResponse;
	}

	/**
	 * Alipay 授权跳转后获取OpenId
	 * 
	 * @param privateKey
	 *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
	 * @param authCode
	 *            授权跳转后用request.getParameter("auth_code")取到
	 * @return alipay_system_oauth_token_response.alipay_user_id
	 */
	public static String getOpenId(String appId, String privateKey, String authCode) {
		logger.debug("{}.getOpenId.authCode: {}", debugStr, authCode);
		String openId = "";
		try {
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType("authorization_code");
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);

			logger.debug("{}.getOpenId.oauthTokenResponse.body: {}", debugStr, oauthTokenResponse.getBody());
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				//用户的open_id（ 已废弃，请勿使用 ）
				//openId = oauthTokenResponse.getAlipayUserId();

				//支付宝用户的唯一userId 示例: 2088411964574197
				openId = oauthTokenResponse.getUserId();
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		logger.debug("{}.getOpenId.return: {}", debugStr, openId);
		return openId;
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
	 * 如：buildRequest("", "",sParaTemp)
	 * @param ALIPAY_GATEWAY 支付宝网关地址
	 * @param sParaTemp 请求参数数组
	 * @return 支付宝处理结果
	 */
	public static String buildRequest(String ALIPAY_GATEWAY, Map<String, String> sParaTemp, String privateKey) throws Exception {
		//待请求参数数组
		Map<String, String> sPara = buildRequestParaRSA(sParaTemp, privateKey);
		logger.info("sPara=" + sPara);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.INPUT_STREAM);
		//设置编码集
		request.setCharset(AlipayConstant.INPUT_CHARSET);
		request.setParameters(sPara);
		request.setUrl(AlipayConstant.ALIPAY_WAPPAY_GATEWAY + "?_input_charset=" + AlipayConstant.INPUT_CHARSET);

		HttpResponse response = httpProtocolHandler.execute(request, "");
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * @param sParaTemp 请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private static Map<String, String> buildRequestParaRSA(Map<String, String> sParaTemp, String privateKey) {
		//除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		//生成签名结果
		String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = RSA.sign(prestr, privateKey, AlipayConstant.INPUT_CHARSET);

		//签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		if (!sPara.get("service").equals("alipay.wap.trade.create.direct")
				&& !sPara.get("service").equals("alipay.wap.auth.authAndExecute")) {
			sPara.put("sign_type", AlipayConstant.SIGN_TYPE_RSA_0001);
		} else {
			sPara.put("sign_type", AlipayConstant.SIGN_TYPE_RSA);
		}

		return sPara;
	}

	/**
	 * 解析远程模拟提交后返回的信息，获得token
	 * @param text 要解析的字符串
	 * @return 解析结果
	 * @throws Exception 
	 */
	public static String getRequestTokenRSA(String text, String privateKey) throws Exception {
		String request_token = "";
		//以“&”字符切割字符串
		String[] strSplitText = text.split("&");
		//把切割后的字符串数组变成变量与数值组合的字典数组
		Map<String, String> paraText = new HashMap<String, String>();
		for (int i = 0; i < strSplitText.length; i++) {

			//获得第一个=字符的位置
			int nPos = strSplitText[i].indexOf("=");
			//获得字符串长度
			int nLen = strSplitText[i].length();
			//获得变量名
			String strKey = strSplitText[i].substring(0, nPos);
			//获得数值
			String strValue = strSplitText[i].substring(nPos + 1, nLen);
			//放入MAP类中
			paraText.put(strKey, strValue);
		}

		if (paraText.get("res_data") != null) {
			String res_data = paraText.get("res_data");
			//解析加密部分字符串（RSA与MD5区别仅此一句）
			res_data = RSA.decrypt(res_data, privateKey, AlipayConstant.INPUT_CHARSET);

			//token从res_data中解析出来（也就是说res_data中已经包含token的内容）
			Document document = DocumentHelper.parseText(res_data);
			request_token = document.selectSingleNode("//direct_trade_create_res/request_token").getText();
		}
		return request_token;
	}

	public static Map<String, String> getMPayFormParams(HospitalCodeAndAppVo vo, Alipay pay) {
		JSONObject attach = new JSONObject();
		attach.put("objectCode", vo.getHospitalCode());
		attach.put("tradeMode", vo.getTradeMode());

		return getMPayFormParams(vo.getMchId(), vo.getMchId(), pay.getOrderNo(), pay.getTotalFee(), pay.getBody(), pay.getAgtTimeout(),
				pay.getMerchantUrl(), pay.getPaySuccessPageUrl(), attach, vo.getAliPrivateKey());
	}

	/**
	 * 支付宝 手机网站支付 提交表单参数
	 * @param partner 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	 * @param sellerId 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	 * @param notifyUrl 服务器异步通知页面路径
	 */
	public static Map<String, String> getMPayFormParams(String partner, String sellerId, String orderNo, String totalFee, String body,
			String timeout, String showUrl, String returnUrl, JSONObject attach, String privateKey) {
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("service", "alipay.wap.create.direct.pay.by.user");
		formParams.put("partner", partner);
		formParams.put("seller_id", sellerId);
		formParams.put("_input_charset", AlipayConstant.INPUT_CHARSET);
		formParams.put("payment_type", AlipayConstant.PAYMENT_TYPE);
		formParams.put("notify_url", AlipayConstant.ALIPAY_NOTIFY_URL);
		formParams.put("out_trade_no", orderNo);
		formParams.put("subject", body);
		formParams.put("total_fee", AlipayUtil.f2y(totalFee));
		//用户付款中途退出返回商户网站的地址。
		formParams.put("show_url", showUrl);
		formParams.put("return_url", returnUrl);
		formParams.put("app_pay", "Y"); //启用此参数可唤起钱包APP支付。

		//设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。
		//取值范围：1m～15d。
		//m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
		//该参数数值不接受小数点，如1.5h，可转换为90m。
		//当用户输入支付密码、点击确认付款后（即创建支付宝交易后）开始计时。
		//支持绝对超时时间，格式为yyyy-MM-dd HH:mm。
		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(timeout, AlipayConstant.ALIPAY_ORDER_TIMEOUT));
		formParams.put("it_b_pay", DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyy-MM-dd HH:mm"));

		//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
		if (attach != null) {
			//formParams.put("passback_params", attach.toJSONString());
			formParams.put("body", attach.toJSONString());
		}

		formParams = buildRequestParaRSA(formParams, privateKey);

		return formParams;
	}

	public static String getPayFormHtml(HospitalCodeAndAppVo vo, Alipay pay) {
		JSONObject attach = new JSONObject();
		attach.put("objectCode", vo.getHospitalCode());
		attach.put("tradeMode", vo.getTradeMode());

		return getPayFormHtml(vo.getAppId(), vo.getAliPrivateKey(), pay.getOrderNo(), pay.getBody(), pay.getTotalFee(), pay.getBody(),
				pay.getAgtTimeout(), pay.getMerchantUrl(), pay.getPaySuccessPageUrl(), attach);
	}

	public static String getPayFormHtml(String appId, String privateKey, String orderNo, String subject, String totalFee, String body,
			String timeout, String quitUrl, String returnUrl, JSONObject attach) {

		AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(orderNo);
		model.setSubject(subject);
		model.setTotalAmount(AlipayUtil.f2y(totalFee));
		model.setBody(body);

		//绝对超时时间，格式为yyyy-MM-dd HH:mm。 
		//注：1）以支付宝系统时间为准；2）如果和timeout_express参数同时传入，以time_expire为准。
		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(timeout, AlipayConstant.ALIPAY_ORDER_TIMEOUT));
		model.setTimeExpire(DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyy-MM-dd HH:mm"));

		model.setProductCode(AlipayConstant.ALIPAY_PRODUCT_CODE);
		model.setPassbackParams(attach.toJSONString());
		//添加该参数后在h5支付收银台会出现返回按钮，可用于用户付款中途退出并返回到该参数指定的商户网站地址。
		//注：该参数对支付宝钱包标准收银台下的跳转不生效。
		model.setQuitUrl(quitUrl);

		alipayTradeWapPayRequest.setBizModel(model);
		// 设置异步通知地址
		alipayTradeWapPayRequest.setNotifyUrl(AlipayConstant.ALIPAY_NOTIFY_URL);
		// 设置同步地址
		alipayTradeWapPayRequest.setReturnUrl(returnUrl);

		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
			//AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClientRSA2(appId, privateKey);

			form = alipayClient.pageExecute(alipayTradeWapPayRequest).getBody();

			if (StringUtils.isNotBlank(form)) {
				//document.forms[0].submit();
				form = form.replace("document.forms[0].submit();", "");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return form;
	}

	/**
	 * 扫码支付下单
	 * 
	 */
	public static AlipayTradePrecreateResponse tradePrecreate(String appId, String privateKey, JSONObject attach, Alipay pay) {
		return tradePrecreate(appId, privateKey, pay.getOrderNo(), pay.getTotalFee(), pay.getBody(), pay.getAgtTimeout(), attach, null,
				null, null);
	}

	/**
	 * 扫码支付下单
	 * 
	 */
	public static AlipayTradePrecreateResponse tradePrecreate(String appId, String privateKey, String orderNo, String totalFee,
			String body, String timeout, JSONObject attach, JSONArray goodsDetail, String operatorId, JSONObject extendParams) {

		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(timeout, AlipayConstant.ALIPAY_ORDER_TIMEOUT));

		JSONObject bizContentJson = new JSONObject();

		// 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
		bizContentJson.put("out_trade_no", orderNo);

		// 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
		// 如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入；
		// 如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】

		bizContentJson.put("total_amount", AlipayUtil.f2y(totalFee));

		// 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
		// 如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
		// bizContentJson.put("discountable_amount", "0.00");

		// 不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
		// 如果该值未传入，但传入了【订单总金额】和【可打折金额】，则该值默认为【订单总金额】-【可打折金额】
		// bizContentJson.put("undiscountable_amount", total_amount);

		// 订单标题
		bizContentJson.put("subject", body);

		// 对交易或商品的描述, 支付宝无原样返回数据，暂用 body 代替
		bizContentJson.put("body", attach.toJSONString());

		//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
		if (attach != null) {
			bizContentJson.put("passback_params", attach.toJSONString());
		}

		// 订单包含的商品列表信息，Json格式，其它说明详见
		// 详细字段：
		// goods_id 商品编号
		// goods_name 商品名称
		// quantity 商品数量
		// price 商品单价
		// body 商品描述

		// JSONArray goods_detail_ja = new JSONArray();
		//
		// JSONObject goods_detail1 = new JSONObject();
		// goods_detail1.put("goods_id", "test-apple-01");
		// goods_detail1.put("goods_name", "iPhone6s玫瑰金");
		// goods_detail1.put("quantity", "1");
		// goods_detail1.put("price", "0.01");
		// goods_detail1.put("body", "走过路过，千万不要错过");
		// goods_detail_ja.add(goods_detail1);

		if (goodsDetail != null) {
			bizContentJson.put("goods_detail", goodsDetail);
		}

		// 商户操作员编号，可选参数，此处以我们的业务可填写就诊卡
		if (StringUtils.isNotBlank(operatorId)) {
			bizContentJson.put("operator_id", operatorId);
		}

		// 商户门店编号，可选参数
		// bizContentJson.put("store_id", "");
		// 机具终端编号，可选参数
		// bizContentJson.put("terminal_id", "");
		// 业务扩展参数，可选参数
		if (extendParams != null) {
			bizContentJson.put("extend_params", extendParams);
		}

		// 该笔订单允许的最晚付款时间，逾期将关闭交易。格式为：yyyy-MM-dd HH:mm:ss
		bizContentJson.put("time_expire", DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyy-MM-dd HH:mm:ss"));

		//timeout_express（新参数，当发现就参数time_expire不能用时再改吧）
		//该笔订单允许的最晚付款时间，逾期将关闭交易。
		//取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		//bizContentJson.put("timeout_express", "");

		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);

		// 使用SDK，构建群发请求模型
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(bizContentJson.toJSONString());
		request.setNotifyUrl(AlipayConstant.ALIPAY_NOTIFY_URL);
		// request.putOtherTextParam("ws_service_url", "");
		AlipayTradePrecreateResponse response = null;
		try {
			logger.info(request.getTextParams().toString());
			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			logger.info(response.getBody());
			logger.info("response.isSuccess(): {}", response.isSuccess());
			logger.info("response.getMsg(): {}", response.getMsg());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					logger.info("商户订单号：{}", response.getOutTradeNo());
					logger.info("二维码值：{}", response.getQrCode());// 商户将此二维码值生成二维码，然后展示给用户，用户用支付宝手机钱包扫码完成支付
					// 二维码的生成，网上有许多开源方法，可以参看：http://blog.csdn.net/feiyu84/article/details/9089497

				} else {

					// 打印错误码
					logger.info("错误码：{}", response.getSubCode());
					logger.info("错误描述：{}", response.getSubMsg());
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return response;
	}

	public static Map<String, String> genRefundParams(AlipayRefund refund) {
		Map<String, String> params = new HashMap<>();

		//out_trade_no | trade_no 二选一
		if (StringUtils.isNotBlank(refund.getOrderNo())) {
			params.put("out_trade_no", refund.getOrderNo());
		}
		if (StringUtils.isNotBlank(refund.getAgtOrderNo())) {
			params.put("trade_no", refund.getAgtOrderNo());
		}

		params.put("refund_amount", f2y(refund.getRefundFee()));
		params.put("refund_reason", refund.getRefundDesc());
		//可选
		//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
		if (StringUtils.isNotBlank(refund.getRequestNo())) {
			params.put("out_request_no", refund.getRequestNo());
		}

		return params;

	}

	public static AlipayRefundResponse tradeRefund(String appId, String privateKey, Map<String, String> params) {
		AlipayRefundResponse refundResponse = new AlipayRefundResponse();

		try {
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setNotifyUrl(AlipayConstant.ALIPAY_REFUND_NOTIFY_URL);

			String bizContent = JSONObject.toJSONString(params);
			logger.info("trade.refund bizContent:" + bizContent);

			request.setBizContent(bizContent);

			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
			AlipayTradeRefundResponse response = alipayClient.execute(request);

			if (response != null) {
				if (response.isSuccess()) {
					if ("10000".equals(response.getCode())) {
						refundResponse = new AlipayRefundResponse(BizConstant.METHOD_INVOKE_SUCCESS, "OK");

						logger.info("fund_change: {}, refund_fee: {}, gmt_refund_pay: {}", response.getFundChange(),
								response.getRefundFee(), response.getGmtRefundPay());
						refundResponse.setFundChange(response.getFundChange());
						//refundResponse.setRefundFee(y2f(response.getRefundFee()));
						refundResponse.setRefundSuccessTime(DateUtil.formatDate(response.getGmtRefundPay(), "yyyy-MM-dd HH:mm:ss"));
						refundResponse.setBuyerUserId(response.getBuyerUserId());
						refundResponse.setBuyerLogonId(response.getBuyerLogonId());
					} else {
						refundResponse =
								new AlipayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, response.getSubCode().concat(
										" : ".concat(response.getSubMsg())));
					}
				} else {
					refundResponse =
							new AlipayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, response.getCode().concat(
									" : ".concat(response.getMsg())));
				}
			} else {
				refundResponse = new AlipayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, "退费失败，AlipayTradeRefundResponse is null");
			}

		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();

			refundResponse = new AlipayRefundResponse(BizConstant.METHOD_INVOKE_EXCEPTION, "退费异常：".concat(alipayApiException.getMessage()));
		}

		return refundResponse;
	}

	public static AlipayOrderQueryResponse tradeQuery(String appId, String privateKey, String agtOrderNo, String orderNo) {

		AlipayOrderQueryResponse queryResponse = new AlipayOrderQueryResponse();

		try {
			JSONObject bizContent = new JSONObject();
			// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
			bizContent.put("out_trade_no", orderNo);
			// 支付宝交易号，和商户订单号不能同时为空
			bizContent.put("trade_no", agtOrderNo);
			logger.info("tradeQuery.bizContent: {}", bizContent.toJSONString());

			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent(bizContent.toJSONString());
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			logger.info("AlipayTradeQueryResponse: {}", JSONObject.toJSONString(response));

			if (response != null) {
				if (response.isSuccess()) {
					if ("10000".equals(response.getCode())) {
						queryResponse = new AlipayOrderQueryResponse(BizConstant.METHOD_INVOKE_SUCCESS, "OK");

						logger.info("trade_no: {}, out_trade_no: {}", response.getTradeNo(), response.getOutTradeNo());
						queryResponse.setAgtOrderNo(response.getTradeNo());
						queryResponse.setOrderNo(response.getOutTradeNo());
						queryResponse.setTradeState(alipayTradeStatus2TradeState(response.getTradeStatus()));
						queryResponse.setTotalFee(y2f(response.getTotalAmount()));
						queryResponse.setTradeTime(DateUtil.formatDate(response.getSendPayDate(), "yyyy-MM-dd HH:mm:ss"));
						queryResponse.setOpenId(response.getBuyerUserId());
						queryResponse.setBuyerLogonId(response.getBuyerLogonId());
						queryResponse.setReceiptAmount(y2f(response.getReceiptAmount()));
					} else {
						queryResponse =
								new AlipayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, response.getSubCode().concat(
										" : ".concat(response.getSubMsg())));
					}
				} else {
					queryResponse =
							new AlipayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, response.getCode().concat(
									" : ".concat(response.getMsg())));
				}
			} else {
				queryResponse = new AlipayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, "查询失败，AlipayTradeQueryResponse is null");
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
			queryResponse =
					new AlipayOrderQueryResponse(BizConstant.METHOD_INVOKE_EXCEPTION, "查询异常：".concat(alipayApiException.getMessage()));
		}
		return queryResponse;
	}

	public static AlipayAsynResponse map2AlipayAsynResponse(Map<String, String> asynResponseMap) {

		AlipayAsynResponse response = new AlipayAsynResponse(BizConstant.METHOD_INVOKE_SUCCESS, "OK");

		response.setTradeState(alipayTradeStatus2TradeState(asynResponseMap.get("trade_status")));
		response.setAttach(OrderUtil.ifBlank(asynResponseMap.get("passback_params"), asynResponseMap.get("body"), "{}"));
		response.setMchId(asynResponseMap.get("seller_id"));
		response.setOrderNo(asynResponseMap.get("out_trade_no"));
		response.setAgtOrderNo(asynResponseMap.get("trade_no"));
		response.setTotalFee(y2f(OrderUtil.ifBlank(asynResponseMap.get("total_amount"), asynResponseMap.get("total_fee"))));
		response.setTradeTime(asynResponseMap.get("gmt_payment"));

		response.setAppId(asynResponseMap.get("app_id"));
		response.setOpenId(asynResponseMap.get("buyer_id"));
		response.setBuyerLogonId(OrderUtil.ifBlank(asynResponseMap.get("buyer_logon_id"), asynResponseMap.get("buyer_email")));
		response.setSellerEmail(asynResponseMap.get("seller_email"));
		response.setSubject(asynResponseMap.get("subject"));
		response.setBody(asynResponseMap.get("body"));

		return response;
	}

	public static String alipayTradeStatus2TradeState(String tradeState) {
		//trade_status
		//WAIT_BUYER_PAY（交易创建，等待买家付款）
		//TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
		//TRADE_SUCCESS（交易支付成功）
		//TRADE_FINISHED（交易结束，不可退款）

		String returnTradeState = "";

		if ("TRADE_SUCCESS".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_SUCCESS;
		} else if ("TRADE_CLOSED".equals(tradeState)) { // 因为不存在 未付款交易超时关闭，我们自己系统有15分左右的超时后不能支付的逻辑
			returnTradeState = TradeConstant.TRADE_STATE_REFUND;
		} else if ("WAIT_BUYER_PAY".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_NOTPAY;
		} else if ("TRADE_FINISHED".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_CLOSED;
		} else {
			returnTradeState = TradeConstant.TRADE_STATE_EXCEPTION;
		}

		return returnTradeState;
	}

	/**
	 * 分转元
	 */
	public static String f2y(String totalFee) {
		//分换元
		//1.确保有3位数
		totalFee = StringUtils.leftPad(totalFee, 3, '0');
		//2.加上小数点
		int lengthSubtract2 = totalFee.length() - 2;
		totalFee = totalFee.substring(0, lengthSubtract2).concat(".").concat(totalFee.substring(lengthSubtract2));

		return totalFee;
	}

	/**
	 * 元转分
	 */
	public static String y2f(String totalFee) {
		DecimalFormat df = new DecimalFormat("0");
		return df.format(Double.valueOf(totalFee) * 100);
	}

}
