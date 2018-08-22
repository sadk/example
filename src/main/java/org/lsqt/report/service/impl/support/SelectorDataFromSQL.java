package org.lsqt.report.service.impl.support;

import java.util.List;
import java.util.Map;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;


public class SelectorDataFromSQL implements SelectorData<List<Map<String,Object>>>{
	private Db db;
	private String dataSourceCode;
	
	public SelectorDataFromSQL(Db baseDb,String targetDataSourceCode) {
		this.db = baseDb;
		this.dataSourceCode = targetDataSourceCode;
	}

	
	public Page<List<Map<String, Object>>> getData() {
		
		return null;
	}
	
	
}
