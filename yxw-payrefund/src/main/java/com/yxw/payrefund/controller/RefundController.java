package com.yxw.payrefund.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.yxw.payrefund.utils.RequestUtil;
import com.yxw.payrefund.utils.ResponseUtil;

@Controller
@RequestMapping(value = "/refund")
public class RefundController {

	private static Logger logger = LoggerFactory.getLogger(RefundController.class);

	//    private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	//    private RefundService refundService = SpringContextHolder.getBean(RefundServiceImpl.class);

	/**
	 * 退费回调(微信)
	 * 
	 *  当商户申请的退款有结果后，微信会把相关结果发送给商户，商户需要接收处理，并返回应答。 
		对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。 （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒） 
		注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。 
		推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。 
		特别说明：退款结果对重要的数据进行了加密，商户需要用商户证书与商户秘钥进行解密后才能获得结果通知的内容
		
		解密步骤如下： 
		第一步，对商户密钥key进行MD5加密，得到32位小写加密串StringA
		key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 
		第二步，使用StringA作为key，对参数加密串进行AES-256（ECB模式，PKCS7Padding）解密，得到加密前参数。
		
		开通该功能需要在商户平台-交易中心-退款配置中配置notify_url。 
		如果链接无法访问，商户将无法接收到微信通知。 
		通知url必须为直接可访问的url，不能携带参数。示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay.action”
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wechatNotify")
	public void wechatNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> data = RequestUtil.getInputStreamAsMap(request);
		logger.info("refund.wechatNotify.data：{}", data);

		//暂时不做处理
		ResponseUtil.printXml(response, "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");

	}

	/**
	 * 退费回调(支付宝)
	 * 新的支付宝退费接口没有退费回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> data = RequestUtil.getRequestParams(request);
		logger.info("refund.alipayNotify.data：{}", data);
		try {
			// 暂时不做处理

			String result = "success";
			logger.info("回复支付宝结果:{}.", result);
			ResponseUtil.print(response, result);
		} catch (Exception e) {
			logger.error("处理支付宝退费回调异常：{}", e.getMessage());
			ResponseUtil.print(response, "fail");
		}
	}

	/**
	 * 退费回调(银联钱包)
	 * 跟同步返回的结果一样，无需处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/unionpayNotify")
	public void unionpayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogUtil.writeLog("refund.unionpayNotify 接收后台通知开始");

		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> data = RequestUtil.getRequestParamsNotBlank(request);

		logger.info("refund.unionpayNotify.data：{}", data);

		String encoding = request.getParameter(SDKConstants.param_encoding);
		Map<String, String> valideData = new HashMap<>();
		if (null != data && !data.isEmpty()) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				String value = new String(entry.getValue().getBytes(encoding), encoding);
				valideData.put(entry.getKey(), value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题

		} else {
			LogUtil.writeLog("验证签名结果[成功].");

			//跟同步返回的结果一样，无需处理
			ResponseUtil.print(response, "ok");
		}
		LogUtil.writeLog("refund.unionPayNotify 接收后台通知结束");
	}

}
