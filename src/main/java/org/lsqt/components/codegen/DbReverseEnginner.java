package org.lsqt.components.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.lsqt.components.codegen.bean.Column;
import org.lsqt.components.codegen.bean.Table;
import org.lsqt.components.mvc.spi.exception.ApplicationException;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DbReverseEnginner {
	
	private String username = "root";
	private String password = "123456";
	private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	private String driverClassName = "com.mysql.jdbc.Driver";
	
	public static void main(String... args) throws Exception {
		DbReverseEnginner enginner = new DbReverseEnginner();
		enginner.reverseTable("uum_user").codegenForSingle("com.example", "uum", "User","");
	}
	
	public Connection prepareConnection() {
		/*
		ApplicationContext app = new ClassPathXmlApplicationContext("application.xml");
		DataSource ds = (DataSource) app.getBean(DataSource.class);
		Connection con = null;
		try {
			con = ds.getConnection();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return con;
		*/
		try {
			Class.forName(driverClassName);
			Connection con = DriverManager.getConnection(url, username, password) ;
			return con;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private Table table = null;
	private List<Column> columnList = null;
	
	public DbReverseEnginner reverseTable(String table) {
		System.out.println(" --- 正在反向生成表元信息~!");
		Table tb = null;
		Connection con = null;
		try {
			con = prepareConnection();
			tb = new Table();
			tb.setTableName(table);
			tb.setType(Table.TYPE_TABLE);
			tb.setDbName(con.getCatalog());
			String sql = "SHOW TABLE STATUS WHERE NAME = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, table);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String tableComment = rs.getString("Comment");
				tb.setComment(tableComment);
				break;
			}
			pstmt.close();
			rs.close();
			
			
			// 1.用元SQL获取表字段和注释信息
			List<Column> columnList = new ArrayList<Column>();
			
			sql ="show full columns from "+ table;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Column column = new Column();
				String name = rs.getString("Field");
				column.setName(name); // 字段名
				
				String key = rs.getString("Key");  
				if (StringUtil.isNotBlank(key) && "PRI".equals(key)) { // 主键
					column.setPrimaryKey(Column.YES);
					column.setOroColumnType(Column.ORO_COLUMN_TYPE_PRIMARY);
				} else {
					column.setPrimaryKey(Column.NO);
					column.setOroColumnType(Column.ORO_COLUMN_TYPE_ORDINARY);
				}
				
				String text = rs.getString("Comment");
				column.setComment(text);
				
				columnList.add(column);
			}
			
			rs.close();
			pstmt.close();

			// 2.用结果集Meta信息获取表字段和Java类型信息
			sql = "select * from "+table+" where 1=2";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
				String name = rsmd.getColumnLabel(i);
				 
				String classType = rsmd.getColumnClassName(i);
				
				resolveJavaType(name, columnList, classType);
				resolveProperty(name, columnList);
				/*
				String dbType = rsmd.getColumnTypeName(i);
				System.out.println(name+"   "+classType+"    "+ dbType);
				*/
			}
			rs.close();
			pstmt.close();
			con.close();
			
			this.table = tb;
			this.columnList = columnList;
			
			return this;
		} catch (Exception e) {
			throw new ReverseException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new ReverseException("数据库连接关闭失败~!",e);
				}
			}
		}
	}
	
	private void resolveProperty(String columnName,List<Column> columns) {
		if(columns ==null || columns.size() ==0) return ;
		
		for (Column column : columns) {
			if(columnName.equals(column.getName())) {
				StringBuilder prop = new StringBuilder();
				// 去除下划线，首字母大写
				if (columnName.indexOf("_") != -1) {
					String[] words = columnName.split("_");
					for (int i=0;i<words.length;i++) {
						if (words[i].length() >= 2) {
							if(i==0) {
								prop.append(words[i].substring(0, 1).toLowerCase().concat(words[i].substring(1, words[i].length())));
							}else {
								prop.append(words[i].substring(0, 1).toUpperCase().concat(words[i].substring(1, words[i].length())));
							}
						}
					}
				} else {
					prop.append(columnName.substring(0, 1).toLowerCase().concat(columnName.substring(1, columnName.length())));
				}
				
				column.setPropertyName(prop.toString());
				break;
			}
		}
	}
	
	
	
	private void resolveJavaType(String columnName,List<Column> columns,String classType) {
		if(columns ==null || columns.size() ==0) return ;
		for (Column column : columns) {
			if(columnName.equals(column.getName())) {
				if(Column.JAVA_TYPE_DESC_JAVA_LANG_STRING.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_STRING);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_BYTE.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_BYTE);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_INTEGER.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_INTEGER);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_LONG.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_LONG);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_FLOAT.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_FLOAT);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_DOUBLE.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_DOUBLE);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_BOOLEAN.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_BOOLEAN);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_MATH_BIGDECIMAL.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_MATH_BIGDECIMAL);
				}
				else if(Column.JAVA_TYPE_DESC_JAVA_MATH_BIGINTEGER.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_MATH_BIGINTEGER);
				}
				
				// 日期
				else if(Column.JAVA_TYPE_DESC_JAVA_SQL_DATE.equals(classType) || 
						Column.JAVA_TYPE_DESC_JAVA_SQL_TIME.equals(classType) ||
						Column.JAVA_TYPE_DESC_JAVA_SQL_TIMESTAMP.equals(classType) || 
						Column.JAVA_TYPE_DESC_JAVA_UTIL_DATE.equals(classType) ) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_UTIL_DATE);
				}
				
				
				// 大字段
				else if(Column.JAVA_TYPE_DESC_JAVA_LANG_BYTE_ARRAY.equals(classType)) {
					column.setJavaType(Column.JAVA_TYPE_JAVA_LANG_BYTE_ARRAY);
				}
				break;
			}
		}
	}
	
	/**
	 * 生成代码
	 * @param groupId 类似于: com.syswin
	 * @param modules 程序模块名
	 * @param entityName 实体名
	 * @param outputDir 代码输出的文件夹，为空则生成到  PROJECT_HOME/src/main/resources/template/codegen-output
	 */
	public void codegenForSingle(String groupId,String modules,String entityName,String outputDir) {
		System.out.println(" --- 正在生成代码~!");
		String clazzFirstLower = entityName.substring(0, 1).toLowerCase().concat(entityName.substring(1,entityName.length()));
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("groupId", groupId);

		if (StringUtil.isNotBlank(modules)) {
			root.put("pkg", groupId.concat(".").concat(modules));
		} else {
			root.put("pkg", groupId);
		}
		
		root.put("tableName", table.getTableName());
		root.put("model", clazzFirstLower); // 小写模型类
		root.put("Model", entityName); // 大写模类名
		root.put("comment", table.getComment());// (主)表注释
		root.put("columnList", columnList);
		
		
		String tmplDirBase   = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen";
		String outputDirBase = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen-output";
		if (StringUtil.isNotBlank(outputDir)) {
			outputDirBase = outputDir;
		}
		
		final String SPRING3_MYBATIS3="/spring3_mybatis3";

		// 1.生成Example工程标准的Controller代码
		String tmplDir = tmplDirBase+SPRING3_MYBATIS3+"/controller";
		
		String md = "";
		if(StringUtil.isNotBlank(modules)) {
			md = "/"+modules;
		}
		
		String fullOutFile = outputDirBase +SPRING3_MYBATIS3+md+"/controller/"+entityName+"Controller.java";
		try { 
			FreemarkCodeGenUtil.toCode(tmplDir, "Controller.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Controller代码失败~!",e);
		}
		
		//  2.生成工程标准的Model代码
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 + "/model";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/model/"+entityName+".java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Model.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		//  2.1生成工程标准的ModelQuery代码
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/model";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/model/" + entityName + "Query.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ModelQuery.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		// 3.生成工程标准的ORO映射文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/mapper";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/mapper/" + entityName + "Mapper.xml";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Mapper.xml", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成SQL文件失败~!",e);
		}
		
		// 3.1生成工程标准的ORO映射文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/mapper";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/mapper/" + entityName + "Mapper.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Mapper.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成SQL文件失败~!",e);
		}
		
		// 4.生成Service接口文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/service";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/service/" + entityName + "Service.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Service.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Service接口代码失败~!",e);
		}
		
		// 5.生成ServiceImpl文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/service/impl";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/service/impl/" + entityName + "ServiceImpl.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ServiceImpl.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成ServiceImpl代码失败~!",e);
		}
		
		System.out.println(" --- 代码生成成功~!");
		System.out.println(" --- 代码路径: "+ new File(outputDirBase));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
}

