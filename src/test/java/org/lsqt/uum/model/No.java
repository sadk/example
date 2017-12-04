package org.lsqt.uum.model;

import org.lsqt.components.context.annotation.Component;

//@Component
public class No {
	private String noCode ;
	public void run(){
		System.out.println("this is no's method!!!");
	}
	public String getNoCode() {
		return noCode;
	}
	public void setNoCode(String noCode) {
		this.noCode = noCode;
	}
}
