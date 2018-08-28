package org.lsqt.components.db.orm.ftl;

import java.io.File;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lsqt.components.db.DbException;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingDb;
import org.lsqt.components.db.orm.SqlStatement;
import org.lsqt.components.db.orm.SqlStatementBuilder;
import org.lsqt.components.db.Table;
import org.lsqt.components.db.Column;
import org.lsqt.components.util.file.FileUtil;
import org.lsqt.components.util.file.IOUtil;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;
import org.lsqt.sys.model.Dictionary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * 如果没有显示配置ORM文件路径，默认从应用跟目录
 * 
 * @author yuanmm
 *
 */
public class SqlStatementFactory implements SqlStatementBuilder {
	
	static final List<Table>  SQL_MAPPINGS_TABLES = new ArrayList<>();
	static final List<SqlStatement> SQL_MAPPINGS_STATMENTS = new ArrayList<SqlStatement>();

	static boolean isBuilded ;
	private List<String> locations ;

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
	
	public List<File> getFilesAppRootDir() {
		List<File> rs = new ArrayList<>();
	
		String dir = PathUtil.getAppRootDir();
		File root = new File(dir);
		if(root.exists() && root.isDirectory()) {
			List<String> list = FileUtil.getDeepFiles(root);
			for(String path : list) {
				if(path.endsWith(".ftl.sql.xml")) {
					
					// 跳过代码生成器的模板文件
					if(path.endsWith("Model.ftl.sql.xml")){
						continue;
					}
					rs.add(new File(path));
				}
			}
		}
		return rs;
	}
	
	public SqlStatementBuilder buildBefore(){
		System.out.println(" --- 构建ORMaping文件开始~!");
		return this;
	}
	
