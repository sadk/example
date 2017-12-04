package org.lsqt.components.context.spi.bean.resolve;

import java.util.List;

import org.lsqt.components.context.spi.bean.BeanDefinition;
import org.lsqt.components.context.spi.bean.BeanException;

public interface BeanMetaResolve {
	
	List<BeanDefinition> resolve() throws BeanException;

	String[] getScanBase();

	void setScanBase(String... base);
}
