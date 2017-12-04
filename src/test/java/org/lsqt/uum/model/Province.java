package org.lsqt.uum.model;

import java.util.Date;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Scope;
import org.lsqt.components.context.annotation.model.Pattern;

@Component(scope=Scope.PROTOTYPE)
public  class Province {
	private String name ;
	private Long total ;
	
	@Pattern("yyyy-MM-dd")
	private Date buildDate ;
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public UserTest getUser() {
		return user;
	}

	public void setUser(UserTest user) {
		this.user = user;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Inject
	private UserTest user;
	
	@Inject
	private City city;
	
	@Override
	protected void finalize() throws Throwable {
		//System.out.println("aaaaaaaaaaaa");
		super.finalize();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
}
