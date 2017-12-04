package org.lsqt.sys.model;

import java.util.Date;

public class CodeTemplate {
	
	private Long id;
	private String name;
	private String code;
	private String projectCode;
	private String content;
	private String pkg;
	private String clazz;
	
	/**
	 * <pre>
	-- 模板类型
	-- ORO         0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml 
	-- Controller  5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java 
	-- Dao         3=Dao_Mybatis3.java 11=Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java
	-- Service     4=Service_Example.java  14=Service_Spring.java
	-- Model       15=Model.java
	-- Page        7=jsp 8=html 9=*.vm 10=*.ftl
	</pre>
	*/
	private Integer tmplType;
	public static final int TMPL_TYPE_ORO_MYBATIS3=0;
	public static final int TMPL_TYPE_ORO_HIBERNATE3=1;
	public static final int TMPL_TYPE_ORO_EXAMPLE=2;
	
	public static final int TMPL_TYPE_CONTROLLER_SPRING3MVC=5;
	public static final int TMPL_TYPE_CONTROLLER_STRUTS2=6;
	public static final int TMPL_TYPE_CONTROLLER_EXAMPLEMVC=20;
	
	public static final int TMPL_TYPE_DAO_MYBATIS3=3;
	public static final int TMPL_TYPE_DAO_HIBERNATE3=11;
	public static final int TMPL_TYPE_DAO_SPRING3JDBC=12;
	public static final int TMPL_TYPE_DAO_DBUTIL=13;
	
	public static final int TMPL_TYPE_SERVICE_EXAMPLE=4;
	public static final int TMPL_TYPE_SERVICE_SPRING=14;
	
	public static final int TMPL_TYPE_MODEL=15;
	
	public static final int TMPL_TYPE_PAGE_JSP=7;
	public static final int TMPL_TYPE_PAGE_HTML=8;
	public static final int TMPL_TYPE_PAGE_VM=9;
	public static final int TMPL_TYPE_PAGE_FTL=10;
	
	public String getTmplTypeDesc() {
		//ORO
		if(tmplType == TMPL_TYPE_ORO_MYBATIS3) {
			return "Mybatis3_Mapper.xml";
		}
		else if(tmplType == TMPL_TYPE_ORO_HIBERNATE3) {
			return "Hibernate3.hbm.xml";
		}
		else if(tmplType == TMPL_TYPE_ORO_EXAMPLE) {
			return "Example.ftl.sql.xml";
		}
		
		//Controller
		else if(tmplType == TMPL_TYPE_CONTROLLER_SPRING3MVC) {
			return "SpringMVC_Controller.java";
		}
		else if(tmplType == TMPL_TYPE_CONTROLLER_STRUTS2) {
			return "Struts2_Action.java";
		}
		else if(tmplType == TMPL_TYPE_CONTROLLER_EXAMPLEMVC) {
			return "ExampleMVC_Controller.java";
		}
		
		
		
		//Dao
		else if(tmplType == TMPL_TYPE_DAO_MYBATIS3) {
			return "Dao_Mybatis3.java";
		}
		else if(tmplType == TMPL_TYPE_DAO_HIBERNATE3) {
			return "Dao_Hibernate3.java";
		}
		else if(tmplType == TMPL_TYPE_DAO_SPRING3JDBC) {
			return "Dao_SpringJDBC.java";
		}
		else if(tmplType == TMPL_TYPE_DAO_DBUTIL) {
			return "Dao_DBUtil.java";
		}
		
		
		//Service
		else if(tmplType == TMPL_TYPE_SERVICE_EXAMPLE) {
			return "Service_Example.java";
		}
		else if(tmplType == TMPL_TYPE_SERVICE_SPRING) {
			return "Service_Spring.java";
		}
		
		//Model
		else if(tmplType == TMPL_TYPE_MODEL) {
			return "Model.java";
		}
		
		//Page
		else if(tmplType == TMPL_TYPE_PAGE_JSP) {
			return "*.jsp";
		}
		else if(tmplType == TMPL_TYPE_PAGE_HTML) {
			return "*.html";
		}
		else if(tmplType == TMPL_TYPE_PAGE_VM) {
			return "*.vm";
		}
		else if(tmplType == TMPL_TYPE_PAGE_FTL) {
			return "*.ftl";
		}
		
		return "";
		
	}
	
	
	private Integer tmplResolveType; // 模板解析方式 0=freemark 1=velocity
	public static final int TMPL_RESOLVE_TYPE_FREEMARK=0;
	public static final int TMPL_RESOLVE_TYPE_VELOCITY=1;
	
	public String getTmplResolveTypeDesc() {
		if(this.tmplResolveType == TMPL_RESOLVE_TYPE_FREEMARK) {
			return "Freemark";
		}else if(this.tmplResolveType == TMPL_RESOLVE_TYPE_VELOCITY) {
			return "Velocity";
		}
		return "";
	}
	
	private Integer sn;
	private String remark;
	private String appCode;
	
	private String gid;
	private Date createTime; 
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public Integer getTmplType() {
		return tmplType;
	}
	public void setTmplType(Integer tmplType) {
		this.tmplType = tmplType;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getTmplResolveType() {
		return tmplResolveType;
	}
	public void setTmplResolveType(Integer tmplResolveType) {
		this.tmplResolveType = tmplResolveType;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
