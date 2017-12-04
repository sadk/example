package org.lsqt.sys;

import java.util.Map;

import org.lsqt.components.context.spi.bean.BeanFactory;

public interface VariableDefinition {
	
	void setRunTimeObject(Map<String,Object> runtimeInstance);
	
	void puttingVariable(BeanFactory beanFactory);
}
