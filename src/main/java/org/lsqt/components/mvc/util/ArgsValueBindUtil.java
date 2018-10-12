package org.lsqt.components.mvc.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lsqt.components.context.ContextUtil;

public class ArgsValueBindUtil {
	private static ThreadLocal<Map<String,Object>> scopeThreadLocal=new ThreadLocal<Map<String,Object>>();
	
	private static final String CHAIN_SCOPE_KEY_CONTROLLER_METHOD_PARAM_VALUES = ArgsValueBindUtil.class.getName()
			.concat("#chain.current.controller.method.args.values");

	/**
	 * 获取上下文数据
	 * @return 返回empty 
	 */
	private static Map<String,Object> getContextMap() {
		if (scopeThreadLocal.get() == null) {
			Map<String, Object> contextMap = new ConcurrentHashMap<String, Object>();
			scopeThreadLocal.set(contextMap);
		}
		return scopeThreadLocal.get();
	}
	
	/**
	 * 绑定当前URL请求参数值到类的某个方法 (如:Controller的某个方法)
	 * @param method
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getMethodArgsValue(Method method) throws Exception {
		final String methodArgsValuekey = CHAIN_SCOPE_KEY_CONTROLLER_METHOD_PARAM_VALUES.concat(method.toString());

		Map<String, Object> contextMap = getContextMap();
		if (contextMap != null && contextMap.containsKey(methodArgsValuekey)) {
			return (List<Object>) contextMap.get(methodArgsValuekey);
		}

		Class<?>[] types = method.getParameterTypes();
		List<String> methodInputParamNames = ParameterNameUtil.getParameterNames(method);

		if (types.length != methodInputParamNames.size()) {
			throw new Exception("param value count and param name count are't equal~!");
		}

		// 1.填充表单
		List<Object> methodInputParamValues = new ArrayList<Object>();
		for (int i = 0; i < methodInputParamNames.size(); i++) {
			Parameter param = method.getParameters()[i];

			Class<?> paramType = types[i];
			String paramName = methodInputParamNames.get(i);
			Object paramValue = null;

			if (ActionFormUtil.isCanBeBaseType(paramType)) { // 处理常规类型
				paramValue = ActionFormUtil.prepareBaseValue(paramType, ContextUtil.getFormMap().get(paramName));

			} else if (ActionFormUtil.isCanBeDate(param)) { // 处理日期类型

				paramValue = ActionFormUtil.prepareDateValue(param, ContextUtil.getFormMap().get(paramName));

			} else if (ActionFormUtil.isCanBeBeanType(paramType)) { // 处理bean类型
				Object formBean = paramType.newInstance();
				paramValue = formBean;
				ActionFormUtil.fillBean(formBean, ContextUtil.getFormMap());
			}

			methodInputParamValues.add(paramValue);
		}

		getContextMap().put(methodArgsValuekey, methodInputParamValues);

		return methodInputParamValues;
	}
}

