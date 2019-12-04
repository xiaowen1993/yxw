/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年2月10日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年4月25日
 */
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	//	private RequestConfig config = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();

	/**
	* 配置默认请求参数
	*/
	private static RequestConfig config() {
		// 配置请求的超时设置 其他参数可以追加
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(MAX_TIME_OUT)// 设置从连接池获取连接实例的超时
				.setConnectTimeout(MAX_TIME_OUT)// 设置连接超时
				.setSocketTimeout(MAX_TIME_OUT)// 设置读取超时
				.build();
		return requestConfig;
	}

	private Map<String, String> headers;

	public HttpClientUtil setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return ( this );
	}

	/**
	* 连接超时时间 可以配到配置文件 （单位毫秒）
	*/
	private static int MAX_TIME_OUT = 30000;

	//设置整个连接池最大连接数
	private static int MAX_CONN = 200;
	//设置单个路由默认连接数
	private static int SINGLE_ROUTE_MAX_CONN = 100;

	//连接丢失后,重试次数
	private static int MAX_EXECUT_COUNT = 0;

	// 创建连接管理器
	private PoolingHttpClientConnectionManager connManager = null;

	private CloseableHttpClient httpClient = null;

	/**
	 * 单例模式
	 */
	private HttpClientUtil() {
		creatHttpClientConnectionManager();
	}

	public static final HttpClientUtil getInstance() {
		return ( HttpClientUtilHolder.instance );
	}

	private static class HttpClientUtilHolder {
		private static final HttpClientUtil instance = new HttpClientUtil();
	}

	/**
	* 设置HttpClient连接池
	*/
	private void creatHttpClientConnectionManager() {
		try {
			if (httpClient != null) {
				return;
			}

			// 创建SSLSocketFactory 
			// 定义socket工厂类 指定协议（Http、Https）
			Registry registry =
					RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory())
							.register("https", createSSLConnSocketFactory().getSocketFactory())//SSLConnectionSocketFactory.getSocketFactory()
							.build();

			// 创建连接管理器
			connManager = new PoolingHttpClientConnectionManager(registry);
			connManager.setMaxTotal(MAX_CONN);//设置最大连接数
			connManager.setDefaultMaxPerRoute(SINGLE_ROUTE_MAX_CONN);//设置每个路由默认连接数

			//设置目标主机的连接数
			//	HttpHost host = new HttpHost("account.dafy.service");//针对的主机
			//	connManager.setMaxPerRoute(new HttpRoute(host), 50);//每个路由器对每个服务器允许最大50个并发访问

			// 创建httpClient对象
			httpClient =
					HttpClients.custom().setConnectionManager(connManager).setRetryHandler(httpRequestRetry())
							.setDefaultRequestConfig(config()).build();

		} catch (Exception e) {
			logger.error("获取httpClient(https)对象池异常:" + e.getMessage(), e);
		}
	}

	/**
	* 创建SSL连接
	* @throws Exception 
	*/
	private SSLConnectionSocketFactory createSSLConnSocketFactory() throws Exception {
		// 创建TrustManager 
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用 
		ctx.init(null, new TrustManager[] { xtm }, null);
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
		return sslsf;
	}

	/**
	* 配置请求连接重试机制
	*/
	private HttpRequestRetryHandler httpRequestRetry() {
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= MAX_EXECUT_COUNT) {// 如果已经重试MAX_EXECUT_COUNT次，就放弃 
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试 
					logger.error("httpclient 服务器连接丢失");
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常 
					logger.error("httpclient SSL握手异常");
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时 
					logger.error("httpclient 连接超时");
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达 
					logger.error("httpclient 目标服务器不可达");
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝 
					logger.error("httpclient 连接被拒绝");
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常 
					logger.error("httpclient SSLException");
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试 暂时没理解先注释
				if (! ( request instanceof HttpEntityEnclosingRequest )) {
					return true;
				}
				return false;
			}
		};
		return httpRequestRetryHandler;
	}

	/**
	 * get request
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public String get(String url, Map<String, String> params) {
		StringBuffer buf = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buf.append(entry.getKey());
				buf.append("=");
				buf.append(entry.getValue());
				buf.append("&");
			}
		}
		url += buf.toString();
		HttpGet httpGet = new HttpGet(url);
		/* 添加请求头 */
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
		/* 设置请求超时时间 */
		httpGet.setConfig(config());
		return ( req(httpGet) );
	}

	/**
	 * 基础的参数Map<String, String>
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年4月27日 
	 * @param url
	 * @param params
	 * @return
	 */
	public String post(String url, Map<String, String> params) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config());

		/* 设置请求头 */
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("无效的字符编码", e);
			}
		}
		return ( req(httpPost) );
	}

	/**
	 * 请求参数是xml/json
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年4月27日 
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public String post(String url, String params) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config());

		/* 设置请求头 */
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if (params != null && params.length() > 0) {
			StringEntity entity = new StringEntity(params, "UTF-8");
			httpPost.setEntity(entity);
		}

		return ( req(httpPost) );
	}

	/**
	 * 发送请求
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年4月27日 
	 * @param request
	 * @return
	 */
	private String req(HttpUriRequest request) {
		String responseBody = null;
		//		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			responseBody = httpClient.execute(request, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int statusCode = response.getStatusLine().getStatusCode();
					logger.debug("----> statusCode: " + statusCode);
					HttpEntity entity = response.getEntity();
					String responseString = EntityUtils.toString(entity);

					EntityUtils.consume(entity);
					return ( ( entity != null && statusCode == 200 ) ? responseString : null );
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("httpclientUtil execute error", e);
		}
		return ( responseBody );
	}

	public static void main(String[] args) {
		/*HttpClientUtil.getInstance().headers = new HashMap<String, String>() {
			{
				put("Content-Type", "text/html");
				put("Accept-Charset", "utf-8");
				put("User-Agent", "Mozilla/4.0");
			}
		};
		for (int i = 0; i < 100; i++) {
			String returnreq = HttpClientUtil.getInstance().post("http://120.24.95.209:6044/index", new HashMap<String, String>());
			//			System.out.println(returnreq);
		}*/

		Map<String, String> params = new HashMap<String, String>();
		params.put("plugin", "getdept");
		params.put("p", "\\|");
		String returnreq = HttpClientUtil.getInstance().post("https://safe.gdhtcm.com/rec/a", params);
		System.out.println(returnreq);
	}
}
