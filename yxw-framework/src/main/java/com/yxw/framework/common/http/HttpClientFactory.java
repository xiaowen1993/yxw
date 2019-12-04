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
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
public class HttpClientFactory {
	private static Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

	/**
	* 连接超时时间 可以配到配置文件 （单位毫秒）
	*/
	private static int MAX_TIME_OUT = 3000;

	//设置整个连接池最大连接数
	private static int MAX_CONN = 200;
	//设置单个路由默认连接数
	private static int SINGLE_ROUTE_MAX_CONN = 100;

	//连接丢失后,重试次数
	private static int MAX_EXECUT_COUNT = 0;

	// 创建连接管理器
	private PoolingHttpClientConnectionManager connManager = null;

	private CloseableHttpClient httpClient = null;

	// 单例开始
	private HttpClientFactory() {
		creatHttpClientConnectionManager();
	}

	private static class HttpClientFactoryInner {
		public static final HttpClientFactory httpClientFactory = new HttpClientFactory();
	}

	public static HttpClientFactory getInstance() {
		return HttpClientFactoryInner.httpClientFactory;
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
							.register("https", createSSLConnSocketFactory())//SSLConnectionSocketFactory.getSocketFactory()
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
					HttpClients.custom().setConnectionManager(connManager).setRetryHandler(httpRequestRetry()).setDefaultRequestConfig(config())
							.build();

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
				//	if (!(request instanceof HttpEntityEnclosingRequest)) { 
				//	return true; 
				//	} 
				return false;
			}
		};
		return httpRequestRetryHandler;
	}

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

	public synchronized void close() {
		if (connManager == null) {
			return;
		}
		// 关闭连接池
		connManager.shutdown();
		// 设置httpClient失效
		httpClient = null;
		connManager = null;
	}

	public synchronized boolean isOpen() {
		if (connManager == null) {
			return false;
		}
		return true;
	}

	public String getForText(String strUrl) {
		return getForText(strUrl, null);
	}

	public String getForText(String strUrl, String strBody) {
		if (!isOpen()) {
			return "本地连接池关闭";
		}

		HttpResponse response = null;

		long start = System.currentTimeMillis();
		// 开始进行get请求
		String strReturn = "";
		try {
			if (logger.isDebugEnabled()) {
				logger.info("HttpClient GET for:" + strUrl);
			}

			if (StringUtils.isNotBlank(strBody)) {
				strUrl = strUrl + "?" + strBody;
			}

			HttpGet get = new HttpGet(strUrl);
			//	get.setHeader("User-Agent", "Mozilla/5.0");
			//	httpRequestBase.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			//	httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//"en-US,en;q=0.5");
			//	httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");

			get.setHeader("Content-type", new StringBuilder().append("application/x-www-form-urlencoded;charset=").append("UTF-8").toString());
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				strReturn = EntityUtils.toString(entity);
				//	EntityUtils.consume(entity);
			}
			long end = System.currentTimeMillis();
			logger.info("HttpClient GET for:" + strUrl + ", 参数：【" + strBody + "】请求花费时间：" + ( end - start ) + "ms");
			return strReturn;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("ClientProtocolException:" + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("IOException:" + e.getMessage(), e);
		} finally {
			//释放连接
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());//会自动释放连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String postForText(String strUrl) {
		return postForText(strUrl, null);
	}

	public String postForText(String strUrl, String strBody) {
		if (!isOpen()) {
			return "本地连接池关闭";
		}
		long start = System.currentTimeMillis();

		HttpResponse response = null;

		// 开始进行post请求
		String strReturn = "";
		String strLog = "";
		try {
			if (logger.isDebugEnabled()) {
				logger.info("HttpClient POST for:" + strUrl);
			}

			HttpPost post = new HttpPost(strUrl);
			post.setHeader("Content-type", "application/xml; charset=UTF-8");
			if (strBody != null) {
				post.setEntity(new StringEntity(strBody, "UTF-8"));
				strLog = strBody;
			}
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				strReturn = EntityUtils.toString(entity);
				//	EntityUtils.consume(entity);
			}
			long end = System.currentTimeMillis();
			logger.info("HttpClient POST for:" + strUrl + ", 参数：【" + strLog + "】请求花费时间：" + ( end - start ) + "ms");
			return strReturn;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("ClientProtocolException:" + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("IOException:" + e.getMessage(), e);
		} finally {
			//释放连接
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());//会自动释放连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(HttpClientFactory.getInstance().postForText("https://safe.gdhtcm.com/rec/a?p=%7C&plugin=getdept"));
	}
}
