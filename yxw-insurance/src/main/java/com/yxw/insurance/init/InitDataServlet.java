package com.yxw.insurance.init;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.spring.ext.config.PropertiesConfListenerHelper;
import com.yxw.framework.config.SystemConfig;

public class InitDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<Object> ICDConfigMapVals = new ArrayList<Object>();

	public static List<Object> medicalInsurancePaymentTypes = new ArrayList<Object>();

	public static List<String> l1 = new ArrayList<String>();

	public static List<String> l2 = new ArrayList<String>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		SystemConfig.loadSystemConfig();

		try {
			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			String path = "";
			if (os_name.indexOf("windows") != -1) {
				path = InitDataServlet.class.getResource("/properties").toURI().getPath().substring(1);
			} else if (os_name.indexOf("linux") != -1) {
				path = InitDataServlet.class.getResource("/properties").toURI().getPath();
			}

			PropertiesConfListenerHelper.addListener(path);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		try {
			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			String path = "";
			if (os_name.indexOf("windows") != -1) {
				path = InitDataServlet.class.getResource("/ICD.json").toURI().getPath().substring(1);
			} else if (os_name.indexOf("linux") != -1) {
				path = InitDataServlet.class.getResource("/ICD.json").toURI().getPath();
			}
			String text = FileUtils.readFileToString(new File(path), "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(text);
			ICDConfigMapVals = jsonObject.getJSONArray("list");

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		try {
			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			String path = "";
			if (os_name.indexOf("windows") != -1) {
				path = InitDataServlet.class.getResource("/medicalInsurancePaymentType.json").toURI().getPath().substring(1);
			} else if (os_name.indexOf("linux") != -1) {
				path = InitDataServlet.class.getResource("/medicalInsurancePaymentType.json").toURI().getPath();
			}
			String text = FileUtils.readFileToString(new File(path), "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(text);
			medicalInsurancePaymentTypes = jsonObject.getJSONArray("list");

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		try {

			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			if (os_name.indexOf("windows") != -1) {
				List<String> a =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/A.txt").toURI().getPath().substring(1)), "UTF-8");
				List<String> b =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/B.txt").toURI().getPath().substring(1)), "UTF-8");
				l1.addAll(a);
				l1.addAll(b);

				List<String> c =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/C.txt").toURI().getPath().substring(1)), "UTF-8");
				List<String> d =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/D.txt").toURI().getPath().substring(1)), "UTF-8");
				l2.addAll(c);
				l2.addAll(d);

			} else if (os_name.indexOf("linux") != -1) {
				List<String> a = FileUtils.readLines(new File(InitDataServlet.class.getResource("/A.txt").toURI().getPath()), "UTF-8");
				List<String> b = FileUtils.readLines(new File(InitDataServlet.class.getResource("/B.txt").toURI().getPath()), "UTF-8");
				l1.addAll(a);
				l1.addAll(b);

				List<String> c = FileUtils.readLines(new File(InitDataServlet.class.getResource("/C.txt").toURI().getPath()), "UTF-8");
				List<String> d = FileUtils.readLines(new File(InitDataServlet.class.getResource("/D.txt").toURI().getPath()), "UTF-8");
				l2.addAll(c);
				l2.addAll(d);
			}

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
