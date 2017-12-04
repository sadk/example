package org.lsqt.sys.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.DataSourceQuery;
import org.lsqt.sys.service.DataSourceService;
import org.lsqt.sys.service.impl.DataSourceFactory;

@Controller(mapping={"/datasource"})
public class DataSourceController {
	
	@Inject private DataSourceService dataSourceService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<DataSource> queryForPage(DataSourceQuery query) throws IOException {
		
		return dataSourceService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<DataSource> getAll() {
		return dataSourceService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public DataSource saveOrUpdate(DataSource form) {
		 
		return dataSourceService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return dataSourceService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/test/connection", "/m/test/connection" })
	public void testConnection(DataSource model) throws Exception {
		dataSourceService.testConnection(model);
	}
	  
	@RequestMapping(mapping = { "/test/connection/id", "/m/test/connection/id" })
	public void testConnectionById(Long id) throws Exception {
		dataSourceService.testConnectionById(id);
	}
	
	@RequestMapping(mapping = { "/get_database_list", "/m/get_database_list" })
	public List<DataSource> getDatabaseList(String dataSourceCode) {
		if (StringUtil.isBlank(dataSourceCode)) {
			return dataSourceService.getDatabaseList();
		} else {

			Connection con = db.getCurrentConnection();
			try {
				DataSourceFactory factory = new DataSourceFactory();
				factory.setBaseDb(db);

				javax.sql.DataSource ds = factory.getDataSouce(dataSourceCode);
				db.setCurrentConnection(ds.getConnection());
				
				List<DataSource> result = new ArrayList<>();
				try {
					result = db.queryForList("getDatabaseList", DataSource.class);
				} finally {
					db.close();
				}
				return result;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				db.setCurrentConnection(con);
			}
			return new ArrayList<>(0);
		}
	}
	
	
	
	@RequestMapping(mapping = { "/get_database_list_by_ds_code", "/m/get_database_list_by_ds_code" })
	public List<DataBaseNode> getDataBaseByCode(String dataSourceCode) {
		List<DataBaseNode> list = new ArrayList<>();
		List<DataSource> temp = dataSourceService.getDatabaseList();
		if (temp != null && temp.size() > 0) {

			DataSourceQuery query = new DataSourceQuery();
			query.setCode(dataSourceCode);
			Page<DataSource> page = dataSourceService.queryForPage(query);
			if (page != null && page.getData() != null && page.getData().size() > 0) {
				DataSource ds = page.getData().iterator().next();
				DataBaseNode root = new DataBaseNode(0L, null, ds.getName(),false);
				root.setChecked(true);
				
				list.add(root);
				long i=1;
				for (DataSource e : temp) {
					DataBaseNode t = new DataBaseNode(i++, root.getId(), e.getName(),true);
					list.add(t);
				}
			}
		}
		 
		 return list;
	}
	/*
	@RequestMapping(mapping = { "/get_prety_sql", "/m/get_prety_sql" })
	public String getPretySql(String sql) {
		String[] sqls = sql.split(";");

		StringBuilder sb = new StringBuilder();
		for (String e : sqls) {
			sb.append(SqlFormatUtil.format(e) + ";\n");
		}
		 return sb.toString();
	}
	*/
	
	@RequestMapping(mapping = { "/exe_sql", "/m/exe_sql" })
	public List<Map<String,Object>> exeSql(String sql) {
		// 这里有安全风险!!!!,登陆用户第一次使用时，需加验证动态密码才能执行
		
		return db.executeQuery(sql);
	}
	
}

class DataBaseNode {
	private Long id;
	private Long pid;
	private String text;
	private Boolean isLeaf=true;
	private Boolean checked = false;
	public DataBaseNode(Long id,Long pid,String name,Boolean isLeaf) {
		this.id = id;
		this.pid = pid;
		this.text = name;
		this.isLeaf = isLeaf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
