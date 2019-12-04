/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年2月9日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.filter.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * GZIP压缩传输.
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年2月9日
 */

public class GzipFilter implements Filter {

	/**
	 * 需要被Gzip压缩的最小文件大小.
	 */
	// private static final int GZIP_MINI_LENGTH = 512;
	private static final String INCLUDE_SUFFIXS_NAME = "includeSuffixs";
	/**
	 * 默认需要压缩的文件类型
	 */
	private static final String[] DEFAULT_INCLUDE_SUFFIXS = { ".js", ".css", ".jpg", ".gif" };
	/**
	 * 需要压缩的文件类型
	 */
	private static String[] includeSuffixs = null;
	/**
	 * 所有文件类型都压缩
	 */
	private static final String INCLUDE_ALL_REQ = ".all";
	private static final String NO_FILTER_URI = "noFilterURI";
	/**
	 * 不进行过滤的请求路径
	 */
	private static String[] noFilterURIs = null;

	/**
	 * 初始化includeSuffixs和noFilterURIs
	 */
	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		String includeSuffixStr = filterconfig.getInitParameter(INCLUDE_SUFFIXS_NAME);
		String noFilterURI = filterconfig.getInitParameter(NO_FILTER_URI);
		if (StringUtils.isNotBlank(includeSuffixStr)) {
			includeSuffixs = includeSuffixStr.split(",");
			// 处理匹配字符串为".includeSuffixs[i]"
			for (int i = 0; i < includeSuffixs.length; i++) {
				includeSuffixs[i] = "." + includeSuffixs[i];
			}
		} else {
			// 如果includeSuffixStr为空,则使用DEFAULT_INCLUDE_SUFFIXS
			includeSuffixs = DEFAULT_INCLUDE_SUFFIXS;
		}
		if (StringUtils.isNotBlank(noFilterURI)) {
			noFilterURIs = noFilterURI.split(",");
		}
		if (noFilterURIs == null) {
			noFilterURIs = new String[0];
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		// 检查是否支持gzip压缩,并且检查是否是需要压缩的文件类型
		if (checkAccetptGzip(request) && shouldNotFilter(request)) {
			// 得到响应对象的封装类对象
			DatasWrapper wrapper = new DatasWrapper(response);
			chain.doFilter(request, wrapper);
			// 设置Content-Encoding实体报头，告诉浏览器实体正文采用了gzip压缩编码
			wrapper.setHeader("Content-Encoding", "gzip");
			wrapper.setHeader("Vary", "Accept-Encoding");
			byte[] gzipData = gzip(wrapper.getResponseData());
			wrapper.setContentLength(gzipData.length);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(gzipData);
			outputStream.flush();
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 过滤控制函数,对指定后缀的请求进行gzip压缩.
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	private boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		boolean isCompression = false;
		// 检查不过滤的请求列表
		for (String noFilterUrl : noFilterURIs) {
			if (path.indexOf(noFilterUrl) > -1) {
				return isCompression;
			}
		}
		// 检查过滤请求列表
		for (String suffix : includeSuffixs) {
			// 全部过滤
			if (INCLUDE_ALL_REQ.endsWith(suffix)) {
				isCompression = true;
				break;
			} else {
				if (path.endsWith(suffix)) {
					isCompression = true;
					break;
				}
			}
		}
		return isCompression;
	}

	private byte[] gzip(byte[] data) {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
		GZIPOutputStream output = null;
		try {
			output = new GZIPOutputStream(byteOutput);
			output.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return byteOutput.toByteArray();
	}

	/**
	 * 检查浏览器客户端是否支持gzip编码.
	 */
	private static boolean checkAccetptGzip(HttpServletRequest request) {
		// Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");
		return StringUtils.contains(acceptEncoding, "gzip");
	}
}
