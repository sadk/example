package org.lsqt.components.db.procedure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储过程返回的结果集
 * @author mmyuan
 * @param <Table>
 */
public interface DataSet {
	/** 获取表格数据 **/
	List<Table> getTables();

	void setTables(List<Table> tables);

	/** 输出参数值 **/
	List<Object> getOutputObjects();

	void setOutputObjects(List<Object> outputObjects);
	
	interface Table {

		/** 获取表格头部 字段 **/
		List<String> getHead();

		void setHead(List<String> head);

		/** 获取表格数据 */
		List<List<Object>> getData();

		void setData(List<List<Object>> data);

		/**
		 * 将表数据转换成javaBean或基础类型或Map类型
		 * 
		 * @param requiredType 可以基本类型、Map、Bean
		 * @return
		 */
		<T> List<T> forList(Class<T> requiredType);

		/**
		 * 将表格数据转换成 
		 * <pre>
		 * [
		 * 	{head1:value1,head2:value2 ...},
		 * 	...,
		 * ]
		 * </pre>
		 * @return
		 */
		List<Map<String,Object>> forKeyValueList();

		class TableModel implements Table {
			/** 表格头 **/
			List<String> head = new ArrayList<>();

			/** 表格数据 **/
			List<List<Object>> data = new ArrayList<List<Object>>();

			public List<String> getHead() {
				return head;
			}

			public void setHead(List<String> head) {
				this.head = head;
			}

			public List<List<Object>> getData() {
				return data;
			}

			public void setData(List<List<Object>> data) {
				this.data = data;
			}

			/**
			 * 将表数据转换成javaBean或基础类型或Map类型
			 * 
			 * @param requiredType  可以基本类型、Map、Bean
			 * @return
			 */
			public <T> List<T> forList(Class<T> requiredType) {

				return null;
			}

			public List<Map<String,Object>> forKeyValueList() {
				List<Map<String,Object>> rows = new ArrayList<>();
				for (List<Object> row : this.data) {
					Map<String,Object> newRow = new LinkedHashMap<>();
					for (int i = 0; i < row.size(); i++) {
						newRow.put(this.head.get(i), row.get(i));
					}
					rows.add(newRow);
				}

				return rows;
			}

		}
	}
	
	
	
	
	
	
	
	
	
	
	class DataSetModel implements DataSet {
		List<Table> tables = new ArrayList<>();
		List<Object> outputObjects = new ArrayList<>();
		public List<Table> getTables() {
			return tables;
		}

		public void setTables(List<Table> tables) {
			this.tables = tables;
		}

		public List<Object> getOutputObjects() {
			return outputObjects;
		}

		public void setOutputObjects(List<Object> outputObjects) {
			this.outputObjects = outputObjects;
		}
	}
	
	
}