class FreemarkCodeGenUtil {
	static final Configuration cfg;
	static {
		cfg = new Configuration() ;
		cfg.setDefaultEncoding("UTF-8");
	}
	
	private static void prepareFileIfNotExists(String fullOutFile) throws IOException {
		String dirPath = fullOutFile.substring(0,fullOutFile.lastIndexOf("/"));
		File dir=new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(fullOutFile);
		if(file.exists()) {
			file.delete();
		}
		
		file.createNewFile();
	}
	
	/**
	 * 
	 * @param tmplDir 读取模板文件的目录   
	 * @param tmplFileName 模板文件名   
	 * @param model 
	 * @param fullOutPutName 输出的文件全路径
	 * @throws TemplateException 
	 * @throws Exception 
	 */
	public static void toCode(String tmplDir,String tmplFileName,Map<String,Object> model,String fullOutFile) throws IOException, TemplateException{
		prepareFileIfNotExists(fullOutFile);
		
		cfg.setDirectoryForTemplateLoading(new File(tmplDir)); // 设置
		Template t = cfg.getTemplate(tmplFileName); // 读取目录中文件名为ftl的模板

		Writer out = new OutputStreamWriter(new FileOutputStream(fullOutFile), "UTF-8"); // 输出流
		t.process(model, out);
		out.close();
		  
	}
	
	public static String toCode(String tmplContent,Map<String,Object> root) throws IOException, TemplateException{
		Template tmpl = new Template("name="+tmplContent.hashCode(), new StringReader(tmplContent), cfg);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		
		
		tmpl.process(root, writer);
		
		writer.flush();
		writer.close();

		return stringWriter.toString().trim();
		
	}
}
	