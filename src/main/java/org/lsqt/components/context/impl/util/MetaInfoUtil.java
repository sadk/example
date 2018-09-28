package org.lsqt.components.context.impl.util;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.lsqt.components.context.bean.BeanException;



final public class MetaInfoUtil {
	
	public static final String FILE_NAME_IN_META_INFO="framework-default.properties";
	
	private static final Properties FRAMEWORK_DEFAULT_PROPERTY = new Properties();
	
	public static Properties getProperties() throws BeanException{
		if(!FRAMEWORK_DEFAULT_PROPERTY.isEmpty()) return FRAMEWORK_DEFAULT_PROPERTY;
		
		Enumeration<URL> enums = null;
		try {
			enums = Thread.currentThread().getContextClassLoader().getResources("META-INF" + File.separator + FILE_NAME_IN_META_INFO);
		} catch (Exception ex) {
			throw new BeanException(ex);
		}

		if (enums == null)
			return null;

		for (Enumeration<URL> e = enums; e.hasMoreElements();) {
			URL url = e.nextElement();

			InputStream is = null;
			try {
				is = url.openStream();
				FRAMEWORK_DEFAULT_PROPERTY.load(is);
			} catch (Exception ex) {
				throw new BeanException(ex);
			} finally {
				close(is);
			}

		}
		return FRAMEWORK_DEFAULT_PROPERTY;
	}
	
    private final static void close(Closeable c) throws BeanException{
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
            	throw new BeanException(e);
            }
        }
    }
}
