package org.lsqt.components.mvc;

import static java.util.Objects.isNull;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.CacheReflectUtil;
import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * JSON视图处理器
 * @author mm
 *
 */
public class JSONViewHandler implements ViewHandler{
	private static final Logger log = LoggerFactory.getLogger(JSONViewHandler.class);
	
	private static final DateValueFilter DATE_VALUE_FILTER = new DateValueFilter() ;
	
	private HttpServletResponse response;
	 
	public JSONViewHandler(HttpServletResponse response) {
		this.response = response;
	}
	
	public <T> T resolve(UrlMappingDefinition urlMapping, Object modelAndView) throws Exception {
		if (isNull(urlMapping) || isNull(urlMapping.getMethod())) {
			return null;
		}

		RequestMapping requestMapping = urlMapping.getMethod().getAnnotation(RequestMapping.class);
		if (isNull(requestMapping) || requestMapping.view() != View.JSON) {
			return null;
		}

		View view = requestMapping.view();
		if (view != View.JSON) {
			return null;
		}
		
		
		response.setContentType("application/json; charset=utf-8");

		OutputStream out = response.getOutputStream();

		if (modelAndView != null) {
			if (String.class.isAssignableFrom(modelAndView.getClass())) {
				out.write(((String) modelAndView).getBytes("UTF-8"));
			} else {
				/*
				 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
				 * WriteMapNullValue——–是否输出值为null的字段,默认为false
				 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
				 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
				 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
				 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
				 */
				String json = JSON.toJSONString(modelAndView, DATE_VALUE_FILTER,
						SerializerFeature.DisableCircularReferenceDetect, 
						SerializerFeature.PrettyFormat,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.WriteMapNullValue);
				out.write(json.getBytes("UTF-8"));
			}
		} else {
			/*
			try {
				out.write("".getBytes("UTF-8"));
			} catch (Exception ex) {
				// 如果有controller控制关闭
				ex.printStackTrace();
				log.info("{}#write was invoked, The output stream has been shut down in advance.",out.getClass().getName());
			}
			*/
		}
		
		out.flush();
		
		return null;
	}

	private static class DateValueFilter implements ValueFilter {
		
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
			List<Field> list = CacheReflectUtil.getBeanField(object.getClass());
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

