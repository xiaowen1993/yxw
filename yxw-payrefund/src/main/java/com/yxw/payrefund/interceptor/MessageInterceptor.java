package com.yxw.payrefund.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.common.WechatConstant;
import com.wechat.WechatUtil;
import com.yxw.payrefund.utils.RequestUtil;
import com.yxw.payrefund.utils.ResponseUtil;

public class MessageInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(MessageInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//signature=a65fb540ee52d932faf08adcf1e249f1f6ca8228&echostr=6272177949080016787&timestamp=1517540371&nonce=2971295814
		logger.info("message.notify... queryString: {}", request.getQueryString());
		
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
		
		String url = String.valueOf(request.getRequestURL());
		
		String appId = url.substring(url.lastIndexOf("/") + 1);
		logger.info("appId: {}", appId);
		
		String method = request.getMethod();// 调用方使用的HTTP方法
		
		if ("GET".equalsIgnoreCase(method)) {// 微信配置token时
            if (echostr != null) {
                if (signature.equalsIgnoreCase(WechatUtil.getSha1Sign(WechatConstant.WECHAT_COMPONENT_TOKEN, timestamp, nonce))) {
                		ResponseUtil.printPlain(response, echostr);
                    return false;
                }
            }
        } else {
	        	String dataString = RequestUtil.getInputStreamAsString(request);
	        	logger.info("wechat.message.notify.dataString：{}", dataString);
	        	
	        	if (StringUtils.isNotBlank(dataString)) {
	        		Document xmlDoc = DocumentHelper.parseText(dataString); // 把流数据转成XML
                String fromUserName = xmlDoc.valueOf("//FromUserName");  // 发送方微信号，若为普通用户，则是一个OpenID
                String toUserName = xmlDoc.valueOf("//ToUserName"); // 我的微信公共帐号ID
                String msgType = xmlDoc.valueOf("//MsgType");
                if (WechatConstant.WECHAT_MSGTYPE_EVENT.equals(msgType)) {
                		String event = xmlDoc.valueOf("MsgType");
                		ResponseUtil.printPlain(response, buildTextMsg(event.concat("from_callback"), fromUserName, toUserName));
                } else if (WechatConstant.WECHAT_MSGTYPE_TEXT.equals(msgType)) {
                		String content = xmlDoc.valueOf("//Content");
                		ResponseUtil.printPlain(response, buildTextMsg(content.concat("_callback"), fromUserName, toUserName));
                }
	        		return false;
	        	}
        }
		
		
		ResponseUtil.printPlain(response, "success");
		return false;
		
	}
	
	public String buildTextMsg(String content, String toUser, String fromUser) {
        Element retXML = DocumentHelper.createElement("xml");//定义返回的xml
        retXML.addElement("MsgType").setText("text");
        retXML.addElement("Content").addCDATA(content);
        retXML.addElement("ToUserName").setText(toUser);
        retXML.addElement("FromUserName").setText(fromUser);
        retXML.addElement("CreateTime").setText(System.currentTimeMillis()+"");
        
        System.out.println(retXML.asXML());
        return retXML.asXML();
    }
}
