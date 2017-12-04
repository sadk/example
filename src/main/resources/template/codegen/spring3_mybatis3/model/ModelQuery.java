package ${pkg};

import org.lsqt.components.db.Page;

/**
 * ${comment}
 */
public class ${Model}Query {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
	<#list columnList as column>
	<#if column.oroColumnType == 4>	
		/**${column.comment}*/	<#if column.javaType == 0>
		private String ${column.propertyName} ;
		</#if>
	 
		<#if column.javaType == 1>
		private Character ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 2>
		private Byte ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 3>
		private Short ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 4>
		private Integer ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 5>
		private Long ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 6>
		private Float ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 7>
		private Double ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 8>
		private Boolean ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 9>
		private java.util.Date ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 10>
		private java.math.BigDecimal ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 11>
		private java.math.BigInteger ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 12>
		private java.sql.Time ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 13>
		private java.sql.Date ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 14>
		private java.sql.TimeStamp ${column.propertyName} ;
		</#if>
		
		<#if column.javaType == 15>
		Byte [] ${column.propertyName} ;
		</#if>
	</#if>
	</#list>
	
	
	// getter、setter
	<#list columnList as column>
		<#if column.oroColumnType == 4>
			public void set${column.propertyName?cap_first} (<#if column.javaType == 0>String ${column.propertyName} </#if><#if column.javaType == 1> Character ${column.propertyName} </#if><#if column.javaType == 2> Byte ${column.propertyName} </#if> <#if column.javaType == 3> Short ${column.propertyName} </#if> <#if column.javaType == 4> Integer ${column.propertyName} </#if> <#if column.javaType == 5> Long ${column.propertyName} </#if> <#if column.javaType == 6> Float ${column.propertyName} </#if> <#if column.javaType == 7> Double ${column.propertyName}  </#if> <#if column.javaType == 8> Boolean ${column.propertyName}  </#if> <#if column.javaType == 9> java.util.Date ${column.propertyName}  </#if> <#if column.javaType == 10> java.math.BigDecimal ${column.propertyName} </#if> <#if column.javaType == 11> java.math.BigInteger ${column.propertyName}  </#if> <#if column.javaType == 12> java.sql.Time ${column.propertyName}  </#if> <#if column.javaType == 13> java.sql.Date ${column.propertyName} </#if> <#if column.javaType == 14> java.sql.TimeStamp ${column.propertyName} </#if> <#if column.javaType == 15> Byte [] ${column.propertyName} </#if> ) {
				this.${column.propertyName} = ${column.propertyName};
			}
			
			public <#if column.javaType == 0>String</#if><#if column.javaType == 1>Character</#if><#if column.javaType == 2>Byte</#if><#if column.javaType == 3>Short</#if><#if column.javaType == 4>Integer</#if><#if column.javaType == 5>Long</#if><#if column.javaType == 6>Float</#if><#if column.javaType == 7>Double</#if><#if column.javaType == 8>Boolean</#if><#if column.javaType == 9>java.util.Date</#if> <#if column.javaType == 10>java.math.BigDecimal</#if><#if column.javaType == 11>java.math.BigInteger</#if><#if column.javaType == 12>java.sql.Time</#if><#if column.javaType == 13>java.sql.Date</#if><#if column.javaType == 14>java.sql.TimeStamp</#if><#if column.javaType == 15>Byte []</#if>  
			get${column.propertyName?cap_first}() {
			return this.${column.propertyName};
			}
		</#if>
    
	</#list>

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
