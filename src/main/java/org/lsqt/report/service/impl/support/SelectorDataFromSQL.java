package org.lsqt.report.service.impl.support;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.DataSourceQuery;
import org.lsqt.sys.service.impl.DataSourceFactory;


public class SelectorDataFromSQL implements SelectorData<Map<String,Object>>{
	private Db db;
	private String dataSourceCode;
	private String sql ;
	
	public SelectorDataFromSQL(Db baseDb,String targetDataSourceCode,String selectorDataFrom) {
		this.db = baseDb;
		this.dataSourceCode = targetDataSourceCode;
		this.sql = selectorDataFrom;
	}

	public Page<Map<String, Object>> getData() {
		Page<Map<String, Object>> page = new Page.PageModel<Map<String, Object>>();

		DataSourceQuery query = new DataSourceQuery();
		query.setCode(dataSourceCode);
		DataSource model = db.queryForObject("queryForPage", DataSource.class, query);
		if (Objects.nonNull(model)) {
			DataSourceFactory dbfactory = new DataSourceFactory();
			dbfactory.setBaseDb(db);
			javax.sql.DataSource ds = dbfactory.getDataSouce(this.dataSourceCode);

			Connection con = db.getCurrentConnection();
			try {
				Connection switchConn = ds.getConnection();
				db.setCurrentConnection(switchConn);
				db.executePlan(() -> {
					Collection<Map<String, Object>> data = db.executeQuery(sql);
					page.setData(data);
				});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.setCurrentConnection(con);
			}
		}
		return page;
	}
	
	
}
