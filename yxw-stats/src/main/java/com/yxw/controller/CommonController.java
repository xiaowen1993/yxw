/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年12月31日
 * @version 1.0
 */
package com.yxw.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.outside.service.YxwStatsService;
import com.yxw.utils.ExcelExportExUtils;

/**
 * 描述: TODO<br>
 * 
 * @author Caiwq
 * @date 2015年12月31日
 */
@Controller
public class CommonController {
	
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);

	@ResponseBody
	@RequestMapping(value = "/common/getInitData")
	public RespBody getInitData(HttpServletRequest request) {
		return new RespBody(Status.OK, getHospitalInfos());
	}

	private Map<String, List<Map<String, String>>> getHospitalInfos() {
		Map<String, List<Map<String, String>>> resultMap = new HashMap<>();

		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		JSONObject source = JSON.parseObject(service.getAreaHospitalInfos());

		@SuppressWarnings("unchecked")
		List<JSONObject> itemsObject = (List<JSONObject>) JSONPath.eval(source, "$.result.items");

		for (JSONObject itemObject : itemsObject) {
			String areaName = ((String) JSONPath.eval(itemObject, "$.areaName")).replace("/", "");
			List<Map<String, String>> areas = resultMap.get(areaName);
			if (areas == null) {
				areas = new ArrayList<Map<String, String>>();
			}

			@SuppressWarnings("unchecked")
			List<JSONObject> subItemObject = (List<JSONObject>) JSONPath.eval(itemObject, "$.onlineItems");

			for (JSONObject hospital : subItemObject) {
				Map<String, String> map = new HashMap<>();
				map.put("name", (String) JSONPath.eval(hospital, "$.hospitalName"));
				map.put("code", (String) JSONPath.eval(hospital, "$.hospitalCode"));
				map.put("areaCode", (String) JSONPath.eval(hospital, "$.areaCode"));
				areas.add(map);
			}

			resultMap.put(areaName, areas);
		}

		return resultMap;
	}

	/**
	 * 普通的下载-通过流的方式
	 * @param request
	 * @param response
	 */
	@RequestMapping("/common/commonDownload")
	public void commonDownload(String fileName, String data, HttpServletRequest request, HttpServletResponse response) {
		logger.info("通用下载, 文件名： {}.", fileName);
		if (StringUtils.isNotBlank(data)) {
			try {
				response.reset();
				response.setContentType("application/ms-excel;charset=utf-8");
				
				// 判断浏览器类型
				fileName = StringUtils.isBlank(fileName) ? "未命名" : fileName;
				fileName = fileName.concat(".xlsx");
				String userAgent = request.getHeader("User-Agent"); 
				if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				}
				
				response.addHeader("Content-disposition", "attachment;filename=" + fileName);
				OutputStream os = response.getOutputStream();
				// byte[] datas = ExcelExportUtils.getExcelData(data);
				byte[] datas = ExcelExportExUtils.getExcelData(data);
				response.setContentLength(datas.length);
				response.addHeader("Connection", "close");
				response.setCharacterEncoding("UTF-8");
				os.write(datas);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("通用 下载， table没有数据");
		}
	}

	/**
	 * springMvc下载文件的方式
	 * @param fileName
	 * @param data
	 * @return
	 */
	@RequestMapping("/common/springMvcDownload")
	public ResponseEntity<byte[]> springMvcDownload(HttpServletRequest request, String fileName, String data) {
		logger.info("springMvc下载, 文件名： {}.", fileName);
		if (StringUtils.isNotBlank(data)) {
			// spring包装的header
			try {
				// 判断浏览器类型
				fileName = StringUtils.isBlank(fileName) ? "未命名" : fileName;
				fileName = fileName.concat(".xlsx");
				String userAgent = request.getHeader("User-Agent"); 
				if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				}
				// System.out.println(data);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				fileName = StringUtils.isBlank(fileName) ? "未命名" : fileName;
				headers.setContentDispositionFormData("attachment", fileName);
				// byte[] datas = ExcelExportUtils.getExcelData(data);
				byte[] datas = ExcelExportExUtils.getExcelData(data);
				// IE 不支持201状态码，都改成200
				return new ResponseEntity<byte[]>(datas, headers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			logger.info("springMvc 下载， table没有数据");
		}

		return null;
	}
}