	public SqlStatementBuilder build() {
		if(isBuilded) return this;
		try {
			buildBefore();
			
			// 1.获取应用根路径文件夹下的所有sql配置
			List<File> rs = getFilesAppRootDir();
		
			if(rs==null || rs.size()==0) return this;
			
			// bug fix：过滤maven的target编译后的文件: web_root/target/classes/xxx.xml
			List<File> xmlList = new ArrayList<>();
			final String path = File.separator + "target" + File.separator + "classes" + File.separator;
			for(File f: rs) {
				if (f.toString().indexOf(path) == -1) {
					xmlList.add(f);
				}
			}
			
			// 2.获取jar包下的所有sql配置

			// 3.获取远程URI的sql配置

			// 4.获取指定路径的sql配置

			// 5.加载xml文件内容，解析成sql对象
			if(rs == null || rs.size() == 0) return this;
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			Iterator<File> iter = xmlList.iterator();
			while(iter.hasNext()) {
				File file = iter.next();
				
				String xml = IOUtil.getFileContent(file);
		       
				Document doc = builder.parse(new InputSource(new StringReader(xml)));
				
				
				Element root = doc.getDocumentElement();
				
				String namespace = root.getAttribute("namespace");
				String importGlobar = root.getAttribute("import");
				String cacheGlobar = root.getAttribute("cache");
				
				if (StringUtil.isBlank(namespace)) {
					throw new DbException( file + ", namespace is empty");
				}
				
				NodeList statments = root.getChildNodes();
				if (statments == null) continue;
			
				Element tableNode = (Element) xpath.evaluate("/statements/table", doc,XPathConstants.NODE);
				String tableName = tableNode.getAttribute("name");
				String schema = tableNode.getAttribute("schema");
				Table table = new Table(schema,tableName,namespace,file.toString());
				
				SQL_MAPPINGS_TABLES.add(table);
				
				NodeList columnNodes = (NodeList) xpath.evaluate("/statements/table/column", doc,XPathConstants.NODESET);
				for (int n = 0; n < columnNodes.getLength(); n++) {
					Element ele = (Element) columnNodes.item(n);

					Column col = new Column();
					String id = ele.getAttribute("id");
					col.setId(id);
					
					String type = ele.getAttribute("type");
					if (StringUtil.isNotBlank(id) && StringUtil.isBlank(type)){
						col.setType(IdGenerator.ID_TYPE_AUTO); //如果定义的主键，没有显示指定type，默认为自动增长类型
					} else {
						col.setType(type);
					}
					
					col.setName(ele.getAttribute("name"));
					col.setText(ele.getAttribute("text"));
					col.setGid(ele.getAttribute("gid"));
					col.setUpdateTime(ele.getAttribute("updateTime"));
					col.setCreateTime(ele.getAttribute("createTime"));
					col.setProperty(ele.getAttribute("property"));

					String isVirtual = ele.getAttribute("isVirtual");
					col.setIsVirtual(StringUtil.isBlank(isVirtual) ? false : Boolean.valueOf(isVirtual));

					if (StringUtil.isBlank(col.getProperty())) {
						System.out.println("警告!!! 没有定义数据库列对应的model属性名(默认启用数据库列名为model名称)，查看映射文件:[" + file + "]~!");
					}

					table.getColumnList().add(col);
				}
				
						  
				
				for (int i = 0; i < statments.getLength(); ++i) {
					Node stmt = statments.item(i);
					
					if ("statement".equals(stmt.getNodeName())) {
						Element row = (Element) stmt;

						SqlStatement s = new SqlStatement();
						
						
						String id = row.getAttribute("id");
					
						String parameterName = row.getAttribute("parameterName");
						String sqltemplateContent = row.getTextContent();
						if (StringUtil.isBlank(id)) {
							throw new DbException(
									"please defind a id for ormapping statement.  (file:" + file.toURI() + ")");
						}
						
						s.setId(id);
						s.setParameterName(parameterName);
						s.setSqlTemplateContent(sqltemplateContent.trim());
						s.setNamespace(namespace);
						s.setPath(file.toString());
						s.setImportClazz(importGlobar);
						s.setTable(table);
						
						if (StringUtil.isNotBlank(cacheGlobar)) {
							s.setCache(Boolean.valueOf(cacheGlobar));
						} else {
							String cache = row.getAttribute("cache");
							s.setCache(StringUtil.isBlank(cache) ? false : Boolean.valueOf(cache));
						}
						
						SQL_MAPPINGS_STATMENTS.add(s);
					}
				}
			 
			}
			 
			// 6.检查命名空间冲突
			
			// 7.检查命名空间下的id冲突
			
			// 8.转译SQL模版语句成sql占位形式
			for(SqlStatement stmt : SQL_MAPPINGS_STATMENTS) {
				//System.out.println(stmt.getPath()+"  ==>"+stmt.getId()+":\n"+stmt.getSqlTemplateContent());
			}
		} catch (Exception ex) {
			throw new DbException(ex);
		} finally {
			buildAfter();
		}
		isBuilded = true;
		return this;
	}
	
	public SqlStatementBuilder buildAfter() {
		System.out.println(" --- 构建ORMaping文件结束~!");
		return this;
	}
	
	public void put(SqlStatement stmt) {
		SQL_MAPPINGS_STATMENTS.add(stmt);
	}

	public List<SqlStatement> getAllStatement() {
		return SQL_MAPPINGS_STATMENTS;
	}
	
