package com.yxw.insurance.chinalife.interfaces.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Soap报文工具类，主要用HttpClient调用WebService接口用到
 * 
 * @author homer.yang
 */
public class SoapUtil {
    
    public final static String ENVELOPE_PREFIX = "soapenv";
    public final static String ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    
    public final static String SPLIT_CHAR = ":";
    
    /**
     * 生成SOAP请求报文
     * 
     * @param prefix
     *            方法名前缀
     * @param namespace
     *            命名空间
     * @param body
     *            具体参数
     * @return SOAP请求报文
     */
    public static String genSoapRequestString(String prefix, String namespace, Element body) {
        return genSoapRequestString(prefix, namespace, body, null, null);
    }
    
    /**
     * 生成SOAP请求报文
     * 
     * @param prefix
     *            方法名前缀
     * @param namespace
     *            命名空间
     * @param body
     *            具体参数
     * @return SOAP请求报文
     */
    public static Element genSoapRequestElement(String prefix, String namespace, Element body) {
        return genSoapRequestElement(prefix, namespace, body, null, null);
    }
    
    /**
     * 生成SOAP请求报文
     * 
     * @param prefix
     *            方法名前缀
     * @param namespace
     *            命名空间
     * @param body
     *            具体参数
     * @param envelopePrefix
     *            SOAP报文Envelope前缀
     * @param envelopeNamespace
     *            SOAP报文Envelope命名空间
     * @return SOAP请求报文
     */
    public static String genSoapRequestString(String prefix, String namespace, Element body, String envelopePrefix, String envelopeNamespace) {
        return genSoapRequestElement(prefix, namespace, body, envelopePrefix, envelopeNamespace).asXML();
    }
    
    /**
     * 生成SOAP请求报文
     * 
     * @param prefix
     *            方法名前缀
     * @param namespace
     *            命名空间
     * @param body
     *            具体参数
     * @param envelopePrefix
     *            SOAP报文Envelope前缀
     * @param envelopeNamespace
     *            SOAP报文Envelope命名空间
     * @return SOAP请求报文
     */
    public static Element genSoapRequestElement(String prefix, String namespace, Element body, String envelopePrefix, String envelopeNamespace) {
        
        if (StringUtils.isBlank(envelopePrefix)) {
            envelopePrefix = ENVELOPE_PREFIX;
        }
        if (StringUtils.isBlank(envelopeNamespace)) {
            envelopeNamespace = ENVELOPE_NAMESPACE;
        }
        
        // 根节点
        Element envElement = DocumentHelper.createElement(ENVELOPE_PREFIX.concat(SPLIT_CHAR).concat("Envelope"));
        // 默认命名空间
        envElement.addNamespace(ENVELOPE_PREFIX, ENVELOPE_NAMESPACE);
        // 附加的命名空间
        envElement.addNamespace(prefix, namespace);
        // 包头节点
        envElement.addElement(ENVELOPE_PREFIX.concat(SPLIT_CHAR).concat("Header"));
        // 包体节点
        Element bodyElement = envElement.addElement(ENVELOPE_PREFIX.concat(SPLIT_CHAR).concat("Body"));
        
        bodyElement.add(body);
        return envElement;
        
    }
    
    /**
     * 从SOAP返回报文获取接口的实际返回值
     * 
     * @param serviceName
     *            调用的服务/方法名称
     * @param soapResponseString
     *            SOAP返回报文
     * @return
     */
    public static Element getSoapResponseElement(String serviceName, String soapResponseString) {
        Element resRoot = null;
        try {
            resRoot = DocumentHelper.parseText(soapResponseString).getRootElement();
            
            if (resRoot != null) {
                return resRoot.element("Body").element(serviceName.concat("Response"));
            } else {
                return null;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 生成请求报文的Body节点
     * 
     * @param prefix
     *            节点前缀
     * @param serviceName
     *            需要调用的服务/方法名称
     * @return
     */
    public static Element genBodyElement(String prefix, String serviceName) {
        return DocumentHelper.createElement(prefix.concat(SoapUtil.SPLIT_CHAR).concat(serviceName));
    }
    
    public static void main(String[] args) {
        String namespace = "http://webservice.yxw.com/";
        String prefix = "web";
        String methodName = "scanToPay";
        
        Element body = genBodyElement(prefix, methodName);
        
        body.addElement("arg0").setText("1");
        body.addElement("arg1").setText("2");
        
        String soapRequestString = genSoapRequestString(prefix, namespace, body);
        System.out.println("soapString: " + soapRequestString);
    }
    
}
