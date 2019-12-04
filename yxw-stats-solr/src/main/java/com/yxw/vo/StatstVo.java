package com.yxw.vo;

import java.io.Serializable;

public class StatstVo implements Serializable {

	private static final long serialVersionUID = -3325185059766722238L;

	private int count;
	
	private int sum;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
