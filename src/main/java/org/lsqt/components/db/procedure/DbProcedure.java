package org.lsqt.components.db.procedure;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.lsqt.components.db.Plan;
import org.lsqt.components.db.procedure.DataSet.Table;
import org.omg.CORBA.SystemException;

/**
 * <pre>
 * 获取数据集
 * DataSet ds = db.execute("",Object [] args,Param ... paramType).dataSet();
 * 
 * 获取表格
 * Table table = db.execute("",Object [] args,Param ... paramType).table();
 * 
 * 获取多个表格
 * List&ltTable&gt tables = db.execute("",Object [] args,Param ... paramType).tables();
 * 
 * 获取过程输出参数值
 * Object obj = db.execute("",Object [] args,Param ... paramType).outputObject();
 * 
 * 获取过程多个输出参数值
 * List&ltObject&gt objs = db.execute("",Object [] args,Param ... paramType).outputObjects();
 * 
 * 获取表格数据转JavaBean、Map、Integer
 * db.execute("",Object [] args,Param ... paramType).table().forList(Bean.class);
 * db.execute("",Object [] args,Param ... paramType).tables(0).forList(Map.class);
 * db.execute("",Object [] args,Param ... paramType).tables(0).forList(Integer.class);
 * </pre>
 * @author mmyuan
 */
public interface DbProcedure {
	enum Param {
		NORMAL, IN, OUT, IN_OUT
	}
	
	void setConfigDataSource (DataSource ds) ;
	
	
	/**
	 * 运行时动态设置数据连接
	 * @param con
	 */
	void setCurrentConnection(Connection con);
	
	/**
	 * 获取当前绑定线程的连接
	 * @return
	 */
	Connection getCurrentConnection();
	
	
	/**
	 * 执行存储过程（带参数）
	 * @param procedureName 存储过程名字
	 * @param args 存储过程参数值 ，输出参数请以null占位
	 * @param paramType 指定每个参数的类型，见Param.IN ...
	 * @return
	 */
	DbProcedure execute(String procedureName, Object[] args, Param... paramType) throws SystemException;

	/**
	 * 执行存储过程（不带参数）
	 * @param procedureName 存储过程名字
	 * @return
	 */
	DbProcedure execute(String procedureName) throws SystemException;
	
	/**
	 * 执行过程或Sql计划
	 * @param plan
	 * @param isTransaction 是否开始事务
	 */
	void executePlan(boolean isTransaction,Plan plan);
	
	/**
	 * 执行过程或Sql计划，默认开启事务
	 * @param plan
	 */
	void executePlan(Plan plan) throws SystemException;
	
	/**
	 * <pre>
	 * 执行完存储过程获取单表数据
	 * 例如：Table table = db.execute("存储过程名字",Object [] args,Param ... paramType).table();
	 * </pre>
	 * @return
	 */
	Table table();
	
	/**
	 * <pre>
	 * 执行完存储过程获取多表数据
	 * 例如：List<Table> tables = db.execute("存储过程名字",Object [] args,Param ... paramType).tables();
	 * </pre>
	 * @return
	 */
	List<Table> tables();
	
	/**
	 * <pre>
	 * 执行完存储过程获取多表数据中的某一个
	 * 例如：List<Table> tables = db.execute("存储过程名字",Object [] args,Param ... paramType).tables(0);
	 * </pre>
	 * @return
	 */
	Table tables(int index);
	
	/**
	 * <pre>
	 * 执行完存储过程获取单个输出参数的值
	 * </pre>
	 */
	Object outputObject();

	/**
	 * <pre>
	 * 执行完存储过程获取单个输出参数的值
	 * </pre>
	 */
	<T> T outputObject(Class<T> requiredType);

	/**
	 * <pre>
	 * 执行完存储过程获取多个输出参数的值
	 * </pre>
	 */
	List<Object> outputObjects();
}

