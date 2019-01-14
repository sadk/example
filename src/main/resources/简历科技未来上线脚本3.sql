ALTER TABLE `bu_job_info` 
ADD COLUMN `head_count` varcar(10) NULL ,
ADD COLUMN `employment_mode` VARCHAR(2) NULL  ;


ALTER TABLE  `bu_job_info` 
CHANGE COLUMN `head_count` `head_count` INT(10) NULL DEFAULT NULL COMMENT '招聘人数' ,
CHANGE COLUMN `employment_mode` `employment_mode` VARCHAR(2) NULL DEFAULT NULL COMMENT '用工方式: 1=正式工、2=派遣工、3=小时工' ;


ALTER TABLE  `ac_year_vedio_vote` 
ADD COLUMN `user_name` VARCHAR(50) NULL  ;


ALTER TABLE `bu_job_info` 
CHANGE COLUMN `head_count` `head_count` VARCHAR(20) NULL DEFAULT NULL COMMENT '招聘人数' ;



ALTER TABLE `bu_job_info` 
ADD COLUMN `min_salary` INT(10) NULL  ,
ADD COLUMN `max_salary` INT(10) NULL ;
