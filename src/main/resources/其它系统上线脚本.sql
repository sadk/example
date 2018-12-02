-- 截止目前为止，只有crm系统没有执行该脚本,生产环境下.by 袁mm 2018-11-22

ALTER TABLE `sys_tenant` 
CHANGE COLUMN `value` `value` VARCHAR(46) NULL DEFAULT NULL COMMENT '租户值' ,
ADD COLUMN `full_name` VARCHAR(46) NULL   COMMENT '租户全称',
ADD COLUMN `nick_name` VARCHAR(46) NULL   COMMENT '租户昵称',
ADD COLUMN `introduction` VARCHAR(1024) NULL   COMMENT '租户介绍';



ALTER TABLE `sys_tenant` 
CHANGE COLUMN `full_name` `full_name` VARCHAR(46) NULL DEFAULT NULL COMMENT '租户全称' AFTER `code`,
CHANGE COLUMN `nick_name` `nick_name` VARCHAR(46) NULL DEFAULT NULL COMMENT '租户昵称' AFTER `full_name`,
CHANGE COLUMN `introduction` `introduction` VARCHAR(1024) NULL DEFAULT NULL COMMENT '租户文字介绍' AFTER `nick_name`,
CHANGE COLUMN `value` `value` VARCHAR(46) NULL DEFAULT NULL COMMENT '租户值' ;



ALTER TABLE `sys_file`  DROP COLUMN `obj_id`;
ALTER TABLE `sys_file`  ADD COLUMN `data_type` INT(4) NULL COMMENT '业务数据类型（从字典引用值）,1=企业logo 2=职位封面 3=职位视频' ;
ALTER TABLE `sys_file` CHANGE COLUMN `data_type` `data_type` INT(4) NULL DEFAULT NULL COMMENT '业务数据类型（从字典引用值）,100=企业logo 200=职位封面 201=职位视频' ;

ALTER TABLE `rpt_definition` ADD COLUMN `data_replica_stroe_precision` INT(2) NULL ;

ALTER TABLE `rpt_definition` 
CHANGE COLUMN `data_replica_stroe_precision` `data_replica_storage_precision` INT(2) NULL DEFAULT NULL COMMENT '数据副本存储精度模式 1=全字段按字符存储  2=按导入定义字段类型存储' ;

ALTER TABLE `rpt_definition` 
ADD COLUMN `report_script` TEXT NULL  comment '报表自定义脚本,用于自定义js函数，自行控制调用' AFTER `data_replica_storage_precision` ;


ALTER TABLE `rpt_definition` 
ADD COLUMN `count_required` INT(2) NULL COMMENT '分页是否统计总记录数' AFTER `report_script`;


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
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
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












-- ------------------------------------------ 简历科技 从当前执行 -------------------------------


ALTER TABLE  `sys_machine` 
CHANGE COLUMN `user_name` `user_name` VARCHAR(255) NULL COMMENT '登陆名称' ,
CHANGE COLUMN `user_password` `user_password` VARCHAR(255) NULL COMMENT '登陆密码' ;



ALTER TABLE `rpt_definition`  ADD COLUMN `store_replica_data` INT(2) NULL COMMENT '是否存储数据副本:1=是 0=否' ;


ALTER TABLE `sys_property`  CHANGE COLUMN `value` `value` VARCHAR(2000) NULL DEFAULT NULL COMMENT '属性值' ;


ALTER TABLE `sys_property` CHANGE COLUMN `remark` `remark` VARCHAR(500) NULL DEFAULT NULL COMMENT '属性说明' ;


ALTER TABLE `uum_res` CHANGE COLUMN `code` `code` VARCHAR(200) NULL DEFAULT NULL COMMENT '编码' ;

-- 查检以下字段是否存在:
-- ALTER TABLE  `uum_res`  ADD COLUMN `url` VARCHAR(2000) NULL ;
-- ALTER TABLE  `uum_res` ADD COLUMN `icon` VARCHAR(200) NULL  ;



