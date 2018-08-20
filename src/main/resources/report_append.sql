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



 CREATE TABLE `rpt_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL comment '报表分类ID',  
  `datasource_id` bigint(20) NOT NULL comment '报表所属的数据源',
  
  `definition_name` varchar(255) NOT NULL COMMENT '定义全称',
  `definition_short_name` varchar(255) DEFAULT NULL COMMENT '定义简称',
  `code` varchar(255) NOT NULL COMMENT '定义编码',

  `type` varchar(4) NOT NULL COMMENT '报表内容类型： 1=SQL 2=Table 3=View 4=http_json 5=存储过程',
  `url` varchar(2000) NULL COMMENT '报表url',

  `db_type` varchar(2) NOT NULL COMMENT '报表数据库类型： 1=MySQL 2=oracle 3=sqlserver 4=PostgreSQL',
    
  `app_code` varchar(20) DEFAULT NULL COMMENT '租户编码',
  `sn` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_def_key` (`definition_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表定义';


CREATE TABLE `rpt_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `definition_id` bigint(20) NOT NULL COMMENT '当前列所属的报表',
  `code` varchar(255) NOT NULL COMMENT '列字段sql编码',
  `name` varchar(255) NOT NULL COMMENT '列名中文',
  `comment` varchar(500) DEFAULT NULL COMMENT '列注释',
  `db_type` varchar(255) NOT NULL COMMENT 'DB字段类型',
  `java_type` int(4) NOT NULL COMMENT 'JAVA类型',
  `property_name` varchar(255) NOT NULL COMMENT 'JAVA属性名',
  `primary_key` int(4) NOT NULL COMMENT '是否是主键：1=是，0=否',
  `oro_column_type` int(4) DEFAULT NULL COMMENT 'ORMapping映射的字段类型：gid(全局唯一码)=1 updateTime(更新时间)=2 createTime(创建时间)=3',
  `search_type` int(2) NOT NULL DEFAULT '0' COMMENT '当前列是否作为查询条件: 0=否，1=是',
  `column_codegen_type` varchar(255) NOT NULL COMMENT '字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键    4=文本框 5=整型框  6=精度型框 7=日期 8=文件  9=下拉框(常量JSON)',
  `column_codegen_format` varchar(255) DEFAULT NULL COMMENT '默认：double型的为两个小数点， date 为 [yyyy-MM-dd HH:mm:ss] ',
  `column_codegen_group_code` varchar(100) DEFAULT NULL COMMENT '字段组:用于生成html的fieldset框',
  `selector_multil_select` varchar(100) DEFAULT NULL COMMENT '选择器，是单选还是多选',
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
  `width` varchar(2) DEFAULT NULL COMMENT '列宽: ',
  `height` varchar(2) DEFAULT NULL COMMENT '列高 ',
  `hidde` varchar(2) DEFAULT NULL COMMENT '是否显示: 0=隐藏 1=显示',
  
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





