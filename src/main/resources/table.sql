use test ;
start transaction;




-- SET SQL_SAFE_UPDATES=0; 
 
drop table  IF EXISTS  sys_file ;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT primary key,
  `pid` bigint(20)  NULL COMMENT '当文件为目录时，有层级关系',
  `name` varchar(500) NOT NULL COMMENT '新文件名称',
  `original_name` varchar(500) NOT NULL COMMENT '原始文件名称',
  `type_code` varchar(50) NULL COMMENT '文件或目录类型编码，引用字典值',
  `file_or_dir` varchar(2) NOT NULL COMMENT '文件或目录： 1=文件 2=目录',
  `data_type` int(4) NULL comment '业务数据类型（从字典引用值）,1=企业logo 2=职位封面 3=职位视频',
  
  `path` varchar(1024) DEFAULT NULL COMMENT '(FastDFS文件)路径或自定义路径',
  `path_large` varchar(1024) DEFAULT NULL COMMENT '文件缩略图（大）',
  `path_medium` varchar(1024) DEFAULT NULL COMMENT '文件缩略图（中）',
  `path_small` varchar(1024) DEFAULT NULL COMMENT '文件缩略图（小）',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(50) DEFAULT NULL ,
  `node_path` varchar(1000) DEFAULT NULL COMMENT '目录节点路径',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文件目录表';

-- ALTER TABLE `sys_file` ADD COLUMN `obj_id` VARCHAR(50) NULL COMMENT '对象ID,如：一个流程表单里，上传几个文件' AFTER `update_time`;


