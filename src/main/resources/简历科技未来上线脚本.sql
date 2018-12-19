-- 用户视频表添加ID自增长，生产上ID为null需要添加行号作为ID
-- ALTER TABLE  `bu_personal_video_info` ADD COLUMN `id` BIGINT(20) NULL  first;
-- ALTER TABLE  `bu_personal_video_info` DROP PRIMARY KEY;
-- update bu_personal_video_info A,(SELECT @rownum:=@rownum+1 AS rid, bu_personal_video_info.*  FROM (SELECT @rownum:=0) r, bu_personal_video_info  ) B set A.id= B.rid  where A.video_id = B.video_id ;
-- ALTER TABLE  `bu_personal_video_info` ADD PRIMARY KEY (`id`);
-- ALTER TABLE `bu_personal_video_info` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;





-- 报表系统脚本(其它系统要上),注意：在线添加字典
ALTER TABLE `rpt_definition` 
ADD COLUMN `export_curr_page` INT(2) NULL COMMENT '导出查询当前页=1 、导出查询所有页 =0 (注：如果报表定义不分页，则这个忽略)' ,
ADD COLUMN `export_data_render` INT(2) NULL COMMENT '按导出模板渲染=0 ， 数据自动渲染 = 1' ;


update rpt_definition set export_data_render = 0 ;
update rpt_definition set export_curr_page = 0;


ALTER TABLE `rpt_definition` CHANGE COLUMN `data_replica_stroe_precision` `data_replica_stroe_precision` INT(2) NULL DEFAULT NULL ;




-- ---------------------简历科技业务SQL ------------------------------------------------

ALTER TABLE `bu_user_info` ADD COLUMN `quit_time` DATETIME NULL;


ALTER TABLE `bu_user_work_record` 
-- DROP COLUMN `store_name`,
-- DROP COLUMN `store_id`,
DROP COLUMN `leave_shift_type`,
DROP COLUMN `type`,
CHANGE COLUMN `extra_hours` `extra_hours` VARCHAR(4) NULL DEFAULT NULL COMMENT '平时(1-5)加班工时' AFTER `working_hours`,
CHANGE COLUMN `record_date` `record_date` BIGINT(20) NOT NULL COMMENT '考勤日期yyyyMMdd' ,
CHANGE COLUMN `extra_shift_type` `shift_type` VARCHAR(10) NULL DEFAULT NULL COMMENT '(上班和加班的)  班次类型:1=白班、2=中班、3=晚班、4=休息' ,
CHANGE COLUMN `weekday` `weekday` INT(2) NULL DEFAULT NULL COMMENT '考勤星期: 1=星期一 2=星期二  3=星期三 等' ;



ALTER TABLE `bu_user_work_record` ADD COLUMN `leave_has` INT(2) NULL COMMENT '是否有请假如： 1=有';
ALTER TABLE `bu_user_work_record` CHANGE COLUMN `leave_has` `leave_has` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否有请假如： 1=有' ;

/*
CREATE TABLE `bu_user_company_relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `name` varchar(50) NOT NULL DEFAULT '用户姓名',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '用户编码',
  `iphone` varchar(50) NOT NULL DEFAULT '' COMMENT '用户手机',
  
  `company_code` varchar(50) DEFAULT '' COMMENT '用户所属企业',
  `company_name` varchar(50) DEFAULT '' COMMENT '',

  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `tenant_code` varchar(32) DEFAULT NULL COMMENT '租户编码'
 
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户所在的工厂表';
*/

ALTER TABLE `bu_user_info` 
CHANGE COLUMN `is_entry` `is_entry` VARCHAR(10) NULL DEFAULT NULL ,
ADD COLUMN `depend_company_name` VARCHAR(100) NULL COMMENT '用户当前所属企业(名称)' ,
ADD COLUMN `depend_company_code` VARCHAR(50) NULL COMMENT '用户当前所属企业(编码)' ;


ALTER TABLE `bu_company_info` ADD COLUMN `attendance_day` INT(2) NULL COMMENT '企业的考勤月切预留天数。例如：5天后不能修改考勤数据' ;


ALTER TABLE `bu_user_work_record` 
ADD COLUMN `company_name` VARCHAR(100) NULL AFTER `leave_has`,
ADD COLUMN `company_code` VARCHAR(50) NULL AFTER `company_name`;



-- 2018-12-13
ALTER TABLE  `bu_user_role`  ADD COLUMN `id` BIGINT(20) NULL FIRST;
update bu_user_role A,(SELECT @rownum:=@rownum+1 AS rid, bu_user_role.*  FROM (SELECT @rownum:=0) r, bu_user_role  ) B set A.id= B.rid  where A.role_id = B.role_id ;

ALTER TABLE `bu_user_role`  CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL , DROP PRIMARY KEY,ADD PRIMARY KEY (`id`);
ALTER TABLE `bu_user_role` ADD COLUMN `remark` VARCHAR(100) NULL AFTER `create_user`;


