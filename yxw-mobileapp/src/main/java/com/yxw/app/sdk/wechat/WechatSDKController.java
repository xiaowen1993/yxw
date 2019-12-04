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
package com.yxw.app.sdk.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.yxw.app.sdk.util.MessageUtil;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;

/**
 * 微信service controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月16日
 */

@Controller
@RequestMapping("/sdk/wechat")
public class WechatSDKController {

	private static Logger logger = LoggerFactory.getLogger(WechatSDKController.class);

	//	@Autowired
	//	private WhiteListCache whiteListCache;

	// 根据appId获取医院信息VO
	public static Map<String, HospIdAndAppSecretVo> hospitalAppSecretMap = new HashMap<String, HospIdAndAppSecretVo>();

	/**
	 * 处理微信发来的请求及事件推送
	 * 
	 * @throws IOException
	 * */
	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public void main(HttpServletRequest request, HttpServletResponse response) {

		String respMsg = processRequest(request, response);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(respMsg);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 处理请求
	 * */
	public String processRequest(HttpServletRequest request, HttpServletResponse response) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 文本消息内容
			String content = requestMap.get("Content");
			if (logger.isDebugEnabled()) {

				logger.debug("WechatSDKController requestMap------" + JSONObject.toJSONString(requestMap));

				logger.debug("WechatSDKController content------" + content);
				logger.debug("WechatSDKController:fromUserName----" + fromUserName);
				logger.debug("WechatSDKController toUserName-----" + toUserName);
				logger.debug("WechatSDKController msgType------" + msgType);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("WechatSDKController respMessage------" + respMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * 网址接入 确认请求来自微信服务器
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void accessURL(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		String token = "yxwtoken";

		if (logger.isDebugEnabled()) {
			logger.debug("the value of token is:" + token);
		}
		String[] strSet = new String[] { token, timestamp, nonce };
		java.util.Arrays.sort(strSet);
		String total = "";
		for (String string : strSet)
			total = total + string;
		// SHA-1加密实例
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(total.getBytes());
		byte[] codedBytes = sha1.digest();
		String codedString = new BigInteger(1, codedBytes).toString(16);// 将加密后的字节数组转换成字符串。参见http://hi.baidu.com/aotori/item/c94813c4f15caa60f6c95d4a
		if (codedString.equals(signature)) // 将加密的结果与请求参数中的signature比对，如果相同，原样返回echostr参数内容
		{
			PrintWriter out = response.getWriter();
			out.println(echostr);
			out.close();
		}
	}

	public int generateRandom(int b) {
		int temp = 0;
		try {
			if (0 > b) {
				temp = new Random().nextInt(0 - b);
				return temp + b;
			} else {
				temp = new Random().nextInt(b - 0);
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

}
