package org.lsqt.sms.model;

import java.util.List;

public class Remark {
	// 签名名称
	private String sign;
	// 投放总量
	private int num;
	
	private List<Schedule> sche;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<Schedule> getSche() {
		return sche;
	}

	public void setSche(List<Schedule> sche) {
		this.sche = sche;
	}
	
	
}
