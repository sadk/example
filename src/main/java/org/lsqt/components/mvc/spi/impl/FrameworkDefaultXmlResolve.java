package org.lsqt.components.mvc.spi.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lsqt.components.mvc.spi.FrameworkConfigXml;
import org.lsqt.components.util.lang.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FrameworkDefaultXmlResolve implements FrameworkConfigXml{
	private static String FRAMEWORK_DEFAULT_XML="framework.cfg.xml";
	

	@Override
	public List<Pattern> getAnonymous() {
		List<Pattern> list = new ArrayList<>();
		
		try{
			InputStream is = new FileInputStream("D:\\workspace\\trunk\\nets-master\\nets-authority\\src\\main\\resources\\framework.cfg.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbd = dbf.newDocumentBuilder();
			Document doc = dbd.parse(is);
	
			XPathFactory f = XPathFactory.newInstance();
			XPath path = f.newXPath();
			NodeList beans = (NodeList) path.evaluate("config/mvc/url/anonymous/pattern", doc, XPathConstants.NODESET);
			
			if(beans!=null && beans.getLength()>0){
				for (int i=0;i<beans.getLength();i++){
					
					String name = getValue("name",beans.item(i));
					String value = getValue("value",beans.item(i));
					if(StringUtil.isBlank(value)) {
						value = beans.item(i).getTextContent().trim();
					}
					String module = getValue("module", beans.item(i));
					
					Pattern url = new Pattern();
					url.module= module;
					url.name = name;
					url.value = value;
					
					list.add(url);
				}
			}
			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		
		return list;
	}

	public static void main(String[] args) throws Exception {
		FrameworkDefaultXmlResolve t=new FrameworkDefaultXmlResolve();
		System.out.println( t.getAnonymous());
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static String getValue(String key, org.w3c.dom.Node node) {
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

	@Override
	public List<Pattern> getPermission() {
		 
		return null;
	}
	
}