-- 简历科技脚本更新
-- --------------------------------------------- (1)企业信息管理 ----------------------------------------------------------
-- 公司信息表
ALTER TABLE `bu_company_info` ADD UNIQUE INDEX `company_id_UNIQUE` (`company_id` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_company_info` ADD COLUMN `id` BIGINT(20) NULL ;

update bu_company_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_company_info.*  FROM (SELECT @rownum:=0) r, bu_company_info  ) B set A.id= B.rid  where A.company_id = B.company_id ;
ALTER TABLE `bu_company_info` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_company_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;


-- 公司的地址表
ALTER TABLE `bu_work_address` ADD UNIQUE INDEX `work_address_id_UNIQUE` (`addr_id` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_work_address` ADD COLUMN `id` BIGINT(20) NULL ;

update bu_work_address A,(SELECT @rownum:=@rownum+1 AS rid, bu_work_address.*  FROM (SELECT @rownum:=0) r, bu_work_address  ) B set A.id= B.rid  where A.addr_id = B.addr_id ;
ALTER TABLE `bu_work_address` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_work_address` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT;
ALTER TABLE `bu_work_address` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST;


-- 公司的图片
update bu_company_picture A,(SELECT @rownum:=@rownum+1 AS rid, bu_company_picture.*  FROM (SELECT @rownum:=0) r, bu_company_picture  ) B set A.id= B.rid  where A.id = B.id ;
ALTER TABLE `bu_company_picture` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;








 -- --------------------------------------------- (2)岗位管理 ----------------------------------------------------------
ALTER TABLE `bu_base_job_info` ADD UNIQUE INDEX `base_job_info_id_UNIQUE` (`base_job_id` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_base_job_info` ADD COLUMN `id` BIGINT(20) NULL ;

update bu_base_job_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_base_job_info.*  FROM (SELECT @rownum:=0) r, bu_base_job_info  ) B set A.id= B.rid  where A.base_job_id = B.base_job_id ;
ALTER TABLE `bu_base_job_info` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_base_job_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;




 -- --------------------------------------------- (3)职位管理 ----------------------------------------------------------
ALTER TABLE `bu_job_info` ADD UNIQUE INDEX `job_info_id_UNIQUE` (`job_id` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_job_info` ADD COLUMN `id` BIGINT(20) NULL ;

update bu_job_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_job_info.*  FROM (SELECT @rownum:=0) r, bu_job_info  ) B set A.id= B.rid  where A.job_id = B.job_id ;
ALTER TABLE `bu_job_info` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_job_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;

-- 删除红包ID字段 (暂时还不能删除)
-- ALTER TABLE `bu_job_info` DROP COLUMN `red_packet_id`, DROP COLUMN `red_id`;




 -- --------------------------------------------- (4)发布人管理  ----------------------------------------------------------
 -- 实体用户
ALTER TABLE `bu_user_info` ADD UNIQUE INDEX `user_info_id_UNIQUE` (`user_id` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_user_info` ADD COLUMN `id` BIGINT(20) NULL ;

update bu_user_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_user_info.*  FROM (SELECT @rownum:=0) r, bu_user_info  ) B set A.id= B.rid  where A.user_id = B.user_id ;
ALTER TABLE `bu_user_info` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_user_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;


-- 用户账号 ,  by me: 这个不要改成整型!!!!!!!!!!!
/*
update bu_account_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_account_info.*  FROM (SELECT @rownum:=0) r, bu_account_info  ) B set A.id= B.rid  where A.id = B.id ;
ALTER TABLE  `bu_account_info` CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL ;
ALTER TABLE `bu_account_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;
*/

-- --------------------------------------------- (5)职位投递记录 ----------------------------------------------------------
-- 职位投递记录 
-- update bu_user_job_record A,(SELECT @rownum:=@rownum+1 AS rid, bu_user_job_record.*  FROM (SELECT @rownum:=0) r, bu_user_job_record  ) B set A.id= B.rid  where A.id = B.id ;
-- ALTER TABLE `bu_user_job_record` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;

ALTER TABLE `bu_user_job_record` ADD COLUMN `company_id` VARCHAR(46) NULL ;

ALTER TABLE  `bu_user_job_record` 
CHANGE COLUMN `company_id` `company_id` VARCHAR(46) NULL DEFAULT NULL AFTER `publisher_id`,
CHANGE COLUMN `company_name` `company_name` VARCHAR(32) NULL DEFAULT NULL COMMENT '公司名称' AFTER `company_id`;

