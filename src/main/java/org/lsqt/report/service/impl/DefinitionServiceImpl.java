package org.lsqt.report.service.impl;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.SqlStatementArgs;
import org.lsqt.components.db.orm.util.ModelUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.model.Resource;
import org.lsqt.report.model.ResourceQuery;
import org.lsqt.report.model.Variable;
import org.lsqt.report.model.VariableQuery;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.report.service.impl.support.FreemarkGenerateReportFile;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.sys.service.impl.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Service
public class DefinitionServiceImpl implements DefinitionService{
	private static final Logger log = LoggerFactory.getLogger(DefinitionServiceImpl.class);
	
	private static final String SQL_PARAM_REGEX = "\\$\\{[ ]*[a-zA-Z0-9]*[ ]*\\}"; //提取SQL参数名的正则
	
	@Inject private Db db;
	
	public Page<Definition>  queryForPage(DefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Definition.class, query);
	}

	public List<Definition> getAll(){
		  return db.queryForList("getAll", Definition.class);
	}
	
	public Definition saveOrUpdate(Definition model) {
		if (model.getId() == null) {
			model.setVersion("0.0.1."+System.currentTimeMillis());
		}
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		String sql = String.format("delete from %s where definition_id in (%s)",db.getFullTable(Column.class),StringUtil.join(Arrays.asList(ids)));
		db.executeUpdate(sql);
		
		return db.deleteById(Definition.class, Arrays.asList(ids).toArray());
	}

	public Definition getById(Long id) {
		return db.getById(Definition.class, id);
	}
	
	public void importColumn(Long id,Integer dataType,boolean isIncremental) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(dataType);
		
		if(!isIncremental) {// 增量导入不删除原有数据
			db.executeUpdate(String.format("delete from %s where definition_id=? and data_type = ? ",db.getFullTable(Column.class)), id,dataType);
		}
		
		
		// 之前已导入过的定义列
		List<org.lsqt.report.model.Column> temp = new ArrayList<>();
		ColumnQuery cq = new ColumnQuery();
		cq.setDefinitionId(id);
		cq.setDataType(dataType);
		List<org.lsqt.report.model.Column> dbColumnList = db.queryForList("queryForPage",org.lsqt.report.model.Column.class, cq);

		
		
		Definition model = db.getById(Definition.class, id);
		DataSource dsModel = db.getById(DataSource.class, model.getDatasourceId());

		DataSourceFactory dbfactory = new DataSourceFactory();
		dbfactory.setBaseDb(db);
		javax.sql.DataSource ds = dbfactory.getDataSouce(dsModel.getCode());

		List<org.lsqt.report.model.Column> reportColumnList = new ArrayList<>();
		
		
		

		
		
		Connection con = db.getCurrentConnection();
		try {
			Connection switchConn = ds.getConnection();
			db.setCurrentConnection(switchConn);
			db.executePlan(() -> {
				// 默认是mysql数据库
				String sqlWrap = "select * from (" + model.getColumnSql() + " ) t10000_amount limit 1"; // 防止用户把所有数据load出来!!!
				
				
				if (dsModel.getDialect() != null && (dsModel.getDialect() == Db.Dialect.Oracle10gDialect
						|| dsModel.getDialect() == Db.Dialect.Oracle9iDialect
						|| dsModel.getDialect() == Db.Dialect.OracleDialect)) {
					
					sqlWrap = "select * from (" + model.getColumnSql() + " ) t10000_amount where rownum=1";
					/*
					// bug fix :ORA-00918: column ambiguously defined
					int whereIdx = model.getColumnSql().toLowerCase().indexOf("where ");
					int groupbyIdx = model.getColumnSql().toLowerCase().indexOf("group by");
					if ((whereIdx!=-1 && groupbyIdx != -1) && (whereIdx < groupbyIdx)) {
						sqlWrap = model.getColumnSql();
					} 
					*/
				}

				List<org.lsqt.components.db.Column> list = db.getMetaDataColumn(sqlWrap);
				
				if(ArrayUtil.isNotBlank(list)) {
					
					for(org.lsqt.components.db.Column e: list) {
						org.lsqt.report.model.Column rptColumn = toRptColumn(dataType, model, e);
						reportColumnList.add(rptColumn);
					}
				}
				
				


				if (isIncremental && ArrayUtil.isNotBlank(dbColumnList)) {
					for (org.lsqt.report.model.Column e : reportColumnList) {
						boolean isExists = false;
						for (org.lsqt.report.model.Column c : dbColumnList) {
							if (e.getCode().equals(c.getCode())) {
								isExists = true;
								break;
							}
						}

						if (!isExists) {
							temp.add(e);
						}
					}

					reportColumnList.clear();
					reportColumnList.addAll(temp);
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.setCurrentConnection(con);
		}
		
		if (ArrayUtil.isNotBlank(reportColumnList)) {
			db.batchSave(reportColumnList);
		}
	}

	/**
	 * 
	 * @param dataType
	 * @param model 报表定义模型
	 * @param e 数据库列字段定义
	 * @return 
	 */
	private org.lsqt.report.model.Column toRptColumn(Integer dataType, Definition model,org.lsqt.components.db.Column e) {
		org.lsqt.report.model.Column rptColumn = new org.lsqt.report.model.Column();
		if("id".equalsIgnoreCase(e.getPropertyName()) || "pk".equalsIgnoreCase(e.getPropertyName())) {
			rptColumn.setPrimaryKey(Dictionary.YES);
		} else {
			rptColumn.setPrimaryKey(Dictionary.NO);
		}
		rptColumn.setDataType(dataType);
		rptColumn.setDbType(e.getDbType());
		rptColumn.setDefinitionId(model.getId());
		rptColumn.setJavaType(e.getJavaType());
		rptColumn.setName(e.getName());
		rptColumn.setCode(e.getName());
		rptColumn.setOptLog("自动解析导入报表列");
		rptColumn.setPropertyName(e.getPropertyName());
		rptColumn.setSearchType(Dictionary.NO);
		rptColumn.setComment(e.getText()); // 取的是SQL字段的别名
		rptColumn.setReportName(model.getName());
		rptColumn.setAllowExport(Dictionary.YES); //默认允许导出
		rptColumn.setAllowImport(Dictionary.NO); //不允许导入
		rptColumn.setSn(0);
		
		
		rptColumn.setWidth(120);
		rptColumn.setAlignType(org.lsqt.report.model.Column.ALIGN_TYPE_MID);
		rptColumn.setHidde(org.lsqt.report.model.Column.HIDE_NO);
		rptColumn.setFrozen( org.lsqt.report.model.Column.FROZEN_NO);
		rptColumn.setAllowSort(Dictionary.NO);
		return rptColumn;
	}
	
	public String generateReportFile(Long id) throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		String rootDir = request.getServletContext().getRealPath("/");
		
		Definition rpt = db.getById(Definition.class, id);

		if (rpt != null) {
			ColumnQuery query = new ColumnQuery() ;
			query.setDefinitionId(rpt.getId());
			query.setSortField("sn");
			query.setSortOrder("asc");
			query.setDataType(Column.DATA_TYPE_REPORT_SHOW);
			List<Column> list = db.queryForList("queryForPage", Column.class, query);
			
			ResourceQuery btnQuery = new ResourceQuery();
			btnQuery.setDefinitionId(rpt.getId());
			List<Resource> btnList = db.queryForList("queryForPage", Resource.class, btnQuery);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("definition", rpt);
			map.put("columnList", list);
			map.put("buttonList", btnList);
			
			
			String layoutDir = rootDir + "/apps/default/admin/report/layout";
			String input = null;
			 
			
			//单表报表
			if(Definition.LAYOUT_LEFT_RIGHT.equals(rpt.getLayout())) {
				input = layoutDir.concat("/left_right.jsp");
			} 
			else if(Definition.LAYOUT_UP_DOWN.equals(rpt.getLayout())) {
				input = layoutDir.concat("/up_down.jsp");
			}
			
			//主子表报表
			else if(Definition.LAYOUT_LEFT_UP_DOWN_SUB_REPORT.equals(rpt.getLayout())) { // 左上下
				input = layoutDir.concat("/left_up_down.jsp");
			}
			else if(Definition.LAYOUT_UP_MID_DOWN_SUB_REPORT.equals(rpt.getLayout())) { // 上中下
				input = layoutDir.concat("/up_mid_down.jsp");
			}
			else if(Definition.LAYOUT_LEFT_MID_RIGHT.equals(rpt.getLayout())) { // 左中右
				input = layoutDir.concat("/left_mid_right.jsp");
			}
			else {
				throw new UnsupportedOperationException("报表类型不支持");
			}
			
			String rptURI = "/apps/default/admin/report/files/report_"+rpt.getId()+".jsp";
			
			String output = rootDir + rptURI;
			FreemarkGenerateReportFile.generate(input, output, map);
			
			log.debug(output);
			
			rpt.setUrl(rptURI);
			db.update(rpt, "url");
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Page<Map<String,Object>> searchHttpJSON(Definition model,Map<String,Object> formMap) throws Exception {
		Page<Map<String,Object>> page = new Page.PageModel<>();
		String json = org.lsqt.report.util.HttpClient.post(model.getReportSql(), JSON.toJSONString(formMap));
		log.info("报表：{} id={}，返回：{}", model.getName(), model.getId(), json);
		Map<String, Object> rs = JSON.parseObject(json, Map.class);
		if (rs != null && rs.containsKey("data")) {
			Object data = rs.get("data");
			if(data instanceof List) {
				List<Map<String,Object>> list = (List<Map<String,Object>>)data;
				page.setData(list);
			}
			
			if(String.class.isAssignableFrom(data.getClass())) { // 服务器异常，data有可能返回字符说明(而不是list数据!!!)
				page.setHook(data.toString());
			}
		} else {
			page.setHook(json);
		}
		return page;
	}
	
	public Page<Map<String,Object>> search(Long id,Map<String,Object> formMap) throws Exception{
		
		class Result { public Page<Map<String,Object>> data; }
		Result rs = new Result();
		
		// 准备报表定义和列定义数据，因为queryData方法需要使用（因切换数据源，db。xxx方法将不在系统数据源下执行）
		Definition model = db.getById(Definition.class, id);
		if(model == null) {
			return new Page.PageModel<>();
		}
		
		if (Definition.TYPE_HTTP_JSON.equals(model.getType())) {
			return searchHttpJSON(model, formMap);
		}
		
		ColumnQuery query = new ColumnQuery();
		query.setDefinitionId(id);
		List<org.lsqt.report.model.Column> list = db.queryForList("queryForPage", org.lsqt.report.model.Column.class, query); //报表的列
		model.setColumnList(list);
		
		// 报表字典变量纳入到SQL渲染的上下文
		VariableQuery vquery = new VariableQuery();
		vquery.setDefinitionId(id);;
		List<Variable> varList = db.queryForList("queryForPage", Variable.class, vquery);
		if (ArrayUtil.isNotBlank(varList)) {
			for (Variable v : varList) {
				if (v.getType() == null) {
					continue;
				}
				
				if (Variable.TYPE_数据字典  == v.getType()) {
					List<Long> dictIdList = StringUtil.split(Long.class, v.getValue(), ",");
					if(ArrayUtil.isNotBlank(dictIdList)) {
						if(dictIdList.size() ==1) {
							Dictionary dict = db.getById(Dictionary.class, dictIdList.get(0));
							formMap.put(v.getCode(), dict);
						}else {
							DictionaryQuery dquery = new DictionaryQuery();
							dquery.setIdList(dictIdList);
							formMap.put(v.getCode(), db.queryForList("queryForPage", Dictionary.class, dquery));
						}
					}
				}
				
				if (Variable.TYPE_当前日期  == v.getType()) {
					formMap.put(v.getCode(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				}
				
				if (Variable.TYPE_常量字符 == v.getType()) {
					formMap.put(v.getCode(), v.getValue());
				}
				
			}
		}

		
		//报表数据源
		DataSource dsModel = db.getById(DataSource.class, model.getDatasourceId());

		DataSourceFactory dbfactory = new DataSourceFactory();
		dbfactory.setBaseDb(db);
		javax.sql.DataSource ds = dbfactory.getDataSouce(dsModel.getCode());

		Connection con = db.getCurrentConnection(); //系统数据源
		try {
			Connection switchConn = ds.getConnection();
			db.setCurrentConnection(switchConn); //切换到报表数据源!!!!
			db.executePlan(false,() -> { //查询不需要开启事务!!
				try {
					rs.data = queryData(db,model,formMap); //queryData方法体中所有方法已切换到报表数据源，如果中间方法有调用db.xxx方法的将不是当前系统数据库
				} catch (Exception e) {
					throw new DbException((e.getCause() == null ? e : e.getCause()));
				}
			});
		}catch(Exception ex) {
			throw ex;
		}finally {
			db.setCurrentConnection(con);
		}
		
		//跟据DB字段，转化成java属性字段
		List<Map<String, Object>> pojoData = new ArrayList<>();
		if (ArrayUtil.isNotBlank(rs.data.getData())) {
			for (Map<String, Object> row : rs.data.getData()) {
				Map<String,Object> pojoMap = new LinkedHashMap<>();
				
				List<String> keyList = MapUtil.toKeyList(row);
				for(String k: keyList) {
					for (org.lsqt.report.model.Column column: list) {
						if(k.equals(column.getCode())) {
							pojoMap.put(column.getPropertyName(), row.get(k));
							break;
						}
					}
				}
				pojoData.add(pojoMap);
			}
		}
		rs.data.setData(pojoData);
		
		
		return rs.data;
	}
	
	/**
	 * 执行报表查询 (注意在客户端切换数据源)
	 * @param db 报表数据源
	 * @param def 报表定义（需包含报表列定义）
	 * @param formMap 表单原始数据
	 * @return 报表数据
	 * @throws Exception
	 */
	private Page<Map<String, Object>> queryData(Db reportDb,Definition def,Map<String,Object> formMap) throws Exception {
		 
		if (def != null && !formMap.isEmpty()) {
			 
			if (def != null) {
				formMap = wrapFormMap(def, formMap);
				boolean countRequired = (def.getCountRequired()!= null && (Dictionary.YES == def.getCountRequired()));
				
				if (String.valueOf(Dictionary.YES).equals(def.getShowPager())) {
					String pageIndexParam = "pageIndex";
					String pageSizeParam = "pageSize";

					Integer pageIndex = 0;
					Integer pageSize = 20;
					if (StringUtil.isNotBlank(def.getPageIndexParam())) {
						pageIndexParam = def.getPageIndexParam();
					}

					if (StringUtil.isNotBlank(def.getPageSizeParam())) {
						pageSizeParam = def.getPageSizeParam();
					}

					Object pageIndexObj = formMap.get(pageIndexParam);
					if (pageIndexObj != null) {
						pageIndex = Integer.valueOf(pageIndexObj.toString());
					}

					Object pageSizeObj = formMap.get(pageSizeParam);
					if (pageSizeObj != null) {
						pageSize = Integer.valueOf(pageSizeObj.toString());
					}
					
					if (pageIndex < 0) {
						pageIndex = 0;
					}

					if (pageSize < 0) {
						pageSize = 20;
					}
					
					if(String.valueOf(Dictionary.YES).equals(def.getPreventSqlInjection())) { //防SQL注入启用
						SqlStatementArgs sqlStmt = renderSQLPrvInjectSQL(formMap,def.getReportSql());
						
						return reportDb.executeQueryForPage(sqlStmt.getSql(),countRequired, pageIndex,  pageSize, sqlStmt.getArgs().toArray());
						
					} else {
						String sql = renderSQL(formMap, def.getReportSql()); 
						return reportDb.executeQueryForPage(sql,countRequired, pageIndex,pageSize);
					}
				} else {
					List<Map<String,Object>> list = new ArrayList<>();
					
					if(String.valueOf(Dictionary.YES).equals(def.getPreventSqlInjection())) { //防SQL注入启用
						SqlStatementArgs sqlStmt = renderSQLPrvInjectSQL(formMap,def.getReportSql());
						list = reportDb.executeQuery(sqlStmt.getSql(), sqlStmt.getArgs().toArray());
					} else {
						String sql = renderSQL(formMap, def.getReportSql());
						list = reportDb.executeQuery(sql);
					}
					
					Page<Map<String,Object>> page = new Page.PageModel<>();
					page.setData(list);
					return page;
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 报表SQL执行，防止SQL注入 
	 * @param formMap 表单参数（包装后的基本类型）
	 * @param reportSQL 报表原始SQL串
	 * @return 返回可DB执行的SQL和参数
	 * @throws Exception 
	 */
	private SqlStatementArgs renderSQLPrvInjectSQL(Map<String, Object> formMap, String reportSQL) throws Exception {
		List<String> paramNameList = new ArrayList<>();
		List<String> expressList = new ArrayList<>(); 
		
		
		
		Pattern pattern = Pattern.compile(SQL_PARAM_REGEX);

		// 正则对象与字符串匹配
		Matcher matcher = pattern.matcher(reportSQL);
		// 匹配成功后打印出找到的结果
		while (matcher.find()) {
			String express = matcher.group();
			
			expressList.add(express);
			paramNameList.add(express.replace("$", "").replace("{", "").replace("}", "").trim());
		}

		List<Object> args = new ArrayList<>();
		for (int i=0;i<expressList.size();i++) {
			reportSQL = reportSQL.replace(expressList.get(i), "?");
			
			Object arg = formMap.get(expressList.get(i));
			if(arg!=null) { // 去掉空的参数,
				args.add(arg);
			}
		}
		
		SqlStatementArgs sqlStmt = new SqlStatementArgs();
		sqlStmt.setSql(renderSQL(formMap, reportSQL));
		sqlStmt.setArgs(args);
		return sqlStmt;
	}
	
	public static void main(String[] args) {
		
		List<String> paramNameList = new ArrayList<>();
		List<String> expressList = new ArrayList<>();
		
		String sql = "select * from table where 1=${enable   } and age=${   age} and sex=${sex} and name like '\\${adfdas}'"; //字付串里的会有个bug!!
		//书写正则表达式
		String regex="\\$\\{[ ]*[a-zA-Z0-9]*[ ]*\\}";

		//将正则表达式转成正则对象
		Pattern pattern =Pattern.compile(regex);

		//正则对象与字符串匹配
		Matcher matcher=pattern.matcher(sql);
		//匹配成功后打印出找到的结果                                                                      
		while(matcher.find()) {
			String express = matcher.group();
			expressList.add(express);
			paramNameList.add(express.replace("$", "").replace("{", "").replace("}", "").trim());
		}
		System.out.println(paramNameList);
		System.out.println(expressList);
	}
	
	/**
	 * 将表单数据转化成基本类型
	 * @param definition 报表定义(需包含报表列定义)
	 * @param formMap 原始的表单查询参数
	 */
	private Map<String,Object> wrapFormMap(Definition definition,Map<String,Object> formMap) {
		Map<String,Object> wrapMap = new HashMap<>();
		wrapMap.putAll(formMap);
		
		for (org.lsqt.report.model.Column e : definition.getColumnList()) {
			Object baseValue = null;
			
			if (e.getJavaType() != null) {
				// 字符
				if (org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_STRING == e.getJavaType()
						|| org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_CHARACTER == e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(String.class, blankText2Null(formMap,e.getPropertyName()));
					
					if(baseValue!=null && e.getLikeSearchIs()!=null) { //如果是模糊查询
						if(Dictionary.YES == e.getLikeSearchIs()) {
							if(e.getLikeSearchType()!=null && org.lsqt.report.model.Column.LIKE_SEARCH_TYPE_LEFT == e.getLikeSearchType()) {
								baseValue = baseValue+"%";
							}
							else if(e.getLikeSearchType()!=null && org.lsqt.report.model.Column.LIKE_SEARCH_TYPE_MID == e.getLikeSearchType()) {
								baseValue = "%"+baseValue+"%";
							}
							else if(e.getLikeSearchType()!=null && org.lsqt.report.model.Column.LIKE_SEARCH_TYPE_RIGHT == e.getLikeSearchType()) {
								baseValue = "%"+baseValue;
							}
							else if(e.getLikeSearchType()!=null && org.lsqt.report.model.Column.LIKE_SEARCH_TYPE_NO_WRAP == e.getLikeSearchType()) {
								//模糊查询不做包装处理
							}
							else {
								throw new UnsupportedOperationException("不支持的模类查询匹配类型");
							}
						}
					}
				}
				
				//数字
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_BYTE==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Byte.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_SHORT==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Short.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_INTEGER==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Integer.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_LONG==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Long.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_FLOAT==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Float.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_DOUBLE==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Double.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_MATH_BIGDECIMAL==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(BigDecimal.class, blankText2Null(formMap,e.getPropertyName()));
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_MATH_BIGINTEGER==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(BigInteger.class, blankText2Null(formMap,e.getPropertyName()));
				}
				
				//日期
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_UTIL_DATE==e.getJavaType()
						|| org.lsqt.components.db.Column.JAVA_TYPE_JAVA_SQL_TIME==e.getJavaType()
						|| org.lsqt.components.db.Column.JAVA_TYPE_JAVA_SQL_DATE==e.getJavaType()) {
					
					baseValue = ModelUtil.prepareBaseValue(BigInteger.class, blankText2Null(formMap,e.getPropertyName()));
					
					List<String> keyList = MapUtil.toKeyList(formMap);
					for(String k: keyList) {
						String dateBegin = e.getPropertyName().concat("Begin");
						String dateEnd = e.getPropertyName().concat("End");
						
						if(k.equals(dateBegin)) {
							baseValue = ModelUtil.prepareBaseValue(String.class, blankText2Null(formMap,e.getPropertyName().concat("Begin"))); //日期开始
							wrapMap.put(dateBegin, baseValue);
							break;
						}
						if(k.equals(dateEnd)) {
							baseValue = ModelUtil.prepareBaseValue(String.class, blankText2Null(formMap,e.getPropertyName().concat("End"))); //日期结束
							wrapMap.put(dateEnd, baseValue);
							break;
						}
					}
					/*
					Object formDate = formMap.get(e.getPropertyName());
					if (formDate != null && StringUtil.isNotBlank(formDate.toString())) {
						try {
							if (StringUtil.isNotBlank(e.getColumnCodegenFormat())) {
								SimpleDateFormat df = new SimpleDateFormat(e.getColumnCodegenFormat());
								baseValue = df.parse(formDate.toString());
							} else {
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								baseValue = df.parse(formDate.toString());
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					*/
				}
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_SQL_TIMESTAMP==e.getJavaType()) {
					Object formTimeStamp = blankText2Null(formMap,e.getPropertyName());
					if(formTimeStamp!=null) {
						baseValue = new java.sql.Timestamp(Long.valueOf(formTimeStamp.toString()));
					}
				}
				
				
				//布尔型
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_BOOLEAN==e.getJavaType()) {
					baseValue = ModelUtil.prepareBaseValue(Boolean.class, blankText2Null(formMap,e.getPropertyName()));
				}
				
				
				//数组
				else if(org.lsqt.components.db.Column.JAVA_TYPE_JAVA_LANG_BYTE_ARRAY==e.getJavaType()) {
					baseValue = (String[]) formMap.get(e.getPropertyName());
				}
				
				else {
					throw new UnsupportedOperationException("不支持的数据类型");
				}
				
				wrapMap.put(e.getPropertyName(), baseValue);
			}
		}
		
		return wrapMap;
	}
	
	/**
	 * 表单里的空字符转null（基本类型转化时用）
	 * @param formMap
	 * @param propertyName
	 * @return
	 */
	private Object blankText2Null(Map<String, Object> formMap, String propertyName) {
		Object obj = formMap.get(propertyName);
		if (obj instanceof String) {
			String value = (String) obj;
			value = StringUtil.escapeSql(value);
			if (StringUtil.isBlank(value)) {
				return null;
			}
		}
		return obj;
	}
	
	/**
	 * 报表定义的SQL转化为Freemark内容
	 * @param root 
	 * @param reportSql 报表原始SQL
	 * @return
	 * @throws Exception
	 */
	private String renderSQL(Map<String, Object> root,String reportSql) throws Exception {

		Template tmpl = new Template(System.currentTimeMillis()+"", new StringReader(reportSql), FreemarkGenerateReportFile.cfg);
	
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);

		try{
			registStaticModel(root); // 注册静态类方法在freemark中调用
	
			tmpl.process(root, writer);
	
			writer.flush();
		
		}catch(Exception ex) {
			throw ex;
		}finally {
			writer.close();
		}
		
		String sql = stringWriter.toString().trim();
		return sql;
	}
	
	/**
	 * 注册工具类StringUtil、ArrayUtil方法到模板引擎调用(多个工具类以逗号分割)
	 *
	 * @param root
	 * @throws TemplateModelException
	 */
	private void registStaticModel(final Map<String, Object> root) throws TemplateModelException {
	 
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		String [] clazzes = new String[]{StringUtil.class.getName(),ArrayUtil.class.getName()};
		for(String impClazz: clazzes) {
			TemplateHashModel utilStatics = (TemplateHashModel) staticModels.get(impClazz);
			
			String util = null;
			if (impClazz.indexOf(".") != -1) {
				util = impClazz.substring(impClazz.lastIndexOf(".") + 1, impClazz.length());

			} else {
				util = impClazz;
			}

			if (util != null) {
				root.put(util, utilStatics);
			}
		}
	}
	
	
	/**
	 * 报表使终分页查询 (注意在客户端切换数据源)
	 * @param reportDb 报表数据源
	 * @param def 报表定义（需包含报表列定义）
	 * @param formMap 表单原始数据
	 * @return 报表数据
	 * @throws Exception
	 */
	public Page<Map<String, Object>> getPerPage(Db reportDb,Definition def,boolean countRequired, Map<String,Object> formMap) throws Exception {
		if (def == null  || formMap.isEmpty()) {
			return new Page.PageModel<Map<String,Object>>();
		}
		formMap = wrapFormMap(def, formMap);
		
		String pageIndexParam = "pageIndex";
		String pageSizeParam = "pageSize";

		Integer pageIndex = 0;
		Integer pageSize = 20;
		if (StringUtil.isNotBlank(def.getPageIndexParam())) {
			pageIndexParam = def.getPageIndexParam();
		}

		if (StringUtil.isNotBlank(def.getPageSizeParam())) {
			pageSizeParam = def.getPageSizeParam();
		}

		Object pageIndexObj = formMap.get(pageIndexParam);
		if (pageIndexObj != null) {
			pageIndex = Integer.valueOf(pageIndexObj.toString());
		}

		Object pageSizeObj = formMap.get(pageSizeParam);
		if (pageSizeObj != null) {
			pageSize = Integer.valueOf(pageSizeObj.toString());
		}
		
		if (pageIndex < 0) {
			pageIndex = 0;
		}

		if (pageSize < 0) {
			pageSize = 20;
		}
		
		String sql = renderSQL(formMap, def.getReportSql()); 
		return reportDb.executeQueryForPage(sql,countRequired, pageIndex,pageSize);
	}
	
	
	/**
	 * 
	 * @param def 报表配置
	 * @param ds 报表导出对应的数据源
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataFromDbByLoopPage(Definition def, javax.sql.DataSource ds,Page.Action action) throws Exception {
		List<Map<String, Object>> dbData = new ArrayList<>();
		
		Connection con = db.getCurrentConnection(); // 系统数据源
		try {
			Connection switchConn = ds.getConnection();
			db.setCurrentConnection(switchConn); // 切换到报表数据源!!!!
			db.executePlan(false, () -> {
				try {
					boolean isCountedTotal = false;
					long pageCount = 0;
					long total = 0;
					
					int pageIndex = Page.DEFAULT_PAGE_INDEX ;
					Object pageIndexObj = ContextUtil.getFormMap().get("pageIndex");
					if (pageIndexObj != null) {
						if(StringUtil.isBlank(pageIndexObj.toString())) {
							pageIndex = Page.DEFAULT_PAGE_INDEX ;
						}else {
							pageIndex = Integer.valueOf(pageIndexObj.toString());
						}
					} 
					ContextUtil.getFormMap().put("pageIndex", pageIndex);
					
					if (def.getExportCurrPage() == Definition.EXPORT_CURR_PAGE_导出查询所有页) {
						ContextUtil.getFormMap().put("pageSize", Page.Action.MAX_PAGE_SIZE_LOOPED);
					}
					
					do {
						Page<Map<String, Object>> page = getPerPage(db, def, !isCountedTotal, ContextUtil.getFormMap());
						if(isCountedTotal == false) {
							pageCount = page.getPageCount();
							total = page.getTotal();
						}
						isCountedTotal = true;
						
						
						if (def.getExportCurrPage() == Definition.EXPORT_CURR_PAGE_导出查询当前页) {
							dbData.addAll(page.getData());
							break;
						} else {
							dbData.addAll(page.getData());
						}
						
						if (action != null) {
							action.doNextPage(pageIndex, page.getPageSize(), new ArrayList<>(page.getData()));
						}
						
						if (pageCount == 0 || pageCount < pageIndex + 1 ) {
							break;
						}
						
						//使终保证退出混环
						if (page == null || page.getData() == null || page.getData().isEmpty() || !page.getHasNext()) {
							break;
						}

						ContextUtil.getFormMap().put("pageIndex", ++pageIndex);
					} while(true) ;
					
				} catch (Exception e) {
					throw new DbException((e.getCause() == null ? e : e.getCause()));
				}
			});
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.setCurrentConnection(con);
		}
		

		
		//跟据DB字段，转化成java属性字段
		List<Map<String, Object>> pojoData = new ArrayList<>();
		if (ArrayUtil.isNotBlank(dbData)) {
			for (Map<String, Object> row : dbData) {
				Map<String, Object> pojoMap = new LinkedHashMap<>();

				List<String> keyList = MapUtil.toKeyList(row);
				for (String k : keyList) {
					for (org.lsqt.report.model.Column column : def.getColumnList()) {
						if (k.equals(column.getCode())) {
							pojoMap.put(column.getPropertyName(), row.get(k));
							break;
						}
					}
				}
				pojoData.add(pojoMap);
			}
		}
		return pojoData;
	}
 
	
	public List<Map<String, Object>> getDataFromDbByLoopPage(Definition def, javax.sql.DataSource ds) throws Exception {
		return getDataFromDbByLoopPage(def, ds, null);
	}
}