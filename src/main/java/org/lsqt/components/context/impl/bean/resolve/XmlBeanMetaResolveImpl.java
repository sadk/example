package org.lsqt.components.context.impl.bean.resolve;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lsqt.components.context.annotation.Scope;
import org.lsqt.components.context.impl.bean.meta.XmlBeanDefinition;
import org.lsqt.components.context.impl.bean.meta.XmlBeanDefinition.ProperyItem;
import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanException;
import org.lsqt.components.context.bean.XmlConfig.AttrFactoryConfig;
import org.lsqt.components.context.bean.XmlConfig.PropertyConfig;
import org.lsqt.components.context.bean.BeanMetaResolve;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.reflect.ClassLoaderUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析XML定义的bean元信息
 * @author Sky
 *
 */
public class XmlBeanMetaResolveImpl implements BeanMetaResolve {
	
	private final static String XML_BEAN_ATTR_ID="id";
	private final static String XML_BEAN_ATTR_NAME="name";
	private final static String XML_BEAN_ATTR_CLASS="class";
	private final static String XML_BEAN_ATTR_INIT_METHOD="init-method";
	private final static String XML_BEAN_ATTR_DESTROY_METHOD="destroy-method";
	private final static String XML_BEAN_ATTR_SCOPE = "scope";
	private final static String XML_BEAN_ATTR_FACTORY_BEAN = "factory-bean";
	private final static String XML_BEAN_ATTR_FACTORY_METHOD = "factory-method";
	
	private final static String SPRING_SCOPE_SINGLETON = "singleton";
	private final static String SPRING_SCOPE_PROTOTYPE = "prototype";
	private final static String SPRING_SCOPE_REQUEST = "request";
	private final static String SPRING_SCOPE_SESSION = "session";
	private final static String SPRING_SCOPE_GLOBAL_SESSION = "global session";
	
	@Override
	public String[] getScanBase() {
		
		return null;
	}