update bu_user_job_record A, bu_company_info B set A.company_id = B.company_id where A.company_name = B.company_short_name  ;



-- --------------------------------------------- (5)考勤管理 ----------------------------------------------------------
drop table  IF EXISTS  bu_user_work_record ;
CREATE TABLE `bu_user_work_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(40) DEFAULT NULL,
  `user_name` varchar(80) DEFAULT NULL,
  `type` int(4) DEFAULT NULL COMMENT '考勤类型 :100=正常上班 200=加班 300=请假',
  `working_hours` varchar(4) DEFAULT NULL COMMENT '正常工时(时长精确到小时，保留一个小数)',
  `leave_hours` varchar(4) DEFAULT NULL COMMENT '请假工时',
  `extra_hours` varchar(4) DEFAULT NULL COMMENT '加班工时',
  `extra_shift_type` varchar(10) DEFAULT NULL COMMENT '加班班次类型:1=白班、2=中班、3=晚班、4=休息',
  `leave_type` varchar(10) DEFAULT NULL COMMENT '请假类型:1=事假、2=病假、3=其他',
  `remark` varchar(256) DEFAULT NULL COMMENT '（请假原因）备注',
  `tenant_code` varchar(20) DEFAULT NULL,
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  `weekday` int(2) DEFAULT NULL COMMENT '1=星期一 2=星期二  3=星期三 等',
  `record_date` bigint(20) NOT NULL COMMENT '记录的日期yyyyMMdd',
  `leave_shift_type` varchar(10) DEFAULT NULL COMMENT '请假班次类型，与加班班次类型一致 :1=白班、2=中班、3=晚班、4=休息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='工时记录';



ALTER TABLE  `bu_store_info` 
ADD COLUMN `id` BIGINT(20) NOT NULL ,
ADD UNIQUE INDEX `store_id_UNIQUE` (`store_id` ASC),
DROP PRIMARY KEY, ADD PRIMARY KEY (`id`);

ALTER TABLE `bu_store_info`  CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL FIRST;
ALTER TABLE `bu_store_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;




ALTER TABLE  `bu_personal_video_info` ADD COLUMN `id` BIGINT(20) NULL  first;
ALTER TABLE  `bu_personal_video_info` DROP PRIMARY KEY;
update bu_personal_video_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_personal_video_info.*  FROM (SELECT @rownum:=0) r, bu_personal_video_info  ) B set A.id= B.rid  where A.video_id = B.video_id ;



ALTER TABLE `bu_area_info` ADD COLUMN `id` BIGINT(20) NULL first;
update bu_area_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_area_info.*  FROM (SELECT @rownum:=0) r, bu_area_info  ) B set A.id= B.rid  where A.code = B.code ;
ALTER TABLE  `bu_area_info`  ADD UNIQUE INDEX `code_UNIQUE` (`code` ASC), DROP PRIMARY KEY;
ALTER TABLE `bu_area_info` CHANGE `id` `id` bigint(20) primary key NOT NULL AUTO_INCREMENT FIRST;



