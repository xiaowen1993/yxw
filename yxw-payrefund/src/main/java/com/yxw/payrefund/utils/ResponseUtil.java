package com.yxw.payrefund.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author homer.yang
 */
public class ResponseUtil {
	
	public static void redirect(HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
	}

	public static void print(HttpServletResponse response, String text) throws IOException {
		PrintWriter out = response.getWriter();
        out.print(text);
        response.flushBuffer();
	}
	
	public static void printPlain(HttpServletResponse response, String text) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(text);
		response.flushBuffer();
	}
	
	public static void printXml(HttpServletResponse response, String text) throws IOException {
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(text);
        response.flushBuffer();
	}

}
