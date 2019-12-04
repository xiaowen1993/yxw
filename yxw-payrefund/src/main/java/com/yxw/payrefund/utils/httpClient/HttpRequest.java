package com.yxw.payrefund.utils.httpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;

public class HttpRequest {
    
    /** HTTP GET method */
    public static final String METHOD_GET = "GET";
    
    /** HTTP POST method */
    public static final String METHOD_POST = "POST";
    
    /**
     * 待请求的url
     */
    private String url = null;
    
    /**
     * 默认的请求方式
     */
    private String method = METHOD_POST;
    
    private String contentType = HttpConstants.CONTENT_TYPE_HTML_TYPE;
    
    private int timeout = 0;
    
    private int connectionTimeout = 0;
    
    /**
     * Post方式请求时组装好的参数值对
     */
    private NameValuePair[] parameters = null;
    
    /**
     * Get方式请求时对应的参数
     */
    private String queryString = null;
    
    /**
     * 默认的请求编码方式
     */
    private String charset = "utf-8";
    
    /**
     * 请求返回的方式
     */
    private HttpResultType resultType = HttpResultType.BYTES;
    
    private List<Header> requestHeaders = new ArrayList<Header>();
    
    public HttpRequest(HttpResultType resultType) {
        super();
        this.resultType = resultType;
    }
    
    public NameValuePair[] getParameters() {
        return parameters;
    }
    
    public void setParameters(Map<String, String> properties) {
        this.parameters = generatNameValuePair(properties);
    }
    
    public String getQueryString() {
        return queryString;
    }
    
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }
    
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
    public int getTimeout() {
        return timeout;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public String getCharset() {
        return charset;
    }
    
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
    public HttpResultType getResultType() {
        return resultType;
    }
    
    public void setResultType(HttpResultType resultType) {
        this.resultType = resultType;
    }
    
    public List<Header> getRequestHeaders() {
        if (requestHeaders.size() == 0) {
            Header header = new Header();
            header.setName("Content-Type");
            header.setValue("application/x-www-form-urlencoded; ".concat(contentType).concat("; charset=").concat(charset));
            requestHeaders.add(header);
        }
        return requestHeaders;
    }
    
    public void setRequestHeaders(List<Header> headers) {
        this.requestHeaders = headers;
    }
    
    public void setRequestHeaders(Map<String, String> headers) {
        List<Header> requestHeaders = new ArrayList<Header>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Header header = new Header();
            header.setName(entry.getKey());
            header.setValue(entry.getValue());
            requestHeaders.add(header);
        }
        
        this.requestHeaders = requestHeaders;
    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        
        return nameValuePair;
    }
    
}
