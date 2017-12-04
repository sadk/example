package org.lsqt.sys.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.reflect.ClassLoaderUtil;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.DataSourceQuery;
import org.lsqt.sys.service.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService{
	
	@Inject private Db db;
	
	public Page<DataSource>  queryForPage(DataSourceQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), DataSource.class, query);
	}

	public List<DataSource> getAll(){
		  return db.queryForList("queryForPage", DataSource.class);
	}
	
	public DataSource saveOrUpdate(DataSource model) {
		return db.saveOrUpdate(model);
	}

	public DataSource getById(Long id){
		return db.getById(DataSource.class, id);
	}
	
	public int deleteById(Long ... ids) {
		return db.deleteById(DataSource.class, Arrays.asList(ids).toArray());
	}

	public void testConnection(DataSource model) throws Exception{
		if(model == null) {
			throw new Exception("入参错误，没有找到数据源");
		}
		
		if(StringUtil.isBlank(model.getDriverClassName())) {
			throw new Exception("驱动不能为空");
		}
		
		if(StringUtil.isBlank(model.getLoginName())) {
			throw new Exception("登陆名不能为空");
		}
		
		if(StringUtil.isBlank(model.getLoginPassword())) {
			throw new Exception("登陆密码不能为空");
		}
		ClassLoaderUtil.classForName(model.getDriverClassName());
		
		Connection conn =null;
		try {
			conn = DriverManager.getConnection(model.getUrl(), model.getLoginName(), model.getLoginPassword());
			System.out.println(" --- testConnection: "+conn);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}

	public void testConnectionById(Long id) throws Exception{
		DataSource model = db.getById(DataSource.class, id);
		testConnection(model);
	}
	
	public List<DataSource> getDatabaseList() {
		List<DataSource> result = db.queryForList("getDatabaseList", DataSource.class) ;
		return result;
	}
}
