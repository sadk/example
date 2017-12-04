package org.lsqt.components.context.impl.bean.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.lsqt.components.context.impl.bean.meta.BeanWrapper;
import org.lsqt.components.context.impl.bean.resolve.XmlBeanMetaResolveImpl;
import org.lsqt.components.context.spi.bean.BeanDefinition;
import org.lsqt.components.context.spi.bean.BeanException;
import org.lsqt.components.context.spi.bean.BeanFactory;
import org.lsqt.components.context.spi.bean.resolve.BeanMetaResolve;
import org.lsqt.components.util.lang.StringUtil;

/**
 * spring 容器启动
 * 实则为配置在spring.xml里的bean进行实例化，跟spring没有任何关系，
 * 依托了spring的配置文件的格式,其它项目的bean组件可以直接插拔过来重用
 * @author yuanmm
 *
 */
public class XmlBeanFactory implements BeanFactory{
	
	private final List<BeanWrapper> singletonObjectList = new ArrayList<BeanWrapper>();
	private final List<BeanDefinition> beanDefinitionList = new ArrayList<BeanDefinition>();
	
	public void buildBeanMeta() {
		 final BeanMetaResolve xmlResolve = new XmlBeanMetaResolveImpl();
		 this.beanDefinitionList.addAll(xmlResolve.resolve());
	}
	
	public void buildBeanInstance() throws BeanException{
		if(beanDefinitionList == null || beanDefinitionList.size()==0) return ;
		
		checkBeanIdUnique();
		
		for(BeanDefinition d:beanDefinitionList) {
			try{
				BeanWrapper object = new BeanWrapper(d,d.getBeanClass().newInstance(),true);
				
				//数据源的实例化处理
				if (DataSource.class.isAssignableFrom(d.getBeanClass())) {
					//System.out.println(DataSource.class.isAssignableFrom(d.getBeanClass()));
					
				} else {
					singletonObjectList.add(object);
				}
			}catch(Exception ex){
				throw new BeanException(ex.getMessage());
			}
		}
		
	}

	
	/**
	 * 容器实例化的整个流程,方法3.
	 * 构建IOC容器管理的单例bean属性
	 */
	public void buildBeanDependency() {
		final Map<Field, Integer> fieldInfo = new LinkedHashMap<Field, Integer>();
		final Set<BeanWrapper> singletonCompleted = new LinkedHashSet<BeanWrapper>(); // 用于标记容器内已经依赖关系成立的单例bean
		for(BeanWrapper obj:singletonObjectList) {
			// 初使化后 init-method 调用 、 处理 factory-bean="xxxxxBeanFactory" factory-method="getInstance"
			// 处理property有集合或数组、属性是bean（或bean里面的属性又有bean ...)
			// 处理constructor-arg 构造函数参数里面有bean、list或数组
			
		}
	}
	
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeanException {
		if (this.beanDefinitionList == null || this.beanDefinitionList.size() == 0) {
			return null;
		}
		for (BeanDefinition b: beanDefinitionList) {
			try {
				BeanWrapper object = new BeanWrapper(b,b.getBeanClass().newInstance(),true);
				singletonObjectList.add(object);
				
			} catch ( Exception e) {
				e.printStackTrace();
				throw new BeanException(e.getMessage(),e);
			}  
			System.out.println(b);
		}
		return null;
	}
	
	@Override
	public <T> T getBean(String id) throws BeanException {
		// TODO Auto-generated method stub
		return null;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void checkBeanIdUnique() {
		Map<String, Integer> cnt = new HashMap<String, Integer>();
		for (BeanDefinition e : beanDefinitionList) {
			String id = e.getId();
			for (BeanDefinition n : beanDefinitionList) {
				if (e == n)
					continue;
				if (id.equals(n.getId())) {
					if (cnt.containsKey(id)) {
						cnt.put(id, cnt.get(id) + 1);
					} else {
						cnt.put(id, 0);
					}
				}
			}
		}
		//System.out.println(cnt);
		
		for (Integer n : cnt.values()) {
			if (n > 1)
				throw new BeanException("bean id is not unique,please check it~!");
		}
	}
	
	public static void main(String args[]) throws IOException{
		XmlBeanFactory xmlBeanFactory = new XmlBeanFactory();
		
		xmlBeanFactory.buildBeanMeta();
		xmlBeanFactory.buildBeanInstance();
		xmlBeanFactory.buildBeanDependency();
		
		//Db db = xmlBeanFactory.getBean(Db.class);
		//Long t =  db.queryForObject("select 1", Long.class);
		//System.out.println(t);
		
		File path=new File("D:\\workspace\\example\\src\\main\\resources\\dataSource.xml");
		FileReader fr=new FileReader(path);//获取文件流
		BufferedReader br = new BufferedReader(fr);
		String str;
		StringBuilder xml = new StringBuilder();
		while((str=br.readLine())!=null){
			if(StringUtil.isNotBlank(str)){
				xml.append(str+"\n");
			}
		}
		
		System.out.println(xml);
		 
	}
}
