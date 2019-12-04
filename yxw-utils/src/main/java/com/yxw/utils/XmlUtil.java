package com.yxw.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.omg.CORBA.portable.ApplicationException;

import com.yxw.exception.UtilsException;

public class XmlUtil {
	private static Logger log = Logger.getLogger(XmlUtil.class);

	/**
	 * 载入一个xml文档
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param uri
	 *            文件路径
	 */
	public static Document loadXMLFile(String fileName) throws ApplicationException {
		Document document = null;
		SAXReader saxReader = new SAXReader();
		File file = new File(fileName);
		if (!file.exists()) {
			throw new UtilsException("文件" + fileName + "未找到.");
		}
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			log.error("load xml occur error : ", e);
			throw new UtilsException("load xml occur error : ", e);
		}

		return document;
	}

	/**
	 * 创建xml
	 * 
	 * @return
	 */
	public static Document createXmlDocument() {
		return DocumentHelper.createDocument();
	}

	/**
	 * doc2String 将xml文档内容转为String
	 * 
	 * @return 字符串
	 * @param document
	 */
	public static String document2String(Document document) {
		String str = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("  ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			str = out.toString("UTF-8");
		} catch (Exception e) {
			log.error("document2String occur error : ", e);
			throw new UtilsException("document2String occur error : ", e);
		}
		return str;
	}

	/**
	 * string2Document 将字符串转为Document
	 * 
	 * @return
	 * @param str
	 *            xml格式的字符串
	 */
	public static Document string2Document(String str) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(str);
		} catch (Exception e) {
			log.error("string2Document occur error : ", e);
			throw new UtilsException("string2Document occur error : ", e);
		}
		return doc;
	}

	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static boolean doc2XmlFile(Document document, String fileName) throws ApplicationException {
		boolean flag = true;
		/* 将document中的内容写入文件中 */
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		File file = new File(fileName);
		if (!file.exists()) {
			throw new UtilsException("文件" + fileName + "未找到.");
		}
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			log.error("write " + fileName + " UTF-8 error : ", e);
			throw new UtilsException("write " + fileName + " UTF error : ", e);
		} catch (IOException e) {
			log.error("write " + fileName + " IO occur error : ", e);
			throw new UtilsException("write " + fileName + " IO occur error : ", e);
		}
		return flag;
	}

	/**
	 * 获取一个节点
	 * 
	 * @param doc
	 * @param xmlPath
	 * @return
	 */
	public static Element selectSingleNode(Document doc, String xmlPath) {
		Element el = (Element) doc.selectSingleNode(xmlPath);
		return el;
	}

	/**
	 * 获取一个节点的文本
	 * 
	 * @param doc
	 * @param xmlPath
	 * @return
	 */
	public static String getSelectSingleText(Document doc, String xmlPath) {
		Element el = selectSingleNode(doc, xmlPath);
		String text = el.getText();
		return text;
	}

	/**
	 * 获取一个节点的属性
	 * 
	 * @param doc
	 * @param xmlPath
	 * @param attributName
	 * @return
	 */
	public static String getSelectSingleAttribute(Document doc, String xmlPath, String attributName) {
		Element el = selectSingleNode(doc, xmlPath);
		String descrition = el.attributeValue(attributName);
		return descrition;
	}

	/**
	 * 获取节点
	 * 
	 * @param doc
	 * @param xmlPath
	 * @return
	 */
	public static List<Element> selectNodes(Document doc, String xmlPath) {
		List<Element> elList = (List<Element>) doc.selectSingleNode(xmlPath);
		return elList;
	}

	/**
	 * 功能说明：
	 * 
	 * @author jacobliang
	 * @param doc
	 * @param xmlPath
	 * @return
	 * @throws ApplicationException
	 * @time Dec 30, 2011 2:24:15 PM
	 */
	public static List<String> selectNodesText(Document doc, String xmlPath) {
		List<String> textList = new ArrayList<String>();
		List<Element> elements = (List<Element>) doc.selectNodes(xmlPath);
		if (elements != null && elements.size() > 0) {
			for (Element element : elements) {
				textList.add(element.getText());
			}
		}
		return textList;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws IOException {
		log.info("XML：【" + strxml + "】");
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		Document doc = string2Document(strxml);
		Element root = doc.getRootElement();

		List list = root.elements();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.elements();
			if (children.isEmpty()) {
				v = e.getTextTrim();
			} else {
				v = XmlUtil.getChildrenText(children);
			}

			m.put(k, v);
		}

		//关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextTrim();
				List list = e.elements();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XmlUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		// String filePath = "src/com/bhtec/common/tools/MainFrame.xml";
		// Document doc = null;
		// try {
		// doc = loadXMLFile(filePath);
		// } catch (ApplicationException e) {
		// e.printStackTrace();
		// }
		// List<Element> elList = doc.selectNodes("//module/fieldNames/field");
		// for (Element e : elList) {
		// String val = e.attributeValue("name");
		// System.out.println("-------->  " + val);
		// }
		//
		// Element el2 = (Element) doc.selectSingleNode("/module/fieldNames/field[@name=\"funName\"]");
		// System.out.println(el2.getText());
		// el2.setText("abc");
		// try {
		// doc2XmlFile(doc, filePath);
		// } catch (ApplicationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// List<Element> elList2 = doc.selectNodes("//module/funArea/area");
		// System.out.println(elList2.size());

	}

}
