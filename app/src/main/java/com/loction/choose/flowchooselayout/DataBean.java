package com.loction.choose.flowchooselayout;

/**
 * Created by localadmin on 2017/12/21.
 */

public class DataBean {
	private String name;

	@Override
	public String toString() {
		return "DataBean{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
