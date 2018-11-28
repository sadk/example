package org.lsqt.code.springtest;

import java.util.Date;

public class Car {
	private Long id;
	private String name;
	private Date createDate;
	private Object [] colors;
	private User [] drivers;
	
	private Long [] cardNos;
	private Date  driverBirthday;
	
	public Car(Long id, String name, Date createDate, Object[] colors, User[] drivers) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.colors = colors;
		this.drivers = drivers;
	}

	public Object[] getColors() {
		return colors;
	}

	public void setColors(Object[] colors) {
		this.colors = colors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long[] getCardNos() {
		return cardNos;
	}

	public void setCardNos(Long[] cardNos) {
		this.cardNos = cardNos;
	}

	public Date getDriverBirthday() {
		return driverBirthday;
	}

	public void setDriverBirthday(Date driverBirthday) {
		this.driverBirthday = driverBirthday;
	}

	public User[] getDrivers() {
		return drivers;
	}

	public void setDrivers(User[] drivers) {
		this.drivers = drivers;
	}

}
