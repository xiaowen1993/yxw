package com.yxw.platform.sdk.wechat.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

/**
 * @Package: com.yxw.platform.payrefund.utils
 * @ClassName: HTTPClient.java
 * @Statement: <p>
 *             http请求类
 *             </p>
 * @JDK version used: 1.6
 * @Author: zhoujb
 * @Create Date: 2015年5月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HTTPClient {

	/**
	 * HTTP_POST
	 * @param url URL
	 * @param map 参数名:key,参数值:value
	 * @param reqStr 无参数名传递参数
	 * @param charset 编码
	 * @return String[]([0]=HttpStatus, [1]=返回值)
	 */
	public static String[] http_post(String url, java.util.Map<String, String> map, String reqStr, String charset) {
		return http_post(url, map, null, reqStr, charset);
	}

	/**
	 * HTTP_POST
	 * @param url URL
	 * @param map 参数名:key,参数值:value
	 * @param repHearderMap 请求头参数 (如：repHearderMap.put("Content-Type", "text/xml;charset=utf-8");)
	 * @param reqStr 无参数名传递参数
	 * @param charset 编码
	 * @return String[]([0]=HttpStatus, [1]=返回值)
	 */
	public static String[] http_post(String url, java.util.Map<String, String> map, java.util.Map<String, String> repHearderMap, String reqStr,
			String charset) {
		String ret_array[] = new String[2];
		org.apache.commons.httpclient.HttpClient client = null;
		org.apache.commons.httpclient.methods.PostMethod method = null;

		try {
			client = new org.apache.commons.httpclient.HttpClient();
			method = new org.apache.commons.httpclient.methods.PostMethod(url);
			method.setRequestHeader("Connection", "close");

			if (repHearderMap != null) {
				java.util.Set<String> keys = repHearderMap.keySet();
				for (String k : keys) {
					method.setRequestHeader(k, repHearderMap.get(k));
				}
			}

			if (org.apache.commons.lang.StringUtils.isBlank(reqStr) && map != null) {
				java.util.Set<String> keys = map.keySet();
				for (String k : keys) {
					method.setParameter(k, map.get(k));
				}
			} else {
				method.setRequestEntity(new org.apache.commons.httpclient.methods.StringRequestEntity(reqStr, "text/html", charset));
			}
			client.getParams().setContentCharset(charset);
			//client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

			int ret = client.executeMethod(method);
			ret_array[0] = ret + "";
			if (ret == org.apache.commons.httpclient.HttpStatus.SC_OK) {//HTTP REQUEST SUCCESS
				//GET RES
				java.io.InputStream resStream = method.getResponseBodyAsStream();
				java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(resStream, charset));
				StringBuffer resBuffer = new StringBuffer();
				String resTemp = "";
				while ( ( resTemp = br.readLine() ) != null) {
					resBuffer.append(resTemp);
				}
				br.close();
				resStream.close();

				ret_array[1] = resBuffer.toString(); //返回值
			} else {
				ret_array[1] = "HTTP:" + ret;
			}
		} catch (Exception e) {
			ret_array[0] = "-1";
			ret_array[1] = "http post request failed, exception:" + e;
		} finally {
			//System.out.println("finally!!!");
			if (method != null) {
				try {
					method.releaseConnection();
				} catch (Exception e) {
					System.out.println("-------> Release HTTP connection exception:" + e.toString());
				}
			}
			if (client != null) {
				try {
					//此项目的httpclient.jar不支持
					//((org.apache.commons.httpclient.SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				} catch (Exception e) {
					System.out.println("-------> Close HTTP connection exception:" + e.toString());
				}
				client = null;
			}
		}
		return ret_array;
	}

	/**
	 * HTTP GET
	 * @param url URL
	 * @param map 参数名:key,参数值:value
	 * @param charset 编码
	 * @return String[]([0]=HttpStatus, [1]=返回值)
	 */
	public static String[] http_get(String url, java.util.Map<String, String> map, String charset, org.apache.commons.httpclient.HttpClient client) {
		String ret_array[] = new String[2];
		org.apache.commons.httpclient.methods.GetMethod method = null;
		boolean nclient = false;
		if (client == null) {
			client = new org.apache.commons.httpclient.HttpClient();
			nclient = true;
		}
		try {
			if (map != null) {
				url += "?";
				java.util.Set<String> keys = map.keySet();
				for (String k : keys) {
					url += k + "=" + java.net.URLEncoder.encode(map.get(k), charset) + "&";
				}
				url = url.substring(0, url.length() - 1);
			}
			System.out.println(url);
			method = new org.apache.commons.httpclient.methods.GetMethod(url);
			method.setRequestHeader("Connection", "close");

			client.getParams().setContentCharset(charset);
			//client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

			int ret = client.executeMethod(method);
			ret_array[0] = ret + "";
			if (ret == org.apache.commons.httpclient.HttpStatus.SC_OK) {//HTTP REQUEST SUCCESS
				//GET RES
				java.io.InputStream resStream = method.getResponseBodyAsStream();
				java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(resStream, charset));
				StringBuffer resBuffer = new StringBuffer();
				String resTemp = "";
				while ( ( resTemp = br.readLine() ) != null) {
					resBuffer.append(resTemp);
				}
				br.close();
				resStream.close();

				ret_array[1] = resBuffer.toString(); //返回值
			} else {
				ret_array[1] = "HTTP:" + ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret_array[0] = "-1";
			ret_array[1] = "http get request failed, exception:" + e;
		} finally {
			//System.out.println("finally!!!");
			if (method != null) {
				try {
					method.releaseConnection();
				} catch (Exception e) {
					System.out.println("-------> Release HTTP connection exception:" + e.toString());
				}
			}
			if (nclient && client != null) {
				try {
					//此项目的httpclient.jar不支持
					//((org.apache.commons.httpclient.SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				} catch (Exception e) {
					System.out.println("-------> Close HTTP connection exception:" + e.toString());
				}
				client = null;
			}
		}
		return ret_array;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("attach", "attach");
		String item[] = http_get("http://127.0.0.1/yxw_framework/pay/dealWith", map, "utf-8", new HttpClient());
		System.out.println(item[0] + "--" + item[1]);
	}
}