	@Override
	public void setScanBase(String... base) {
		
	}
	@Override
	public List<BeanDefinition> resolve() throws BeanException {
		List<BeanDefinition> list = new ArrayList<>();
        try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbd = dbf.newDocumentBuilder();
			Document doc = dbd.parse(new FileInputStream("D:\\workspace\\example\\src\\main\\resources\\dataSource.xml"));

			XPathFactory f = XPathFactory.newInstance();
			XPath path = f.newXPath();
			NodeList beans = (NodeList) path.evaluate("beans/bean", doc, XPathConstants.NODESET);
			
			
			for (int i = 0; i < beans.getLength(); i++) {
				BeanDefinition xmlBeanDefintion = new XmlBeanDefinition();
				Node node = beans.item(i);
				
				xmlBeanDefintion.setId(getValue(XML_BEAN_ATTR_ID,node));
				xmlBeanDefintion.setInitMethod(getValue(XML_BEAN_ATTR_INIT_METHOD, node));
				xmlBeanDefintion.setDestroyMethod(getValue(XML_BEAN_ATTR_DESTROY_METHOD, node));
				xmlBeanDefintion.setName(getValue(XML_BEAN_ATTR_NAME, node));
				xmlBeanDefintion.setScope(Scope.SINGLETON);
				xmlBeanDefintion.setFullClassName(getValue(XML_BEAN_ATTR_CLASS, node));
				xmlBeanDefintion.setBeanClass(ClassLoaderUtil.classForName(xmlBeanDefintion.getFullClassName()));
				xmlBeanDefintion.setLazyInit(true);
				xmlBeanDefintion.setShortClassName(xmlBeanDefintion.getBeanClass().getSimpleName());
				xmlBeanDefintion.setType(BeanDefinition.TYPE_STATIC);
				
				((AttrFactoryConfig)xmlBeanDefintion).setFacotryBeanId(getValue(XML_BEAN_ATTR_FACTORY_BEAN, node));
				((AttrFactoryConfig)xmlBeanDefintion).setFactoryMethodName(getValue(XML_BEAN_ATTR_FACTORY_METHOD, node));
				
				String scope = getValue(XML_BEAN_ATTR_SCOPE, node) ;
				if (StringUtil.isBlank(scope)) {
					scope = Scope.SINGLETON;
					
				} else if (SPRING_SCOPE_SINGLETON.equalsIgnoreCase(scope)) {
					scope = Scope.SINGLETON;
					
				} else if (SPRING_SCOPE_PROTOTYPE.equalsIgnoreCase(scope)) {
					scope = Scope.PROTOTYPE ;
					
				} else if (SPRING_SCOPE_REQUEST.equalsIgnoreCase(scope)) {
					scope = Scope.PROTOTYPE;
					
				} else if (SPRING_SCOPE_SESSION.equalsIgnoreCase(scope)) {
					scope = Scope.SESSION;
					
				} else if (SPRING_SCOPE_GLOBAL_SESSION.equalsIgnoreCase(scope)) {
					scope = Scope.SESSION;
					
				} else {
					throw new BeanException("unsupport ["+scope+"] scope~!") ;
				}
				xmlBeanDefintion.setScope(scope);
				
				
				
				if (StringUtil.isNotBlank(xmlBeanDefintion.getId())) {
					xmlBeanDefintion.setInjectType(BeanDefinition.INJECT_TYPE_ID);
					
				} else if(StringUtil.isNotBlank(xmlBeanDefintion.getName())) {
					xmlBeanDefintion.setInjectType(BeanDefinition.INJECT_TYPE_NAME);
					
				} else {
					xmlBeanDefintion.setInjectType(BeanDefinition.INJECT_TYPE_CLASS_IMPL);
					
				}

				list.add(xmlBeanDefintion);
				
				PropertyConfig def = null;
				if (xmlBeanDefintion instanceof PropertyConfig) {
					def = (PropertyConfig) xmlBeanDefintion;
				}

				if (def == null) continue;
				 
				for (int n = 0; n < node.getChildNodes().getLength(); n++) {
					Node nd = node.getChildNodes().item(n);
					if ("property".equals(nd.getNodeName())) {
						ProperyItem item = new ProperyItem();
						item.setName(getValue(XML_BEAN_ATTR_NAME, nd));

						String value = getValue("value", nd);
						if (StringUtil.isNotBlank(value)) {
							item.setValue(value);
						} else {
							item.setValue(nd.getTextContent());
						}
						def.getPropertyList().add(item);
					}
				}
			}

        } catch (Exception e) {
           throw new BeanException(e);
        }
		return list;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static String getValue(String key, Node node) {
		if (node == null) {
			return null;
		}
		NamedNodeMap map = node.getAttributes();

		node = map.getNamedItem(key);
		if (node == null) {
			return null;
		}

		String pairValue = node.toString().replace("\"", "");
		if (pairValue.split("=").length == 2) {
			return pairValue.split("=")[1];
		}
		return null;
	}

	public static void main(String args[]) throws Exception{
		 XmlBeanMetaResolveImpl res = new XmlBeanMetaResolveImpl();
	 
		res.resolve();
		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbd = dbf.newDocumentBuilder();
		Document doc = dbd.parse(new FileInputStream("D:\\workspace\\example\\src\\main\\resources\\Spring.xml"));

		XPathFactory f = XPathFactory.newInstance();
		XPath path = f.newXPath();
		NodeList beans = (NodeList) path.evaluate("//bean", doc, XPathConstants.NODESET);
		//System.out.println(beans.getLength());
		for (int i=0;i<beans.getLength();i++){
			System.out.println(getValue("class",beans.item(i))
					+" id:"+getValue(XML_BEAN_ATTR_ID, beans.item(i))
					+" factory-bean:"+getValue(XML_BEAN_ATTR_FACTORY_BEAN, beans.item(i))
					+" factory-method:"+getValue(XML_BEAN_ATTR_FACTORY_METHOD, beans.item(i)));
		}
	}
	 
}
