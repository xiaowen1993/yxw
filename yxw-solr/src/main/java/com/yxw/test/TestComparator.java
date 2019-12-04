package com.yxw.test;

import java.util.Map;
import java.util.TreeMap;

import com.yxw.solr.comparator.AscComparator;
import com.yxw.solr.comparator.DescComparator;

public class TestComparator {
	public static void main(String[] args) {
		compareMap();

	}

	public static void compareMap() {
		Map<String, String> map = new TreeMap<String, String>(new AscComparator());
		map.put("a", "12312");
		map.put("c", "bbb");
		map.put("f", "sdfsa");
		map.put("b", "AAA");

		System.out.println(map);

		Map<String, String> map1 = new TreeMap<String, String>(new DescComparator());
		map1.putAll(map);

		System.out.println(map1);

	}
}
