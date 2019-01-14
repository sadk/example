
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
  `contract_id` varchar(50) NOT NULL COMMENT '合同编码',
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

ALTER TABLE `bu_job_info` ADD COLUMN `sn` INT(4) NULL DEFAULT 0 ;

-- 记得字典添加一个530的，表示已入职，原400状态是 ：已完善个人信息!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
update bu_user_entry_info set entry_status=530 where entry_status=400;









-- --------------------------------------------- 林学飞脚本 -------------------------------------------------------

CREATE TABLE `bu_user_challenge_code` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`user_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID' ,
`identiy_card`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '身份证' ,
`telephone_num`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '手机号' ,
`status`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态' ,
`msg`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' ,
`unique_num`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '' ,
`trans_id`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '' ,
`challenge_code`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '' ,
`extprop`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加字段' ,
`extprop2`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加字段' ,
`extprop3`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加字段' ,
`extprop4`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加字段' ,
`create_time`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间' ,
`tenant_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户编码' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
COMMENT='CA挑战码获取表'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;



INSERT INTO `bu_contract_template` (`id`, `company_id`, `name`, `code`, `enable`, `content`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('3', 'sntPhovq5w39bMjSkS882', '模板1', 'i6dRyZoDQbiGHNgPwRSFT', '1', '<p><strong>&nbsp;</strong></p><p><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong><strong><span style=\"font-size:24px\">&nbsp;&nbsp;</span></strong><strong><span style=\"font-size:24px;font-family:宋体\">劳动服务协议书</span></strong><strong> </strong><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong><strong><span style=\";font-family:宋体\">编号：</span></strong></p><p>&nbsp;</p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">甲方：深圳市永祺人力资源管理有限公司</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">乙方：${userYF}&nbsp;</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px\">&nbsp;&nbsp;&nbsp; </span><span style=\"font-size:16px;font-family:宋体\">甲方聘用乙方为短期的派遣工，经双方平等协商，彼此同意约定下述条款以共同遵守。</span></p><p style=\"line-height:35px\"><strong><span style=\"font-size: 16px;font-family:新宋体\">一、工作岗位和工作（工种）</span></strong></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">&nbsp;&nbsp;&nbsp; </span><span style=\"font-size:16px;font-family:新宋体\">甲方根据协作单位的生产、工作需要，决定聘用乙方并派遣在<span style=\"text-decoration:underline;\">　${workCompany}　</span></span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">（单位）从事<span style=\"text-decoration:underline;\">&nbsp;&nbsp; ${contractJobType}&nbsp; </span>岗位工作。</span></p><p style=\"line-height:35px\"><strong><span style=\"font-size:16px;font-family: 新宋体\">二、劳动合同期限</span></strong></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">（一）合同期限</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">合同期限：</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">&nbsp;</span>　　<span style=\"font-size:16px;font-family: 新宋体\">从 <span style=\"text-decoration:underline;\">&nbsp;&nbsp;&nbsp;&nbsp;${yearBegin}&nbsp;&nbsp; </span>年<span style=\"text-decoration:underline;\">&nbsp; ${monthBegin}&nbsp;&nbsp; </span>月<span style=\"text-decoration:underline;\">&nbsp; ${dayBegin}&nbsp; </span>日起至<span style=\"text-decoration:underline;\">&nbsp;&nbsp; ${yearEnd}&nbsp;&nbsp; </span>年 <span style=\"text-decoration:underline;\">&nbsp;&nbsp;${monthEnd}&nbsp; </span>月 <span style=\"text-decoration:underline;\">&nbsp;&nbsp;${dayEnd}&nbsp; </span>日止，具体</span><span style=\"font-size:16px;font-family:新宋体\">劳动</span><span style=\"font-size:16px;font-family:新宋体\">期限以协作单位的实际用工周期为准。</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">（二）试用期限</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">试用期为<span style=\"text-decoration:underline;\">　 ${workMonth}&nbsp; </span>个月（试用期包括在<a href=\"http://www.86exp.com/hetong/\"><span style=\";color:windowtext\">合同</span></a>期内）。</span></p><p style=\"line-height:35px\"><strong><span style=\"font-size:16px;font-family: 新宋体\">三、工作时间、地点<br/> &nbsp;&nbsp;&nbsp; </span></strong><span style=\"font-size:16px;font-family:宋体\">按所在甲方派遣的协作单位（以下简称协作单位）的工时制度执行；工作地点为：深圳市福田保税区，根据协作单位的需求，乙方必须配合协作单位在本市不同区域的工作调动。</span></p><p style=\"margin-left:0;text-indent:0;line-height:35px\"><strong><span style=\"font-size:16px;font-family:宋体\">四、</span></strong><strong><span style=\"font-size:16px;font-family:宋体\">社会保险及工资待遇</span></strong></p><p style=\"text-indent:32px;line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">1.</span><span style=\"font-size:16px;font-family:宋体\">员工待遇按劳动法计算，亦可按劳动法统一折合成每小时计算，折合成每小时的工资等于或高于劳动法计算标准，经用工单位协商一致，员工工资折合成每小时为 <span style=\"text-decoration:underline;\">&nbsp;${hourlySalary} </span>元（高于劳动法）。</span></p><p style=\"text-indent:32px;line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">2.</span><span style=\"font-size:16px;font-family:新宋体\">甲方每月<span style=\"text-decoration:underline;\">${monthlyPayday} </span>日为发薪日，</span><span style=\"font-size:16px;font-family:宋体\">甲方至少每月以货币或转账形式向乙方支付一次工资。</span></p><p style=\"line-height:35px\">　　<span style=\"font-size:16px;font-family:新宋体\">3</span><span style=\";font-family:新宋体\">．</span><span style=\"font-size:16px;font-family:宋体\">合同期内，甲方为乙方免费缴交五险一金，缴交基数统一为2200元。</span></p><p style=\"line-height:35px\"><strong><span style=\"font-size:16px;font-family:宋体\">五、乙方的管理及工作职责</span></strong></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">1．</span><span style=\"font-size:16px;font-family:宋体\">乙方的考勤与管理悉按甲方及所协作单位有关安排的管理制度办理。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">2．</span><span style=\"font-size:16px;font-family:宋体\">乙方的上班时间根据所安排企业的要求进行，甲方根据合作企业的要求不定期、不定时加减派遣员工。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">3．</span><span style=\"font-size:16px;font-family:宋体\">乙方受聘于甲方期间，应根据甲方工作安排，在下述工作场所履行职责：</span><span style=\"font-size:16px\">&nbsp; </span></p><p style=\"margin-left:28px;line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">（一）仓库、生产车间、流水线等；（二）特殊岗位由甲方统一指定安排；</span> </p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">4．</span><span style=\"font-size:16px;font-family:宋体\">乙方的工作职责、事项由甲方依乙方的职务或工种，并视乙方能力及甲方需要进行分派。</span></p><p style=\"margin-left:0;text-indent:27px;line-height:35px\"><span style=\"font-size:16px\">5．</span><span style=\"font-size:16px;font-family:宋体\">乙方的正常工作时间每日为</span><span style=\"text-decoration:underline;\"><span style=\"font-size:16px\"> ${workHours}&nbsp; </span></span><span style=\"font-size:16px;font-family:宋体\">小时；甲方根据企业工作需要，要求乙方加班时，除不可抗拒的事实外，乙方应予配合，有关加班事宜，依所安排企业要求办理。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">6．</span><span style=\"font-size:16px;font-family:宋体\">甲方对乙方奖励，分为嘉奖、记功、晋级、评为先进生产（工作）者和劳动模范等</span><span style=\"font-size:16px\">5</span><span style=\"font-size:16px;font-family: 宋体\">种。甲方对乙方的惩处，分为警告、记过、降级、辞退、除名等</span><span style=\"font-size:16px\">5</span><span style=\"font-size:16px;font-family:宋体\">种。以上奖励及惩处事由和办法，依员工手册办理的。奖励及惩处记录为甲方考核乙方的依据之一。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">7．</span><span style=\"font-size:16px;font-family:宋体\">乙方有权拒绝违章指挥和强令冒险作业，乙方发现直接危及人身安全的紧急情况时，有权停止作业或者在采取可能的应急措施后撤离作业场所。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">8．</span><span style=\"font-size:16px;font-family:宋体\">乙方在协作单位工作期间，必须遵守企业的规章制度，乙方<span style=\"color:black\">严重违反甲方劳动纪律、规章制度的、严重工作失职，营私舞弊，给甲方造成重大经济损失的；甲方可立即与乙方终止协议，如乙方给协作单位造成损失的，乙方应予以赔偿，违反法律的还应承担法律责任。</span></span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">9．</span><span style=\"font-size:16px;font-family:宋体\">甲方因业务需要或乙方不能胜任甲方工作时，甲方有权终止本协议，并提前</span><span style=\"font-size:16px\">3</span><span style=\"font-size:16px;font-family: 宋体\">日通知乙方，协议终止。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">10．</span><span style=\"font-size:16px;font-family:宋体\">乙方主动提出解除本协议时，须提前</span><span style=\"font-size: 16px\">3</span><span style=\"font-size:16px;font-family:宋体\">日通知甲方，调离时，乙方须按员工手册办理有关手续。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">11．</span><span style=\"font-size:16px;font-family:新宋体\">当派往公司生产发生重大变化或合同到期后，乙方被退回甲方后，甲方免费为乙方安排同待遇就业一次，若乙方不予接受，则视为自动解除合同，甲方不承担任何经济赔偿责任。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">12．</span><span style=\"font-size:16px;font-family:宋体\">甲、乙双方就履行本协议所发生的一切争议，双方共同协商解决。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px;font-family:新宋体\">13．</span><span style=\"font-size:16px;font-family:新宋体\">本协议所有条款均在甲、乙双方都了解接受的前提下签字并发生法律效力，双方不得因此协议中双方协定的条款再发生相关争议。</span></p><p style=\"margin-left:0;text-indent:32px;line-height:35px\"><span style=\"font-size:16px\">14．</span><span style=\"font-size:16px;font-family:宋体\">本协议一式两份，甲、乙双方各一份，深圳市永祺人力资源管理有限公司具有最终解释权。</span></p><p style=\"margin-left:28px;line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">（以下无正文）</span></p><p style=\"margin-left:28px;line-height:35px\"><span style=\"font-size:16px\">&nbsp;</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">甲</span><span style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;&nbsp; </span><span style=\"font-size:16px;font-family:宋体\">方：深圳市永祺人力资源管理有限公司</span><span style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span style=\"font-size:16px;font-family:宋体\">乙</span><span style=\"font-size:16px\">&nbsp;&nbsp;&nbsp; </span><span style=\"font-size:16px;font-family:宋体\">方：</span><span style=\"font-size:16px\">&nbsp; ${userYF}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">授权代表：</span><span style=\"font-size:16px\">&nbsp; ${userJF}&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size:16px;font-family:宋体\">签名：</span></span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">联络方式：</span><span style=\"font-size:16px\">0755-83805878&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:16px;font-family:宋体\">联络方式</span><span style=\"font-size:16px\">: ${phone}</span></p><p style=\"line-height:35px\"><span style=\"font-size:16px;font-family:宋体\">日期：</span><span style=\"font-size:16px\">&nbsp; <span style=\"text-decoration-line: underline; font-family: 新宋体;\">${yearBegin}</span><span style=\"font-family: 新宋体;\">年</span><span style=\"text-decoration-line: underline; font-family: 新宋体;\">${monthBegin}</span><span style=\"font-family: 新宋体;\">月</span><span style=\"text-decoration-line: underline; font-family: 新宋体;\">${dayBegin}</span><span style=\"font-family: 新宋体;\">日</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:16px;font-family:宋体\">日期：<span style=\"text-decoration-line: underline; font-family: 新宋体;\">${yearBegin}</span><span style=\"font-family: 新宋体;\">年</span><span style=\"text-decoration-line: underline; font-family: 新宋体;\">${monthBegin}</span><span style=\"font-family: 新宋体;\">月</span><span style=\"text-decoration-line: underline; font-family: 新宋体;\">${dayBegin}<span style=\"font-family: 新宋体;\">日</span></span></span></p><p style=\"line-height:36px\"><span style=\"font-size:16px\">&nbsp;</span></p><p style=\"line-height:36px\">&nbsp;</p><p><br/></p>', NULL, NULL, NULL, '6000', 'VxYB4isxjfgFVWvaMWGaf', '2019-01-02 15:21:50', '2019-01-03 18:34:44');


INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('2', '3', '从事岗位', 'contractJobType', '从事岗位', '1', '1', '0', NULL, NULL, '6000', '2', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('3', '3', '合同期限起始（年）', 'yearBegin', '合同期限起始（年）', '1', '1', '0', NULL, NULL, '6000', '3', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('4', '3', '合同期限起始（月）', 'monthBegin', '合同期限起始（月）', '1', '1', '0', NULL, NULL, '6000', '4', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('5', '3', '合同期限起始（日）', 'dayBegin', '合同期限起始（日）', '1', '1', '0', NULL, NULL, '6000', '5', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('6', '3', '合同期限结束（年）', 'yearEnd', '合同期限结束（年）', '1', '1', '0', NULL, NULL, '6000', '6', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('7', '3', '合同期限结束（月）', 'monthEnd', '合同期限结束（月）', '1', '1', '0', NULL, NULL, '6000', '7', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('8', '3', '合同期限结束（日）', 'dayEnd', '合同期限结束（日）', '1', '1', '0', NULL, NULL, '6000', '8', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('9', '3', '试用期（N个月）', 'workMonth', '试用期（N个月）', '1', '1', '0', NULL, NULL, '6000', '9', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('10', '3', '乙方签字', 'userYF', '乙方签字', '1', '1', '0', NULL, NULL, '6000', '10', '2018-12-26 20:19:23', '2018-12-26 20:19:23');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('12', '3', '甲方授权代表', 'userJF', '甲方授权代表', '1', '1', '0', NULL, NULL, '6000', '11', '2018-12-28 14:31:19', '2018-12-28 14:31:18');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('14', '3', '工作单位', 'workCompany', '工作单位', '1', '1', '0', NULL, NULL, '6000', '11', '2018-12-28 14:31:19', '2018-12-28 14:31:18');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('15', '3', '联系方式', 'phone', '联系方式', '1', '1', '0', NULL, NULL, '6000', '11', '2019-01-03 10:37:51', '2019-01-03 10:37:49');
INSERT INTO `bu_contract_template_variable` (`id`, `template_id`, `name`, `code`, `value`, `type`, `enable`, `sn`, `remark`, `app_code`, `tenant_code`, `gid`, `create_time`, `update_time`) VALUES ('16', '3', '工价', 'hourlySalary', '工价', '1', '1', '0', NULL, NULL, '6000', 'tSRuJNY2XkxQJtpQ4qVAD', '2019-01-07 17:54:19', '2019-01-07 17:54:19');


alter table bu_contract_template AUTO_INCREMENT = 100;
alter table bu_contract_template_variable AUTO_INCREMENT = 100;





/*
ALTER TABLE `bu_personal_video_info` 
CHANGE COLUMN `status` `status` VARCHAR(255) NULL DEFAULT NULL COMMENT '上架状态： 审核结果：1=上架  0=下架' ,
ADD COLUMN `check_status` INT(2) NULL COMMENT '审核结果：1=审核通过  2=审核不通过 3=审核中' ;


update bu_personal_video_info set check_status=1 where status =1;
update bu_personal_video_info set check_status=0 where status =0;
*/