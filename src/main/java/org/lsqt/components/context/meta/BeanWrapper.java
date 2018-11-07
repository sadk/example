package org.lsqt.components.context.meta;

import org.lsqt.components.context.bean.BeanDefinition;

/**
 * @author Sky
 *
 */
public class BeanWrapper {

	private BeanDefinition beanDefinition ;
	
	private Object originalBean ;
	
	private boolean isWeakBean = true; //是否是差对象,通过class.newInstace() 实例化的，未完成bean的依赖关系
	
	public BeanWrapper(){}
	
	public BeanWrapper(BeanDefinition beanDefinition, Object originalBean, boolean isWeakBean ){
		this.beanDefinition = beanDefinition;
		this.originalBean = originalBean;
		this.isWeakBean = isWeakBean;
	}
	
	public boolean getIsWeakBean() {
		return this.isWeakBean;
	}
	
	public void setIsWeakBean(boolean isWeakBean) {
		this.isWeakBean = isWeakBean;
	}
	
	public BeanDefinition getBeanDefinition() {
		return beanDefinition;
	}
	public void setBeanDefinition(BeanDefinition beanDefinition) {
		this.beanDefinition = beanDefinition;
	}
	
	public Object getOriginalBean() {
		return originalBean;
	}

	public void setOriginalBean(Object originalBean) {
		this.originalBean = originalBean;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("--- "+originalBean+"\n");
		sb.append("    {\n");
		sb.append("        \"id\": \""+this.beanDefinition.getId()+"\",\n");
		sb.append("        \"name\": \""+this.beanDefinition.getName()+"\",\n");
		sb.append("        \"version\": \""+this.beanDefinition.getVersion()+"\",\n");
		sb.append("        \"scope\": \""+this.beanDefinition.getScope()+"\",\n");
		sb.append("        \"class\": \""+this.beanDefinition.getFullClassName()+"\",\n");
		sb.append("        \"lazyInit\": \""+this.beanDefinition.getLazyInit()+"\",\n");
		sb.append("        \"initMethod\": \""+this.beanDefinition.getInitMethod()+"\",\n");//在注解的方式下定义bean的前置和后置，请统一用生命周期接口实现init-method
		sb.append("        \"injectType\": \""+this.beanDefinition.getInjectType()+"\",\n");
		sb.append("        \"destroyMethod\": \""+this.beanDefinition.getDestroyMethod()+"\"\n");
		sb.append("        \"type\": \""+this.beanDefinition.getType()+"\"\n");
		sb.append("    }\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return originalBean.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return originalBean.equals(obj);
	}

	
}
