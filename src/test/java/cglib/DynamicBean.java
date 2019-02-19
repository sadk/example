package cglib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.components.context.CacheReflectUtil;
import org.lsqt.sys.model.ApplicationQuery;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;


/**
 * <pre>
 * 给对象动态添加属性
 * 如:
 * 		Object bean = new DynamicBean(query,propertyMap)
				.buildTargetBean()
				.appendTargetPropertyValue(propertyValue)
				.toFinalBean();
 * </pre>
 * @author mm
 *
 */
public class DynamicBean {

	private Object object = null;// 动态生成的类对象
	private BeanMap beanMap = null;// 存放属性名称以及属性的类型

	private Object sourceBean;
	private Map<String,Class<?>> appendPropertyMap ;
	public DynamicBean() {
		super();
	}

	public DynamicBean(Object sourceBean,Map<String,Class<?>> propertyMap) {
		this.appendPropertyMap = new HashMap<>();
		this.appendPropertyMap.putAll(propertyMap); //克隆要追加的属性
		
		this.sourceBean = sourceBean;
		this.object = generateBean(propertyMap); //生成空对象的表示:net.sf.cglib.empty.Object
		this.beanMap = BeanMap.create(this.object); //当前对象的属性数据表示
		
		
	}

	/**
	 * @param propertyMap
	 * @return
	 */
	private Object generateBean(Map<String, Class<?>> propertyMap) {

		//1.源对象属性
		Class<?> type = sourceBean.getClass();
		List<Field> fieldList = CacheReflectUtil.getBeanField(type);
		for (Field e: fieldList) {
			propertyMap.put(e.getName(), e.getType());
		}
		
		//2.添加用户想要加的属性
		BeanGenerator generator = new BeanGenerator();
		Set<String> keySet = propertyMap.keySet();
		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			generator.addProperty(key, (Class<?>) propertyMap.get(key));
		}
		
		return generator.create();
	}

	/**
	 * 得到该实体bean对象
	 * @return
	 * @throws Exception 
	 */
	public DynamicBean buildTargetBean() throws Exception {
		// 1.源对象属性利用反射拷贝
		Class<?> type = sourceBean.getClass();
		Map<Field, Method> map = CacheReflectUtil.getBeanFieldMethodMapping(type, true);
		for (Field e : map.keySet()) {
			Method m = map.get(e);
			//if(BeanUtil.isCanGetter(m)) {
				beanMap.put(e.getName(), map.get(e).invoke(sourceBean));
			//}
		}

		return this;
	}
	
	public DynamicBean appendTargetPropertyValue(Map<String,Object> propValue) {
		beanMap.putAll(propValue);
		return this;
	}


	public Object toFinalBean() {
		return this.object;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {

		Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
		propertyMap.put("permissionSQL", String.class);

		Map<String, Object> propertyValue = new HashMap<>();
		propertyValue.put("permissionSQL", " and (name= '张三' and sex = 0 )");

		
		ApplicationQuery query = new ApplicationQuery();
		query.setId(234L);
		query.setName("张三");

		Object bean = new DynamicBean(query,propertyMap)
				.buildTargetBean()
				.appendTargetPropertyValue(propertyValue)
				.toFinalBean();
		
		Class<?> clazz = bean.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].getName());
			
			if(methods[i].getName().equals("getPermissionSQL")) {
				System.out.println(methods[i].invoke(bean));
			}
		}

	}

}
