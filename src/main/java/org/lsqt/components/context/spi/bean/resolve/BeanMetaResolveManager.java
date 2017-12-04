package org.lsqt.components.context.spi.bean.resolve;

import java.util.List;

import org.lsqt.components.context.spi.bean.BeanDefinition;
import org.lsqt.components.context.spi.bean.BeanException;



/**
 * 
 * bean元信息解析管理器
 * @author Sky
 *
 */
public interface BeanMetaResolveManager  {
	/**
	 * 注册bean元信息解析器
	 * @param resolver
	 */
	void regist(BeanMetaResolve resolver);
	
	/**
	 * 
	 * @return
	 * @throws BeanMetaResolveException
	 */
	List<BeanDefinition>  resolve() throws BeanException;
}