-- 国、省、市、地区表
drop table  IF EXISTS  sys_region ;
CREATE TABLE `sys_region` (
  `id` bigint(20) NOT NULL ,
  `pid` bigint(20) DEFAULT NULL,
  `type` int(4) NOT NULL,
  `name` varchar(256) NOT NULL COMMENT '名称',
  `value` varchar(256) DEFAULT NULL COMMENT '名称',
  `code` varchar(256) NOT NULL COMMENT '编码',
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(500) DEFAULT NULL,
  `node_path_text` varchar(2000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国省市区表';



drop table  IF EXISTS sys_tenant ;  
CREATE TABLE `sys_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '租户名称',
  `value` varchar(46) DEFAULT NULL COMMENT '租户值',
  `code` varchar(100) NOT NULL COMMENT '租户编码',

  `full_name` varchar(256) NOT NULL COMMENT '租户全称',
  `nick_name` varchar(256) NOT NULL COMMENT '租户昵称',
  `introduction` varchar(1024) NOT NULL COMMENT '租户介绍',
  
  
  `sn` int(10) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL,
  `enable` varchar(2) DEFAULT NULL COMMENT '启用状态 0=未起用，1=启用',
  
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号',
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='租户表';



drop table  IF EXISTS sys_application ;   
CREATE TABLE `sys_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '应用名称',
  `value` varchar(255) NULL COMMENT '应用值',
  `code` varchar(100) NOT NULL COMMENT '应用编码',
  `sn` int(10) DEFAULT 0 COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL,
  
  `enable` varchar(2) DEFAULT NULL COMMENT '启用状态 0=未起用，1=启用',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',

  `tenant_code` varchar(100) NULL COMMENT '租户编码',
  `tenant_name` varchar(100) NULL COMMENT '租户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_app_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用表';

insert into sys_application(name,code,sn,remark,enable,gid,create_time,update_time) values ('后台管理系统','1000',0,'超级管理系统',1,'1000',now(),now());
insert into sys_application(name,code,sn,remark,enable,gid,create_time,update_time) values ('门户系统','2000',0,'门户系统',1,'2000',now(),now());

-- 系统业务上的分类表
drop table  IF EXISTS sys_category ;   
CREATE TABLE `sys_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父节点',
  `code` varchar(100) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `name` varchar(255) NOT NULL COMMENT '类型名称',
  `node_path` varchar(255) DEFAULT NULL COMMENT '树路径',
  `app_code` varchar(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cate_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统分类信息表';

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'dictionary',0,'数据字典','1','1000','dictionary',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'attachment',0,'文件分类','2','1000','attachment',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,   'channel',0,'栏目分类','3','1000','content',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,      'form',0,'表单分类','4','1000','form',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,      'bbs',0,'BBS版块','5','1000','bbs',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'act_category',0,'流程分类','6','1000','act_category',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'cache_category',0,'缓存分类','7','1000','cache_category',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'server_machine_category',0,'服务器分类','8','1000','server_machine_category',now(),now());

insert into sys_category(pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values (-1,'report_online_category',0,'报表类','8','1000','report_online_category',now(),now());

-- 系统字典表（是分类表的一个子集，但不存业务相关类别，如：男、女等字典； 
drop table  IF EXISTS  sys_dictionary ;
CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `category_code` varchar(255) DEFAULT NULL COMMENT '分类代码',
  
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据类型',
  `enable` varchar(2) DEFAULT NULL COMMENT '是否启用: 1=启用  0=禁用',
  
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',

  `gid` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='字典名称';

-- 性别
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (1,-1,'性别','sex','sex','dictionary','1000',0,'1','性别','sex',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (2,1,'男','1','man','dictionary','1000',0,'1,2','男','man',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (3,1,'女','0','woman','dictionary','1000',0,'1,3','女','woman',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (4,1,'未知','2','sex_unknow','dictionary','1000',0,'1,4','未知','sex_unknow',now(),now()) ;

-- 启用状态

insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (5,-1,'启用状态','enable_status','enable_status','dictionary','1000',0,'5','启用状态','enable_status',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (6,5,'启用','1','enable','dictionary','1000',0,'5,6','启用状态','enable',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (7,5,'禁用','0','disable','dictionary','1000',0,'5,7','启用状态','disable',now(),now()) ;

-- 审核状态
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (8,-1,'审核状态','auth_status','auth_status','dictionary','1000',0,'8','审核状态','auth_status',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (9,8,'已审核','1','auth_done','dictionary','1000',0,'8,9','已审核','auth_done',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (10,8,'审核中','2','auth_ing','dictionary','1000',0,'8,10','审核中','auth_ing',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (11,8,'未审核','3','auth_none','dictionary','1000',0,'8,11','未审核','auth_none',now(),now()) ;


-- 资讯生成类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (12,-1,'资讯生成类型','generate_type','generate_type','dictionary','1000',0,'12','资讯生成类型','generate_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (13,12,'管理员后台提交','0','generate_admin','dictionary','1000',0,'12,13','管理员后台提交','generate_admin',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (14,12,'应用程序采集','1','generate_code','dictionary','1000',0,'12,14','应用程序采集','generate_code',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (15,12,'网络用户提交','2','generate_user','dictionary','1000',0,'12,15','网络用户提交','generate_user',now(),now()) ;


-- 数据源连接状态
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (16,-1,'数据源连接状态','datasource','datasource','dictionary','1000',0,'16','数据源连接状态','datasource',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (17,16,'正常','1','datasource_normal','dictionary','1000',0,'16,17','正常','datasource_normal',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (18,16,'不正常','0','datasource_unnormal','dictionary','1000',0,'16,18','不正常','datasource_unnormal',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (19,16,'未知','2','datasource_unknow','dictionary','1000',0,'16,19','未知','datasource_unknow',now(),now()) ;

-- 代码生器模板类型：模板类型有 
-- ORO         0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml 
-- Controller  5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java 
-- Dao         3=Dao_Mybatis3.java 11= Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java
-- Service     4=Service_Example.java  14=Service_Spring.java
-- Model       15= Model.java
-- Page        7=jsp 8=html 9=*.vm 10=*.ftl
	
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (20,-1,'代码生成器模板类型','tmpl_type','tmpl_type','dictionary','1000',0,'20','代码生成器模板类型','tmpl_type',now(),now()) ;
 
-- oro
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (21,20,'(ORO映射文件)Mybatis3_Mapper.xml','0','Mybatis3_Mapper','dictionary','1000',0,'20,21','Mybatis3的SQL映射文件',  'Mybatis3_Mapper',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (22,20, '(ORO映射文件)Hibernate3.hbm.xml','1','Hibernate3',     'dictionary','1000',0,'20,22','Hibernate3的SQL映射文件','Hibernate3',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (23,20, '(ORO映射文件)Example.ftl.sql.xml','2','Example',       'dictionary','1000',0,'20,23','Example的SQL映射文件',   'Example',now(),now()) ;

-- controller
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (24,20, '(Controller层模板)ExampleMVC_Controller.java','20','ExampleMVC_Controller', 'dictionary','1000',0,'20,24','ExampleMVC_Controller的Controller文件',   'ExampleMVC_Controller',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (29,20, '(Controller层模板)SpringMVC_Controller.java','5','SpringMVC_Controller', 'dictionary','1000',0,'20,29','SpringMVC_Controller的Controller文件',   'SpringMVC_Controller',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (30,20, '(Controller层模板)Struts2_Action.java','6','Struts2_Action', 'dictionary','1000',0,'20,30','Struts2_Action的Action文件',   'Struts2_Action',now(),now()) ;

-- dao
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (25,20, '(Dao层模板)Dao_Mybatis3.java','3','Dao_Mybatis3', 'dictionary','1000',0,'20,25','Mybatis3的Dao文件',   'Dao_Mybatis3',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (26,20, '(Dao层模板)Dao_Hibernate3.java','11','Dao_Hibernate3', 'dictionary','1000',0,'20,26','Hibernate3的Dao文件',   'Dao_Hibernate3',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (27,20, '(Dao层模板)Dao_SpringJDBC.java','12','Dao_Spring3JDBC', 'dictionary','1000',0,'20,27','Spring3JDBC的Dao文件',   'Dao_Spring3JDBC',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (28,20, '(Dao层模板)Dao_DBUtil.java','13','Dao_DBUtil', 'dictionary','1000',0,'20,28','DBUtil的Dao文件',   'Dao_DBUtil',now(),now()) ;

-- service
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (31,20, '(Service层模板)Service_Example.java','4','Service_Example', 'dictionary','1000',0,'20,31','Example的Service文件',   'Service_Example',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (32,20, '(Service层模板)Service_Spring.java','14','Service_Spring', 'dictionary','1000',0,'20,32','Spring的Service文件',   'Service_Spring',now(),now()) ;

-- page
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (33,20, '(页面模板)jsp','7','jsp', 'dictionary','1000',0,'20,33','jsp模板文件',   'jsp',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (34,20, '(页面模板)html','8','html', 'dictionary','1000',0,'20,34','html模板文件(可用于angular、node.js(的ejs或其它)的模板)',   'html',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (35,20, '(页面模板)velocity','9','velocity', 'dictionary','1000',0,'20,35','velocity模板文件',   'velocity',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (36,20, '(页面模板)freemark','10','freemark', 'dictionary','1000',0,'20,36','freemark模板文件',   'freemark',now(),now()) ;




-- JAVA常用的字段类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (37,-1, 'Java常用数据类型','java_type','java_type', 'dictionary','1000',0,'37','freemark模板文件',   'java_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (38,37, 'java.lang.String','0','java.lang.String', 'dictionary','1000',0,'37,38','字符型',   'java.lang.String',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (39,37, 'java.lang.Character','1','java.lang.Character', 'dictionary','1000',0,'37,39','字符型',   'java.lang.Character',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (40,37, 'java.lang.Byte','2','java.lang.Byte', 'dictionary','1000',0,'37,40','整型',   'java.lang.Byte',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (41,37, 'java.lang.Short','3','java.lang.Short', 'dictionary','1000',0,'37,41','整型',   'java.lang.Short',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (42,37, 'java.lang.Integer','4','java.lang.Integer', 'dictionary','1000',0,'37,42','整型',   'java.lang.Integer',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (43,37, 'java.lang.Long','5','java.lang.Long', 'dictionary','1000',0,'37,43','整型',   'java.lang.Long',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (44,37, 'java.lang.Float','6','java.lang.Float', 'dictionary','1000',0,'37,44','精度型',   'java.lang.Float',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (45,37, 'java.lang.Double','7','java.lang.Double', 'dictionary','1000',0,'37,45','精度型',   'java.lang.Double',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (46,37, 'java.lang.Boolean','8','java.lang.Boolean', 'dictionary','1000',0,'37,46','布尔型',   'java.lang.Boolean',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (47,37, 'java.lang.Date','9','java.lang.Date', 'dictionary','1000',0,'37,47','日期时间类',   'java.lang.Date',now(),now()) ;

-- 非注流类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (48,37, 'java.math.BigDecimal','10','java.math.BigDecimal', 'dictionary','1000',0,'37,48','大精度型',   'java.math.BigDecimal',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (49,37, 'java.math.BigInteger','11','java.math.BigInteger', 'dictionary','1000',0,'37,49','大整型',   'java.math.BigInteger',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (50,37, 'java.sql.Time','12','java.sql.Time', 'dictionary','1000',0,'37,50','日期时间类',   'java.sql.Time',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (51,37, 'java.sql.Date','13','java.sql.Date', 'dictionary','1000',0,'37,51','日期时间类',   'java.sql.Date',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (52,37, 'java.sql.Timestamp','14','java.sql.Timestamp', 'dictionary','1000',0,'37,52','日期时间类',   'java.sql.Timestamp',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (53,37, 'java.lang.Byte []','15','java.lang.Byte []', 'dictionary','1000',0,'37,53','字节数组、大字段',   'java.lang.Byte []',now(),now()) ;






-- OROMaping字段类别
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (54,-1, 'ORMapping字段类别','oro_column_type','oro_column_type', 'dictionary','1000',0,'54','ORMapping字段类别',   'oro_column_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (55,54, '全局唯一字段','1','gid', 'dictionary','1000',0,'54,55','全局唯一字段',   'gid',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (56,54, '创建时间','2','createTime', 'dictionary','1000',0,'54,56','创建时间',   'createTime',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (57,54, '更新时间','3','updateTime', 'dictionary','1000',0,'54,57','更新时间',   'updateTime',now(),now()) ;

-- 字段的代码生成器类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (58,-1, '字段的代码生成器类型','column_codegen_type','column_codegen_type', 'dictionary','1000',0,'58','字段的代码生成器类型',   'column_codegen_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (59,58, '选择器','1','selector', 'dictionary','1000',0,'58,59','字段的代码生成器类型',   'selector',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (60,58, '下拉框(字典)','2','dic', 'dictionary','1000',0,'58,60','下拉框(字典)',   'dic',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (62,58, '文本框','4','textbox', 'dictionary','1000',0,'58,62','文本',   'text',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (63,58, '整型框','5','long', 'dictionary','1000',0,'58,63','整型',   'long',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (64,58, '精度型框','6','doubleBox', 'dictionary','1000',0,'58,64','精度型',   'double',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (65,58, '日期框','7','datePicker', 'dictionary','1000',0,'58,65','日期型',   'date',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (66,58, '文件','8','file', 'dictionary','1000',0,'58,66','文件上传',   'file',now(),now()) ;



-- 是否
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (67,-1, '是否标识','yes_or_no','yes_or_no', 'dictionary','1000',0,'67','是否标识',   'yes_or_no',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (68,67, '否','0','no', 'dictionary','1000',0,'67,68','否',   'no',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (69,67, '是','1','yes', 'dictionary','1000',0,'67,69','是',   'no',now(),now()) ;


-- 代码生成类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (70,-1,'代码生成类型','codegen_type','codegen_type','dictionary','1000',0,'70','代码类型','codegen_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (71,70,'一键生成单表代码','1','codegen_type_single','dictionary','1000',0,'70,71','一键生成单表代码','codegen_type_single',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (72,70,'一键生成主子表代码','2','codegen_type_main_sub','dictionary','1000',0,'70,72','一键生成主子表代码','codegen_type_main_sub',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (73,70,'一键生成自定义代码','3','codegen_type_custom','dictionary','1000',0,'70,73','一键生成自定义代码','codegen_type_custom',now(),now()) ;


-- OROMaping字段类别(补)
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (74,54, '普通字段','4','ordinary', 'dictionary','1000',0,'54,74','普通字段',   'ordinary',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (75,54, '主键字段','5','primary', 'dictionary','1000',0,'54,75','主键字段',   'primary',now(),now()) ;


-- 表类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (76,-1, '表类型','table_type','table_type', 'dictionary','1000',0,'76','表类型',   'table_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (77,76, '数据库表','1','BASE TABLE', 'dictionary','1000',0,'76,76','数据表',   'BASE TABLE',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (78,76, '普通视图','2','SYSTEM VIEW', 'dictionary','1000',0,'76,77','普通视图',   'SYSTEM VIEW',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (79,76, '物化视图','3','MATERIALIZED VIEW', 'dictionary','1000',0,'76,78','物化视图',   'MATERIALIZED VIEW',now(),now()) ;


-- 选择器数据来源
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (80,-1, '选择器数据来源','selector_data_form_type','selector_data_form_type', 'dictionary','1000',0,'80','选择器数据来源',   'selector_data_form_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (81,80, 'URL(返回JSON)','1','url_json', 'dictionary','1000',0,'80,81','URL(返回JSON)',   'url_json',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (82,80, 'URL(返回XML)','2','url_xml', 'dictionary','1000',0,'80,82','URL(返回XML)',   'url_xml',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (83,80, '代码片断(JavaScript)数组','3','code_js_array', 'dictionary','1000',0,'80,83','代码片断(JavaScript)数组',   'code_js_array',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (84,80, 'SQL','4','sql', 'dictionary','1000',0,'80,84','SQL',   'sql',now(),now()) ;

-- 代码生成器-模板占位符-值解析类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (85,-1, '代码生成器-模板占位符-值解析类型','value_resolve_type','value_resolve_type', 'dictionary','1000',0,'85','代码生成器-模板占位符-值解析类型',   'value_resolve_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (86,85, 'freemark解析','1','freemark_resolve', 'dictionary','1000',0,'85,86','freemark_resolve',   'freemark_resolve',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (87,85, 'velocity解板','2','velocity_resolve', 'dictionary','1000',0,'85,87','velocity_resolve',   'velocity_resolve',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (88,85, 'groovy解析','3','groovy_resolve', 'dictionary','1000',0,'85,88','groovy_resolve',   'groovy_resolve',now(),now()) ;

-- 补一个字典
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (89,58, '下拉框(常量JSON)','9','combobox', 'dictionary','1000',0,'58,89','文件上传',   'combobox',now(),now()) ;


-- 用户状态
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (90,-1, '用户状态','user_status','user_status', 'dictionary','1000',0,'90','用户状态',   'user_status',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (91,90, '过期','3','expired', 'dictionary','1000',0,'90,91','过期',  'expired',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (92,90, '锁定','2','locked', 'dictionary','1000',0,'90,92','锁定',   'locked',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (93,90, '激活','1','active', 'dictionary','1000',0,'90,93','激活',   'active',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (94,90, '禁用','0','u_disable', 'dictionary','1000',0,'90,94','禁用',  'disable',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (95,90, '删除','-1','deleted', 'dictionary','1000',0,'90,95','删除', 'deleted',now(),now()) ;


-- 订单的状态: 0=下单成功 1=支付成功 2=支付失败
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (96,-1, '订单状态','order_status','order_status', 'dictionary','1000',0,'96','订单状态',   'order_status',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (97,96, '支付成功','1','pay_ok', 'dictionary','1000',0,'96,97','支付成功',   'pay_ok',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (98,96, '支付失败','2','pay_fail', 'dictionary','1000',0,'96,98','支付失败',   'pay_fail',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (99,96, '下单成功','0','ordered_ok', 'dictionary','1000',0,'96,99','下单成功',   'ordered_ok',now(),now()) ;


-- UUM状态
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (100,-1, '统一用户管理状态','uum_status','uum_status', 'dictionary','1000',0,'100','统一用户管理状态',   'uum_status',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (101,100, '启用','1','uum_status_enable', 'dictionary','1000',0,'100,101','启用',   'uum_status_enable',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (102,100, '禁用','0','uum_status_disable', 'dictionary','1000',0,'100,102','禁用',   'uum_status_disable',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (103,100, '删除','-1','uum_status_deleted', 'dictionary','1000',0,'100,103','删除',   'uum_status_deleted',now(),now()) ;


-- UUM资源类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (104,-1, '资源类型','res_type','res_type', 'dictionary','1000',0,'104','资源类型',   'res_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (105,104, '菜单','100','res_type_menu', 'dictionary','1000',0,'104,105','菜单',   'res_type_menu',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (106,104, '页面元素','200','res_type_page_element', 'dictionary','1000',0,'104,106','页面元素',   'res_type_element',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (107,104, '数据查询','300','res_type_query', 'dictionary','1000',0,'104,107','数据查询',   'res_type_query',now(),now()) ;



-- UUM称谓类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (108,-1, '称谓类型','title_type','title_type', 'dictionary','1000',0,'108','称谓类型',   'title_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (109,108, '职称','1','title_type_title', 'dictionary','1000',0,'108,109','职称',   'title_type_title',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (110,108, '岗位','2','title_type_job', 'dictionary','1000',0,'108,110','岗位',   'title_type_job',now(),now()) ;



-- uum资源类型：（补）
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (111,104, '页面','400','res_type_page', 'dictionary','1000',0,'104,111','页面',   'res_type_page',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (112,104, '链接','401','res_type_action_url', 'dictionary','1000',0,'104,112','链接',   'res_type_action_url',now(),now()) ;


-- 代码生成器，解析类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (113,-1, '代码解析类型','tmpl_resolve_type','tmpl_resolve_type', 'dictionary','1000',0,'113','页面',   'tmpl_resolve_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (114,113, 'freemark','0','tmpl_resolve_type_freemark', 'dictionary','1000',0,'113,114','Freemark',   'tmpl_resolve_type_freemark',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (115,113, 'velocity','1','tmpl_resolve_type_velocity', 'dictionary','1000',0,'113,115','Velocity',   'tmpl_resolve_type_velocity',now(),now()) ;

-- 补(Model模板类型)
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (116,20, '(Model层模板)Model.java','15','Model', 'dictionary','1000',0,'20,116','Model类(或实体类)文件',   'Model',now(),now()) ;


-- 补(模板变量解析类型)
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (117,85, 'javascript解析','4','javascript_resolve', 'dictionary','1000',0,'85,117','javascript_resolve',   'javascript_resolve',now(),now()) ;


-- 变量 值类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (118,-1, '变量值类型','variable_value_type','variable_value_type', 'dictionary','1000',0,'118','页面',   'variable_value_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (119,118, '固定值','1','variable_value_type_fix', 'dictionary','1000',0,'118,119','固定值',   'variable_value_type_fix',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (120,118, '运行时','2','variable_value_type_runtime', 'dictionary','1000',0,'118,120','运行时',   'variable_value_type_runtime',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (121,118, '内置值','3','variable_value_type_inner', 'dictionary','1000',0,'118,121','内置值',   'variable_value_type_inner',now(),now()) ;

-- 补（表单控件）
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (122,80, 'URL(页面)','0','url_page', 'dictionary','1000',0,'80,122','URL(页面)',   'url_page',now(),now()) ;



-- 思源软件
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (123,-1, '企业名片','enterprise_card_node','enterprise_card_node', 'dictionary','1000',0,'123','企业名片',   'enterprise_card_node',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (124,123, '宏发地产','1427598448086793','enterprise_card_node_1427598448086793', 'dictionary','1000',0,'123,124','香颂地产',   'enterprise_card_node_1427598448086793',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (125,123, '中粮地产','3976','enterprise_card_node_1467662161427772', 'dictionary','1000',0,'123,125','中粮地产',   'enterprise_card_node_1467662161427772',now(),now()) ;


-- (流程用户)数据映射类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (126,-1, '映射类型','user_data_config_config_type','user_data_config_config_type', 'dictionary','1000',0,'126','映射类型',   'user_data_config_config_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (127,126, '外部系统','1','user_data_config_remote', 'dictionary','1000',0,'126,127','外部系统',   'user_data_config_remote',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (128,126, '本地系统','2','user_data_config_local', 'dictionary','1000',0,'126,128','本地系统',   'user_data_config_local',now(),now()) ;

-- 映射对象类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (129,-1, '对象映射类型','object_mapping_type','object_mapping_type', 'dictionary','1000',0,'129','映射类型',   'object_mapping_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (130,129, '组织','3','mapping_org', 'dictionary','1000',0,'129,130','组织',   'mapping_org',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (131,129, '用户','5','mapping_user', 'dictionary','1000',0,'129,131','用户',   'mapping_user',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (132,129, '岗位','2','mapping_position', 'dictionary','1000',0,'129,132','岗位',   'mapping_position',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (133,129, '组','4','mapping_group', 'dictionary','1000',0,'129,133','组',   'mapping_group',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (134,129, '角色','7','mapping_role', 'dictionary','1000',0,'129,134','角色',   'mapping_role',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (135,129, '称谓','1','mapping_title', 'dictionary','1000',0,'129,135','称谓',   'mapping_title',now(),now()) ;

-- 参数值类型(ext_user_data_config_param.param_type):100=登陆用户的租户编码 104=登陆用户的组织ID 105=登陆用户的用户ID 102=登陆用户的岗位ID  ; 200=静态值 ;  300=从审批对象里取值
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (136,-1, '参数类型','user_data_config_param','user_data_config_param', 'dictionary','1000',0,'136','参数类型',   'user_data_config_param',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (137,136, '登陆用户的租户编码','100','param_loginer_app_code', 'dictionary','1000',0,'136,137','登陆用户的租户编码',   'param_loginer_app_code',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (138,136, '登陆用户的组织ID','104','param_loginer_org_id', 'dictionary','1000',0,'136,138','登陆用户的组织ID',   'param_loginer_org_id',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (139,136, '登陆用户的用户ID','105','param_loginer_user_id', 'dictionary','1000',0,'136,139','登陆用户的用户ID',   'param_loginer_user_id',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (140,136, '登陆用户的(主)岗位ID','102','param_loginer_postion_id', 'dictionary','1000',0,'136,140','登陆用户的(主)岗位ID',   'param_loginer_postion_id',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (141,136, '静态值','200','param_static', 'dictionary','1000',0,'136,141','静态值',   'param_static',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (142,136, '审批对象扩展属性','300','param_approve_express', 'dictionary','1000',0,'136,142','审批对象扩展属性',   'param_approve_express',now(),now()) ;

-- 表单按钮定义
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (143,-1, '表单节点操作按钮','','form_node_button_type', 'dictionary','1000',0,'143','表单节点操作按钮', 'form_node_button_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (144,143, '同意','1','button_type_agree', 'dictionary','1000',0,'143,144','同意',   'button_type_agree',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (145,143, '驳回(上一步)','2','button_type_reject', 'dictionary','1000',0,'143,145','驳回到上一步',   'button_type_reject',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (146,143, '驳回到发起人','3','button_type_reject_to_starter', 'dictionary','1000',0,'143,146','流程驳回到拟稿节点(需设置节点类型为拟稿节点)',   'button_type_reject_to_starter',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (147,143, '驳回到选择节点','4','button_type_reject_to_choose_node', 'dictionary','1000',0,'143,146','驳回到选择节点',   'button_type_reject_to_choose_node',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (148,143, '(转交)代理','5','button_type_proxy_do', 'dictionary','1000',1,'143,148','转交代办',   'button_type_proxy_do',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (149,143, '沟通(暂不启用)','6','button_type_communicate', 'dictionary','1000',0,'143,149','沟通（把人员加进来可查看单据，给出意见，不能提交下一步流程审批）', 'button_type_communicate',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (150,143, '保存表单','7','button_type_save_form', 'dictionary','1000',0,'143,150','保存表单', 'button_type_save_form',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (151,143, '保存草稿','8','button_type_save_draft', 'dictionary','1000',0,'143,151','保存草稿', 'button_type_save_draft',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (152,143, '作废','9','button_type_abort', 'dictionary','1000',0,'143,152','作废', 'button_type_abort',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (153,143, '(抢单)授理','10','button_type_accept', 'dictionary','1000',0,'143,153','(抢单)授理', 'button_type_accept',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (154,143, '(发起人)撤回','11','button_type_reback', 'dictionary','1000',3,'143,154','(发起人)撤回', 'button_type_reback',now(),now()) ;

-- 表单定义类别
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (155,-1, '表单定义类别','','form_definition_type', 'dictionary','1000',0,'155','表单定义类别', 'form_definition_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (156,155, '任务表单','1','form_definition_type_task', 'dictionary','1000',0,'155,156','任务表单',   'form_definition_type_task',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (157,155, '全局表单','2','form_definition_type_global', 'dictionary','1000',0,'155,157','全局表单',   'form_definition_type_global',now(),now()) ;

-- 表单定义类别
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (158,-1, '表单类型','','act_form_type', 'dictionary','1000',0,'158','表单类', 'act_form_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (159,158, 'URL表单','1','act_form_type_url', 'dictionary','1000',0,'158,159','URL表单', 'act_form_type_url',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (160,158, '动态表单','2','act_form_type_syn', 'dictionary','1000',0,'158,160','动态表单', 'act_form_type_syn',now(),now()) ;

-- 流程节点定义类别
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (161,-1, '流程节点定义业务类别','node_task_biz_type','node_task_biz_type', 'dictionary','1000',0,'161','流程节点定义业务类别', 'node_task_biz_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (162,161, '拟稿节点    (退回到发起人生效)','0','node_task_biz_type_draftnode', 'dictionary','1000',0,'161,162','拟稿节点',   'node_task_biz_type_draftnode',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (163,161, '普通任务节点','1','node_task_biz_type_undraftnode', 'dictionary','1000',0,'163,161','普通任务节点',   'node_task_biz_type_undraftnode',now(),now()) ;

-- 流程节点跳转类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (164,-1, '流程节点跳转类型','node_jump_type','node_jump_type', 'dictionary','1000',0,'161','流程节点定义业务类别', 'node_task_biz_type',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (165,164, '正常跳转','1','node_jump_type_normal', 'dictionary','1000',0,'161,162','正常跳转',   'node_jump_type_normal',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (166,164, '选择跳转    (选择分支的时候生效)','2','node_jump_type_choose_road', 'dictionary','1000',0,'163,161','选择跳转',   'node_jump_type_choose_road',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (167,164, '自由跳转    (选择分支的时候生效)','3','node_jump_type_any_jump', 'dictionary','1000',0,'163,161','自由跳转',   'node_jump_type_any_jump',now(),now()) ;

-- 流程节点变量来源设置
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (168,-1, '流程节点变量来源设置','node_variable_copy','node_variable_copy', 'dictionary','1000',0,'168','流程节点变量来源设置', 'node_variable_copy',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (169,168, '当前任务','0','node_variable_copy_from_curr_task', 'dictionary','1000',0,'168,169','当前任务',   'node_variable_copy_from_curr_task',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (170,168, '全局变量','1','node_variable_copy_from_global_variable', 'dictionary','1000',0,'168,170','全局变量',   'node_variable_copy_from_global_variable',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (171,168, '第三方接口','2','node_variable_copy_custom', 'dictionary','1000',0,'168,171','第三方接口',   'node_variable_copy_custom',now(),now()) ;

-- 补一个表单按钮 
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (172,143, '不同意(流程继续往下走)','12','button_type_disagree', 'dictionary','1000',0,'143,172','流程正常流转(默认设置流程变量action=disagree)', 'button_type_desagree',now(),now()) ;


-- 补一个节点类型
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (173,161, '首个任务节点（可选配置）','2','node_task_biz_type_firstnode', 'dictionary','1000',0,'173,157','首个任务节点',   'node_task_biz_type_firstnode',now(),now()) ;


-- 补流程审批动作字典
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (174,143, '(任意)撤回','19','button_type_any_reback', 'dictionary','1000',3,'143,174','(任意)撤回', 'button_type_any_reback',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (175,143, '加签(产生待办)','14','button_type_add_assign', 'dictionary','1000',0,'143,175','加签(加签至流程节点之外的人员审批)', 'button_type_add_assign',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (176,143, '转发(不产生待办)','15','button_type_forword_read', 'dictionary','1000',1,'143,176','转发(不产生待办)', 'button_type_forword_read',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (177,143, '抄送(不产生待办)','16','button_type_copy_send', 'dictionary','1000',0,'143,177','抄送(不产生待办)', 'button_type_copy_send',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (178,143, '不同意(流程结束)','17','button_type_disagree_to_close', 'dictionary','1000',0,'143,178','不同意(流程结束)', 'button_type_disagree_to_close',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (179,143, '不同意(流程实例级联删除)','18','button_type_disagree_to_clear', 'dictionary','1000',0,'143,179','不同意(流程实例级联删除)', 'button_type_disagree_to_clear',now(),now()) ;
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (180,143, '发起','13','button_type_start', 'dictionary','1000',0,'143,180','发起', 'button_type_start',now(),now()) ;



INSERT INTO `sys_dictionary` (id,`pid`, `name`, `value`, `code`, `category_code`, `app_code`, `sn`, `node_path`, `remark`, `gid`, `create_time`, `update_time`) VALUES (181,'164', '审批用户为空自动跳过', '5', 'node_jump_type_empty_approve_user_auto_jump', 'dictionary', '1000', '0', '164,181', '审批用户为空自动跳过', 'node_jump_type_empty_approve_user_auto_jump', '2017-05-12 10:28:39', '2017-05-12 10:28:39');


-- 服务器类型
INSERT INTO `sys_dictionary` (id,`pid`, `name`, `value`, `code`, `category_code`, `app_code`, `sn`, `node_path`, `remark`, `gid`, `create_time`, `update_time`) VALUES (182,'-1', '服务器类型', '0', 'machine_type', 'dictionary', '1000', '0', '182', '服务器类型', 'machine_type', '2017-05-12 10:28:39', '2017-05-12 10:28:39');
INSERT INTO `sys_dictionary` (id,`pid`, `name`, `value`, `code`, `category_code`, `app_code`, `sn`, `node_path`, `remark`, `gid`, `create_time`, `update_time`) VALUES (183,'182', '数据库服务器', '1', 'database', 'dictionary', '1000', '0', '182,183', '数据库服务器', 'database', '2017-05-12 10:28:39', '2017-05-12 10:28:39');
INSERT INTO `sys_dictionary` (id,`pid`, `name`, `value`, `code`, `category_code`, `app_code`, `sn`, `node_path`, `remark`, `gid`, `create_time`, `update_time`) VALUES (184,'182', 'Redis服务器', '2', 'redis', 'dictionary', '1000', '0', '182,184', 'Redis服务器', 'redis', '2017-05-12 10:28:39', '2017-05-12 10:28:39');



-- 流程发起参数变量设置
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (185,-1,'简单数据类型','variable_data_type','variable_data_type','dictionary',NULL,'1','1000',0,'185',NULL,'variable_data_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (186,185,'数字类型','1','variable_number','dictionary',NULL,'1','1000',0,'185,186',NULL,'bJPHUq9Wd4iTiMAqp3nim','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (187,185,'字符','2','variable_string','dictionary',NULL,'1','1000',0,'185,187',NULL,'YGkcBDaeNKyfv49ZdpvH','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (188,185,'日期','3','variable_date','dictionary',NULL,'1','1000',0,'185,188',NULL,'4ZLBpHuhSCVxyyo2aWisG','2018-04-23 10:57:50','2018-04-23 10:57:50');

-- 技能定义数据类型：0=技能分类 1=具体技能
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (189,-1,'技能定义数据类型','tech_definition_data_type','tech_definition_data_type','dictionary',NULL,'1','1000',0,'189',NULL,'tech_definition_data_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (190,189,'技能分类','0','tech_category','dictionary',NULL,'1','1000',0,'189,190',NULL,'tech_category','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (191,189,'具体技能','1','tech_def','dictionary',NULL,'1','1000',0,'189,191',NULL,'tech_def','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- 在线报表生成类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (192,-1,'报表数据生成类型','rpt_general_type','rpt_general_type','dictionary',NULL,'1','1000',0,'192',NULL,'rpt_general_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (193,192,'SQL语句','1','rpt_general_type_sql','dictionary',NULL,'1','1000',0,'192,193',NULL,'rpt_general_type_sql','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (194,192,'数据表','2','rpt_general_type_table','dictionary',NULL,'1','1000',0,'192,194',NULL,'rpt_general_type_table','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (195,192,'DB视图','3','rpt_general_type_view','dictionary',NULL,'1','1000',0,'192,195',NULL,'rpt_general_type_view','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (196,192,'Http_Json','4','rpt_general_type_http_json','dictionary',NULL,'1','1000',0,'192,196',NULL,'rpt_general_type_http_json','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (197,192,'存储过程','5','rpt_general_type_db_procedure','dictionary',NULL,'1','1000',0,'192,197',NULL,'rpt_general_type_db_procedure','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表数据库类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (198,-1,'报表数据库类型','rpt_db_type','rpt_db_type','dictionary',NULL,'1','1000',0,'198',NULL,'rpt_db_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (199,198,'MySQL','1','rpt_db_type_mysql','dictionary',NULL,'1','1000',0,'198,199',NULL,'rpt_db_type_mysql','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (200,198,'Oracle','2','rpt_db_type_oracle','dictionary',NULL,'1','1000',0,'198,200',NULL,'rpt_db_type_oracle','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (201,198,'SqlServer','3','rpt_db_type_sqlserver','dictionary',NULL,'1','1000',0,'198,201',NULL,'rpt_db_type_sqlserver','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (202,198,'PostgreSQL','4','rpt_db_type_postgresql','dictionary',NULL,'1','1000',0,'198,201',NULL,'rpt_db_type_postgresql','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- 是和否用字符true和false表示
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (203,-1,'是和否(true和false字符表示)','string_true_or_false','string_true_or_false','dictionary',NULL,'1','1000',0,'203',NULL,'string_true_or_false','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (204,203,'是','1','true','dictionary',NULL,'1','1000',0,'203,204',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (205,203,'否','0','false','dictionary',NULL,'1','1000',0,'203,205',NULL,'false','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 页面布局
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (206,-1,'报表页面布局','report_file_layout','report_file_layout','dictionary',NULL,'1','1000',0,'206',NULL,'report_file_layout','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (207,206,'左右布局','1','left_right_layout','dictionary',NULL,'1','1000',0,'206,207',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (208,206,'上下布局','2','up_down_layout','dictionary',NULL,'1','1000',0,'206,208',NULL,'false','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (209,206,'左上下（用于子报表，左是高级查询区）','3','left_up_down_layout','dictionary',NULL,'1','1000',0,'206,209',NULL,'false','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (210,206,'上中下（用于子报表，上是高级查询区）','4','up_mid_down_layout','dictionary',NULL,'1','1000',0,'206,210',NULL,'false','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (211,206,'左中右（用于子报表，左是高级查询区）','5','left_mid_right_layout','dictionary',NULL,'1','1000',0,'206,211',NULL,'false','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表排序模式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (212,-1,'报表排序模式','report_sort_mode','report_sort_mode','dictionary',NULL,'1','1000',0,'212',NULL,'report_sort_mode','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (213,212,'客户端排序','1','report_sort_mode_client','dictionary',NULL,'1','1000',0,'212,213',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (214,212,'服务器端排序','2','report_sort_mode_server','dictionary',NULL,'1','1000',0,'212,214',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表列对齐方式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (215,-1,'报表列对齐方式','report_column_align_type','report_column_align_type','dictionary',NULL,'1','1000',0,'215',NULL,'report_sort_mode','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (216,215,'左对齐','1','report_column_align_type_left','dictionary',NULL,'1','1000',0,'215,216',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (217,215,'居中对齐','2','report_column_align_type_mid','dictionary',NULL,'1','1000',0,'215,217',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (218,215,'右对齐','3','report_column_align_type_right','dictionary',NULL,'1','1000',0,'215,218',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表列模糊查询匹配方式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (219,-1,'报表列对齐方式','report_column_like_search_type','report_column_like_search_type','dictionary',NULL,'1','1000',0,'219',NULL,'report_sort_mode','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (220,219,'匹配开头(like \'张%\')','1','report_column_like_search_type_left','dictionary',NULL,'1','1000',0,'219,220',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (221,219,'匹配中间(like \'%张%\')','2','report_column_like_search_type_mid','dictionary',NULL,'1','1000',0,'219,221',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (222,219,'匹配结尾(like \'%张\')','3','report_column_like_search_type_right','dictionary',NULL,'1','1000',0,'219,222',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (223,219,'不做包装处理','4','report_column_like_search_type_no_wrap','dictionary',NULL,'1','1000',0,'219,223',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表业务（按钮）控件类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (224,-1,'报表业务控件类型','report_biz_controll_type','report_biz_controll_type','dictionary',NULL,'1','1000',0,'224',NULL,'report_biz_controll_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (225,224,'按钮','1','report_biz_controll_type_button','dictionary',NULL,'1','1000',0,'224,225',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (226,224,'下拉框（单选）','2','report_biz_controll_type_select_sign','dictionary',NULL,'1','1000',0,'224,226',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (227,224,'下拉框（多选）','3','report_biz_controll_type_select_mutil','dictionary',NULL,'1','1000',0,'224,227',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (228,224,'文本框','4','report_biz_controll_type_text','dictionary',NULL,'1','1000',0,'224,228',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (229,224,'文本域','5','report_biz_controll_type_text_area','dictionary',NULL,'1','1000',0,'224,229',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (230,224,'文件','6','report_biz_controll_type_file','dictionary',NULL,'1','1000',0,'224,230',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (231,224,'日历','7','report_biz_controll_type_date_pick','dictionary',NULL,'1','1000',0,'224,231',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (232,224,'数值框','8','report_biz_controll_type_number','dictionary',NULL,'1','1000',0,'224,232',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表业务（按钮）控件事件响应类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (233,-1,'报表控件事件响应类型','report_controll_event_type','report_controll_event_type','dictionary',NULL,'1','1000',0,'233',NULL,'report_controll_event_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (234,233,'请求URL','1','report_controll_event_type_url','dictionary',NULL,'1','1000',0,'233,234',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (235,233,'发送消息(rabitMQ)','2','report_controll_event_type_msg_rabitmq','dictionary',NULL,'1','1000',0,'233,235',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表数据导出文件类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (236,-1,'报表数据导出文件','report_export_file_type','report_export_file_type','dictionary',NULL,'1','1000',0,'236',NULL,'report_export_file_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (237,236,'excel','1','report_export_file_type_excel','dictionary',NULL,'1','1000',0,'236,237',NULL,'true','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (238,236,'word','2','report_export_file_type_word','dictionary',NULL,'0','1000',0,'236,238',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (239,236,'pdf','3','report_export_file_type_pdf','dictionary',NULL,'0','1000',0,'236,239',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (240,236,'txt','4','report_export_file_type_txt','dictionary',NULL,'0','1000',0,'236,240',NULL,'true','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- MySQL数据库类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (241,-1,'MySQL数据库字段类型','report_mysql_db_column_type','report_mysql_db_column_type','dictionary',NULL,'1','1000',0,'241',NULL,'report_mysql_db_column_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (242,241,'char','100','char','dictionary',NULL,'1','1000',1,'241,242',NULL,'char','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (243,241,'varchar','101','varchar','dictionary',NULL,'1','1000',1,'241,243',NULL,'varchar','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (244,241,'text','102','text','dictionary',NULL,'1','1000',1,'241,244',NULL,'text','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (245,241,'longtext','103','longtext','dictionary',NULL,'1','1000',1,'241,245',NULL,'longtext','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (246,241,'mediumtext','104','mediumtext','dictionary',NULL,'1','1000',1,'241,246',NULL,'mediumtext','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (247,241,'tinytext','105','tinytext','dictionary',NULL,'1','1000',1,'241,247',NULL,'tinytext','2018-04-23 10:57:24','2018-04-23 10:57:24');


INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (248,241,'int','200','int','dictionary',NULL,'1','1000',1,'241,248',NULL,'int','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (249,241,'smallint','201','smallint','dictionary',NULL,'1','1000',1,'241,249',NULL,'smallint','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (250,241,'tinyint','202','tinyint','dictionary',NULL,'1','1000',1,'241,250',NULL,'tinyint','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (251,241,'mediumint','203','mediumint','dictionary',NULL,'1','1000',1,'241,251',NULL,'mediumint','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (252,241,'bigint','204','bigint','dictionary',NULL,'1','1000',1,'241,252',NULL,'bigint','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (254,241,'float','205','float','dictionary',NULL,'1','1000',1,'241,253',NULL,'float','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (255,241,'decimal','206','decimal','dictionary',NULL,'1','1000',1,'241,254',NULL,'decimal','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (256,241,'double','207','double','dictionary',NULL,'1','1000',1,'241,256',NULL,'double','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (257,241,'numeric','208','numeric','dictionary',NULL,'0','1000',1,'241,257',NULL,'numeric','2018-04-23 10:57:24','2018-04-23 10:57:24');


INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (258,241,'date','300','date','dictionary',NULL,'1','1000',1,'241,258',NULL,'date','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (259,241,'datetime','301','datetime','dictionary',NULL,'1','1000',1,'241,259',NULL,'datetime','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (260,241,'time','302','time','dictionary',NULL,'1','1000',1,'241,260',NULL,'time','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (261,241,'timestamp','303','timestamp','dictionary',NULL,'1','1000',1,'241,261',NULL,'timestamp','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (262,241,'year','304','year','dictionary',NULL,'1','1000',1,'241,262',NULL,'year','2018-04-23 10:57:24','2018-04-23 10:57:24');

INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (263,241,'bit','400','bit','dictionary',NULL,'1','1000',1,'241,263',NULL,'bit','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (264,241,'bool','401','bool','dictionary',NULL,'0','1000',1,'241,264',NULL,'bool','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (265,241,'boolean','402','boolean','dictionary',NULL,'0','1000',1,'241,265',NULL,'boolean','2018-04-23 10:57:24','2018-04-23 10:57:24');

INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (266,241,'blob','400','blob','dictionary',NULL,'1','1000',1,'241,266',NULL,'blob','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (267,241,'longblob','401','longblob','dictionary',NULL,'1','1000',1,'241,267',NULL,'longblob','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (268,241,'mediumblob','402','mediumblob','dictionary',NULL,'1','1000',1,'241,268',NULL,'mediumblob','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (269,241,'tinyblob','402','tinyblob','dictionary',NULL,'1','1000',1,'241,269',NULL,'tinyblob','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- 报表数据导出模式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (270,-1,'报表数据导出模式','report_data_export_mode','report_data_export_mode','dictionary',NULL,'1','1000',0,'270',NULL,'report_data_export_mode','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (271,270,'(默认)全部字段导出','0','export_mode_all_column','dictionary',NULL,'1','1000',1,'270,271',NULL,'export_mode_all_column','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (272,270,'用户可选择字段导出','1','export_mode_selected_column','dictionary',NULL,'1','1000',1,'270,272',NULL,'export_mode_selected_column','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表数据导入模式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (273,-1,'报表数据导入模式','report_data_import_mode','report_data_import_mode','dictionary',NULL,'1','1000',0,'273',NULL,'report_data_import_mode','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (274,273,'(默认)全部字段导入','0','import_mode_all_column','dictionary',NULL,'1','1000',1,'273,274',NULL,'import_mode_all_column','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (275,273,'用户可选择字段导入','1','import_mode_selected_column','dictionary',NULL,'1','1000',1,'273,275',NULL,'import_mode_selected_column','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- 报表数据导入数据副本存储精度模式
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (276,-1,'报表数据导入数据副本存储精度模式','rpt_imported_data_store_precision','rpt_imported_data_store_precision','dictionary',NULL,'1','1000',0,'276',NULL,'rpt_imported_data_store_precision','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (277,276,'全字段字符存储','1','rpt_imported_data_store_precision_string','dictionary',NULL,'1','1000',1,'276,277',NULL,'rpt_imported_data_store_precision_string','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (278,276,'按定义的字段类型存储','2','rpt_imported_data_store_precision_column','dictionary',NULL,'1','1000',1,'276,278',NULL,'rpt_imported_data_store_precision_column','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- API接口类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (279,-1,'API接口类型','api_system_api_type','api_system_api_type','dictionary',NULL,'1','1000',0,'279',NULL,'api_system_api_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (280,279,'内部系统接口','1','local_system_api','dictionary',NULL,'1','1000',1,'279,280',NULL,'local_system_api','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (281,279,'外部系统接口','2','remote_system_api','dictionary',NULL,'1','1000',1,'279,281',NULL,'remote_system_api','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- API报文发送类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (282,-1,'API报文发送类型','api_message_send_type','api_message_send_type','dictionary',NULL,'1','1000',0,'282,',NULL,'api_message_send_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (283,282,'form_data','1','form_data','dictionary',NULL,'1','1000',1,'282,283',NULL,'form_data','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (284,282,'x-www-form-urlencodeed','2','x-www-form-urlencodeed','dictionary',NULL,'1','1000',1,'282,284',NULL,'x-www-form-urlencodeed','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (285,282,'raw','3','raw','dictionary',NULL,'1','1000',1,'282,285',NULL,'raw','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (286,282,'binary','4','binary','dictionary',NULL,'1','1000',1,'282,286',NULL,'binary','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- API参数值类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (287,-1,'API接口参数类型','api_param_request_type','api_param_request_type','dictionary',NULL,'1','1000',0,'287,',NULL,'api_param_request_type','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (288,287,'静态值','200','param_type_static','dictionary',NULL,'1','1000',1,'287,288,',NULL,'param_type_static','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (289,287,'动态值','100','param_type_runtime','dictionary',NULL,'1','1000',1,'287,289,',NULL,'param_type_runtime','2018-04-23 10:57:24','2018-04-23 10:57:24');

-- HTTP请求类型
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (290,-1,'Http请求方法','http_request_method','http_request_method','dictionary',NULL,'1','1000',0,'290,',NULL,'http_request_method','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (291,290,'GET','1','GET','dictionary',NULL,'1','1000',1,'290,291,',NULL,'GET','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (292,290,'POST','2','POST','dictionary',NULL,'1','1000',1,'290,292,',NULL,'POST','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (293,290,'PUT','3','PUT','dictionary',NULL,'1','1000',0,'290,293,',NULL,'PUT','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (294,290,'PATCH','4','PATCH','dictionary',NULL,'1','1000',1,'290,294,',NULL,'PATCH','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (295,290,'DELETE','5','DELETE','dictionary',NULL,'1','1000',1,'290,295,',NULL,'DELETE','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (296,290,'COPY','6','COPY','dictionary',NULL,'1','1000',0,'290,296,',NULL,'COPY','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (297,290,'HEAD','7','HEAD','dictionary',NULL,'1','1000',1,'290,297,',NULL,'HEAD','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (298,290,'OPTIONS','8','OPTIONS','dictionary',NULL,'1','1000',1,'290,298,',NULL,'OPTIONS','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (299,290,'LINK','9','LINK','dictionary',NULL,'1','1000',0,'290,299,',NULL,'LINK','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (300,290,'UNLINK','10','UNLINK','dictionary',NULL,'1','1000',1,'290,300,',NULL,'UNLINK','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (301,290,'PURGE','11','PURGE','dictionary',NULL,'1','1000',1,'290,301',NULL,'PURGE','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (302,290,'LOCK','12','LOCK','dictionary',NULL,'1','1000',0,'290,302,',NULL,'LOCK','2018-03-19 09:49:35','2018-03-19 09:49:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (303,290,'UNLOCK','13','UNLOCK','dictionary',NULL,'1','1000',1,'290,303',NULL,'UNLOCK','2018-04-23 10:54:35','2018-04-23 10:54:35');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (304,290,'PROPFIND','14','PROPFIND','dictionary',NULL,'1','1000',1,'290,304',NULL,'PROPFIND','2018-04-23 10:57:24','2018-04-23 10:57:24');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (305,290,'VIEW','15','VIEW','dictionary',NULL,'1','1000',1,'290,305',NULL,'VIEW','2018-04-23 10:57:24','2018-04-23 10:57:24');


-- 服务器类型
INSERT INTO `sys_dictionary` (id,`pid`, `name`, `value`, `code`, `category_code`, `app_code`, `sn`, `node_path`, `remark`, `gid`, `create_time`, `update_time`) VALUES (306,'182', 'RabitMQ服务器', '3', 'rabitmq', 'dictionary', '1000', '0', '182,306', 'RabitMQ服务器', 'rabitmq', '2017-05-12 10:28:39', '2017-05-12 10:28:39');


-- 单码生成器字段控件类型（补）
insert into sys_dictionary(id,pid,name,value,code,category_code,app_code,sn,node_path,remark,gid,create_time,update_time)  values (307,58, '日期（单个框）','10','datePickerSingle', 'dictionary','1000',0,'58,307','日期框(单个框)', 'datePickerSingle',now(),now()) ;


drop table  if exists sys_machine;
CREATE TABLE `sys_machine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) DEFAULT NULL COMMENT '服务器编码',
  `app_code` varchar(40) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '服务器名称',
  `user_name` varchar(255)  NULL COMMENT '登陆名称',
  `user_password` varchar(255)  NULL COMMENT '登陆密码',
  `url` varchar(500) DEFAULT NULL COMMENT '链接url',
  `type` varchar(40) DEFAULT NULL COMMENT 'datasource=数据库服务器 、redis = redis服务器',
 
  
  `status` int(4) DEFAULT '0' COMMENT '数据源状态：1=可用 0=不可用',
  `address` varchar(255) DEFAULT NULL COMMENT '地址,可以是ip也可以是域名',
  `port` varchar(20) DEFAULT NULL COMMENT '端口',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_ds_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务器管理';



drop table if exists sys_log;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  
  `tenant_code` varchar(100) DEFAULT NULL COMMENT '租户编码',
  `tenant_name` varchar(200) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL comment '系统编码',
  `app_name` varchar(40) DEFAULT NULL comment '系统名称',
 
  
  `handle_user` varchar(50) DEFAULT NULL COMMENT '操作用户',
  `handle_time` datetime NOT NULL COMMENT '操作日期',
  `handle_ip` varchar(15) COMMENT '操作IP',
  `handle_action` varchar(50) COMMENT '操作行为',
  `handlers` varchar(200) COMMENT '被操作者',
  `handle_result` int(2) COMMENT '操作结果 1=成功 0=失败',

  `module_code` varchar(40) DEFAULT NULL comment '模块码',
  `module_name` varchar(40) DEFAULT NULL comment '模块名',
  
  `resource_name` varchar(40) DEFAULT NULL comment '操作日志：访问的资源名称(菜单、URL)',
  `resource_code` varchar(40) DEFAULT NULL comment '操作日志：访问的资源编码(菜单、URL)',
  
  `query_field` varchar(10) DEFAULT NULL comment '操作日志：查询字段',
  `query_value` varchar(2000) DEFAULT NULL COMMENT '操作日志：查询值',
  `query_count` bigint(20) DEFAULT NULL COMMENT '操作日志：查询返回数',
  
  `remark` varchar(256) DEFAULT NULL ,

  `data_type` varchar(2) comment '1=登陆日志(记录用户登录日志;包含登录成功、失败的各条记录) 2=系统管理日志(记录管理员操作;包括创建、删除账号、启用、禁用账号) 3=用户操作日志(记录用户敏感查询操作、导出操作;包含操作人、查询模块、查询值、查询结果、条数)' 
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime  NOT NULL  COMMENT '更新日期',
 
  PRIMARY KEY (`id`)
 
) ENGINE=myisam AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统日志表,如果达到阀值表名跟据日期自动创建';


