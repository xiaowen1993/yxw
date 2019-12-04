package com.thoughtworks.xstream.ext;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author homer.yang
 * @date 2015年11月11日
 */
public class XStreamCDATA {

	private static String PREFIX_CDATA = "<![CDATA[";
	private static String SUFFIX_CDATA = "]]>";

	// 生成带CDATA的XML
	private static XStream xstream_cdata = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {

		        @Override
		        @SuppressWarnings("rawtypes")
		        public void startNode(String name, Class clazz) {
			        super.startNode(name, clazz);
		        }

		        @Override
		        public String encodeNode(String name) {
			        return name;
		        }

		        // 对所有XML节点的转换都增加CDATA标记
		        @Override
		        protected void writeText(QuickWriter writer, String text) {
			        writer.write(PREFIX_CDATA);
			        writer.write(text);
			        writer.write(SUFFIX_CDATA);
		        }
	        };
		}
	});

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {

		        @Override
		        @SuppressWarnings("rawtypes")
		        public void startNode(String name, Class clazz) {
			        super.startNode(name, clazz);
		        }

		        @Override
		        public String encodeNode(String name) {
			        return name;
		        }
	        };
		}
	});

	public static XStream getXStream(boolean needCDATA) {
		return needCDATA ? xstream_cdata : xstream;
	}
}
