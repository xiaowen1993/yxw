package com.yxw.payrefund.utils.httpClient;

import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.apache.commons.lang3.StringUtils;

public class HttpProtocolHandler {

    private static String              DEFAULT_CHARSET                     = "UTF-8";

    /** 连接超时时间，由bean factory设置，缺省为8秒钟 */
    private int                        defaultConnectionTimeout            = 8000;

    /** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
    private int                        defaultSoTimeout                    = 30000;

    /** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
    private int                        defaultIdleConnTimeout              = 60000;

    private int                        defaultMaxConnPerHost               = 30;

    private int                        defaultMaxTotalConn                 = 80;

    /** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒*/
    private static final long          defaultHttpConnectionManagerTimeout = 3 * 1000;

    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     */
    private HttpConnectionManager      connectionManager;

    private static HttpProtocolHandler httpProtocolHandler                 = new HttpProtocolHandler();

    /**
     * 工厂方法
     * 
     * @return
     */
    public static HttpProtocolHandler getInstance() {
        return httpProtocolHandler;
    }

    /**
     * 私有的构造方法
     */
    private HttpProtocolHandler() {
        // 创建一个线程安全的HTTP连接池
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
        connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);

        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(connectionManager);
        ict.setConnectionTimeout(defaultIdleConnTimeout);

        ict.start();
    }

    /**
     * 执行Http请求
     * 
     * @param request 请求数据
     * @param requestEntityString
     * @return 
     * @throws HttpException, IOException 
     */
    public HttpResponse execute(HttpRequest request, String requestEntityString) throws HttpException, IOException {
        HttpClient httpclient = new HttpClient(connectionManager);

        // 设置连接超时
        int connectionTimeout = defaultConnectionTimeout;
        if (request.getConnectionTimeout() > 0) {
            connectionTimeout = request.getConnectionTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);

        // 设置回应超时
        int soTimeout = defaultSoTimeout;
        if (request.getTimeout() > 0) {
            soTimeout = request.getTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

        // 设置等待ConnectionManager释放connection的时间
        httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;
        
        httpclient.getParams().setContentCharset(charset);
        
        HttpMethod method = null;

        //get模式且不带RequestEntity
        if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
            method = new GetMethod(request.getUrl());
            method.getParams().setCredentialCharset(charset);

            // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
            method.setQueryString(request.getQueryString());
        } else if(StringUtils.isBlank(requestEntityString)) {
            //post模式且不带RequestEntity
            method = new PostMethod(request.getUrl());
            if (request.getParameters() != null) {
                ((PostMethod) method).addParameters(request.getParameters());
            }
            
            for (Header herder : request.getRequestHeaders()) {
                method.addRequestHeader(herder);
            }
        } else {
            //post模式且带RequestEntity
            method = new PostMethod(request.getUrl());
            
            // 设置请求体
            ((PostMethod) method).setRequestEntity(new StringRequestEntity(requestEntityString, request.getContentType(), charset));
        }

        HttpResponse response = new HttpResponse();

        try {
            int httpStatus = httpclient.executeMethod(method);
            response.setHttpStatus(httpStatus);
            
            if (httpStatus == HttpStatus.SC_OK) {
                if (request.getResultType().equals(HttpResultType.STRING)) {
                    response.setStringResult(method.getResponseBodyAsString());
                } else if (request.getResultType().equals(HttpResultType.BYTES)) {
                    response.setByteResult(method.getResponseBody());
                } else if (request.getResultType().equals(HttpResultType.INPUT_STREAM)) {
                    // 当返回数据量比较大，可能会消耗太多内存的情况下用到
                    // 用此种方式不会出现 “Going to buffer response body of large or unknown size. Using getResponseBodyAsStream instead is recommended.” 警告
                    InputStream inputStream = method.getResponseBodyAsStream();
                    java.io.BufferedReader br = new java.io.BufferedReader( new java.io.InputStreamReader(inputStream, charset) );
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }
                    br.close();
                    inputStream.close();
                    
                    response.setStringResult(buffer.toString());
                }
            }
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {

            return null;
        } catch (IOException ex) {

            return null;
        } catch (Exception ex) {

            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     * 将NameValuePairs数组转变为字符串
     * 
     * @param nameValues
     * @return
     */
    protected String toString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];

            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }
}
