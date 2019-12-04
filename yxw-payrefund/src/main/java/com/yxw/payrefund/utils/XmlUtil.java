package com.yxw.payrefund.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.ext.XStreamCDATA;

/**
 * @author homer.yang
 */
public class XmlUtil {
    
    private static final String XML_FORMAT_INDENT = "    ";
    
    public static Document getXML(String xmlFileName) throws DocumentException {
        SAXReader xmlReader = new SAXReader();
        return xmlReader.read(XmlUtil.class.getClassLoader().getResource(xmlFileName));
    }
    
    public static String getFormatXMLString(String xmlFileName) throws DocumentException, IOException {
        return getFormatXMLString(getXML(xmlFileName).getRootElement());
    }
    
    public static String getFormatXMLString(Document document) throws IOException {
        return getFormatXMLString(document.getRootElement());
    }
    
    public static String getFormatXMLString(Element element) throws IOException {
        String formatXML = null;
        XMLWriter writer = null;
        if (element != null) {
            try {
                StringWriter stringWriter = new StringWriter();
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setIndent(XML_FORMAT_INDENT);
                
                writer = new XMLWriter(stringWriter, format);
                writer.write(element);
                writer.flush();
                formatXML = stringWriter.getBuffer().toString().trim();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return formatXML;
    }
    
    
    /**
     * XML转换成JavaBean
     */
    public static <T> T convertToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return t;
    }
    
    public static String xstreamToXml(XStream xstream, Object obj) {
        if (xstream == null) {
            xstream = XStreamCDATA.getXStream(false);
        }
        xstream.processAnnotations(obj.getClass()); // 识别类中的注解
        //xstream.alias("alipay", obj.getClass());
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(obj);
    }
    
    public static String xstreamToXml(Object obj) {
        return xstreamToXml(null, obj);
    }
    
    public static String xstreamToCDATAXml(Object obj) {
        return xstreamToXml(XStreamCDATA.getXStream(true), obj);
    }
    
    public static <T> T xstreamToBean(String xml, Class<T> c) {
        XStream xstream = XStreamCDATA.getXStream(true);
        xstream.processAnnotations(c); // 识别类中的注解
        //xstream.alias("alipay", obj.getClass());
        
        //忽略比实体类多的节点,否则会报异常
        xstream.ignoreUnknownElements();
        return (T) xstream.fromXML(xml);
    }
    
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "utf-8", false);
    }
    
    public static String convertToXml(Object obj, String encoding) {
        return convertToXml(obj, encoding, false);
    }
    
    public static String convertToFormatXml(Object obj) {
        return convertToXml(obj, "utf-8", true);
    }
    
    public static String convertToFormatXml(Object obj, String encoding) {
        return convertToXml(obj, encoding, true);
    }
    
    /**
     * JavaBean转换成XML
     */
    public static String convertToXml(Object obj, String encoding, Boolean format) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 决定是否在转换成XML时同时进行格式化（即按标签自动换行，否则即是一行的XML）
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
            // XML的编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            // 决定 Marshaller 是否将生成文档级事件。如果未指定该属性，则默认为 false。将其设置为 true 时，根据所使用的编组 API，此属性具有不同的含义：（具体含义情景请查阅文档，此处只用到不加XML头）
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
