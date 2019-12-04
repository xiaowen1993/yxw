package com.yxw.interfaces.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YxwESService {

	public JSONObject search(String index, String type, Map<String, String> match);

	public JSONObject getPlatformByOrderNo(String type, String... orderNo);

	public JSONObject getEasyHealthUserByIDCardOrMobile(String type, String IDCard, String mobile);
}
