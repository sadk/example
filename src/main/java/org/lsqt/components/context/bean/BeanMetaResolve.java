package org.lsqt.components.context.bean;

import java.util.List;

import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanException;

public interface BeanMetaResolve {
	
	List<BeanDefinition> resolve() throws BeanException;

	String[] getScanBase();

	void setScanBase(String... base);
}
