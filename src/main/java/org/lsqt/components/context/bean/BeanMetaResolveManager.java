package org.lsqt.components.context.bean;

import java.util.List;

import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanException;



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
