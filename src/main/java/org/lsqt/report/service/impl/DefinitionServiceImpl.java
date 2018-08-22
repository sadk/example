package org.lsqt.report.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.service.impl.DataSourceFactory;

@Service
public class DefinitionServiceImpl implements DefinitionService{
	
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

	public void importColumn(Long id) {
		db.executeUpdate(String.format("delete from %s where definition_id=?",db.getFullTable(Column.class)), id);
		
		Definition model = db.getById(Definition.class, id);
		DataSource dsModel = db.getById(DataSource.class, model.getDatasourceId());

		DataSourceFactory dbfactory = new DataSourceFactory();
		dbfactory.setBaseDb(db);
		javax.sql.DataSource ds = dbfactory.getDataSouce(dsModel.getCode());

		Connection con = db.getCurrentConnection();
		try {
			Connection switchConn = ds.getConnection();
			db.setCurrentConnection(switchConn);
			db.executePlan(() -> {
				List<org.lsqt.components.db.Column> list = db.getMetaDataColumn(model.getColumnSql());
				
				if(ArrayUtil.isNotBlank(list)) {
					List<org.lsqt.report.model.Column> reportColumnList = new ArrayList<>();
					for(org.lsqt.components.db.Column e: list) {
						org.lsqt.report.model.Column rptColumn = new org.lsqt.report.model.Column();
						rptColumn.setDbType(e.getDbType());
						rptColumn.setDefinitionId(model.getId());
						rptColumn.setJavaType(e.getJavaType());
						rptColumn.setName(e.getName());
						rptColumn.setCode(e.getName());
						rptColumn.setOptLog("自动解析导入报表列");
						rptColumn.setPropertyName(e.getPropertyName());
						rptColumn.setSearchType(org.lsqt.sys.model.Column.NO);
						rptColumn.setComment(e.getText()); // 取的是SQL字段的别名
						rptColumn.setReportName(model.getName());
						reportColumnList.add(rptColumn);
					}
					
					db.batchSave(reportColumnList);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.setCurrentConnection(con);
		}
	}
}

/**
 * MySql语句生成列。
 * @author mingmin.yuan
 *
 */
class MySqlSqlGenerateColumn {
	private Db db;
	private Definition def;

	public MySqlSqlGenerateColumn(Db db, Definition def) {
		this.db = db;
		this.def = def;
	}
	
	/**
	 * MySql数据源下，跟据SQL语句生成表
	 * @return
	 */
	public List<Column> generate() {
		List<Column> list = new ArrayList<>();
		
		
		
		return list;
	}
}