ALTER TABLE `bu_work_address`  CHANGE COLUMN `addr_id` `addr_id` VARCHAR(50) NOT NULL COMMENT '地址ID' ;



 

 drop table  IF EXISTS  bu_company_admin_relationship ;
 CREATE TABLE `bu_company_admin_relationship` (
  `id` bigint(20) not null primary key AUTO_INCREMENT ,
  `company_id` varchar(32) NOT NULL DEFAULT '' COMMENT '厂区id',
  `company_name` varchar(32) DEFAULT '' COMMENT '厂区名称',
  `company_admin_id` varchar(32) NOT NULL DEFAULT '' COMMENT '驻场管理员id',
  `company_admin_name` varchar(32) DEFAULT '' COMMENT '驻场管理员姓名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `tenant_code` varchar(32) DEFAULT NULL COMMENT '租户编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='驻场管理员信息表';




ALTER TABLE `bu_job_info` CHANGE COLUMN `job_id` `job_id` VARCHAR(40) NOT NULL COMMENT '职位ID' ;
ALTER TABLE `bu_job_info` ADD COLUMN `responsibility` VARCHAR(200) NULL COMMENT '岗位职责' ;
ALTER TABLE `bu_job_info` ADD COLUMN `salary_details` VARCHAR(200) NULL COMMENT '工资详情';


ALTER TABLE `bu_job_address_relationship` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT '' ,
CHANGE COLUMN `addr_id` `addr_id` VARCHAR(50) NULL DEFAULT NULL ;


ALTER TABLE `bu_company_info`  CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NOT NULL COMMENT '企业编号' ;
ALTER TABLE `bu_work_address` CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '公司ID' ;
ALTER TABLE  `bu_company_picture` CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '企业编号' ;

ALTER TABLE `bu_job_welfare_relationship` 
CHANGE COLUMN `welfare_id` `welfare_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '福利ID' ;

ALTER TABLE `bu_job_welfare_relationship` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ;

ALTER TABLE `bu_base_job_info`  CHANGE COLUMN `base_job_id` `base_job_id` VARCHAR(50) NOT NULL COMMENT '岗位ID' ;


ALTER TABLE `bu_base_job_info`  ADD COLUMN `sn` INT(4) NULL COMMENT '排序号';

ALTER TABLE  `bu_job_info` 
CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NOT NULL COMMENT '公司ID' ,
CHANGE COLUMN `announcer_id` `announcer_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '发布者编码' ;



ALTER TABLE  `uum_user` 
CHANGE COLUMN `code` `code` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户编码' ,
ADD COLUMN `tenant_code` VARCHAR(50) NULL COMMENT '用户所属租户码' ;

ALTER TABLE  `uum_user` 
ADD COLUMN `tenant_name` VARCHAR(50) NULL AFTER `tenant_code`;


ALTER TABLE `bu_video_info` CHANGE COLUMN `id` `id` VARCHAR(50) NOT NULL ;


ALTER TABLE `bu_job_video` CHANGE COLUMN `create_date` `create_date` DATETIME NULL DEFAULT now() COMMENT '创建时间' ;

ALTER TABLE `bu_job_video` CHANGE COLUMN `update_date` `update_date` DATETIME NULL DEFAULT now() COMMENT '修改时间' ;

ALTER TABLE  `bu_code_library`  CHANGE COLUMN `code_no` `code_no` VARCHAR(50) NOT NULL COMMENT '编码' ,
CHANGE COLUMN `item_no` `item_no` VARCHAR(200) NULL DEFAULT NULL COMMENT '项目编号' ;


ALTER TABLE `bu_job_video` 
CHANGE COLUMN `video_id` `video_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '视频id' ,
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ;


ALTER TABLE `bu_job_info` 
CHANGE COLUMN `picture_url` `picture_url` VARCHAR(128) NULL DEFAULT NULL COMMENT '企业logo图' ,
CHANGE COLUMN `cover_url` `cover_url` VARCHAR(128) NULL DEFAULT NULL COMMENT '职位的封面图' ;


 -- --------------------------------------------------- 字段加长
ALTER TABLE `bu_job_info_backup` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位编码' ;

ALTER TABLE `bu_job_info_extension` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NOT NULL ;

ALTER TABLE  `bu_job_record_follow_up` 
CHANGE COLUMN `job_hunter_id` `job_hunter_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '求职者编码' ,
CHANGE COLUMN `job_record_id` `job_record_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '求职记录编码' ,
CHANGE COLUMN `follow_person_id` `follow_person_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '跟进人编码' ;


ALTER TABLE `bu_job_relationship` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位编码' ;


ALTER TABLE `bu_job_user_look` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ,
CHANGE COLUMN `user_id` `user_id` VARCHAR(50) NOT NULL COMMENT '查看用户ID' ;


ALTER TABLE `bu_job_user_statistics` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ;


ALTER TABLE `bu_personal_video_info` 
CHANGE COLUMN `video_id` `video_id` VARCHAR(50) NOT NULL COMMENT '视频ID' ,
CHANGE COLUMN `user_id` `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户ID' ;



ALTER TABLE `bu_user_job_record` 
CHANGE COLUMN `user_id` `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户ID' ,
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位编码' ,
CHANGE COLUMN `last_follow_id` `last_follow_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '最后一次跟进的编码' ,
CHANGE COLUMN `company_admin_id` `company_admin_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '驻场管理员id' ,
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '门店id' ;



ALTER TABLE `bu_store_with_manager` 
CHANGE COLUMN `manager_id` `manager_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '管理者id' ;


ALTER TABLE `bu_account_info` 
CHANGE COLUMN `account_id` `account_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '账户编码' ;


ALTER TABLE `bu_activity_info` 
CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '公司ID' ;


ALTER TABLE `bu_company_admin_relationship` 
CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '厂区id' ,
CHANGE COLUMN `company_admin_id` `company_admin_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '驻场管理员id' ;


ALTER TABLE `bu_company_store_relationship` 
CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '厂区id' ,
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '门店id' ;


ALTER TABLE `bu_company_user` 
CHANGE COLUMN `user_id` `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户ID' ,
CHANGE COLUMN `company_id` `company_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '公司ID' ;


ALTER TABLE `bu_job_info` 
CHANGE COLUMN `work_addr_id` `work_addr_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '工作地址ID' ;


ALTER TABLE `bu_personal_video_info` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ;


ALTER TABLE `bu_red_packet` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NOT NULL ;

ALTER TABLE `bu_store_info` 
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NOT NULL DEFAULT '门店id' ;


ALTER TABLE `bu_store_user_relationship` 
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '门店id' ;


ALTER TABLE `bu_store_with_manager` 
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '门店id' ;

ALTER TABLE `bu_user_video_comment` 
CHANGE COLUMN `job_id` `job_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '职位ID' ,
CHANGE COLUMN `user_id` `user_id` VARCHAR(50) NOT NULL COMMENT '评论用户ID' ;


ALTER TABLE  `bu_job_info` 
CHANGE COLUMN `responsibility` `responsibility` VARCHAR(1024) NULL DEFAULT NULL COMMENT '岗位职责' ,
CHANGE COLUMN `salary_details` `salary_details` VARCHAR(1024) NULL DEFAULT NULL ;






-- 处历科技以上脚本已执行： 2018-12月
ALTER TABLE `sys_datasource` ADD COLUMN `dialect` INT(4) NULL  ;

INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (432,NULL,'数据库方言','sys_datasource_dialect','sys_datasource_dialect','dictionary',NULL,'1',NULL,0,'432','数据库方言','wM2drKUXAeZER5yvUbTwE','2018-12-01 18:37:51','2018-12-01 19:01:15');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (433,432,'DB2Dialect','0','DB2Dialect','dictionary',NULL,'1',NULL,0,'432,433',NULL,'fnqPQXVqDNNgmBKvxUsmm','2018-12-01 18:38:17','2018-12-01 18:38:46');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (434,432,'DB2 AS/400','1','DB2400Dialect','dictionary',NULL,'1',NULL,0,'432,434','DB2 AS/400','N8bksiJKGVqCADodAKFEA','2018-12-01 18:39:33','2018-12-01 18:44:19');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (435,432,'DB2 OS390','2','DB2390Dialect','dictionary',NULL,'1',NULL,0,'432,435','DB2 OS390','mcz4ZqobCDeSeK3Qzy3Hz','2018-12-01 18:40:15','2018-12-01 18:44:14');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (436,432,'PostgreSQL','3','PostgreSQLDialect','dictionary',NULL,'1',NULL,0,'432,436','PostgreSQL','KUoEa1DDAvSDJDRwYqfqj','2018-12-01 18:40:45','2018-12-01 18:44:09');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (437,432,'MySQLDialect','4','MySQLDialect','dictionary',NULL,'1',NULL,0,'432,437','MySQLDialect','wjZNf8rHrWJriT5b1Hu6n','2018-12-01 18:41:11','2018-12-01 18:44:02');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (438,432,'MySQL with InnoDB','5','MySQLInnoDBDialect','dictionary',NULL,'1',NULL,0,'432,438','MySQL with InnoDB','TJFptLdgu2oJVZn6Si9jr','2018-12-01 18:41:38','2018-12-01 18:43:57');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (439,432,'MySQL with MyISAM','6','MySQLMyISAMDialect','dictionary',NULL,'1',NULL,0,'432,439','MySQL with MyISAM','vELr34QX2xird55czdDvm','2018-12-01 18:42:35','2018-12-01 18:43:52');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (440,432,'Oracle（any version）','7','OracleDialect','dictionary',NULL,'1',NULL,0,'432,440','Oracle（any version）','pyvWRmQCjSYhtXvtopNXi','2018-12-01 18:43:28','2018-12-01 18:43:44');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (441,432,'Oracle 9i','8','Oracle 9i','dictionary',NULL,'1',NULL,0,'432,441','Oracle9iDialect','Ua7L5jYbVEcrWmxbm2w2g','2018-12-01 18:45:11','2018-12-01 18:45:21');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (442,432,'Oracle 10g','9','Oracle10gDialect','dictionary',NULL,'1',NULL,0,'432,442','Oracle10gDialect','W3guS9DBzsxMYx2McymBP','2018-12-01 18:45:56','2018-12-01 18:45:56');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (443,432,'Sybase','10','SybaseDialect','dictionary',NULL,'1',NULL,0,'432,443','Sybase','wm9zLLRd3Bhh8GLwLa4J','2018-12-01 18:46:25','2018-12-01 18:46:25');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (444,432,'Sybase Anywhere','11','SybaseAnywhereDialect','dictionary',NULL,'1',NULL,0,'432,444','SybaseAnywhereDialect','Np2zTXMWvsPe7eateRf3T','2018-12-01 18:46:59','2018-12-01 18:46:59');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (445,432,'Microsoft SQL Server','12','SQLServerDialect','dictionary',NULL,'1',NULL,0,'432,445','Microsoft SQL Server','tSXDjAsq6RXiAsHidjTFr','2018-12-01 18:47:39','2018-12-01 18:47:39');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (446,432,'SAP DB','13','SAPDBDialect','dictionary',NULL,'1',NULL,0,'432,446','SAPDBDialect','MPLTgYErJwTWrjq3uCvcs','2018-12-01 18:48:08','2018-12-01 18:48:08');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (447,432,'Informix','14','InformixDialect','dictionary',NULL,'1',NULL,0,'432,447','Informix','ZM44i8BxCSAtPeVFvJiH','2018-12-01 18:48:43','2018-12-01 18:48:43');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (448,432,'HypersonicSQL','15','HSQLDialect','dictionary',NULL,'1',NULL,0,'432,448','HSQLDialect','QZwfvoN2XPbDNHABXQgDu','2018-12-01 18:49:37','2018-12-01 19:01:21');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (449,432,'Ingres','16','IngresDialect','dictionary',NULL,'1',NULL,0,'432,449','IngresDialect','feyhv6sq3Si16nV9w1ub6','2018-12-01 18:50:17','2018-12-01 18:50:41');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (450,432,'Progress','17','ProgressDialect','dictionary',NULL,'1',NULL,0,'432,450','Progress','w2Keo4Xd4USkn8LX8GaKp','2018-12-01 18:51:17','2018-12-01 19:01:27');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (451,432,'Mckoi SQL','18','MckoiDialect','dictionary',NULL,'1',NULL,0,'432,451','MckoiDialect','BkdfyXcq9MdsGU2iUHuwG','2018-12-01 18:51:51','2018-12-01 18:51:51');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (452,432,'Interbase','19','InterbaseDialect','dictionary',NULL,'1',NULL,0,'432,452','InterbaseDialect','CUVyT1AeQzU4CV3j42dFW','2018-12-01 18:52:20','2018-12-01 18:52:20');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (453,432,'Pointbase','20','PointbaseDialect','dictionary',NULL,'1',NULL,0,'432,453','Pointbase','S2attvhv4xYtdR61Ky3H2','2018-12-01 18:52:54','2018-12-01 18:52:54');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (454,432,'FrontBase','21','FrontbaseDialect','dictionary',NULL,'1',NULL,0,'432,454','FrontBase','Wn6SZVKMTDpoSeHw4bNEo','2018-12-01 18:53:18','2018-12-01 18:53:18');
INSERT INTO `sys_dictionary` (`id`,`pid`,`name`,`value`,`code`,`category_code`,`data_type`,`enable`,`app_code`,`sn`,`node_path`,`remark`,`gid`,`create_time`,`update_time`) VALUES (455,432,'Firebird','22','FirebirdDialect','dictionary',NULL,'1',NULL,0,'432,455','Firebird','zt1yRdoatzSADjnRRMw83','2018-12-01 18:54:04','2018-12-01 18:54:04');

