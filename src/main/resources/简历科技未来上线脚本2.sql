
drop table  IF EXISTS  bu_contract_template ;
CREATE TABLE `bu_contract_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL comment '工厂ID',
  
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

  `name` varchar(200) DEFAULT NULL COMMENT '变量名称',
  `value` text NOT NULL COMMENT '变量值',
  `type` int(4) DEFAULT 0 comment '变量类型 ',
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同模板管理';




drop table  IF EXISTS  bu_user_contract_info ;
CREATE TABLE `bu_user_contract_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL comment '合同模板ID',
  
  `user_name` varchar(256) NOT NULL COMMENT '用户名',
  `user_id` varchar(50) NOT NULL COMMENT '用户Id',
  
  `content` text NOT NULL COMMENT '模板实体内容',
  
  `sn` int(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `app_code` varchar(40) DEFAULT NULL,
  `tenant_code` varchar(40) DEFAULT NULL,
  
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户(入职)合同信息';
