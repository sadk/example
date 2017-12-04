package org.lsqt.code.springtest;

public class ObjectFactory {
	public ObjectFactory() {
		
	}
	public <T> T getBean(Class<T> clazz) {
		try {
			return   clazz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