	public List<Table> getAllTable() {
		
		return SQL_MAPPINGS_TABLES;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ test!!! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void main(String args[]) throws SQLException {
		/*
		String str = "insert into  cms_application values(@{name},@{age})";
		Pattern pattern = Pattern.compile("[@][{][a-zA-Z0-9].+[}]");

		Matcher matcher = pattern.matcher(str);
		List list = new ArrayList();
		while (matcher.find()) {
			String srcStr = matcher.group();
			list.add(srcStr);
		}
		System.out.println(list);
*/
		ApplicationContext app = new ClassPathXmlApplicationContext("application.xml");
		
		DataSource ds = (DataSource) app.getBean(DataSource.class);
		 
		
		SqlStatementBuilder builder =new SqlStatementFactory().build();
		
		
		ORMappingDb db = new FtlDbExecute(builder);
		db.setConfigDataSource(ds);
		System.out.println(db.getFullTable(Dictionary.class));
		System.out.println(db.getColumn(Dictionary.class,"updateTime"));
		System.out.println(db.getColumn(Dictionary.class,"id"));
		System.out.println(db.getColumn(Dictionary.class,"gid"));
		System.out.println(db.getColumn(Dictionary.class,"nodePath"));
		
		Application appModel = new Application();
		appModel.setCode("code_"+System.currentTimeMillis());
		appModel.setName("默认系统_fly_"+System.currentTimeMillis());
		appModel.setRemark("默认系统，由sky创建");
		appModel.setSn(0);
		appModel.setEnable(Application.ENABLE_YES);
		db.save(appModel);
		
		ApplicationQuery query = new ApplicationQuery();
		query.setKey("code");
		//query.setCode("code");
		
		Page<Application> p = db.queryForPage(Application.class.getName(), "queryForPage", 1, 5, Application.class, query);
		p.getData();
		if(true) return ;
		
		/*
		Dictionary dic = new Dictionary();
		dic.appCode = "appcode11111";
		dic.code = "code22222"+System.currentTimeMillis();
		dic.createTime = new Date();
		dic.name = "name3333";
		dic.nodePath = "nodePath44444";
		dic.remark = "remark5555";
		dic.categoryCode = "categoryCode_"+System.currentTimeMillis();
		dic.sn = 1000;
		dic.categoryName = "categoryName";
		dic.value = "value_"+System.currentTimeMillis();
		db.save(dic);
		//db.save(dic,"appCode","code","createTime","name","nodePath","categoryCode","value");
		System.out.println(dic.id);
		
		dic.appCode = "xxxxxxx";
		db.update(dic, "gid","id","updateTime","updateTime");
		
		dic.code = "code_987654321_"+System.currentTimeMillis();
		dic.remark = "remark_"+System.currentTimeMillis();
		dic.categoryName = "categoryName_kwg 哈哈！！";
		dic.createTime = new Date();
		db.update(dic, "code","remark","categoryName","createTime");
		
		dic = db.getById(Dictionary.class, dic.id);
		dic = db.queryForObject(Dictionary.class.getName(), "getById", Dictionary.class, dic.id);
		System.out.println(dic.categoryName);
		
		//db.deleteById(Dictionary.class, 3,4,5,6,7);
		/*
		Dictionary dic1 = new Dictionary();
		Dictionary dic2 = new Dictionary();
		Dictionary2 dic3 = new Dictionary2();
		dic1.id = 16L ;
		dic2.id = 17L ;
		dic3.id = 18L;
		db.delete(dic1,dic2,dic3);
		
		Dictionary dic100 = db.getById(Dictionary.class, 18L);
		System.out.println(dic100);
		
				
		dic.code = "987654321_"+System.currentTimeMillis();
		dic.remark = "remark_"+System.currentTimeMillis();
		db.update(dic, "code","remark");
		
		Dictionary dicttt =  db.queryForObject("getById", Dictionary.class, 20);
		db.queryForObject("getById", Dictionary.class, 21);
		
		Dictionary example =  new Dictionary();
		example.id = 20L;
		example.code = "code's 哈哈";
		Dictionary dit = db.queryForObject(Dictionary.class.getName() ,"getByExample", Dictionary.class);
		*/
		
		List<Dictionary> dit = db.queryForList(Dictionary.class.getName(), "getByExample2", Dictionary.class,"code");
		
		db.executePlan(()->{
			Page<Dictionary> page = db.queryForPage(Dictionary.class.getName(), "getByExample2", 0,10,Dictionary.class,"code");
		});
	}

	
}