package org.lsqt.uum.model;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Scope;
import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.db.annotation.Id;
import org.lsqt.components.db.annotation.Id.Type;

@Component(scope=Scope.PROTOTYPE)
public class UserTest {
	
	@Id(type=Type.AUTO)
	private Long id;
	
	private String name;
	//private Date birthday;
	@Inject
	private  Province province;
	
	private City cityxxxx;
	
	//@Cascade(name="city_id",column="id")
	private City city;
	
	//生日
	@Pattern("yyyyMMdd")
	private Date birthday;
	
	//头像
	private File mytxt;
	
	//银行卡照
	private List<File> bankCard ;
	
	//借书卡
 	private File [] bookCard ;
 	
	public City getCityyy() {
		return cityyy;
	}
	public void setCityyy(City cityyy) {
		this.cityyy = cityyy;
	}
	private City cityyy;
	private int age ;
	
	public City getCityxxxx() {
		return cityxxxx;
	}
	public void setCityxxxx(City cityxxxx) {
		this.cityxxxx = cityxxxx;
	}
	
	public Province getProvince() {
		return province;
	}
	
	public void setProvince(Province province) {
		this.province = province;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	/*
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}*/
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public File getMytxt() {
		return mytxt;
	}
	public void setMytxt(File mytxt) {
		this.mytxt = mytxt;
	}
	public List<File> getBankCard() {
		return bankCard;
	}
	public void setBankCard(List<File> bankCard) {
		this.bankCard = bankCard;
	}
	public File[] getBookCard() {
		return bookCard;
	}
	public void setBookCard(File[] bookCard) {
		this.bookCard = bookCard;
	}
}