-- 系统日志明系:涉及一个http请求到结束,记录请求资源、请求参数、SQL语句、返回响应的耗时
drop table  if exists sys_log_detail;
CREATE TABLE `sys_log_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_id` bigint(20) NOT NULL COMMENT '日志ID',
  `execute_type` int(2) DEFAULT NULL COMMENT '1=http 2=SQL',
  `execute_content` text DEFAULT NULL COMMENT '执行的请求内容,如果是http就是url; 如果是SQL语句就是语句',
  `execute_param` text DEFAULT NULL COMMENT '执行的内容参数值',  
  `execute_sn` int(4) default 0  COMMENT '执行的堆栈序号',
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cms_content_code` (`code`) USING BTREE
) ENGINE=myisam AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='日志明细';




drop table  if exists cms_content;
-- 内容表
CREATE TABLE `cms_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `object_id` bigint(20) NOT NULL COMMENT '对象ID，如果内容为新闻，则是新闻表的ID',
  `title` varchar(200) DEFAULT NULL  COMMENT '标题',
  `type` int(10) DEFAULT NULL COMMENT '类型:300=新闻 301=博客 302=贴子(3开头的都是FreeMark解析内容) 303=Html页面内容    见： 3xx=ftl内容模板 4xx=vm内容模板',
  `enable` int(4) DEFAULT NULL COMMENT '是否启用： 0=不启用 1=启用',
  `sn` int(10) default 0  COMMENT '排序号',
  `content` text DEFAULT NULL COMMENT '备注',
  `code` varchar(40) DEFAULT NULL  COMMENT '编码',
  `app_code` varchar(40) DEFAULT NULL  COMMENT '编码',
    
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cms_content_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='字典名称';

