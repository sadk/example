
drop table  IF EXISTS  bu_contract_template ;
CREATE TABLE `bu_contract_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(50) NOT NULL comment '工厂ID',
  
  `name` varchar(255) NOT NULL COMMENT '模板名',
  `code` varchar(255) NOT NULL COMMENT '模板编码',
  `enable` int(2) DEFAULT 1 comment '是否启用: 1=启用  0=禁用',
  
  `content` text NOT NULL COMMENT '模板内容',
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同模板管理';


drop table  IF EXISTS  bu_contract_template_variable ;
CREATE TABLE `bu_contract_template_variable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL COMMENT '模板ID',

  `name` varchar(200) DEFAULT NULL COMMENT '变量名称:如合同年限、岗位类别、工价等',
  `code` varchar(200) DEFAULT NULL COMMENT '变量编码:编程使用码，需唯一',
  `value` varchar(200)  NULL COMMENT '变量值',
  `type` int(4) DEFAULT 1 comment '变量类型: 1=固定值 ',

  `enable` int(2) DEFAULT 1 comment '是否启用: 1=启用  0=禁用',
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同模板变量';




drop table  IF EXISTS  bu_user_contract_info ;
CREATE TABLE `bu_user_contract_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` varchar(50) NOT NULL comment '合同编号',
  `status` varchar(2) default '1' comment '合同状态:1=有效 、 0=无效',
  
  `title` varchar(200) NOT NULL comment '合同(模板)名称',
  
  `template_id` bigint(20) NOT NULL comment '合同模板ID',
  `user_name` varchar(256) NOT NULL COMMENT '用户名',
  `user_id` varchar(50) NOT NULL COMMENT '用户Id',
  
  `url` varchar(2000) NULL COMMENT '模板实体内容的url',
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户(入职的)合同信息';



drop table  IF EXISTS  bu_contract_variable_value ;
CREATE TABLE `bu_contract_variable_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL COMMENT '合同ID',
  `user_id` varchar(50) NOT NULL COMMENT '用户ID',
  
  `name` varchar(200) DEFAULT NULL COMMENT '变量名称:如合同年限、岗位类别、工价等',
  `code` varchar(200) DEFAULT NULL COMMENT '变量编码:编程使用码，需唯一',
  `value` varchar(200) NOT NULL COMMENT '变量值',
  
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户(生成)合同变量值';



drop table  IF EXISTS  bu_job_category ;
CREATE TABLE `bu_job_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父节点',
  `code` varchar(100) DEFAULT NULL,
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `name` varchar(255) NOT NULL COMMENT '类型名称',
  `node_path` varchar(255) DEFAULT NULL COMMENT '树路径',
  `app_code` varchar(50) DEFAULT NULL,
  `tenant_code` varchar(50) DEFAULT NULL,
  
  `remark` varchar(256) DEFAULT NULL,
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_cate_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='岗位分类信息表';


update sys_dictionary set value=530  where code= 'rst_jianli_tech_entry_status_new_yrz';


drop table  IF EXISTS  bu_job_category_attr ;
CREATE TABLE `bu_job_category_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  
  `name` varchar(200) NOT NULL COMMENT '属性名称',
  `value` varchar(200) DEFAULT NULL COMMENT '属性值',
  `code` varchar(200) NOT NULL COMMENT '编码',

  `enable` varchar(2) DEFAULT NULL COMMENT '是否启用: 1=启用  0=禁用',
  `tenant_code` varchar(50) DEFAULT NULL,
  
  `app_code` varchar(20) DEFAULT NULL,
  `sn` int(4) DEFAULT '0' COMMENT '排序',
  `node_path` varchar(1000) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='岗位分类属性名称';


ALTER TABLE `bu_base_job_info` ADD COLUMN `category_id` BIGINT(20) NULL ;
ALTER TABLE `bu_base_job_info` ADD COLUMN `enable` INT(2) NULL ;

update bu_base_job_info set `enable` = 1;

ALTER TABLE `bu_job_category_attr` ADD COLUMN `category_id` BIGINT(20) NULL ;

update bu_company_info set entry_sign_setting=1;

/*
ALTER TABLE `bu_personal_video_info` 
CHANGE COLUMN `status` `status` VARCHAR(255) NULL DEFAULT NULL COMMENT '上架状态： 审核结果：1=上架  0=下架' ,
ADD COLUMN `check_status` INT(2) NULL COMMENT '审核结果：1=审核通过  2=审核不通过 3=审核中' ;


update bu_personal_video_info set check_status=1 where status =1;
update bu_personal_video_info set check_status=0 where status =0;
*/