/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月23日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.rules.common;


/**
 * @author 规则转换工具类
 * @version 1.0
 * @since 2015年4月23日
 */

public class RuleConvertUtil {
    
    public static final String ENVELOPE_PREFIX = "soapenv";
    public static final String ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    
    // /**
    // * xml文件转换为Businesses对象
    // *
    // * @param file
    // * @return
    // */
    // public static Businesses xml2Businesses(File file) {
    // XStream xStream = new XStream();
    // xStream.alias("businesses", Businesses.class);
    // xStream.alias("business", Function.class);
    // xStream.alias("globalType", GlobalType.class);
    // xStream.alias("option", Option.class);
    // xStream.alias("function", Function.class);
    // xStream.alias("hisInterface", HisInterface.class);
    // xStream.alias("parameter", Parameter.class);
    // xStream.alias("dataNode", DataNode.class);
    // xStream.alias("convert", Convert.class);
    // Businesses businesses = (Businesses) xStream.fromXML(file);
    // return businesses;
    // }
    //
    // /**
    // * 根据functionCode和interfaceCode来获取一个接口
    // *
    // * @param businesses
    // * @param functionCode
    // * @param interfaceCode
    // * @return
    // */
    // public static HisInterface getHisInterface(String businessesCode, String functionCode, String interfaceCode) {
    // Businesses businesses = SystemConfig.businessesMap.get(businessesCode);
    // for (Function function : businesses.getFunctions()) {
    // if (functionCode.equals(function.getCode())) {
    // for (HisInterface hisInterface : function.getHisInterfaces()) {
    // if (interfaceCode.equals(hisInterface.getCode())) {
    // return hisInterface;
    // }
    // }
    // }
    // }
    // return null;
    // }
    //
    // /**
    // * 获取xml格式请求字符串
    // *
    // * @param businessesCode
    // * @param functionCode
    // * @param interfaceCode
    // * @param paramMap
    // * @return
    // */
    // public static RequestInfo getRequestXmlStr(String businessesCode, String functionCode, String interfaceCode,
    // Object object) {
    // HisInterface hisInterface = RuleConvertUtil.getHisInterface(businessesCode, functionCode, interfaceCode);
    // // 拼接xml格式请求报文头
    // Element envElement = DocumentHelper.createElement(ENVELOPE_PREFIX + ":Envelope");
    // envElement.addNamespace(ENVELOPE_PREFIX, ENVELOPE_NAMESPACE);
    // envElement.addNamespace(hisInterface.getCustomNamespaceName(), hisInterface.getCustomNamespaceUrl());
    // envElement.addElement(ENVELOPE_PREFIX + ":Header");
    // Element bodyElement = envElement.addElement(ENVELOPE_PREFIX + ":Body");
    // Element requestParamElement = DocumentHelper.createElement(hisInterface.getCustomNamespaceName() + ":"
    // + hisInterface.getName());
    // bodyElement.add(requestParamElement);
    // // 拼接请求参数
    // for (Parameter parameter : hisInterface.getParameters()) {
    // Object value = null;
    // // 如果需要工具类辅助转换值
    // if (parameter.getConvertUtil() != null) {
    // ConvertUtil convertUtil = parameter.getConvertUtil();
    // Object param = getValue(object, convertUtil.getParamsName());
    // value = getValue(convertUtil.getClassName(), convertUtil.getMethodName(), param,
    // convertUtil.getParamsType());
    // value = getValue(parameter, String.valueOf(value));
    // } else {
    // // 直接获取从前端接收到的值
    // value = getValue(object, parameter.getName());
    // }
    // if (value != null) {
    // requestParamElement.addElement(hisInterface.getCustomNamespaceName() + ":" + parameter.getTargetName())
    // .addText(String.valueOf(value));
    // } else if (StringUtils.isNotBlank(parameter.getDefaultValue())) {
    // requestParamElement.addElement(hisInterface.getCustomNamespaceName() + ":" + parameter.getTargetName())
    // .addText(parameter.getDefaultValue());
    // }
    // }
    // RequestInfo requestInfo = new RequestInfo(hisInterface, envElement.asXML());
    // return requestInfo;
    // }
    //
    // /**
    // * 获取处理后的xml响应报文 document对象
    // *
    // * @param responseXmlStr
    // * @param hisInterface
    // * @return
    // */
    // public static Document getResponseXML(String responseXmlStr, HisInterface hisInterface) {
    // Document document = null;
    // try {
    // document = DocumentHelper.parseText(getResponseXMLStr(responseXmlStr, hisInterface));
    // System.out.println(document.asXML());
    // } catch (DocumentException e) {
    // e.printStackTrace();
    // }
    // return document;
    // }
    //
    // /**
    // * 获取处理后的xml响应报文字符串
    // *
    // * @param responseXmlStr
    // * @param hisInterface
    // * @return
    // */
    // public static String getResponseXMLStr(String responseXmlStr, HisInterface hisInterface) {
    // // 处理转义
    // String xmlStr = StringEscapeUtils.unescapeXml(responseXmlStr);
    // String node = hisInterface.getDataNode().getName();
    // // 截取结果集
    // if (xmlStr.indexOf("</" + node + ">") != -1) {
    // xmlStr = xmlStr.substring(xmlStr.indexOf("<" + node + ">"), xmlStr.indexOf("</" + node + ">")
    // + ("</" + node + ">").length());
    // } else {
    // // 首先清除节点中的空格,比如:<GetPatientInfoResult nas="ss" />会变成<GetPatientInfoResult nas="ss"/>
    // xmlStr = xmlStr.replaceAll("\\s*/>", "/>");
    // xmlStr = xmlStr.substring(xmlStr.indexOf("<" + node + "/>"), xmlStr.indexOf("<" + node + "/>")
    // + ("<" + node + "/>").length());
    // }
    //
    // // 过滤掉xml声明,有些webservice返回的xml格式不是规范的xml格式
    // // xmlStr = xmlStr.replaceAll("<\\?xml\\s+version=\"1.0\"\\s+encoding=\"(.*?)\"\\?>", "");
    // xmlStr = xmlStr.replaceAll("<\\?xml([\\s\\S]*)\\?>", "");
    // return xmlStr;
    // }
    //
    // /**
    // * 递归检索xml响应报文格式
    // *
    // * @param rootDataNode
    // * @param tempXPath
    // * @return
    // */
    // public static String getResponseXPath(DataNode rootDataNode) {
    // StringBuilder xPath = new StringBuilder();
    // if (StringUtils.isNotBlank(rootDataNode.getName())) {
    // xPath.append("/").append(rootDataNode.getName());
    // }
    // if (rootDataNode.getDataNode() != null) {
    // String temp = getResponseXPath(rootDataNode.getDataNode());
    // xPath.append("/").append(temp);
    // }
    // return xPath.toString();
    // }
    //
    // /**
    // * 递归查找响应报文的参数定义
    // *
    // * @param dataNode
    // * @return
    // */
    // public static DataNode getResponseDataNode(DataNode dataNode) {
    // DataNode tempNode = dataNode;
    // if (dataNode.getDataNode() != null) {
    // tempNode = getResponseDataNode(dataNode.getDataNode());
    // }
    // return tempNode;
    // }
    //
    // /**
    // * 获取报文数据
    // *
    // * @param document
    // * @param dataNode
    // * @return
    // */
    // public static Map<String, Object> getResponseData(Document document, DataNode dataNode) {
    // Map<String, Object> map = new HashMap<String, Object>();
    // // 获取参数列表
    // List<Parameter> parameters = getResponseDataNode(dataNode).getParameters();
    // // 定位报文中的参数节点
    // String xPath = getResponseXPath(dataNode);
    // Node node = document.selectSingleNode(xPath);
    // if (node != null) {
    // Element element = (Element) node;
    // // 参数按目标名称为key,报文参数的值为value的方式存储
    // for (Parameter parameter : parameters) {
    // String value = getValue(parameter, element.element(parameter.getName()).getTextTrim());
    // if (StringUtils.isNotBlank(value)) {
    // map.put(parameter.getTargetName(), value);
    // }
    // }
    // }
    //
    // return map;
    // }
    //
    // /**
    // * 获取转换后的参数值
    // *
    // * @param parameter
    // * @param element
    // * @return
    // */
    // public static String getValue(Parameter parameter, String value) {
    // // 忽略掉无用参数
    // if (StringUtils.isNotBlank(parameter.getTargetName())) {
    // // 参数转换
    // if (parameter.getConverts() != null && parameter.getConverts().size() > 0) {
    // for (Convert convert : parameter.getConverts()) {
    // if (convert.getSourceData().equals(value)) {
    // value = convert.getTargetData();
    // break;
    // }
    // }
    // }
    // return value;
    // }
    // return null;
    // }
    //
    // /**
    // * 通过反射来赋值
    // *
    // * @param object
    // * @param map
    // * @return
    // */
    // public static Object getObject(Object object, Map<String, Object> map) {
    // if (map != null && map.size() > 0) {
    // Class<?> class1 = object.getClass();
    // for (String key : map.keySet()) {
    // try {
    // Field field = class1.getDeclaredField(key);
    // field.setAccessible(true);
    // field.set(object, map.get(key));
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // return object;
    // }
    //
    // /**
    // * 通过反射来取值
    // *
    // * @param object
    // * @param fieldName
    // * @return
    // */
    // public static Object getValue(Object object, String fieldName) {
    // Class<?> class1 = object.getClass();
    // try {
    // Field field = class1.getDeclaredField(fieldName);
    // field.setAccessible(true);
    // return field.get(object);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
    //
    // /**
    // * 通过反射执行某个方法来获取值
    // *
    // * @param className
    // * @param methodName
    // * @param value
    // * @return
    // */
    // public static Object getValue(String className, String methodName, Object value, String valueType) {
    // try {
    // Class<?> class1 = Class.forName(className);
    // Class<?> class2 = Class.forName(valueType);
    // Object obj = class1.newInstance();
    // Method method = class1.getDeclaredMethod(methodName, class2);
    // return method.invoke(obj, value);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
    //
    // public static void main(String[] args) {
    // String string =
    // "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetPatientInfoResponse xmlns=\"http://www.gdcc.com.cn/\"><GetPatientInfoResult   /></GetPatientInfoResponse></soap:Body></soap:Envelope>";
    // string = string.replaceAll("\\s*/>", "/>");
    // string = string.substring(string.indexOf("<GetPatientInfoResult/>"), string.indexOf("<GetPatientInfoResult/>")
    // + "<GetPatientInfoResult/>".length());
    // System.out.println(string);
    // }
}