ALTER TABLE `bu_company_info` 
ADD COLUMN `enable_ocr` INT(2) NULL COMMENT '是否开启OCR识别: 1=开启 ' ,
ADD COLUMN `enable_face_detect` INT(2) NULL COMMENT '是否开启人脸识别: 1=开启 ' ;

ALTER TABLE `bu_company_info` 
CHANGE COLUMN `enable_ocr` `enable_ocr_idcard` INT(2) NULL DEFAULT NULL COMMENT '是否开启OCR识别: 1=开启 ' ,
ADD COLUMN `enable_ocr_bankcard` VARCHAR(45) NULL AFTER `enable_face_detect`;

ALTER TABLE `bu_company_info` 
CHANGE COLUMN `enable_ocr_bankcard` `enable_ocr_bankcard` INT(2) NULL DEFAULT NULL ;


ALTER TABLE `bu_company_info` 
CHANGE COLUMN `enable_ocr_idcard` `enable_ocr_idcard` INT(2) NULL DEFAULT NULL COMMENT '是否开启OCR识别（身份证): 1=开启 ' ,
CHANGE COLUMN `enable_ocr_bankcard` `enable_ocr_bankcard` INT(2) NULL DEFAULT NULL COMMENT '是否开启OCR识别（银行卡): 1=开启 ' ;

update bu_company_info set enable_ocr_idcard = 0 , enable_ocr_bankcard=0,enable_face_detect = 0 ;


-- 2018-12-14
ALTER TABLE `bu_user_entry_info` ADD COLUMN `remrak` VARCHAR(200) NULL COMMENT '入职备注' ;


-- 2018-12-17
ALTER TABLE `bu_user_extra_info` 
CHANGE COLUMN `idcard0_url` `idcard0_url` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '身份证正面url' ,
CHANGE COLUMN `idcard1_url` `idcard1_url` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '身份证反面url' ,
CHANGE COLUMN `face_url` `face_url` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '脸部识别url' ,
CHANGE COLUMN `bank_url` `bank_url` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '卡号url' ;

ALTER TABLE `bu_store_info` 
ADD COLUMN `province_code` VARCHAR(50) NULL AFTER `tenant_code`,
ADD COLUMN `province_name` VARCHAR(50) NULL AFTER `province_code`,
ADD COLUMN `city_code` VARCHAR(50) NULL AFTER `province_name`,
ADD COLUMN `city_name` VARCHAR(50) NULL AFTER `city_code`,
ADD COLUMN `area_code` VARCHAR(50) NULL AFTER `city_name`,
ADD COLUMN `area_name` VARCHAR(50) NULL AFTER `area_code`;

ALTER TABLE `bu_store_city_relationship` 
CHANGE COLUMN `id` `id` INT(20) NOT NULL COMMENT '自增ID' ;

ALTER TABLE `bu_user_role` CHANGE `id` `id` bigint(20)  NOT NULL AUTO_INCREMENT FIRST;

-- 2018-12-18
ALTER TABLE `bu_user_entry_info` 
CHANGE COLUMN `store_id` `store_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户当前所属门店(编码) (冗余字段)' ,
CHANGE COLUMN `store_name` `store_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户当前所属门店(冗余字段)' ;

ALTER TABLE `bu_user_job_record` 
CHANGE COLUMN `platfrom` `platfrom` VARCHAR(16) NULL DEFAULT NULL COMMENT '投递平台' ,
CHANGE COLUMN `tenant_code` `tenant_code` VARCHAR(46) NULL DEFAULT NULL ,
CHANGE COLUMN `store_id` `store_id` VARCHAR(46) NULL DEFAULT NULL ,
ADD COLUMN `record_id` VARCHAR(46) NULL;

ALTER TABLE `bu_user_extra_info` 
CHANGE COLUMN `bank_card` `bank_card` VARCHAR(20) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '银行卡号' ,
CHANGE COLUMN `bank_card_deposit` `bank_card_deposit` VARCHAR(40) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '开户行' ;

ALTER TABLE `bu_user_extra_info` 
CHANGE COLUMN `identity_card` `identity_card` VARCHAR(30) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '身份证号' ;


ALTER TABLE `bu_user_info` 
CHANGE COLUMN `role_code` `role_code` VARCHAR(50) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '角色编码' ,
CHANGE COLUMN `role_name` `role_name` VARCHAR(60) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '角色名称' ;


insert into bu_user_entry_info (user_id,record_id,real_name,phone,entry_status,job_name,company_name,company_id,store_id,store_name,entry_time,leave_time,tenant_code,remrak)
	select user_id,null record_id,real_name,phone,400 entry_status, null job_name , depend_company_name company_name, depend_company_code company_id, null store_id, null store_name,registration_time entry_time,null leave_time, tenant_code , null remrak from bu_user_info where is_entry='P5'; 
    

