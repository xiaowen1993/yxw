/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年10月22日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.easyhealth.biz.user.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.config.SystemConfig;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年10月22日
 */
public class BindPhone {
    
    private static Logger logger = LoggerFactory.getLogger(BindPhone.class);
    
    /**
     * 修改手机时需要调用的接口地址
     */
    private final static String PHP_BIND_PHONE_URL = SystemConfig.getStringValue("php_bind_phone_url");
    private final static String SECRET = SystemConfig.getStringValue("secret");
    private final static String MODULE = SystemConfig.getStringValue("module");
    private final static String CONTROLLER = "Account";
    private final static String ACTION = "changeAccountPhone";
    
    public static final String RESULT_CODE = "code";
    public static final String RESULT_MSG = "msg";
    public static final String SUCCESS = "40000";
    
    /**
     * code
     *  40000 修改成功
        40001 请填写新手机号
        40002 请填写旧手机号
        40003 不是一个合法的手机号
        40004没有此账号
        40005新手机号与旧手机号相同
        40006 手机已使用
        
        Msg 对应的提示

     * @param newPhone
     * @param oldPhone
     * @return
     */
    public static Map<String, String> changePhone(String newPhone, String oldPhone) {
        Map<String, String> map = new HashMap<String, String>();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(PHP_BIND_PHONE_URL);
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        NameValuePair[] data = {
        new NameValuePair("module", MODULE), new NameValuePair("controller", CONTROLLER), new NameValuePair("action", ACTION), 
        new NameValuePair("secret", SECRET), new NameValuePair("phone", oldPhone), new NameValuePair("new_phone", newPhone) };
        
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String submitResult = method.getResponseBodyAsString();
            logger.debug("修改手机，newPhone：{}, oldPhone:{}, submitResult:{}", new Object[] { newPhone, oldPhone, submitResult });
            JSONObject json = JSONObject.parseObject(submitResult);
            map.put(RESULT_CODE, json.getString("code"));
            map.put(RESULT_MSG, json.getString("msg"));
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return map;
    }
    
}
