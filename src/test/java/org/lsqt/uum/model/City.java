package org.lsqt.uum.model;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.LifeCycle;

//@Component
public class City implements LifeCycle.Bean.CycleAfterBeanProperty{
	private String cname;
	@Inject
	private No no;

	@Override
	public void afterBeanPropertySet() {
		
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	
}
