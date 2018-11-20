ALTER TABLE `sys_tenant` 
CHANGE COLUMN `value` `value` VARCHAR(46) NULL DEFAULT NULL COMMENT '租户值' ,
ADD COLUMN `full_name` VARCHAR(46) NULL   COMMENT '租户全称',
ADD COLUMN `nick_name` VARCHAR(46) NULL   COMMENT '租户昵称',
ADD COLUMN `introduction` VARCHAR(1024) NULL   COMMENT '租户介绍';



ALTER TABLE `sys_tenant` 
CHANGE COLUMN `full_name` `full_name` VARCHAR(45) NULL DEFAULT NULL COMMENT '租户全称' AFTER `code`,
CHANGE COLUMN `nick_name` `nick_name` VARCHAR(45) NULL DEFAULT NULL COMMENT '租户昵称' AFTER `full_name`,
CHANGE COLUMN `introduction` `introduction` VARCHAR(1024) NULL DEFAULT NULL COMMENT '租户文字介绍' AFTER `nick_name`,
CHANGE COLUMN `value` `value` VARCHAR(45) NULL DEFAULT NULL COMMENT '租户值' ;



ALTER TABLE `sys_file`  DROP COLUMN `obj_id`;
ALTER TABLE `sys_file`  ADD COLUMN `data_type` INT(4) NULL COMMENT '业务数据类型（从字典引用值）,1=企业logo 2=职位封面 3=职位视频' ;
ALTER TABLE `sys_file` CHANGE COLUMN `data_type` `data_type` INT(4) NULL DEFAULT NULL COMMENT '业务数据类型（从字典引用值）,1=企业logo 2=职位封面 3=职位视频' AFTER `file_or_dir`;


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




ALTER TABLE  `sys_machine` 
CHANGE COLUMN `user_name` `user_name` VARCHAR(255) NULL COMMENT '登陆名称' ,
CHANGE COLUMN `user_password` `user_password` VARCHAR(255) NULL COMMENT '登陆密码' ;


-- 简历科技系统(已于2018-11-20发给勇波执行)
-- update sys_machine set status = 1 where code = 'RabitMQ_FengKong' ;
-- update sys_machine set status = 1 where code = 'IFC_HTTP_HOST' ;


-- 
