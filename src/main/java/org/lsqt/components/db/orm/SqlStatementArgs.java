package org.lsqt.components.db.orm;

import java.util.List;


public class SqlStatementArgs {
	private String sql; // 完整的SQL语句
	private List<Object> args; // 完整SQL语句对应的参数值
	
	public SqlStatementArgs(){}
	public SqlStatementArgs(String sql,List<Object> args){
		this.sql = sql;
		this.args = args;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getArgs() {
		return args;
	}
	public void setArgs(List<Object> args) {
		this.args = args;
	}
	
}
