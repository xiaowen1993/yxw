package com.yxw.comparator;

import java.util.Comparator;

public class DescComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		return o2.compareTo(o1);
	}

}
