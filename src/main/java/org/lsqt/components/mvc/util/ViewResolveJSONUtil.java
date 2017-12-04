package org.lsqt.components.mvc.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.context.impl.util.CacheReflect;
import org.lsqt.components.mvc.spi.UrlMappingDefinition;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;


public class ViewResolveJSONUtil {
	private  static final Logger log = Logger.getLogger(ViewResolveJSONUtil.class.getName());
	
	private static final DateValueFilter DATE_VALUE_FILTER = new DateValueFilter() ;
	
	public static void resolve(OutputStream out,UrlMappingDefinition urlMapDefinition,Object dataModel) throws IOException  {
		if(urlMapDefinition == null || urlMapDefinition.getMethod() == null) return ;
		
		RequestMapping requestMapping =urlMapDefinition.getMethod().getAnnotation(RequestMapping.class);
		if(requestMapping == null ) return ;
		if(requestMapping.view() != View.JSON) return ;
		
	
		
		if (dataModel != null) {
			if (String.class.isAssignableFrom(dataModel.getClass())) {
				out.write((dataModel + "").getBytes("UTF-8"));
			} else {
				/*
				 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
				 * WriteMapNullValue——–是否输出值为null的字段,默认为false
				 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
				 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
				 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
				 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
				 */
				String json = JSON.toJSONString(dataModel, DATE_VALUE_FILTER,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
				out.write(json.getBytes("UTF-8"));
			}
		} else {
			out.write("".getBytes("UTF-8"));
		}
	}
	
	public final static class DateValueFilter implements ValueFilter {
		
		@Override
		public Object process(Object object, String name, Object value) {
			if(value == null) return value;
			
			String parttern = getDateField(object, name) ;
			if(StringUtil.isNotBlank(parttern)){ //日期类型
				SimpleDateFormat df = new SimpleDateFormat(parttern);
				return df.format(value) ;
			} else {
				return value;
			}
			
		}
		
		private String getDateField(Object object ,String name) {
			List<Field> list = CacheReflect.getBeanField(object.getClass());
			if(list == null || list.size() == 0) return null;
			for (Field f : list) {
				if(name.equals(f.getName()) && Date.class.isAssignableFrom(f.getType())){
					Pattern pattern = f.getAnnotation(Pattern.class);
					if(pattern!=null && StringUtil.isNotBlank(pattern.value())){
						return pattern.value();
					}
				}
			}
			return null;
		}
	}
}
