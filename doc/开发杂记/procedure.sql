                                                   /********************* 创建表 *****************************/
                                                    delimiter //
                                                    
                                                    DROP TABLE if exists test //
                                                    
                                                    CREATE TABLE test(
                                                      id int(11) NULL
                                                    ) //
                                                    
                                                    /********************** 最简单的一个存储过程 **********************/
                                                    drop procedure if exists sp//
                                                     CREATE PROCEDURE sp() select 1 //
                                                     
                                                     call sp()//
                                                     
                                                    /********************* 带输入参数的存储过程  *******************/
                                                    
                                                    drop procedure if exists sp1 //
                                                    
                                                    create procedure sp1(in p int)
                                                    comment 'insert into a int value'
                                                    begin
                                                      /* 定义一个整形变量 */
                                                      declare v1 int;
                                                      
                                                      /* 将输入参数的值赋给变量 */
                                                      set v1 = p;
                                                      
                                                      /* 执行插入操作 */
                                                      insert into test(id) values(v1);
                                                    end
                                                    //
                                                    
                                                    /* 调用这个存储过程  */
                                                    call sp1(1)//
                                                    
                                                    /* 去数据库查看调用之后的结果 */
                                                    select * from test//
                                                    
                                                     /****************** 带输出参数的存储过程 ************************/
                                                    
                                                    drop procedure if exists sp2 //
                                                    create procedure sp2(out p int)
                                                    /*这里的DETERMINISTIC子句表示输入和输出的值都是确定的,不会再改变.我一同事说目前mysql并没有实现该功能,因此加不加都是NOT DETERMINISTIC的*/
                                                    DETERMINISTIC
                                                    begin
                                                      select max(id) into p from test;
                                                    end
                                                    //
                                                    
                                                    /* 调用该存储过程，注意：输出参数必须是一个带@符号的变量 */
                                                    call sp2(@pv)//
                                                    
                                                    /* 查询刚刚在存储过程中使用到的变量 */
                                                    select @pv//                                                    
                                                    
                                                    /******************** 带输入和输出参数的存储过程 ***********************/
                                                    
                                                    drop procedure if exists sp3 //
                                                    create procedure sp3(in p1 int , out p2 int)
                                                    begin
                                                    
                                                      if p1 = 1 then
                                                        /* 用@符号加变量名的方式定义一个变量，与declare类似 */
                                                        set @v = 10;
                                                      else
                                                        set @v = 20;
                                                      end if;
                                                      
                                                      /* 语句体内可以执行多条sql，但必须以分号分隔 */
                                                      insert into test(id) values(@v);
                                                      select max(id) into p2 from test;
                                                      
                                                    end
                                                    //
                                                    
                                                    /* 调用该存储过程，注意：输入参数是一个值，而输出参数则必须是一个带@符号的变量 */
                                                    call sp3(1,@ret)//
                                                    
                                                    select @ret//
                                                    
                                                    /***************** 既做输入又做输出参数的存储过程 ***************************************/
                                                    
                                                    drop procedure if exists sp4 //
                                                    create procedure sp4(inout p4 int)
                                                    begin
                                                       if p4 = 4 then
                                                          set @pg = 400;
                                                       else
                                                          set @pg = 500;
                                                       end if; 
                                                       
                                                       select @pg;
                                                       
                                                    end//
                                                    
                                                    call sp4(@pp)//
                                                    
                                                    /* 这里需要先设置一个已赋值的变量，然后再作为参数传入 */
                                                    set @pp = 4//
                                                    call sp4(@pp)//
                                                    
                                                    
                                                    /********************************************************/