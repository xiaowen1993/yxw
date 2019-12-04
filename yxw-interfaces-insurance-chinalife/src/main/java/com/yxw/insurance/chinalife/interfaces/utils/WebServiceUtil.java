package com.yxw.insurance.chinalife.interfaces.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yxw.insurance.chinalife.interfaces.constants.CommonConstant;

public class WebServiceUtil {
	
	public static final String namespace = "http://www.e-chinalife.com/soa/";
	private static Map<String, String> reqHearderMap = new HashMap<String, String>();
	
	private static String PREFIX = "soa";
	
	static {
		reqHearderMap.put("Content-Type", "text/xml;charset=utf-8");
	}
	
	public static String invoke(String data) throws DocumentException {
		return invoke(data, null);
	}
	
	public static String invoke(String data, String url) throws DocumentException {
		if (StringUtils.isBlank(url)) {
			url = CommonConstant.WS_URL;
		}
		
		String methodName = "execute";
		Element body = DocumentHelper.createElement(PREFIX + SoapUtil.SPLIT_CHAR + methodName);
		
		Element xml = body.addElement("requestData");
		//Element xml = DocumentHelper.createElement(PREFIX + SoapUtil.SPLIT_CHAR + "requestData");
		//body.add(xml);
		
		//xml.addCDATA(data);
		xml.setText(data);
		
		//System.out.println(body.asXML());

		String soapString = SoapUtil.genSoapRequestString(PREFIX, namespace, body);
		System.out.println("soapString: " + soapString);
		
		String[] postRes = HTTPUtils.httpPostInvoke(url, null, reqHearderMap, soapString, "utf-8");
		System.out.println(postRes[0] + " - " + postRes[1]);

		if ("200".equals(postRes[0])) {
			System.out.println( postRes[1] );
			
			Element resRoot = DocumentHelper.parseText(postRes[1]).getRootElement();
			return resRoot.element("Body").element(methodName.concat("Response")).elementText("return");
		}

		return null;
	}

}