-- 栏目表: 栏目和新闻是多对多的关系
drop table  if exists cms_channel;
CREATE TABLE `cms_channel` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `pid` bigint(20) DEFAULT NULL,
    `code` varchar(40) NOT NULL ,
    `status` varchar(4) NULL ,
    `category_code` varchar(255) DEFAULT NULL COMMENT '分类代码',
    `name` varchar(200) DEFAULT NULL ,
    `node_path` varchar(1000) DEFAULT NULL,
    `remark` varchar(500) DEFAULT NULL ,
    `app_code` varchar(40) DEFAULT NULL ,
    `sn` int DEFAULT NULL COMMENT '排序号',
    `gid` varchar(40) DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime DEFAULT NULL,
    
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_cms_channel_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='栏目表';


insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,'entertainment',0,'娱乐新闻','1','1000','entertainment',now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,        'sport',0,'体育新闻','2','1000','sport', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,    'financial',0,'财经新闻','3','1000','financial', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,         'blog',0,'博客专栏','4','1000','blog', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,        'house',0,'房产','5','1000','house', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,   'technology',0,'科技','6','1000','technology', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,      'healthy',0,'健康养生','7','1000','healthy', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,     'cultural',0,'文化','8','1000','cultural', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,      'tourism',0,'旅游','9','1000','tourism', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,    'education',0,'教育','10','1000','education', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,        'games',0,'游戏','11','1000','games', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,         'baby',0,'母婴','12','1000','baby', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,         'food',0,'美食','13','1000','food', now(),now());
insert into cms_channel(status,category_code,pid,code,sn,name,node_path,app_code,gid,create_time,update_time) values ('1','channel',-1,      'fiction',0,'小说','14','1000','fiction', now(),now());



-- 先是有物理模板定义，然后可分类(有个分类表)，有个tag表，如：按颜色或按标签找到模板下载
drop table  IF EXISTS  cms_template ;
 CREATE TABLE `cms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `title` varchar(45) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL COMMENT '编码',
  `enable` int(4) DEFAULT NULL COMMENT '是否启用： 0=不启用 1=启用',
  
  
  `thumbnail_name` varchar(1000) DEFAULT NULL COMMENT '缩略图名称',
  `path_thumbnail` varchar(1000) DEFAULT NULL COMMENT '缩略图路径',

  `path_thumbnail_l` varchar(1000) DEFAULT NULL COMMENT '缩略图路径(大)',
  `path_thumbnail_m` varchar(1000) DEFAULT NULL COMMENT '缩略图路径(中)',
  `path_thumbnail_s` varchar(1000) DEFAULT NULL COMMENT '缩略图路径(小)',
     
  `path_zip` varchar(1000) DEFAULT NULL COMMENT '缩略图下载的物理文件路径',
  `sn` int(11) DEFAULT NULL COMMENT '排序号',
  `app_code` varchar(40) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `channel_id` bigint(20) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cms_template` (`code`) USING BTREE
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='内容模板表';




-- 附件表 注：附件与分类有个多对多的关系表
drop table  if exists cms_attachment;
CREATE TABLE `cms_attachment` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `code` varchar(40) NOT NULL ,
    `name` varchar(200) DEFAULT NULL ,
    `path` varchar(2000) DEFAULT NULL ,
    `size` int DEFAULT NULL  COMMENT '附件大小（1M）',
	`type` varchar(10) DEFAULT NULL COMMENT 'mime类型，如：*.doc,*.pdf,*.txt等值',
	`summary` varchar(500) DEFAULT NULL ,
	`tags` varchar(1000) DEFAULT NULL ,
	`remark` varchar(500) DEFAULT NULL ,
	`app_code` varchar(40) DEFAULT NULL ,
	
	`gid` varchar(40) DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime DEFAULT NULL,
    
	 PRIMARY KEY (`id`),
  	 UNIQUE KEY `uq_cms_attachment_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='附件表';



-- 新闻表 注：新闻和分类有个多对多的关系表、此外还有新闻内容、新闻附件中间表
drop table if exists cms_news;
CREATE TABLE `cms_news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL  COMMENT '名称',
  `code` varchar(40) DEFAULT NULL  COMMENT '编码',
  `title` varchar(200) DEFAULT NULL  COMMENT '标题',
  `summary` text DEFAULT NULL  COMMENT '摘要',
  `author` varchar(200) DEFAULT NULL  COMMENT '作者',
  
  `tags` varchar(500) DEFAULT NULL  COMMENT '新闻的标签',
  `publish_date` datetime DEFAULT NULL COMMENT '发布日期',
  `enable` int DEFAULT NULL COMMENT '0=未启用 1=启用 ',
  `status_auth` int DEFAULT NULL COMMENT '状态：0=未审 1=审核中 2=审核通过',
  `top_days` int DEFAULT NULL COMMENT '置顶日期（天）',
  `cnt_replay` int DEFAULT NULL COMMENT '回复数',
  `cnt_view` int DEFAULT NULL COMMENT '阅读数',
  `sn` int DEFAULT NULL COMMENT '排序号',
  `app_code` varchar(40) DEFAULT NULL ,
  `remark` varchar(200) DEFAULT NULL ,

  `time` datetime DEFAULT NULL COMMENT '时间',
  `address` varchar(200) DEFAULT NULL COMMENT '地点',
  `person` varchar(200) DEFAULT NULL COMMENT '人物',

  `source` varchar(100) DEFAULT NULL COMMENT '来源',
  `source_url` varchar(2000) DEFAULT NULL COMMENT '原链接',
  `generate_type` int DEFAULT NULL COMMENT '生成的类型：0=用户后台发布 1=应用程序采集 2=注册用户自己发布',

  `static_file_flag` int DEFAULT NULL COMMENT '是否生成静态文件 0=未生成 1=已生成',
  `static_file_path` varchar(100) DEFAULT NULL COMMENT '静态文件相对路径',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL ,
  `update_time` datetime DEFAULT NULL,
  
   PRIMARY KEY (`id`),
   UNIQUE KEY `uq_cms_news` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='新闻表';


drop table if exists cms_news_comment;
CREATE TABLE `cms_news_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `news_id` bigint(20) DEFAULT NULL,  
  
  `node_path` varchar(1000) DEFAULT NULL,  
  
  `comment_user_name` varchar(20) DEFAULT NULL,  
  `comment_nick_name` varchar(20) DEFAULT NULL,  
  `content` varchar(1000) DEFAULT NULL,  
  
  `app_code` varchar(40) DEFAULT NULL,
  `sn` int(11) DEFAULT NULL COMMENT '排序号',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='新闻评论表';


drop table  IF EXISTS  cms_resource ;
CREATE TABLE `cms_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '资源名称',
  `alias` varchar(255) NOT NULL COMMENT '资源别名：一般是中文有意义的名称',
  `value` varchar(255) DEFAULT NULL COMMENT '资源值',
  `code` varchar(255) NOT NULL COMMENT '资源编码',
  
  `url` varchar(500) NULL COMMENT '资源路径标识符可以是http的url，也可以是磁盘路径',
  `type` int(2) DEFAULT NULL COMMENT '数据类型：100=目录 2xx=文件:200=txt 201=html 202=css 203=js ',
  `enable` varchar(2) DEFAULT NULL COMMENT '是否启用: 1=启用  0=禁用',
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='站点资源,描述CSS、HTML、文件夹等径信息';



drop table  IF EXISTS  cms_tags ;
CREATE TABLE `cms_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '标签名称',
  `value` varchar(255) DEFAULT NULL COMMENT '标签值',
  `code` varchar(255) NOT NULL COMMENT '标签编码',
  
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- 该中间表暂时不用
-- drop table  IF EXISTS  cms_mid_news_tags ;
-- CREATE TABLE `cms_mid_news_tags` (
--   `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
--   `news_id` bigint(20) NOT NULL COMMENT '内容ID'
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='新闻与标签多对多中间表';

