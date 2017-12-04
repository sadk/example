-- 第一个例子
CREATE TABLE test(
  id INT(11) NULL
) ;


DROP PROCEDURE IF EXISTS sp ;
CREATE PROCEDURE sp()  SELECT * FROM sys_dictionary ;
CALL sp();
 
 
-- 第二个例子：返回多个结果集
-- 以下语句直接持拷贝，$$符号很重要
DROP PROCEDURE IF EXISTS new_procedure2 ;
DELIMITER $$
 
CREATE PROCEDURE `new_procedure2` ()
BEGIN
select * from sys_dictionary limit 2;  
select * from uum_user limit 2; 
select * from sys_table limit 2;
END $$


-- 第三个例子：返回结果集和输出参数
DROP PROCEDURE IF EXISTS new_procedure4 ;
DELIMITER $$
create procedure new_procedure4(in uid int,inout msg varchar(20),out msg2 varchar(20)) 
begin
   select * from sys_dictionary where id=uid;
   select * from uum_user limit 2;
   set msg='helloxxxxxxxx';
   set msg2='hello222222';
   end $$

call new_procedure4(1,@msg,@msg2);
select @msg;
select @msg2;