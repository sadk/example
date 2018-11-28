package org.lsqt.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.components.db.annotation.Child;
import org.lsqt.components.db.annotation.Column;
import org.lsqt.components.db.annotation.Id;
import org.lsqt.components.db.annotation.Id.Type;
import org.lsqt.components.db.annotation.Parent;
import org.lsqt.components.db.annotation.Table;
import org.lsqt.components.db.execute.AnnotationDbExecute;
import org.lsqt.components.log.db.DataSourceProvider;



public class SqlExecutorTest {
	public static final String dirver="com.mysql.jdbc.Driver";
	public static final String url="jdbc:mysql://127.0.0.1:3306/?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static final String username="root";
	public static final String pwd="";
	//@Test
	public void tran(){
         try    
         {
        	 Class.forName("com.mysql.jdbc.Driver");    
             Connection con=DriverManager.getConnection(url, username, pwd);    
             Statement stmt=con.createStatement();    
             
             String sqls="insert into sys_db.sys_test(name,description,birthday) value('name_"+System.nanoTime()+"' ,'description','2013-04-20 23:22:22')";
     		
             System.out.println("开启事务");    
             con.setAutoCommit(false);  //关闭自动提交开启事务    
             stmt.executeUpdate(sqls);    
             stmt.executeUpdate(sqls);    
             con.rollback();    
             stmt.executeUpdate(sqls);    
             stmt.executeUpdate(sqls);    
             con.commit();  //如果你不提交那么本次操作不会对数据库造成任何    
             System.out.println("事务提交");    
         }catch(Exception e)    
         {    
             System.out.println("数据库操作出现异常");    
         }    
     
	}
	 
	public  static void main(String args[]) {
		
		final  AnnotationDbExecute db=new  AnnotationDbExecute();
		db.setConfigDataSource(DataSourceProvider.getCatchedDataSource(dirver, url, username, pwd));
		long begin = System.currentTimeMillis();
		 
		CMSSchool school = new CMSSchool();
		school.setBuildDate(new Date());
		school.setName("某校名称"+System.currentTimeMillis());
		school.setId(100L);
	//	db.save(school);
		
		final CMSUser u=new CMSUser();
		u.setId("235L");
		u.setBirthday(new Date());
		u.setName("111this is name"+System.currentTimeMillis());
		u.setSalary(343_242D);
		u.setPwd("123456");
		u.setQq("5533634673");
		u.setRemark("remark, hahah!");
		u.setEmail("keke@sohu.com");
		//db.save(u,"name","id","qq");
		
		db.executePlan(()->{
			//db.save(school);
			//if(1==1) throw new org.lsqt.components.db.execute.Db.DbException("test");
			db.save(u);
		});
		
		if(1==1)return ;
		//System.out.println(u.getId());
		
		u.setName("张三"+System.currentTimeMillis());
		u.setRemark("哈哈！！！");
		//db.update(u, "name","remark");
		
		List<CMSUser> list = db.queryForList("select * from test.cms_user where 1=?" , CMSUser.class, 1);
		if(list!=null){
			for(CMSUser t : list) {
				System.out.println(t.getName());
			}
		}
		
		CMSUser ut= db.queryForObject("select * from "+db.getFullTable(CMSUser.class)+" where 1=? limit 1 ", CMSUser.class, 1);
		//System.out.println(ut.name);
		
		CMSUser ttt=db.getById(CMSUser.class,"1eead058a86a47f5b34ed65358c0360b");
		//System.out.println(ttt.getName());
		
		db.deleteById(CMSUser.class, "bb3f6b708f534caf9c8ed3351d7e7f0a");
		
		//db.delete(u);
		
		Page<CMSUser> page = db.queryForPage("select * from "+db.getFullTable(CMSUser.class),1, 2, CMSUser.class);
		System.out.println(page.getTotal());
		System.out.println(page.getHasNext()+"  "+page.getHasPrevious());
		System.out.println(page.getPageCount());
		if(page.getData()!=null && page.getData().size()>0){
			for(CMSUser n : page.getData()) {
				//System.out.println(n.getName());
			}
		}
		//System.out.println(System.currentTimeMillis() - begin);
		
	} 
	
	@Table(schema="test",name="cms_school")
	public static class CMSSchool{
		@Id(type= Type.AUTO)
		private Long id;
		
		@Column(name="name")
		private String name;
		
		@Column(name="build_date")
		private Date buildDate;

		@Child(fk="")
		private List<CMSUser> student;
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getBuildDate() {
			return buildDate;
		}

		public void setBuildDate(Date buildDate) {
			this.buildDate = buildDate;
		}
	}
	
	@Table(schema="test",name="cms_user")
	public static class CMSUser{
		@Id(type= Type.UUID64)
		private String id;
		
		@Column(name="name")
		private String name;
		
		@Column(name="pwd")
		private String pwd;
		
		@Column(name="birthday")
		private Date birthday;
		
		@Column(name="email")
		private String email;
		
		@Column(name="qq")
		private String qq;
		
		@Column(name="salary")
		private Double salary;
		
		@Column(name="remark")
		private String remark;
		
		@Parent(fk="id")
		private CMSSchool school;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public Double getSalary() {
			return salary;
		}
		public void setSalary(Double salary) {
			this.salary = salary;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	}
}