drop table  IF EXISTS  cms_mid_channel_news ;
CREATE TABLE `cms_mid_channel_news` (
  `channel_id` bigint(20) NOT NULL COMMENT '标签ID',
  `news_id` bigint(20) NOT NULL COMMENT '新闻ID'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='栏目与新闻多对多中间表';

drop table  IF EXISTS  cms_mid_template_tags ;
CREATE TABLE `cms_mid_template_tags` (
  `template_id` bigint(20) NOT NULL COMMENT '模板定义ID',
  `tags_id` bigint(20) NOT NULL COMMENT '标签ID'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='模板定义与标签多对多中间表';













 
-- 系统数据源管理
drop table  IF EXISTS  sys_datasource ;
CREATE TABLE `sys_datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) DEFAULT NULL COMMENT '数据源编码',
  `app_code` varchar(40) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '数据源名称',
  `login_name` varchar(255) NOT NULL COMMENT '登陆名称',
  `login_password` varchar(255) NOT NULL COMMENT '登陆密码',
  `login_default_db` varchar(255) NULL COMMENT '登陆的默认数据库名',
  
  `url` varchar(500) DEFAULT NULL COMMENT '链接url',
  `status` int(4) DEFAULT '0' COMMENT '数据源状态：1=可用 0=不可用',

  `address` varchar(255) DEFAULT NULL COMMENT '地址,可以是ip也可以是域名',
  `port` varchar(20) DEFAULT NULL COMMENT '端口',
  `sn` int(11) DEFAULT '0' COMMENT '排序',

  `driver_class_Name` varchar(500) NOT NULL COMMENT '驱动名,如: com.mysql.jdbc.Driver',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_ds_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据源管理';

insert sys_datasource (id,code,app_code,name,login_name,login_password,url,status,address,port,sn,driver_class_name,remark,gid,create_time,update_time) values(1,'localhost','1000','本地数据源','root','123456','jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=6000&socketTimeout=6000',0,'127.0.0.1','3306',0,'com.mysql.jdbc.Driver','本地数据源','localhost',now(),now()) ;



-- 系统(扩展)属性表
drop table  IF EXISTS  sys_property ;
CREATE TABLE `sys_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_code` varchar(40) DEFAULT NULL COMMENT '(外键)引用的编码' ,
  `name` varchar(200) DEFAULT NULL COMMENT '属性名称' ,
  `value` varchar(2000) DEFAULT NULL COMMENT '属性值' ,
  `remark` varchar(500) DEFAULT NULL COMMENT '属性说明' ,
  `app_code` varchar(40) DEFAULT NULL COMMENT '所属应用' ,
  `sn` int(4) DEFAULT '0' ,

  `type` varchar(20) DEFAULT NULL COMMENT 'datasource=jdbc数据源属性  redis=redis数据源属性' ,

  `gid` varchar(200) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='属性管理';
 
-- 初使化本地数据源连接池数据
insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(1,'localhost','initialSize','1','链接池初使化大小','1000',0,'localhost_initialSize',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(2,'localhost','minIdle','1','链接池初使化大小.最小','1000',0,'localhost_minIdle',now(),now(),'datasource');
 
insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(3,'localhost','maxActive','20','链接池初使化大小.最大','1000',0,'localhost_maxActive',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(4,'localhost','maxWait','60000','配置获取连接等待超时的时间','1000',0,'localhost_maxWait',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(5,'localhost','timeBetweenEvictionRunsMillis','60000','配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒','1000',0,'localhost_timeBetweenEvictionRunsMillis',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(6,'localhost','minEvictableIdleTimeMillis','300000','配置一个连接在池中最小生存的时间，单位是毫秒','1000',0,'localhost_minEvictableIdleTimeMillis',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(7,'localhost','validationQuery','select 1 ','验证查询','1000',0,'localhost_validationQuery',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(8,'localhost','testWhileIdle','true','','1000',0,'localhost_testWhileIdle',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(9,'localhost','testOnBorrow','false','','1000',0,'localhost_testOnBorrow',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(10,'localhost','testOnReturn','false','','1000',0,'localhost_testOnReturn',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(11,'localhost','poolPreparedStatements','true','打开PSCache，并且指定每个连接上PSCache的大小','1000',0,'localhost_poolPreparedStatements',now(),now(),'datasource');

insert into sys_property (id,parent_code,name,value,remark,app_code,sn,gid,create_time,update_time,type) values 
(12,'localhost','maxPoolPreparedStatementPerConnectionSize','20','打开PSCache，并且指定每个连接上PSCache的大小','1000',0,'localhost_maxPoolPreparedStatementPerConnectionSize',now(),now(),'datasource');


-- 表定义管理
drop table  IF EXISTS  sys_table ;
CREATE TABLE `sys_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) NOT NULL COMMENT '表名称',
  `model_name` varchar(255) NOT NULL COMMENT '模型名称',
  `datasource_code` varchar(40) DEFAULT NULL COMMENT '数据源编码',
  `db_name` varchar(255) NOT NULL COMMENT '数据库',
  `type` varchar(20) NOT NULL COMMENT '表类型：table or view',
  `engine` varchar(255) NOT NULL COMMENT '表引擎',
  `rows` int(20) DEFAULT NULL COMMENT '表的数据行数',
  `auto_increment` int(20) DEFAULT NULL COMMENT '表的自动增长值',
  `collation` varchar(255) DEFAULT NULL COMMENT '表的编码',
  `create_option` varchar(40) DEFAULT NULL COMMENT '创建项，如限制最大行数',
  `comment` varchar(100) DEFAULT NULL COMMENT '表的注释',
  `version` varchar(100) DEFAULT NULL COMMENT '表的版本号',
  
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据表管理';

alter table sys_table add column project_code varchar(50);

-- 子表定义
drop table  IF EXISTS  sys_table_sub ;
CREATE TABLE `sys_table_sub` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `main_table_id` bigint(20) NOT NULL COMMENT '主表id',
  `main_table_name` varchar(255) NOT NULL COMMENT '主表名称',
  
  `project_code` varchar(50) NOT NULL COMMENT '选用的工程框架',
  `table_name` varchar(255) NOT NULL COMMENT '子表名称',
  `datasource_code` varchar(40) DEFAULT NULL COMMENT '子表数据源编码',
  `db_name` varchar(255) NOT NULL COMMENT '子表数据库',
  `version` varchar(100) DEFAULT NULL COMMENT '子表的版本号',
  
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='子表管理';


-- 工程模板
drop table  IF EXISTS  sys_project ;
CREATE TABLE `sys_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '工程模板名',
  `code` varchar(255) NOT NULL COMMENT '工程模板编码',
  
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(500) DEFAULT NULL COMMENT '工程路径',
  `structure` varchar(255) NOT NULL COMMENT '(maven)结构',
  `eclipse` varchar(255) NOT NULL COMMENT 'eclipse工程',
  `level_db` varchar(255) NOT NULL COMMENT 'Db层技术',
  `level_web` varchar(255) NOT NULL COMMENT 'Web层技术',
  `level_view` varchar(255) NOT NULL COMMENT '视图层技术',
  `plugin` varchar(255) DEFAULT NULL COMMENT '插件名',
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='工程模板管理';

insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (1,'example工程(自研)', 'example_1','example_1.zip','/upload/code/project/example_1.zip','maven','eclipse','ExampleDB','exampleMVC','jsp','spring容器',0,'example框架标准','1000','example-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (2,'spring工程1(典型1)','spring_1','spring_1.zip','/upload/code/project/spring_1.zip','maven','eclipse','Mybatis(3.x)','springMVC','jsp',null,0,'Spring(3.x)+SpringMVC(3.x)+Mybatis(3.x)框架','1000','spring-1-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (3,'spring工程2(典型2)','spring_2','spring_2.zip','/upload/code/project/spring_2.zip','maven','eclipse','Mybatis(3.x)','struts2.x','jsp',null,0,'Spring(3.x)+Struts(2.x)+Mybatis(3.x)框架','1000','spring-2-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (4,'spring工程3(典型3)','spring_3','spring_3.zip','/upload/code/project/spring_3.zip','maven','eclipse','Hibernate(3.x)','Struts(2.x)','jsp',null,0,'Spring(3.x)+SpringMVC(3.x)+Hibernate(3.x)框架','1000','spring-3-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (5,'spring工程4(典型4)','spring_4','spring_4.zip','/upload/code/project/spring_4.zip','maven','eclipse','hibernate3.x','springMVC(3.x)','jsp',null,0,'Spring(3.x)+Struts(2.x)+Hibernate(3.x)框架','1000','spring-4-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (6,'spring工程5(典型5)','spring_5','spring_5.zip','/upload/code/project/spring_5.zip','maven','eclipse','springJDBC(3.x)','springMVC(3.x)','jsp',null,0,'Spring(3.x)+SpringJDBC(3.x)+SpringMVC(3.x)框架','1000','spring-5-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (7,'spring工程6(小众6)','spring_6','spring_6.zip','/upload/code/project/spring_6.zip','maven','eclipse','DbUtil','springMVC(3.x)','jsp',null,0,'Spring(3.x)+SpringMVC(3.x)+DbUtil框架','1000','spring-6-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (8,'spring工程7(小众7)','spring_7','spring_7.zip','/upload/code/project/spring_6.zip','maven','eclipse','DbUtil','springMVC(3.x)','jsp',null,0,'Spring(3.x)+Struts(2.x)+DbUtil框架','1000','spring-7-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (9,'其它(其它1)',         'other_1','other_1.zip','/upload/code/project/other_1.zip','maven','eclipse','DbUtil','Struts(2.x)','jsp',null,0,'Struts(2.x)+DbUtil框架','1000','spring-8-project',now(),now());
insert into sys_project(id,name,code,file_name,file_path,structure,eclipse,level_db,level_web,level_view,plugin,sn,remark,app_code,gid,create_time,update_time) values (10,'思源平台工程(其它2)', 'spring_9','spring_9.zip','/upload/code/project/spring_9.zip','maven','eclipse','Mybatis(3.2.8)','springMvc(4.0.8)','jsp/html',null,0,'SpringMvc(4.0.8)+Mybatis3.2.8框架','1000','spring-9-project',now(),now());


-- 字段定义
drop table  IF EXISTS  sys_column ;
CREATE TABLE `sys_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_type` int(2) DEFAULT NULL COMMENT '列的类型：1=展示用的列  2=导入数据用的列',
  
  `definition_id` bigint(20) NOT NULL COMMENT '当前列所属的表ID(定义的表或SQL结果集)',
  `definition_name` varchar(40) NOT NULL COMMENT '当前列所属的名(定义表或SQL结果集)',
  `db_name` varchar(40) NULL COMMENT 'Db数据库名（冗余字段）',
  
  `code` varchar(50) NOT NULL COMMENT '列字段sql编码',
  `name` varchar(100) NOT NULL COMMENT '列名中文',
  `comment` varchar(200) DEFAULT NULL COMMENT '列注释',
  `db_type` varchar(50) NOT NULL COMMENT 'DB字段类型',
  `java_type` int(4) DEFAULT NULL COMMENT 'JAVA类型',
  `property_name` varchar(100) NOT NULL COMMENT 'JAVA属性名',
  `primary_key` int(4) DEFAULT NULL COMMENT '是否是主键：1=是，0=否',
  `oro_column_type` int(4) DEFAULT NULL COMMENT 'ORMapping映射的字段类型：gid(全局唯一码)=1 updateTime(更新时间)=2 createTime(创建时间)=3',
  `search_type` int(2) NOT NULL DEFAULT '0' COMMENT '当前列是否作为查询条件: 0=否，1=是',
  `search_required` int(2) DEFAULT NULL COMMENT '是否查询必填：search_required',
  `column_codegen_type` int(2) DEFAULT NULL COMMENT '字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键    4=文本框 5=整型框  6=精度型框 7=日期 8=文件  9=下拉框(常量JSON)',
  `column_codegen_format` varchar(50) DEFAULT NULL COMMENT '默认：double型的为两个小数点， date 为 [yyyy-MM-dd HH:mm:ss] ',
  `column_codegen_group_code` varchar(20) DEFAULT NULL COMMENT '字段组:用于生成html的fieldset框',
  `selector_multil_select` varchar(4) DEFAULT NULL COMMENT '选择器，是单选还是多选',
  `selector_text_cols` varchar(100) DEFAULT NULL COMMENT '选择器选择后显示的文本字段(多选以逗号分割)',
  `selector_value_cols` varchar(100) DEFAULT NULL COMMENT '选择器选择后显示的值字段(多选以逗号分割)',
  `selector_data_from_type` varchar(4) DEFAULT NULL COMMENT '选择器数据来源:0=URL(页面)  1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL',
  `selector_data_from` text COMMENT '选择器数据来源',
  `selector_data_source_code` varchar(50) DEFAULT NULL COMMENT '选择器的数据来源为SQL时，选定的数据源',
  `selector_db_name` varchar(50) DEFAULT NULL COMMENT '选择器的数据来源为SQL时，选定的数据库',
  `dict_ref_code` varchar(100) DEFAULT NULL COMMENT '当前列是字典值时，对应的字典code值，如(值为“sex”就是男、女,对应的值就是1和0)',
  `dict_text_col` varchar(100) DEFAULT NULL COMMENT '显示的文本字段',
  `dict_value_col` varchar(100) DEFAULT NULL COMMENT '显示的值字段',
  `file_multil` varchar(4) DEFAULT NULL COMMENT '是否可多选批量上传',
  `file_custom_content` text COMMENT '自定义的文件上传控件(代码片断)',
  `align_type` varchar(2) DEFAULT NULL COMMENT '列对齐方式: ',
  `width` int(2) DEFAULT NULL COMMENT '列宽: ',
  `height` int(2) DEFAULT NULL COMMENT '列高 ',
  `hidde` int(2) DEFAULT NULL COMMENT '是否隐藏: 0=隐藏 1=显示',


  `like_search_is` int(2) DEFAULT NULL COMMENT '是否模糊查询',
  `like_search_type` int(2) DEFAULT NULL COMMENT '是否是模糊查询: 1=匹配开头 ，2=匹配中间 3=匹配结尾',
  `frozen` int(2) DEFAULT NULL,
  `allow_sort` int(2) DEFAULT NULL,
  `db_type_length` varchar(10) DEFAULT NULL COMMENT 'DB字段长度,如decimal保留两位小数，填 10,2',
  `import_required` int(2) DEFAULT NULL,
  `coordinate` varchar(20) DEFAULT NULL,
  `allow_export` int(2) DEFAULT '1' COMMENT '是否允许导出(到excel):0=不允许 1=允许',
  `allow_import` int(2) DEFAULT '0' COMMENT '是否允许导入(到excel):0=不允许 1=允许',

  `sn` int(4) DEFAULT '0',
  `version` varchar(100) DEFAULT NULL COMMENT '表字段的版本号',
  `opt_log` varchar(100) DEFAULT NULL COMMENT '用户操作备注',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据表(或视图、SQL结果集) 列名定义';





-- 代码模板
drop table  IF EXISTS  sys_code_template ;
CREATE TABLE `sys_code_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '模板名',
  `code` varchar(255) NOT NULL COMMENT '模板编码',
  `project_code` varchar(255) NOT NULL COMMENT '所属的工程模板编码',
  
  `content` text NOT NULL COMMENT '模板内容',
  `pkg` varchar(500) DEFAULT NULL COMMENT '包名',
  `clazz` varchar(255) DEFAULT NULL COMMENT '类名',
  
  `tmpl_type` int(4) NOT NULL COMMENT '模板类型: {ORO 0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml } 、{Model 15=Model.java}、 {Controller 5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java } 、 {Dao 3=Dao_Mybatis3.java 11= Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java}、{Service   4=Service_Example.java  14=Service_Spring.java} 、{Page        7=jsp 8=html 9=*.vm 10=*.ftl}',
  `tmpl_resolve_type` int(4) NOT NULL COMMENT '模板解析方式 : 0=freemark 1=velocity',
  
  
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='代码模板管理';


-- 代码内容
drop table  IF EXISTS  sys_code_content ;
CREATE TABLE `sys_code_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NULL COMMENT '模板ID',
  `table_id` bigint(20) NULL COMMENT '表ID' ,
  
  `content` text NOT NULL COMMENT '代码内容',
  `log` text NOT NULL COMMENT '代码生成的日志',

  `opt_user_id` bigint(20) NULL ,
  
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,


  `gid` varchar(40) DEFAULT NULL COMMENT 'gid: 全局唯一码',
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime DEFAULT NULL COMMENT 'updateTime:更新日期',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='代码生成器生成的代码管理';






drop table  IF EXISTS  sys_variable ;
CREATE TABLE `sys_variable` (
  `id` bigint(20) NOT NULL  AUTO_INCREMENT,
  `obj_id` bigint(20) NULL ,
  `name` varchar(256) NOT NULL COMMENT '变量名',
  `code` varchar(200) NOT NULL COMMENT '变量编码',
  `use_type` varchar(10) NOT NULL COMMENT '变量使用类型 1=流程变量 2=代码生成器变量 3=内置常用变量',
  
  `value` text NULL COMMENT '变量值',
  `value_type` varchar(10)  NULL COMMENT '值类型 1=固定值 2=运行时 3=内置值',
  `value_resolve_type` varchar(10) NOT NULL COMMENT '变量值解析类型:1=freemark 2=velocity 3=groovy 4=javascript',
 
  
  `sn` int(4) DEFAULT '0' ,
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='变量管理';

insert into sys_variable(name,code,use_type,value_resolve_type,remark,app_code,gid,create_time,update_time) 
values ('实体类-(首字母大写)','Model','3','1','实体类-(首字母大写)',1000,'Model',now(),now());

insert into sys_variable(name,code,use_type,value_resolve_type,remark,app_code,gid,create_time,update_time) 
values ('实体类-(首字母小写)','model','3','1','实体类-(首字母小写)',1000,'model',now(),now());

insert into sys_variable(name,code,use_type,value_resolve_type,remark,app_code,gid,create_time,update_time) 
values ('表名','tableName','3','1','表名',1000,'tableName',now(),now());

insert into sys_variable(name,code,use_type,value_resolve_type,remark,app_code,gid,create_time,update_time) 
values ('表注释','tableComment','3','1','表注释',1000,'tableComment',now(),now());

insert into sys_variable(name,code,use_type,value_resolve_type,remark,app_code,gid,create_time,update_time) 
values ('表列名','tableColumnList','3','1','表的列名',1000,'tableColumnList',now(),now());





-- 缓存分类
drop table  IF EXISTS  sys_cache_category ;
CREATE TABLE `sys_cache_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `name` varchar(255) NOT NULL COMMENT '名称',
  
  `category_code` varchar(255) DEFAULT NULL COMMENT '系统分类码',
  `value` varchar(255) DEFAULT NULL COMMENT '分类值',
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据（业务）类型',
  
  `node_path` varchar(1000) DEFAULT NULL,
  `app_code` varchar(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='缓存分类表';

-- 系统缓存管理表
drop table  IF EXISTS  sys_cache_manage ;
CREATE TABLE `sys_cache_manage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT primary key,
  
  `code` varchar(100) NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(2000)  NULL COMMENT '缓存刷新地址',
  `data_type` varchar(4) NULL COMMENT '自定义的数据类型',
  
  `category_id` bigint(20) NULL,
  
  `app_code` varchar(255) NOT NULL COMMENT '应用编码',  
  `sn` int(10) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号',
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间'
 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统缓存管理表';

-- 缓存刷新参数
drop table  IF EXISTS  sys_cache_url_param ;
CREATE TABLE `sys_cache_url_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cache_id` bigint(20) NOT NULL COMMENT '系统缓存管理表ID',
  `param_name` varchar(255) NOT NULL COMMENT '参数名称',
  `param_value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `param_code` varchar(255) NOT NULL COMMENT '参数编码',
  
  `param_type` varchar(5) NULL COMMENT '参数类型: 100=登陆用户的租户ID 104=登陆用户的主组织ID 105=登陆用户的用户ID 102=登陆用户的主岗位ID  150=登陆用户的单元ID ; 200=静态值 ',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='缓存刷新参数配置';







-- ---------------------------------------------- 统一用户管理 ----------------------------------------------------------

drop table  IF EXISTS  uum_user ;
CREATE TABLE `uum_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '姓名',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `login_name` varchar(50) NOT NULL COMMENT '帐号',
  `login_pwd` varchar(50) NOT NULL COMMENT '密码',
  `status` int(4) DEFAULT NULL COMMENT '状态\n 3=过期(长久没有登陆，僵尸用户) 2=锁定（可登陆不能操作） 1=激活（可登陆、可操作） 0=禁用（不可登陆） -1=已删除',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `num_qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `num_wx` varchar(20) DEFAULT NULL COMMENT '微信',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  
  `address_office` varchar(500) DEFAULT NULL COMMENT '办公地点（多个地址用@@分割）',
  `address_home` varchar(500) DEFAULT NULL COMMENT '家庭地址（多个地址用@@分割）',
    
  `sex` int(1) DEFAULT NULL COMMENT '1=男\n  0=女',
  `sn` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,  
  `app_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  

  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name_unique` (`login_name`),
  UNIQUE KEY `email_unique` (`email`),
  UNIQUE KEY `mobile_unique` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


drop table  IF EXISTS  uum_org ;
CREATE TABLE `uum_org` (
  `id` bigint(20) NOT NULL COMMENT '组织ID' AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '上级',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `name_short` varchar(100) NOT NULL COMMENT '部门简称',
  `code` varchar(40) DEFAULT NULL COMMENT '组织机构编码',
  `status` int(4) DEFAULT NULL COMMENT '状态\n  1=启用 0=禁用 -1=已删除',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(2000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(40) DEFAULT NULL,


  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构表';


drop table  IF EXISTS  uum_res ;
CREATE TABLE `uum_res` (
  `id` bigint(20) NOT NULL  AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '上级',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(80) DEFAULT NULL COMMENT '编码',
  `value` text DEFAULT NULL COMMENT '资源值',
  `type` int(4) DEFAULT 1 COMMENT '资源类型：100=菜单 200=页面元素 300=数据查询条件 400=页面 401=链接',
  `url` varchar(2000) DEFAULT NULL COMMENT '资源url地址',
  `icon` varchar(80) DEFAULT NULL COMMENT '资源图标',
  `sn` int(10) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(40) DEFAULT NULL,
  `status` int(4) DEFAULT NULL COMMENT '状态\n  1=启用 0=禁用 -1=已删除',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

 


drop table  IF EXISTS  uum_role ;
CREATE TABLE `uum_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `name_short` varchar(100) NOT NULL COMMENT '角色简称',
  `code` varchar(40) DEFAULT NULL COMMENT '角色编码',
  `status` int(4) DEFAULT NULL COMMENT '状态\n  1=启用 0=禁用 -1=已删除',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(40) DEFAULT NULL,


  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


drop table  IF EXISTS  uum_group ;
CREATE TABLE `uum_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `name_short` varchar(100) NOT NULL COMMENT '组简称',
  `code` varchar(40) DEFAULT NULL COMMENT '组编码',
  `status` int(4) DEFAULT NULL COMMENT '状态\n  1=启用 0=禁用 -1=已删除',
  `node_path` text DEFAULT NULL,
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(40) DEFAULT NULL,


  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组表';



drop table  IF EXISTS  uum_title ;
CREATE TABLE `uum_title` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL ,
  `name` varchar(200) NOT NULL COMMENT '名称',
  `node_path` text DEFAULT NULL,
  
  `name_short` varchar(100) NOT NULL COMMENT '称谓简称',
  `code` varchar(40) DEFAULT NULL COMMENT '称谓编码',
  `status` int(4) DEFAULT NULL COMMENT '状态\n  1=启用 0=禁用 -1=已删除',
  `type` varchar(4) DEFAULT NULL COMMENT '称谓类型: 1=职称 2=岗位',
   
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(40) DEFAULT NULL,


  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户称谓表，如岗位、职位等';


-- -----------------------------------------------  权限管理 -----------------------------------------------
-- 授权遵循的一个原则: 
-- 1.资源授权只授给角色；
-- 2.给部门、组、岗位/职称的授权,都只是授与一些角色; 
-- 3.用户最终的权限 = 用户所在的角色 +（组、部门、岗位/职称)所拥有的角色
-- -----------------------------------------------  权限管理 -----------------------------------------------
drop table  IF EXISTS  uum_object_role;
CREATE TABLE `uum_object_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL ,
  `obj_id`  bigint(20) NOT NULL ,
  `obj_type` varchar(4) DEFAULT NULL COMMENT '对象类型: 1=职称 2=岗位 3=部门 4=组 5=用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对象(指的是部门、组、岗位/职称、用户[注意用户的角色保存在这张表!])拥有的角色，多对多表(中间表)';


drop table  IF EXISTS  uum_user_object;
CREATE TABLE `uum_user_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL ,
  `obj_type` varchar(4) NOT NULL COMMENT '对象类型: 1=职称 2=岗位 3=部门 4=组 7=角色',
  `obj_id`  bigint(20) NOT NULL ,
  `obj_pid` bigint(20) default NULL ,
  `obj_node_path` text default NULL ,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户所在(部门、组、岗位/职称)[注意没有角色!该表只保存用户的"职能",而角色不是"职能体"]，多对多表(中间表)';


drop table  IF EXISTS  uum_role_res;
CREATE TABLE `uum_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL ,
  `res_id`  bigint(20) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色下拥有的资源,多对多的关系表(中间表)';



-- ------------------------------------------------------ 商城管理 -----------------------------------------
-- 商城管理:订单表
drop table  IF EXISTS  shop_order ;
create table shop_order (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单编号-对外显示',
  `code` varchar(50) DEFAULT NULL COMMENT '订单编码',
  `total` DECIMAL(18,2) NOT NULL COMMENT '总价',
  `user_id` VARCHAR(50) NOT NULL COMMENT '下单用户ID',
  `user_name` VARCHAR(50) NOT NULL COMMENT '下单用户名',
  `status` bigint(20) NOT NULL COMMENT '订单的状态: 0=下单成功 1=支付成功 2=支付失败 ',
    

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- 订单详情表
drop table  IF EXISTS  shop_order_item ;
create table shop_order_item (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '订单货物名称',
  `price_transaction` DECIMAL(18,2) NOT NULL COMMENT '交易单价',
  `num` int(20) NOT NULL COMMENT '数量',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';














-- --------------------------------ETL 数据抽取相关: 可人一个数据源抽到多个数据源 ------------------------------------------------------------------
drop table  IF EXISTS  etl_config ;
create table etl_config (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '数据抽取转换名称',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据抽取转换名表';






-- --------------------------------- 工作流程相关 -----------------------------------------------------------
drop table  IF EXISTS  ext_run_instance ;
CREATE TABLE `ext_run_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `instance_id` varchar(255) NOT NULL COMMENT '流程实例ID',
  `title` varchar(500) NOT NULL COMMENT '流程标题',
  
  `process_definition_id` varchar(100) NOT NULL COMMENT '流程定义ID',
  `process_definition_key` varchar(255) NOT NULL COMMENT '流程定义key',
  `process_definition_name` varchar(255) NOT NULL COMMENT '流程定义名称',
  
  `start_user_id` varchar(20) NOT NULL COMMENT '流程发起人ID',
  `start_user_name` varchar(50) DEFAULT NULL COMMENT '流程发起人名称',
  
  `business_key` varchar(20) NOT NULL COMMENT '业务数据主键ID',
  `business_status` varchar(4) DEFAULT NULL COMMENT '业务自定义的状态,如自定义审批通过、未通过、正常、异常等',
  `business_status_desc` varchar(20) DEFAULT NULL COMMENT '业务状态中文表示',
  `business_category` varchar(20) DEFAULT NULL COMMENT '业务自定义的分类',


  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程实例扩展（用于丰富我的待办查询）';


drop table  IF EXISTS  ext_user_rule ;
CREATE TABLE `ext_user_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL comment '工作流分类表ID',
  `name` varchar(255) NOT NULL COMMENT '规则名称',
  `content` text COMMENT '规则内容',
  `resolve_type` tinyint(4) NOT NULL COMMENT '规则解析类型: 1=freemark 2=velocity 3=javascript 4=groovy',
  
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户规则定义';


drop table  IF EXISTS  ext_category ;
CREATE TABLE `ext_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `category_code` varchar(255) DEFAULT NULL COMMENT '系统分类代码',
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',

  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cat_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='工作流分类';



drop table  IF EXISTS  ext_node ;
CREATE TABLE `ext_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(255) NOT NULL COMMENT '流程定义ID',
  `task_key` varchar(255) NOT NULL COMMENT '节点定义Key',
  `task_name` varchar(255) NOT NULL COMMENT '节点名称',
  `task_def_type` tinyint(4) NOT NULL COMMENT '节点定义类型: 1=usertask ',
  `task_biz_type` tinyint(4) DEFAULT NULL COMMENT ' 节点类型: 0=拟稿节点(退回到拟稿人时有用) 1=非拟稿节点',
  `node_variable_copy` tinyint(4) NOT NULL DEFAULT '0' COMMENT '节点变量copy: 是否从全局变量里copy流程变量到当前节点 0=不copy  1=从全局变量copy  2=从第三方接口返回值设置',
  `node_jump_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' 1=正常跳转 2=选择路径跳转 3=自由跳转 (这个字段待删除!!)',
  
  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='节点信息扩展';


-- 流程节点用户设置表
drop table  IF EXISTS  ext_node_user ;
CREATE TABLE `ext_node_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(255) NOT NULL COMMENT '流程定义ID',
  `definition_name` varchar(255) NOT NULL COMMENT '流程定义名称',
  `task_key` varchar(255) NOT NULL COMMENT '节点定义Key',
  `approve_obj_variable_code` varchar(100) DEFAULT NULL COMMENT '审批对象变量编码',
  `approve_obj_id` varchar(100) NOT NULL COMMENT '审批对象ID',
  `approve_obj_json` text COMMENT '审批对象值',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `user_type` tinyint(4) NOT NULL COMMENT '结点用户类型:  1=职称 2=岗位 3=组织 5=用户 7=角色 4=用户组(与流程引擎的group不是一个概念，主要是第三方系统的UUM)； 角本计算：10=java角本计算 11=groovy角本计算 12=javascript角本计算(注：角本计算必须返回Collection<String>集合',
  `user_from_type` tinyint(4) NOT NULL COMMENT '用户数据来源：1=内部 2=外部（如http-json数据）',
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='节点审批人设置';


-- 流程节点表单按钮设置表
drop table  IF EXISTS  ext_node_button ;
CREATE TABLE `ext_node_button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(255) NOT NULL COMMENT '流程定义ID',
  `task_key` varchar(255) NOT NULL COMMENT '节点定义Key',
  `data_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据类型: 1=节点按钮 2=全局按钮 3=自定义的按钮',
  `btn_name` varchar(255) NOT NULL COMMENT '按钮名称',
  `btn_code` varchar(255) NOT NULL COMMENT '按钮编码',
  `btn_type` tinyint(4) NOT NULL COMMENT '按钮类型:  1=同意(agree) 2=驳回(reject) 3=驳回到发起人(reject_to_starter) 4=驳回到选择节点(reject_to_choose_node) 5=转交代办(proxy_do)  6=沟通(communicate) 7=保存表单(save_form) 8=保存草稿(save_draft)  9=作废 (abort) 10=授理(accept)  11=撤回(reback)',
  `before_script` text COMMENT '前置角本',
  `before_script_type` varchar(4) DEFAULT NULL COMMENT '1=url 2=javascript_code 3=javacode ',
  
  `after_script` text COMMENT '后置角本',
  `after_script_type` varchar(4) DEFAULT '1' COMMENT '1=url 2=javascript_code 3=javacode ',
  
  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='节点按钮设置';



drop table  IF EXISTS  ext_node_form ;
CREATE TABLE `ext_node_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(255) NOT NULL COMMENT '流程定义ID',
  `task_name` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `task_key` varchar(255) DEFAULT NULL COMMENT '节点定义Key',
  `form_name` varchar(255) DEFAULT NULL COMMENT '表单名称',
  `form_code` varchar(255) DEFAULT NULL COMMENT '表单编码',
  `data_type` tinyint(4) NOT NULL COMMENT '数据类型: 1=节点表单 2= 全局表单',
  `form_type` tinyint(4) NOT NULL COMMENT '表单类型: 0=动态表单 1=URL表单,注：在线表单,为系统自定义表单;url表单,是外部表单。地址写法规则为：如果表单页面平台在同一个应用中，路径从根开始写，不需要上下文路径，例如 ：/form/addUser.do。 如果需要使用的表单不再同一个应用下，则需要写完整路径如:http://xxxxx/crm/addUser.do  ',
  `form_url` text COMMENT '当form_type=1时：表单URL值',
  `form_detail_url` text COMMENT '当form_type=1时: 明细URL值',
  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='节点表单设置';


-- 流程审批意见
drop table  IF EXISTS  ext_approve_opinion ;
CREATE TABLE `ext_approve_opinion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_key` varchar(255) DEFAULT NULL COMMENT '表单数据id',
  `business_type` varchar(100) DEFAULT NULL,
  `process_instance_id` varchar(50) DEFAULT NULL COMMENT '流程实例id',
  `definition_id` varchar(255) DEFAULT NULL COMMENT '流程定义ID',
  `definition_name` varchar(255) DEFAULT NULL COMMENT '流程定义名称',
  `definition_key` varchar(200) DEFAULT NULL,
  `approve_task_id` varchar(255) DEFAULT NULL COMMENT '记录审批时的节点ID',
  `approve_task_key` varchar(255) DEFAULT NULL COMMENT '记录审批时的节点Key',
  `approve_action` varchar(45) DEFAULT NULL COMMENT '审批操作 code： 同意、拒绝等',
  `approve_result` varchar(100) DEFAULT NULL COMMENT '审批操作结果： 同意、拒绝等',
  `approve_opinion` varchar(100) DEFAULT NULL COMMENT '记录 意见内容',
  `approve_user_id` varchar(255) DEFAULT NULL COMMENT '审批用户Id',
  `approve_user_name` varchar(255) DEFAULT NULL COMMENT '审批用户名',
  `last_approve_task_key` varchar(255) DEFAULT NULL COMMENT '记录上一步节点key,流程驳回时使用',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='审批意见';


-- 流程审批意见历史(流程跑完了，意见信息归档到该表)
drop table  IF EXISTS  ext_approve_opinion_his ;
CREATE TABLE `ext_approve_opinion_his` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  
--  `opinion_id` bigint(20) NOT NULL COMMENT '原表记录',
  `business_key` varchar(255) NULL COMMENT '表单数据id',
    
  `definition_id` varchar(255)  NULL COMMENT '流程定义ID',
  `definition_name` varchar(255)  NULL COMMENT '流程定义名称',
  
  `approve_task_id` varchar(255)  NULL COMMENT '记录审批时的节点ID',
  `approve_task_key` varchar(255)  NULL COMMENT '记录审批时的节点Key',
  
  `approve_action` varchar(45)  NULL COMMENT '审批操作 code： 同意、拒绝等',
  `approve_result` varchar(100)  NULL COMMENT '审批操作结果： 同意、拒绝等',
  `approve_opinion` varchar(100)  NULL COMMENT '记录 意见内容',


  `approve_user_id` varchar(255) comment '审批用户Id',
  `approve_user_name` varchar(255)  NULL COMMENT '审批用户名',

  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='审批意见历史';



 drop table  IF EXISTS  ext_approve_opinion_file ;
 CREATE TABLE `ext_approve_opinion_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_key` varchar(50) DEFAULT NULL COMMENT '业务主键（如付款审批ID)',
  `opinion_id` varchar(50) DEFAULT NULL COMMENT '审批意见id(ext_approve_opinion.id)',
  
  `approve_process_instance_id` varchar(45) DEFAULT NULL,
  `approve_task_id` varchar(50) DEFAULT NULL,
  `approve_task_key` varchar(45) DEFAULT NULL,
  
  `stored_name` varchar(200) NOT NULL COMMENT '存储文件名',
  `original_name` varchar(255) NOT NULL COMMENT '原始文件名',
  `type_code` varchar(50) DEFAULT NULL COMMENT '文件类型编码，引用字典值或自定义',
  
  
  `path` varchar(2000) DEFAULT NULL COMMENT '(FastDFS文件)路径',
  `path_large` varchar(1000) DEFAULT NULL COMMENT '文件缩略图（大）',
  `path_medium` varchar(1000) DEFAULT NULL COMMENT '文件缩略图（中）',
  `path_small` varchar(1000) DEFAULT NULL COMMENT '文件缩略图（小）',
  
  `upload_user_id` varchar(50) DEFAULT NULL COMMENT '上传的用户ID',
  `upload_user_name` varchar(50) DEFAULT NULL COMMENT '上传的用户名称',
  
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(50) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='流程意见附件表';



-- 流程分类
drop table  IF EXISTS  ext_category ;
CREATE TABLE `ext_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据类型',
  `enable` varchar(2) DEFAULT NULL COMMENT '是否启用: 1=启用  0=禁用',
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程分类表';


-- ---------------------------- 流程用户数据映射 配置开始 -----------------------------------------

drop table  IF EXISTS  ext_user_data_config ;
CREATE TABLE `ext_user_data_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) DEFAULT NULL COMMENT '配置键',
  `config_name` varchar(255) NOT NULL COMMENT '配置名称',
  `config_code` varchar(255) DEFAULT NULL COMMENT '配置编码',
  `app_code` varchar(255) DEFAULT NULL COMMENT '应用编码',
  `config_type` varchar(4) NOT NULL COMMENT '映射类型: 1=外部系统-http-json 2=本地系统',
  `entity_type` varchar(4) NOT NULL COMMENT '映射的实体类型: 1=职称 2=岗位 3=部门 4=组 5=用户 7=角色 ',
  `url` varchar(255) NOT NULL COMMENT '接口地址(不带参数的地址)',
  `data_prop` varchar(500) DEFAULT NULL COMMENT 'JSON数据的某个数据字段',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='数据映射配置';

INSERT INTO `ext_user_data_config` (`id`,`config_name`,`config_code`,`app_code`,`config_type`,`entity_type`,`url`,`sn`,`remark`,`gid`,`create_time`,`update_time`,`data_prop`) VALUES (1,'外部系统部门映射',NULL,'1427598448086793','1','3','http://t200.api.dct.qitoon.com/api/organization/getAllOrganUnits',1,'1','1','2017-03-22 09:11:36','2017-03-22 09:11:36',NULL);
INSERT INTO `ext_user_data_config` (`id`,`config_name`,`config_code`,`app_code`,`config_type`,`entity_type`,`url`,`sn`,`remark`,`gid`,`create_time`,`update_time`,`data_prop`) VALUES (3,'外部系统用户映射',NULL,'1427598448086793','1','5','http://t200.admin.qitoon.com/account/queryAccounts',2,'2','2','2017-03-22 09:11:36','2017-03-22 09:11:36','data.list');
INSERT INTO `ext_user_data_config` (`id`,`config_name`,`config_code`,`app_code`,`config_type`,`entity_type`,`url`,`sn`,`remark`,`gid`,`create_time`,`update_time`,`data_prop`) VALUES (4,'获取部门下的用户','getUserListByOrg',NULL,'1','5','http://t200.admin.qitoon.com/account/queryAccounts',4,'4','4','2017-03-22 09:11:36','2017-03-22 09:11:36','data.list');


drop table  IF EXISTS  ext_user_data_config_param ;
CREATE TABLE `ext_user_data_config_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` varchar(255) NOT NULL COMMENT '数据配置ID(引用ext_user_data_config.id)',
  `param_name` varchar(255) NOT NULL COMMENT '参数名称',
  `param_code` varchar(255) NOT NULL COMMENT '参数编码',
  `param_type` varchar(5) NOT NULL COMMENT '参数类型: 100=登陆用户的租户ID 104=登陆用户的组织ID 105=登陆用户的用户ID 102=登陆用户的岗位ID  150=登陆用户的单元ID ; 200=静态值 ;  300=从审批对象里取值',
  `param_value` varchar(255) NULL COMMENT '参数值',
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据映射配置';

INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (1,'1','登陆用户组织架构ID','orgId','200','1427598448086793',1,'1','1','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (4,'3','获取(香颂地产)所有员工','orgId','200','1427598448086793',4,'4','4','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (5,'3','获取(香颂地产)所有员工','orgUnitId','200','5555',5,'5','5','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (6,'3','获取(香颂地产)所有员工','curPage','200','0',6,'6','6','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (7,'3','获取(香颂地产)所有员工','pageSize','200','100000',7,'7','7','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (9,'4','获取部门下的所有用户(多层)','orgId','200','1427598448086793',8,'8','8','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (10,'4','获取部门下的所有用户(多层)','orgUnitId','201',NULL,9,'9','9','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (14,'4','获取部门下的所有用户(多层)','curPage','200','0',10,'10','10','2017-03-22 09:11:36','2017-03-22 09:11:36');
INSERT INTO `ext_user_data_config_param` (`id`,`config_id`,`param_name`,`param_code`,`param_type`,`param_value`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (15,'4','获取部门下的所有用户(多层)','pageSize','200','100000',11,'11','11','2017-03-22 09:11:36','2017-03-22 09:11:36');


drop table  IF EXISTS  ext_user_data_mapping ;
CREATE TABLE `ext_user_data_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` varchar(255) NOT NULL COMMENT '数据配置ID(引用ext_user_data_config.id)',
  `local_field` varchar(255) NOT NULL COMMENT '本地字段',
  `remote_field` varchar(255) NOT NULL COMMENT '远程字段',
  
  
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据映射配置';

INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (1,'1','id','organUnitId',0,'组织机构id','1','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (2,'1','pid','parentId',0,'父id','2','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (3,'1','name','organUnitName',0,'组织名称','3','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (4,'2','id','positionId',0,'岗位ID','4','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (5,'2','name','positionName',0,'岗位名称','5','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (6,'2','code','externalCode',0,'岗位编码','6','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (7,'3','id','empId',0,'用户ID','7','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (8,'3','name','empName',0,'员工姓名','8','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (9,'3','loginName','loginAccount',0,'员工账号','9','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (10,'3','remark','department',0,'备注','10','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (11,'4','id','empId',0,'用户ID','11','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (12,'4','name','empName',0,'员工姓名','12','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (13,'4','loginName','loginAccount',0,'员工账号','13','2017-03-20 23:49:58','2017-03-20 23:49:58');
INSERT INTO `ext_user_data_mapping` (`id`,`config_id`,`local_field`,`remote_field`,`sn`,`remark`,`gid`,`create_time`,`update_time`) VALUES (14,'4','remark','department',0,'备注','14','2017-03-20 23:49:58','2017-03-20 23:49:58');


-- ---------------------------- 流程用户数据映射 配置 结束 -----------------------------------------




-- 流程变量表
drop table  IF EXISTS  ext_variable ;
CREATE TABLE `ext_variable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(64) DEFAULT NULL COMMENT '流程定义',
  `name` varchar(256) NOT NULL COMMENT '变量名',
  `code` varchar(200) NOT NULL COMMENT '变量编码',
  `type` int(2) NOT NULL COMMENT '是否是常用类,1=常用固定类(如流程发起人等） 0=自定义类',
  `data_type` int(4) NOT NULL COMMENT '变量使用类型 1=字符串 2=数字 3=日期',
  `format` varchar(100) NULL COMMENT '变量使用类型 1=字符串 2=数字 3=日期',
  `requried` int(2) NOT NULL COMMENT '是否必填',
  `default_value` text COMMENT '默认值',
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程变量管理';



-- 流程条件管理
drop table  IF EXISTS  ext_condition ;
CREATE TABLE `ext_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` varchar(64) DEFAULT NULL COMMENT '流程定义',
  `name` varchar(256) NOT NULL COMMENT '条件名',
  `code` varchar(200) NOT NULL COMMENT '条件编码-也就是流程定义里的条件id',
  `express` varchar(1000) DEFAULT NULL COMMENT '条件表达式',
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程条件管理';
























-- ---------------------------------------------约单系统相关表 ----------------------------------------------------------------
drop table  IF EXISTS  yue_tech ;
CREATE TABLE `yue_tech` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL ,  
  `code` varchar(50) DEFAULT NULL COMMENT '编码',  
  `name` varchar(100) DEFAULT NULL COMMENT '技能名称',
  
  `type` varchar(50) NOT NULL COMMENT '技能定义数据类型：0=技能分类 1=具体技能',
  `node_path` varchar(50) NULL COMMENT '节点密码',
  `enable` int(4) DEFAULT NULL COMMENT '1=启用 0=禁用',
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能定义,如家教';

drop table  IF EXISTS  yue_tech_info ;
CREATE TABLE `yue_tech_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tech_id` bigint(20) NOT NULL COMMENT '引用技能表ID' ,
  `name` varchar(256) DEFAULT NULL COMMENT '如：培训课程',
  `code` varchar(50) NOT NULL ,
  `sn` int(4) DEFAULT '0',
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能信息,如家教里的培训课程、服务方式等';

drop table  IF EXISTS  yue_tech_info_item ;
CREATE TABLE `yue_tech_info_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_id` bigint(20) NULL ,  
  `name` varchar(256) DEFAULT NULL COMMENT '技能细项名，如家教技能里的"语文" ',
  `code` varchar(50) NOT NULL  ,
  `remark` varchar(256) DEFAULT NULL,
  `sn` int(4) DEFAULT '0',
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能信息项';






-- ------------------------------------------------报表系统 -------------------------------------------------
DROP TABLE IF EXISTS `rpt_category`;
CREATE TABLE `rpt_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `category_code` varchar(255) DEFAULT NULL COMMENT '备注',
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `node_path` varchar(1000) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据（业务）类型',
  `app_code` varchar(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表分类';


DROP TABLE IF EXISTS `rpt_definition`;
CREATE TABLE `rpt_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL COMMENT '报表分类ID',
  `category_name` varchar(40) NOT NULL COMMENT '报表分类名',
  
  `datasource_id` bigint(20) NOT NULL COMMENT '报表所属的数据源',
  `datasource_name` varchar(40) NOT NULL COMMENT '数据源名称',
  
  `name` varchar(255) NOT NULL COMMENT '定义全称',
  `short_name` varchar(255) DEFAULT NULL COMMENT '定义简称',
  
  `code` varchar(255) NOT NULL COMMENT '定义编码',
  `type` varchar(4) NOT NULL COMMENT '报表内容类型： 1=SQL 2=Table 3=View 4=http_json 5=存储过程',
  `url` varchar(500) DEFAULT NULL COMMENT '报表url',
  `column_sql` varchar(2000) DEFAULT NULL COMMENT '数据SQL用于导入字段用，可以直接执行',
  `report_sql` varchar(2000) DEFAULT NULL COMMENT '报表SQL，带参数的真实报表SQL',
  `prevent_sql_injection` varchar(2) DEFAULT NULL COMMENT '是否启用防SQL注入',
  `db_type` varchar(2) NOT NULL COMMENT '报表数据库类型： 1=MySQL 2=oracle 3=sqlserver 4=PostgreSQL',
  
  `layout` int(2) DEFAULT NULL comment '报表页面布局:1=左右布局 2=上下布局 3=左上下（用于子报表，左是高级查询区） 4=上中下（用于子报表，上是高级查询区） 5=左中右（用于子报表，左是高级查询区）',
  `search_area_control_num_per_row` int(2) DEFAULT NULL COMMENT '高级查询区每行显示几个查询控件',
  `show_pager` int(2) DEFAULT NULL,
  
  `page_size` int(4) DEFAULT NULL,
  `sort_mode` int(2) DEFAULT NULL COMMENT '表格排序模式: 1=客户端 2=服务器端',
  `page_size_list` varchar(40) DEFAULT NULL,
  `search_area_width` int(2) DEFAULT NULL COMMENT '查询区域宽度',
  
  `can_export` int(2) DEFAULT NULL,
  `export_mode` int(2) DEFAULT NULL COMMENT '数据导出的模式, 1=默认全部字段数据导出 2=用户选择字段导出',
  
  `can_import` int(2) DEFAULT NULL COMMENT '是否可以导入数据',
  `import_mode` int(2) DEFAULT NULL COMMENT '数据导入的模式, 1=默认全部字段数据导入 2=用户选择字段导入',
  
  `import_datasource_id` bigint(20) DEFAULT NULL COMMENT '导入数据的数据源Id',
  `import_datasource_name` varchar(50) DEFAULT NULL,
  `import_table` varchar(80) DEFAULT NULL COMMENT '志入的目标表',
  
  
  `store_replica_data` int(2) DEFAULT NULL COMMENT '是否存储数据副本:1=是 0=否',
  `import_data_stroe_precision` int(2) DEFAULT NULL COMMENT '数据副本存储精度模式 1=全字段按字符存储  2=按导入定义字段类型存储',
  `data_replica_datasource_id` bigint(20) DEFAULT NULL COMMENT '数据副本用的数据源,导入数据时，本地会存量一分副本用于留证',
  `data_replica_datasource_name` varchar(50) DEFAULT NULL,
  
  `status` varchar(2) NOT NULL COMMENT '报表状态： 0=禁用   1=启用',
  `version` varchar(30) DEFAULT NULL COMMENT '报表版本号',
  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表定义';


 
DROP TABLE IF EXISTS `rpt_resource`;
CREATE TABLE `rpt_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL COMMENT '所属的报表',
  `name` varchar(40) NOT NULL COMMENT '元素中文名',
  `code` varchar(255) NOT NULL COMMENT '定义编码',
  `type` varchar(4) NOT NULL COMMENT '元素类型: 1=按钮 2=下拉框（单选）3=下拉框（多选）4=文本框 5=TextArae 6=File 7=日历 8=数字框 ',
  
  `event_name` varchar(200) DEFAULT NULL COMMENT '元素事件名称,如：onclick',
  `event_function` varchar(200) DEFAULT NULL COMMENT '事件响应函数名称,如：onclick=事件响应函数名称',
  `btn_before_script` varchar(2000) DEFAULT NULL COMMENT '按钮点击前事件触发的js函数体',
  `btn_after_script` varchar(2000) DEFAULT NULL COMMENT '按钮点击后事件触发js函数体',
  `btn_script` text DEFAULT NULL COMMENT '按钮点击事件触发js函数体',

  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
 
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表页面元素定义（如：头部按钮）';




DROP TABLE IF EXISTS `rpt_column`;
CREATE TABLE `rpt_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL COMMENT '当前列所属的报表',
  `data_type` int(2) NOT NULL COMMENT '1=报表展示列配 2=报表导入数据列',
  `code` varchar(50) NOT NULL COMMENT '列字段sql编码',
  `name` varchar(100) NOT NULL COMMENT '列名中文',
  `report_name` varchar(100) NOT NULL COMMENT '列名中文',
  
  `comment` varchar(200) DEFAULT NULL COMMENT '列注释',
  `db_type` varchar(50) NOT NULL COMMENT 'DB字段类型',
  `db_type_length` VARCHAR(10) NULL COMMENT 'DB字段长度,如decimal类型保留两位小数，填  20,2',
  `import_required` int(2) NULL COMMENT '是否导入必填1=必填  0=非必填',
  `coordinate` varchar(10) NULL COMMENT 'Excel表头单元格坐标',

  `java_type` int(4)  NULL COMMENT 'JAVA类型',
  `property_name` varchar(100) NOT NULL COMMENT 'JAVA属性名',
  `primary_key` int(4)  NULL COMMENT '是否是主键：1=是，0=否',
  `oro_column_type` int(4) DEFAULT NULL COMMENT 'ORMapping映射的字段类型：gid(全局唯一码)=1 updateTime(更新时间)=2 createTime(创建时间)=3',

  `search_type` int(2) NOT NULL DEFAULT '0' COMMENT '当前列是否作为查询条件: 0=否，1=是',
  `search_required` int(2) NOT NULL DEFAULT '0' COMMENT '是否查询必填: 0=否，1=是',
  
  `like_search_is` int(2)  NULL DEFAULT 0 COMMENT '是否是模糊查询: 0=否，1=是',
  `like_search_type` int(2)  NULL DEFAULT 2 COMMENT '是否是模糊查询: 1=匹配开头 ，2=匹配中间 3=匹配结尾 4=不做包装处理',

  `column_codegen_type` int(2)  NULL COMMENT '字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键    4=文本框 5=整型框  6=精度型框 7=日期 8=文件  9=下拉框(常量JSON)',
  `column_codegen_format` varchar(50) DEFAULT NULL COMMENT '默认：double型的为两个小数点， date 为 [yyyy-MM-dd HH:mm:ss] ',
  `column_codegen_group_code` varchar(20) DEFAULT NULL COMMENT '字段组:用于生成html的fieldset框',
  `selector_multil_select` varchar(4) DEFAULT NULL COMMENT '选择器，是单选还是多选',
  `selector_text_cols` varchar(100) DEFAULT NULL COMMENT '选择器选择后显示的文本字段(多选以逗号分割)',
  `selector_value_cols` varchar(100) DEFAULT NULL COMMENT '选择器选择后显示的值字段(多选以逗号分割)',
  `selector_data_from_type` varchar(4) DEFAULT NULL COMMENT '选择器数据来源:0=URL(页面)  1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL',
  `selector_data_from` text COMMENT '选择器数据来源',
  `selector_data_source_code` varchar(50) DEFAULT NULL COMMENT '选择器的数据来源为SQL时，选定的数据源',
  `selector_db_name` varchar(50) DEFAULT NULL COMMENT '选择器的数据来源为SQL时，选定的数据库',
  `dict_ref_code` varchar(100) DEFAULT NULL COMMENT '当前列是字典值时，对应的字典code值，如(值为“sex”就是男、女,对应的值就是1和0)',
  `dict_text_col` varchar(100) DEFAULT NULL COMMENT '显示的文本字段',
  `dict_value_col` varchar(100) DEFAULT NULL COMMENT '显示的值字段',
  `file_multil` varchar(4) DEFAULT NULL COMMENT '是否可多选批量上传',
  `file_custom_content` text COMMENT '自定义的文件上传控件(代码片断)',
  
  
  `width` int(2) DEFAULT NULL COMMENT '列宽: ',
  `align_type` varchar(2) DEFAULT NULL COMMENT '列对齐方式: ',
  `height` int(2) DEFAULT NULL COMMENT '列高 ',
  `hidde` varchar(2) DEFAULT NULL COMMENT '是否隐藏: 1=隐藏 0=不隐藏',
  `frozen` int(2) DEFAULT NULL COMMENT '是否冻结列: 0=不冻结 1=冻结',
  `allow_sort` int(2) DEFAULT NULL COMMENT '当前列是否可排序',  
  `allow_export` int(2) DEFAULT 0 COMMENT '是否允许导出（到excel):0=不允许 1=允许',  
  `allow_import` int(2) DEFAULT 1 COMMENT '是否允许导入(到excel):0=不允许 1=允许',
  
  
  `sn` int(4) DEFAULT '0',
  `version` varchar(100) DEFAULT NULL COMMENT '表字段的版本号',
  `opt_log` varchar(100) DEFAULT NULL COMMENT '用户操作备注',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表字段管理';


DROP TABLE IF EXISTS `rpt_export_tempalte`;
CREATE TABLE `rpt_export_tempalte` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL,
  `name` varchar(500) NOT NULL COMMENT '文件名称',
  `name_original` varchar(500) NULL COMMENT '原始文件名称',
  `type` int(2) DEFAULT NULL COMMENT '100=导入模板 200=导出模板',
  `path` varchar(1000) DEFAULT NULL COMMENT '(FastDFS文件)路径或自定义路径',
 
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `app_code` varchar(50) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表导入导出数据模板';



-- ----------------------------------- API系统 ----------------------------------------------
drop table  IF EXISTS  api_category ;
CREATE TABLE `api_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '分类值',
  
  `category_code` varchar(250) DEFAULT NULL COMMENT '分类维度编码引用 sys_category.code',
  `category_name` varchar(250) DEFAULT NULL COMMENT '分类维度名称',
  
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据（业务）类型',
  `node_path` varchar(1000) DEFAULT NULL,
  `app_code` varchar(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dic_name_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='API分类表';

drop table  IF EXISTS  api_definition ;
CREATE TABLE `api_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL ,
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(100) DEFAULT NULL COMMENT '分类名称',
  
  `value` varchar(200) DEFAULT NULL COMMENT '配置键',
  `name` varchar(200) NOT NULL COMMENT '配置名称',
  `code` varchar(256) DEFAULT NULL COMMENT '配置编码',
  
  `type` varchar(4) NOT NULL COMMENT '映射类型: 1=外部系统-http-json 2=本地系统',
  `entity_class_request` varchar(200)  NULL COMMENT '请求实体',
  `entity_class_response` varchar(200)  NULL COMMENT '响应实体',
  
  `url` varchar(100) NOT NULL COMMENT '接口地址(不带参数的地址)',
  `method` varchar(20) NOT NULL COMMENT '请求方法：post/get/delete/put...',
  `send_type` varchar(20) NOT NULL COMMENT '报文发送方式: 1=form_data 2=x-www-form-urlencodeed 3=raw 4=binary(选择文件)',
  `timeout` int(20) DEFAULT NULL COMMENT 'url请求超时间',
  
  `data_prop` varchar(200) DEFAULT NULL COMMENT 'JSON数据数据字段',
  
  
  `app_code` varchar(50) DEFAULT NULL COMMENT '应用编码',
  `sn` int(10) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统API接口定义';

drop table  IF EXISTS  api_param ;
CREATE TABLE `api_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL COMMENT '系统缓存管理表ID',
  `name` varchar(255) NOT NULL COMMENT '参数名称',
  `value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `code` varchar(255) NOT NULL COMMENT '参数编码',
  `type` varchar(4) DEFAULT NULL COMMENT '参数值类型: 100=动态值 ; 200=静态值 ',
  `required` int(2) DEFAULT '0' COMMENT '是否必填 1=是  0=否',
  `sn` int(10) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='API参数配置';

drop table  IF EXISTS  api_request_mock ;
CREATE TABLE `api_request_mock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL COMMENT '系统缓存管理表ID',
  `content_request` text DEFAULT NULL COMMENT '请求报文',
  `content_response` text DEFAULT NULL COMMENT '向应报文',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='API模拟配置';




-- ##################################################### 简历系统 ####################################################


drop table  IF EXISTS  res_job_tag ;
CREATE TABLE `res_job_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL unique COMMENT '编码',
  `value` varchar(50) DEFAULT NULL COMMENT '标签值',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='简历系统岗位标签定义:质检员、销售员等';

drop table  IF EXISTS  res_job_info ;
CREATE TABLE `res_job_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL unique COMMENT '职位编码',
  `name` varchar(48) NOT NULL COMMENT '职位名称',
  
  `tenant_code` varchar(20) NOT NULL COMMENT '公司编码',
  `tenant_short_name` varchar(48) DEFAULT NULL COMMENT '公司简称',

  `publish_platfrom` int(1) DEFAULT '0' COMMENT '发布平台(0:全平台，1：公众号，2：小程序)',
  `intermediary_name` varchar(56) DEFAULT NULL COMMENT '中介姓名',
  `intermediary_mobile` varchar(20) DEFAULT NULL COMMENT '中介手机号',
  `pub_user_code` varchar(32) DEFAULT NULL COMMENT '发布者编码',
  `pub_time` datetime DEFAULT NULL COMMENT '发布时间',
  `salary_composited` varchar(80) DEFAULT NULL COMMENT '综合工资',
  `work_time_desc` varchar(80) DEFAULT NULL COMMENT '作息时间描述:早9晚5',
  `requirement_sex` varchar(10) DEFAULT NULL COMMENT '性别要求',
  `requirement_age` varchar(20) DEFAULT NULL COMMENT '年龄要求',
  `requirement_edu` varchar(25) DEFAULT NULL COMMENT '学历要求',
  `work_years` varchar(25) DEFAULT NULL COMMENT '工作年限',
  `welfare` varchar(120) DEFAULT NULL COMMENT '岗位福利',
  `status` int(4) NOT NULL COMMENT '状态(0:未发布，1：已发布)',
  

--  `work_addr_id` varchar(20) DEFAULT NULL COMMENT '工作地址ID',
--  `picture_url` varchar(128) DEFAULT NULL COMMENT '图片路径',
--  `cover_url` varchar(128) DEFAULT NULL COMMENT '岗位封面图片路径',
  `category_id` bigint(20) DEFAULT NULL COMMENT '职位分类',
  `is_top` int(1) DEFAULT NULL COMMENT '是否置顶（0：不置顶，1：置顶）',
  `offline_time` datetime DEFAULT NULL COMMENT '下线时间',
  
  `is_need_resume` varchar(1) DEFAULT '0' COMMENT '是否需要简历',
  `interview_address` varchar(255) DEFAULT '0' COMMENT '面试地址',
  
  
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `bu_job_info_company_id_index` (`code`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位表';




drop table  IF EXISTS  res_address_attach ;
CREATE TABLE `res_address_attach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL unique COMMENT '地址编码',
  
  `status` int(4) NOT NULL COMMENT '状态(0=审核不通过，1=通过)',
  
  `object_id` varchar(50) NOT NULL unique COMMENT '引用的对象ID',
  `biz_type` int(2) NOT NULL COMMENT '(注100内的为企业实体、200的为职位实体)100=公司地址 101=企业图片    200=职位的企业logo 201=职位封面    202=职位视频   203=职位的工作地址',
  `address` varchar(1024) NOT NULL COMMENT '地址值',
 
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='企业地址、图片、视频地址、职位工作地址';



-- ##################################################### 演示使用的表 ##################################################

drop table  IF EXISTS  demo_user ;
CREATE TABLE `demo_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'ID值 Long',

  `code` varchar(50) DEFAULT NULL COMMENT '编码 String',
  `name` varchar(100) DEFAULT NULL COMMENT '技能名称 String',
  
  
  `user_babies` int(2) DEFAULT NULL COMMENT '用户的小孩数 Byte',
  `user_house_count` integer(2) DEFAULT NULL COMMENT '用户的房子数 Integer',
  `month_salary` double(10,2) DEFAULT NULL COMMENT '月薪 double',
  `day_salary` float(10,2) DEFAULT NULL COMMENT '月薪 float',
  
  `is_boss` bit DEFAULT NULL COMMENT '是否是老板 Boolean',
  `employee_count` decimal(10,0) DEFAULT NULL COMMENT '拥有的员工数 BigInteger',
  `year_salary` decimal(10,4) DEFAULT NULL COMMENT '年薪 BigDecimal',


  `birthday` datetime DEFAULT NULL COMMENT '生日 Date(起始值)',
  `leave_date` datetime DEFAULT NULL COMMENT '毕业日期（单值)',
  
  
  `head_img` varchar(1000) DEFAULT NULL COMMENT '用户头像(File)',
  `sex` bigint(2) DEFAULT NULL comment '性别(字典)' ,
  `department` varchar(50) DEFAULT NULL COMMENT '用户主部门(选择器-单选)',
  `department_list` varchar(50) DEFAULT NULL COMMENT '用户部门(选择器-多选)',
  
  `eable` int(1) DEFAULT NULL COMMENT '是否启用(字典)',
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  `sn` int(4) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表 - 代码生成测试用';


drop table  IF EXISTS  demo_address ;
CREATE TABLE `demo_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment '地址ID Long',
  `user_id` bigint(2) DEFAULT NULL comment '用户ID Long' ,
  `code` varchar(50) DEFAULT NULL COMMENT '编码 String',
  `name` varchar(100) DEFAULT NULL COMMENT '地址名称 String',
  

  `building_height` decimal(10,4) DEFAULT NULL COMMENT '地址楼高 BigDecimal',
  `building_box` decimal(10,0) DEFAULT NULL COMMENT '建筑的砖头数 BigInteger',
  
  `building_house_desk` int(2) DEFAULT NULL COMMENT '建筑的桌子数 Byte',
  `building_human_count` int(8) DEFAULT NULL COMMENT '建筑容纳的人数 Integer',
  `building_length` float(10,2) DEFAULT NULL COMMENT '地址楼长 Float',
  `building_width` double(10,2) DEFAULT NULL COMMENT '地址楼宽 Double',
  

  `regist_date` datetime DEFAULT NULL COMMENT '注册日期 Date(起始值)',
  `come_in_date` datetime DEFAULT NULL COMMENT '入住日期 Date(单值)',
  
  
  `building_img` varchar(1000) DEFAULT NULL COMMENT '地址图片(File)',
  
  `building_province` varchar(50) DEFAULT NULL COMMENT '建筑所在省份(选择器-单选)',
  `duilding_dev` varchar(50) DEFAULT NULL COMMENT '建筑开发商(选择器-多选)',
  
  `eable` int(1) DEFAULT NULL COMMENT '是否启用(字典)',
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  `sn` int(4) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表子表,联系地址';




-- ---------------------------------------  核查平台 (用户的犯罪记录、默名单啥的) ----------------------------------------

drop table  IF EXISTS  chk_user_crime ;
CREATE TABLE `chk_user_crime` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '引用系统用户id',
  `batch_no` varchar(32) DEFAULT NULL comment '上传的批次号，一般以年月日小时分钟秒钟来',
  
  `name` varchar(50) NOT NULL COMMENT '用户姓名',
  `idcard` varchar(50) NOT NULL COMMENT '身份证号',
  `sex` varchar(4) DEFAULT NULL COMMENT '性别',
  `photo` varchar(2000) DEFAULT NULL COMMENT '用户头像或照片',
  `police_address` varchar(500) DEFAULT NULL COMMENT '签证机关',
  
  -- 匹配说明
  `match_res_code` varchar(32)  DEFAULT NULL comment '匹配响应码',
  `match_res_desc` varchar(32)  DEFAULT NULL comment '系统响应说明',
  
  `match_biz_code` varchar(32)  DEFAULT NULL COMMENT '匹配业务响应码',
  `match_biz_desc` varchar(50)  DEFAULT NULL COMMENT '身份证匹配说明',
  
 
  -- 犯罪核查说明
  `res_code` varchar(20) DEFAULT NULL COMMENT '犯罪记录核查响应码',
  `res_msg` varchar(50) DEFAULT NULL COMMENT '系统响应描述|犯罪记录核查响应描述',
  
  
  `sn` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表刑事案底核查表(北京优分数据科技接口)';





drop table  IF EXISTS  chk_crime_detail ;
CREATE TABLE `chk_crime_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uc_id` bigint(20) NOT NULL COMMENT '引用chk_user_crime.id',
 
  
  `crime_type` varchar(20) DEFAULT NULL COMMENT '案件类型',
  `count` varchar(10) DEFAULT NULL COMMENT '案件数量',
  `case_type` varchar(20) DEFAULT NULL COMMENT '案件类型',
  `case_source` varchar(20) DEFAULT NULL COMMENT '案件来源',
  `case_period` varchar(20) DEFAULT NULL COMMENT '案件时间段',
  `case_level` varchar(20) DEFAULT NULL COMMENT '案件级别',
 
 
  `sn` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `app_code` varchar(40) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='犯罪记录详情表';


commit;