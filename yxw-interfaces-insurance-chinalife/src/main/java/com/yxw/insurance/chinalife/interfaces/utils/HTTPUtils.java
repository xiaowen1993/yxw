package com.yxw.insurance.chinalife.interfaces.utils;

import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年6月24日
 */
public class HTTPUtils {
    
    /**
     * 通过http post 的方式调用webservice
     * @param url                       URL
     * @param map                       参数名:key,参数值:value
     * @param reqStr                    无参数名传递参数
     * @param charset                   编码
     * @return                          String[]([0]=HttpStatus, [1]=返回值)
     */
    public static String[] httpPostInvoke(String url, Map<String, Object> map, String reqStr, String charset){
        return httpPostInvoke(url, map, null, reqStr, charset);
    }

    /**
     * 通过http post 的方式调用webservice
     * @param url                       URL
     * @param map                       参数名:key,参数值:value
     * @param repHearderMap             请求头参数
     * @param reqStr                    无参数名传递参数
     * @param charset                   编码
     * @return                          String[]([0]=HttpStatus, [1]=返回值)
     */
    public static String[] httpPostInvoke(String url, Map<String, Object> map, Map<String, String> repHearderMap, String reqStr, String charset){
        return httpPostInvoke(url, map, repHearderMap, reqStr, charset, charset);
    }

    /**
     * 通过http post 的方式调用webservice
     * @param url                       URL
     * @param map                       参数名:key,参数值:value
     * @param repHearderMap             请求头参数
     * @param reqStr                    无参数名传递参数
     * @param inputCharset              请求编码
     * @param outputCharset             返回编码
     * @return                          String[]([0]=HttpStatus, [1]=返回值)
     */
    public static String[] httpPostInvoke(String url, Map<String, Object> map, Map<String, String> repHearderMap, String reqStr, String inputCharset, String outputCharset){
        String[] result = new String[2];
        HttpClient client = null;
        PostMethod method = null;
        
        try {
            client = new HttpClient();
            method = new PostMethod(url);
            method.setRequestHeader("Connection", "close");
            
            if (repHearderMap != null) {
                Set<String> keys = repHearderMap.keySet();
                for (String k : keys) {
                    method.setRequestHeader(k, repHearderMap.get(k));
                }
            }
            
            if (StringUtils.isBlank(reqStr) && map != null) {
                Set<String> keys = map.keySet();
                for (String k : keys) {
                    method.setParameter(k, map.get(k).toString());
                }
            } else {
                method.setRequestEntity(new StringRequestEntity(reqStr, "text/html", inputCharset));
            }
            client.getParams().setContentCharset(inputCharset);
            
            System.out.println("post.url: " + url);
            int ret = client.executeMethod(method);
            result[0] = ret + "";
            if (ret == HttpStatus.SC_OK) {//HTTP REQUEST SUCCESS
                //GET RES
                result[1] = IOUtils.toString(method.getResponseBodyAsStream(), outputCharset);
            } else {
                result[1] = "HTTP:" + ret;
            }
        } catch (Exception e) {
            result[0] = "-1";
            result[1] = "http post request failed, exception:" + e;
        } finally {
            //System.out.println("finally!!!");
            if (method != null) {
              try {
                  method.releaseConnection();
              } catch (Exception e) {
                  System.out.println("-------> Release HTTP connection exception:"+ e.toString());
              }
            }
            if (client != null) {
              try {
                  ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
              } catch (Exception e) {
                System.out.println("-------> Close HTTP connection exception:"+e.toString());
              }
              client = null;
            }
        }
        return result;
    }
    
    
    public static String[] httpGetInvoke(String url, Map<String, Object> map, String charset, HttpClient client) {
        String result[] = new String[2];
        GetMethod method = null;
        boolean nclient = false;
        if (client == null) {
            client = new HttpClient();
            nclient = true;
        }
        try {
            if (map != null) {
                url += "?";
                java.util.Set<String> keys = map.keySet();
                for (String k : keys) {
                    url += k + "=" + java.net.URLEncoder.encode(map.get(k).toString(), charset) + "&";
                }
                url = url.substring(0, url.length() - 1);
            }
            // System.out.println(url);
            method = new GetMethod(url);
            method.setRequestHeader("Connection", "close");
            
            client.getParams().setContentCharset(charset);
            // client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            
            int ret = client.executeMethod(method);
            result[0] = ret + "";
            if (ret == HttpStatus.SC_OK) {// HTTP REQUEST SUCCESS
                // GET RES
            	result[1] = IOUtils.toString(method.getResponseBodyAsStream(), charset);
            } else {
            	result[1] = "HTTP:" + ret;
            }
        } catch (Exception e) {
            result[0] = "-1";
            result[1] = "http get request failed, exception:" + e;
        } finally {
            // System.out.println("finally!!!");
            if (method != null) {
                try {
                    method.releaseConnection();
                } catch (Exception e) {
                    System.out.println("-------> Release HTTP connection exception:" + e.toString());
                }
            }
            if (nclient && client != null) {
                try {
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
                    System.out.println("-------> Close HTTP connection exception:" + e.toString());
                }
                client = null;
            }
        }
        return result;
    }
    
}
