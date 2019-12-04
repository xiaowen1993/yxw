/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年4月1日
 * @version 1.0
 */
package com.yxw.cache.core.serve.impl;

import java.util.List;

import com.yxw.cache.core.exception.CacheException;
import com.yxw.cache.core.process.CallbackProcessor;
import com.yxw.cache.core.serve.ServeComm;

/**
 * 描述: TODO<br>
 * 
 * @author Caiwq
 * @date 2016年4月1日
 */
public class ServeCommImpl implements ServeComm {
    
    private static CallbackProcessor callbackProcessor;
    
    public static CallbackProcessor getCallbackProcessor() {
        return callbackProcessor;
    }
    
    public static void setCallbackProcessor(CallbackProcessor callbackProcessor) {
        ServeCommImpl.callbackProcessor = callbackProcessor;
    }
    
    /**
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月1日
     * @param request
     * @return
     * @throws CacheException
     */
    @Override
    public List<Object> get(String serviceName, String methodName, List<Object> parameters) {
        @SuppressWarnings("unchecked")
        List<Object> obj = (List<Object>) callbackProcessor.call(serviceName, methodName, parameters);
        
        return obj;
    };
    
    /**
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月1日
     * @param request
     * @return
     * @throws CacheException
     */
    @Override
    public int set(String serviceName, String methodName, List<Object> parameters) {
        // TODO Auto-generated method stub
        
        Integer obj = (Integer) callbackProcessor.call(serviceName, methodName, parameters);
        
        return obj.intValue();
    }
    
    /**
     * 
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月5日
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return
     */
    @Override
    public int delete(String serviceName, String methodName, List<Object> parameters) {
        Integer obj = (Integer) callbackProcessor.call(serviceName, methodName, parameters);
        return obj.intValue();
    }
    
    /**
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月1日
     * @param serviceName
     * @param methodName
     * @param params
     * @return
     */
    @Override
    public Object pop(String serviceName, String methodName, List<Object> parameters) {
        Object object = callbackProcessor.call(serviceName, methodName, parameters);
        return object;
    }
    
    /**
     * 
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月5日
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return
     */
    @Override
    public int count(String serviceName, String methodName, List<Object> parameters) {
        Integer obj = (Integer) callbackProcessor.call(serviceName, methodName, parameters);
        return obj;
    }
    
    /**
     * 
     * 描述: TODO
     * 
     * @author Caiwq
     * @date 2016年4月14日
     * @param serviceName
     * @param methodName
     * @param values
     * @return
     */
    public Boolean pipeline(String serviceName, String methodName, List<Object> values) {
        Boolean brokenState = (Boolean) callbackProcessor.call(serviceName, methodName, values);
        return brokenState;
    }
}
