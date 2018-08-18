package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lsqt.act.model.Form;
import org.lsqt.act.model.FormField;
import org.lsqt.act.model.FormFieldQuery;
import org.lsqt.act.service.FormFieldService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.TableQuery;

@Service
public class FormFieldServiceImpl implements FormFieldService{
	
	@Inject private Db db;
	
	public Page<FormField>  queryForPage(FormFieldQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), FormField.class, query); // 查sys_table定义的表元信息
	}
	
	public List<FormField>  queryForList(FormFieldQuery query) {
		return db.queryForList("queryForPage", FormField.class, query);
	}
	
	public List<FormField> getAll(){
		  return db.queryForList("getAll", FormField.class); 
	}
	
	public FormField saveOrUpdate(FormField model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(FormField.class, Arrays.asList(ids).toArray());
	}

	@Override
	public void impColumsByTable(Long formId) {
		Form form = db.getById(Form.class, formId);
		
		TableQuery query = new TableQuery();
		query.setDataSourceCode(form.getDataSourceCode());
		query.setTableName(form.getTableName());
		query.setDbName(form.getDbName());
		Table table = db.queryForObject("queryForPage",Table.class, query);

		ColumnQuery cq = new ColumnQuery();
		cq.setTableId(table.getId());
		List<Column> list = db.queryForList("queryForPage",Column.class,cq);
		if(ArrayUtil.isBlank(list)) {
			return ;
		}
		
		db.executeUpdate(
				"delete from " + db.getFullTable(FormField.class)
						+ " where datasource_code=? and db_name=? and table_name=?",
				table.getDataSourceCode(), table.getDbName(), table.getTableName());// 不会误删字表字段
		
		List<FormField> mList = new ArrayList<>();
		for (Column e : list) {
			FormField m = new FormField();
			m.setFormId(form.getId());
			m.setAppCode(e.getAppCode());
			m.setColumnCodegenFormat(e.getColumnCodegenFormat());
			m.setColumnCodegenGroupCode(e.getColumnCodegenGroupCode());
			m.setColumnCodegenType(e.getColumnCodegenType());
			m.setComment(e.getComment());
			m.setDataSourceCode(form.getDataSourceCode());
			m.setDbName(e.getDbName());
			m.setDbType(e.getDbType());
			m.setDictRefCode(e.getDictRefCode());
			m.setDictTextCol(e.getDictTextCol());
			m.setDictValueCol(e.getDictValueCol());
			m.setFileCustomContent(e.getFileCustomContent());
			m.setFileMultil(e.getFileMultil());
			m.setFieldName(e.getName());
			m.setFieldCode(e.getName()); // 字段编码和名称暂保持一致
			m.setFormName(form.getName());
			m.setJavaType(e.getJavaType());
			m.setOptLog(e.getOptLog());
			m.setOroColumnType(e.getOroColumnType());
			m.setPrimaryKey(e.getPrimaryKey());
			m.setPropertyName(e.getPropertyName());
			m.setRemark(e.getRemark());
			m.setSearchType(e.getSearchType());
			m.setSelectorDataFrom(e.getSelectorDataFrom());
			m.setSelectorDataFromType(e.getSelectorDataFromType());
			m.setSelectorDataSourceCode(e.getSelectorDataSourceCode());
			m.setSelectorDbName(e.getSelectorDbName());
			m.setSelectorMultilSelect(e.getSelectorMultilSelect());
			m.setSelectorTextCols(e.getSelectorTextCols());
			m.setSelectorValueCols(e.getSelectorValueCols());
			m.setSn(e.getSn());
			m.setTableName( form.getTableName());
			m.setVersion(e.getVersion());
			mList.add(m);
		}
		
		db.batchSave(mList);
	}
	 
}


