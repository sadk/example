-- 厂家基本信息表
drop table  IF EXISTS T_CT_ORG ;   
create table T_CT_ORG  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `org_id`       varchar(50)  NOT NULL COMMENT '厂家编号',
  `org_name`      varchar(50) NOT NULL COMMENT '厂家名称',
  `org_cdt_code`  varchar(50) NOT NULL COMMENT '厂家社会统一信用代码',
  `remark`       varchar(50)  DEFAULT NULL COMMENT '备注',
  `sn` int(4) DEFAULT 0 ,
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_org_id` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='厂家基本信息表';


-- 厂家生产地
drop table  IF EXISTS T_CT_ORG_PDCADD ;   
create table T_CT_ORG_PDCADD  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   add_id     varchar(50) NOT NULL COMMENT '生产地编号',
   org_id     varchar(50) NOT NULL COMMENT '厂家编号',
   pd_place   varchar(50) NOT NULL COMMENT '生产地址',
   link_man   varchar(50)  NOT NULL COMMENT '联系人',
   telephone  varchar(50)  NOT NULL COMMENT '联系电话',
   
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_add_id` (`add_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='厂家生产地';

-- 厂家销售地
drop table  IF EXISTS T_CT_ORG_SALADD ;   
create table T_CT_ORG_SALADD  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   idx_id     varchar(50) NOT NULL COMMENT '销售地编号',
   org_id     varchar(50) NOT NULL COMMENT '厂家编号',
   sal_add    varchar(50) NOT NULL COMMENT '销售地址',
   link_man   varchar(50)  NOT NULL COMMENT '联系人',
   telephone  varchar(50)  NOT NULL COMMENT '联系电话',
   
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_idx_id` (`idx_id`) USING BTREE
   ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='厂家销售地';







-- ----------------------------------------------- 产品、销售、原材料信息 -----------------------------------

-- 产品生产信息表
drop table  IF EXISTS T_CT_PDC ;   
create table T_CT_PDC  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `org_id`    varchar(50) NOT NULL COMMENT '厂家编号',
  `pid`       varchar(50) NOT NULL COMMENT '追朔编码',
  `pd_place`  varchar(50)  NULL COMMENT '生产地',
  `pd_dt`     datetime  NULL COMMENT '生产日期',
  `pd_spf`    varchar(50)  NULL COMMENT '产品规格',
  `pd_bat_opt` numeric(12,2)  NULL COMMENT '批次生产量',
     
  `pd_type`    varchar(4) NOT NULL COMMENT '类型:1=蜂窝煤、2=块煤、3=其他型煤',
  `pd_sgl_wgt` numeric(12,2)  NULL COMMENT '单袋静重',
   
  `remark`    varchar(500)  NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_pid` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品生产信息';


-- 产品销售信息表
drop table  IF EXISTS T_CT_SAL ;
create table T_CT_SAL  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code`    varchar(50) NOT NULL COMMENT '唯一标识',
  `pid`    varchar(50) NOT NULL COMMENT '追溯编码',
  
  `sal_add`  varchar(200)  NULL COMMENT '销售地点',
  `sal_num`     numeric(12,2)  NULL COMMENT '数量',
  `sal_dt` datetime NULL COMMENT '销售日期',
  
  `link_man`    varchar(50)  NULL COMMENT '联系人',
  `telephone` varchar(20)  NULL COMMENT '联系电话',
   
  `remark`    varchar(500)  NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品销售信息表';


-- 原材料信息表
drop table  IF EXISTS T_CT_RAW ;   
create table T_CT_RAW  (
  `id`      bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code`    varchar(50) NOT NULL COMMENT '唯一标识',
  `pid`     varchar(50) NOT NULL COMMENT '追溯编码',
  
  `idx_id`  varchar(50)  NULL COMMENT '指标编码',
  `raw_add` varchar(200)  NULL COMMENT '来源地',
  `idx_va`  numeric(12,2)   NULL COMMENT '指标值',
  
  `buy_dt`  datetime  NULL COMMENT '进货日期',
  `dt`      datetime  NULL COMMENT '记录日期',
   
  `remark`    varchar(500)  NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='原材料信息表';








-- ------------------------------------ 质量检验 --------------------------------------------------

-- 煤炭质量检验表
drop table  IF EXISTS T_CT_QTEST ;   
create table T_CT_QTEST  (
  `id`      bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code`    varchar(50) NOT NULL COMMENT '唯一标识',
  `pid`     varchar(50) NOT NULL COMMENT '追溯编码',
  
  `idx_id`  varchar(50)  NULL COMMENT '指标编码',
  `qt_imp_sdt` varchar(200)  NULL COMMENT '执行标准',
  `qt_dt`  datetime   NULL COMMENT '检验日期',
  
  `qt_man`   varchar(200)  NULL COMMENT '检验人',
  `qt_cn`    varchar(200)  NULL COMMENT '检验结论',
  `qt_org`   varchar(200)  NULL COMMENT '检验机构',
  
  `ash_val`  varchar(20)  NULL COMMENT '灰分实测值',
  `suf_val`  varchar(20)  NULL COMMENT '全硫实测值',
  `vlt_val`  varchar(20)  NULL COMMENT '挥发分实测值',
  `heat_val` varchar(20)  NULL COMMENT '发热量实测值',
	
  `remark`    varchar(500)  NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='煤炭质量检验表';


-- 煤炭质量检验标准
drop table  IF EXISTS T_CT_QTEST_STD ;   
create table T_CT_QTEST_STD  (
  `id`      bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code`    varchar(50) NOT NULL COMMENT '唯一标识',
  `idx_code` varchar(50) NOT NULL COMMENT '指标编码',
  
  `ash_std`  varchar(20)  NULL COMMENT '灰分标准值',
  `suf_std` varchar(20)  NULL COMMENT '全硫标准值',
  `vlt_std`  varchar(20)   NULL COMMENT '挥发分标准值',
  
  `heat_std`   varchar(200)  NULL COMMENT '发热量标准值',
  `std_version`    varchar(200)  NULL COMMENT '国标版本号',
  `market`    varchar(500)  NULL COMMENT '备注',
  
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='煤炭质量检验标准';


-- 指标表
drop table  IF EXISTS T_CT_IDX ;   
create table T_CT_IDX  (
  `id`      bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code`    varchar(50) NOT NULL COMMENT '唯一标识',
  
  `idx_id` varchar(50) NOT NULL COMMENT '指标编码',
  `idx_pid` varchar(50) DEFAULT '-1'  NULL COMMENT '指标父级编码',
  `index_name`  varchar(50)  NULL COMMENT '指标名称',
  `idx_unit`  varchar(50)  NULL COMMENT '量纲名称',
  `is_use` varchar(4)  NULL COMMENT '是否启用 1=启用 0=未启用',
  `sn` int(4)  NULL COMMENT '序号',
  
  `node_path` text DEFAULT NULL,
  `node_path_text` text DEFAULT NULL,
    
  `remark`    varchar(500)  NULL COMMENT '备注',
  `gid` varchar(40) DEFAULT NULL COMMENT 'gid:全局唯一编号', 
  `create_time` datetime NOT NULL COMMENT 'createTime:创建日期',
  `update_time` datetime NOT NULL COMMENT 'updateTime:更新时间',
   
   UNIQUE KEY `uq_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='指标表';