package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 审批通用对象封装: 假定每个对象都有id和名称、编码
 * @author mmyuan
 *
 */
public class ApproveObject {
	private String id;
	private String pid;
	private String code;
	private String name;
	private String remark;
	private String appCode; //租户编码
	
	// --- 审批对象为用户时(非常、非常常用的字段!!!)
	private String email;
	private String mobile;
	
	// --- 审批对象，所有的详情信息
	private Map<String,Object> object = new HashMap<>();
	
	
	public static List<String> toIdList(List<ApproveObject> list) {
		List<String> idList = new ArrayList<>();
		if (list == null) {
			return idList;
		}
		Set<String> set = new HashSet<>();
		for (ApproveObject e : list) {
			set.add(e.getId());
		}
		return new ArrayList<>(set);
	}
	
	@SuppressWarnings({ "unchecked"})
	public static  <T> List<T> toIdList(Class<T> type, List<ApproveObject> list) {
		List<T> idList = new ArrayList<>();
		if (list == null) {
			return idList;
		}
		Set<T> set = new HashSet<>();
		for (ApproveObject e : list) {
			if (Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type)) {
				set.add((T)Byte.valueOf(e.getId()));
			} 
			
			if (Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type)) {
				set.add((T)Short.valueOf(e.getId()));
			}
			
			if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
				set.add((T)Integer.valueOf(e.getId()));
			}   
			
			if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
				set.add((T)Long.valueOf(e.getId()));
			}   
			
			if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)) {
				set.add((T)Float.valueOf(e.getId()));
			}
			
			if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) {
				set.add((T)Double.valueOf(e.getId()));
			}
			
			if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
				set.add((T)Boolean.valueOf(e.getId()));
			}
			
			if (java.lang.Character.class.isAssignableFrom(type) || char.class.isAssignableFrom(type)) {
				set.add((T)e.getId());
			}
			
			throw new RuntimeException("unsupport type: "+type);
		}
		return new ArrayList<>(set);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof ApproveObject)) {
			return false;
		}

		ApproveObject model = (ApproveObject) obj;
		if (model.getId() == null) {
			return false;
		}

		if (model.getId().equals(this.getId())) {
			return true;
		}
		return false;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Map<String, Object> getObject() {
		return object;
	}

	public void setObject(Map<String, Object> object) {
		this.object = object;
	}
	
}
