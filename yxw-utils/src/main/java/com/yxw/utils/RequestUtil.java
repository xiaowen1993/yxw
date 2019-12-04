package com.yxw.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author homer.yang
 */
public class RequestUtil {
	
	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParamsByQueryString(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				String[] queryStringSplitArray = queryString.split("&");
				
				for (String item : queryStringSplitArray) {
					String[] itemSplitArray = item.split("=");
					String key = itemSplitArray[0];
					String value = "";
					if (itemSplitArray.length == 2) {
						value = itemSplitArray[1];
					} else if (itemSplitArray.length > 2) {
						itemSplitArray[0] = "";
						value = StringUtils.join(itemSplitArray, "");
					}
					params.put(key, value);
				}
			}
		}

		return params;
	}

	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			@SuppressWarnings("unchecked")
			Map<String, String[]> reqParams = request.getParameterMap();
			for (Map.Entry<String, String[]> entry : reqParams.entrySet()) {
				// System.out.println(entry.getKey() + "=" + StringUtils.join(entry.getValue(), ","));
				params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
			}
		}

		return params;
	}
	
	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParamsNotBlank(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			@SuppressWarnings("unchecked")
			Set<String> keys = request.getParameterMap().keySet();
			for (String key : keys) {
				String value = request.getParameter(key);
				if (StringUtils.isNotBlank(value)) {
					params.put(key, value);
				}
			}
		}

		return params;
	}

	/**
	 * 获取 request 输入流参数
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param charset
	 * @return String
	 */
	public static String getInputStreamAsString(HttpServletRequest request, String charset) {
		String s = "";

		try {
			ServletInputStream inStream = request.getInputStream();
			if (inStream == null)
				return null;
			
			s = IOUtils.toString(inStream, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	public static String getInputStreamAsString(HttpServletRequest request) {
		return getInputStreamAsString(request, "utf-8");
	}
	
	
	public static Map<String, String> getInputStreamAsMap(HttpServletRequest request) throws DocumentException {
		return xmlToMap(getInputStreamAsString(request, "utf-8"));
	}
	
	
	public static String mapToXml(Map<String, String> params) {
        Element xml = DocumentHelper.createElement("xml");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            xml.addElement(entry.getKey()).addCDATA(entry.getValue());
        }
        
        return xml.asXML();
    }
    
    public static Map<String, String> xmlToMap(Element root) {
        if (root == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        
        @SuppressWarnings("unchecked")
		List<Element> elsments = root.elements();
        for (Element element : elsments) {
            map.put(element.getName(), element.getText());
        }
        
        return map;
    }
    
    public static Map<String, String> xmlToMap(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        return xmlToMap(doc.getRootElement());
    }

}
