select 

	   A.PUTOUTNO , -- 合同号,
       A.HXRQ, -- AS 核销日期,
       A.JZRQ, -- AS 记账日期, --当天做报表的时间
       A.TJY, -- AS 统计月, --统计该月报表的时间
       A.KHQD, -- 客户渠道,
       A.YWMS , -- AS 业务模式,
       A.CPZLX , 		-- 产品子类型 
       B.APPLY_PRODUCT_CODE CPMC, 		-- 产品名称  
       B.city_name as SF,		 		-- 省
       A.CS, -- AS 市,
       B.CITY_CODE as CSBM, 		-- 城市编码
       A.HKLX, -- 还款类型,
       A.SFQXFQ, -- 是否取消分期,
       A.SSJFL, -- AS 十四级分类,
       A.DDF, -- AS 代垫方,
       A.ZCSSF, -- AS 资产所属方,
       A.BZF, -- AS 保证方,
       A.BDBF, -- AS 被担保方,
       A.DBFS, -- AS 担保方式,
       A.SFDQDQ, -- AS 是否当期到期,
       A.PAYDATE , -- 实还月,
       A.YQTS, -- 逾期天数,

       A.SHBJ, -- AS 实还本金,
       A.SHLX, --  AS 实还利息,
       A.SHCWGLF, -- AS 实还财务管理费,
       A.SHZZFWF, -- AS 实还增值服务费,
       A.SHTQHKSXF, -- AS 实还提前还款手续费,
       A.SHZNJ, -- AS 实还滞纳金,
       A.SHYHS, -- AS 实还印花税,
       A.SHCSF, -- AS 实还催收费,
       A.SHTQCSF, -- AS 实还提前催收费,
       (A.SHBJ + A.SHLX+A.SHCWGLF+A.SHZZFWF+A.SHTQHKSXF+A.SHZNJ+A.SHYHS+A.SHCSF+A.SHTQCSF) HJ -- 合计


from 
(
SELECT 
			 T.PUTOUTNO , -- 合同号,
       '公式' HXRQ, -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' AS KHQD, -- 客户渠道,
       '单一信托' YWMS , -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       ''  CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       (CASE  WHEN NVL(T.PA9, 0) > 0 THEN  '提前结清' ELSE  '一般还款'  END) HKLX, -- 还款类型,
       (CASE WHEN T.SEQID = (SELECT MAX(MAS.SEQID)
                           FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN T2
                          WHERE T2.PUTOUTNO = MAS.PUTOUTNO
                            AND T.PUTOUTNO = MAS.PUTOUTNO
                            AND T2.CANCELTYPE IS NOT NULL) THEN  '是'  ELSE  '否' END) SFQXFQ, -- 是否取消分期,
       '公式' SSJFL, -- AS 十四级分类,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
       -- '公式' BZF, -- AS 保证方,
       (CASE when T.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
      -- '公式' SFDQDQ, -- AS 是否当期到期,
       (CASE when substr(REPLACE(T.PAYDATE,'/',''),0,6) = to_char(ADD_MONTHS(sysdate,-1),'yyyyMM') THEN '是' else '否' END) SFDQDQ, -- AS 是否当期到期,
       T.PAYDATE , -- 实还月,
       nvl(al.CPDDAYS, 0) YQTS, -- 逾期天数,
       NVL(T.ACTUALPAYPRINCIPALAMT, 0) SHBJ, -- AS 实还本金,
       NVL(T.ACTUALPAYINTEAMT, 0) SHLX, --  AS 实还利息,
       NVL(T.PA7, 0) SHCWGLF, -- AS 实还财务管理费,
       NVL(T.PA12, 0) SHZZFWF, -- AS 实还增值服务费,
       NVL(T.PA9, 0) SHTQHKSXF, -- AS 实还提前还款手续费,
       NVL(T.PA10, 0) SHZNJ, -- AS 实还滞纳金,
       NVL(T.PA11, 0) SHYHS, -- AS 实还印花税,
       NVL(T.PA17, 0) SHCSF, -- AS 实还催收费,
       NVL(T.PA19, 0) SHTQCSF, -- AS 实还提前催收费,
       '公式' HJ -- 合计

  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS T, ACCT_LOAN AL
 WHERE AL.SERIALNO = T.LOANSERIALNO
   
 	--AND T.PAYDATE <= '2018/10/31'
    <#if PAYDATE??> 
    	AND T.PAYDATE <= '${StringUtil.escapeSql(PAYDATE)}'
    </#if>
    
   AND NVL(T.TOTALACTUALBALANCE, 0) > 0
UNION ALL
SELECT 
			 T.PUTOUTNO , -- 合同号,
       '公式' HXRQ, -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, --  AS 产品名称,
       '公式' SF, -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       (CASE  WHEN NVL(T.PA9, 0) > 0 THEN '提前结清'  ELSE  '一般还款' END) HKLX, -- 还款类型,
       (CASE
         WHEN T.SEQID = (SELECT MAX(MAS.SEQID)
                           FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN T2
                          WHERE T2.PUTOUTNO = MAS.PUTOUTNO
                            AND T.PUTOUTNO = MAS.PUTOUTNO
                            AND T2.CANCELTYPE IS NOT NULL) THEN  '是'  ELSE '否'  END) SFQXFQ, -- 是否取消分期,
       '公式' SSJFL, -- AS 十四级分类,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
      -- '公式' BZF, -- AS 保证方,
       (CASE when T.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
      -- '公式' SFDQDQ, -- AS 是否当期到期,
       (CASE when substr(REPLACE(T.PAYDATE,'/',''),0,6) = to_char(ADD_MONTHS(sysdate,-1),'yyyyMM') THEN '是' else '否' END) SFDQDQ, -- AS 是否当期到期,
       T.PAYDATE , -- 实还月,
       nvl(al.CPDDAYS, 0) YQTS, -- 逾期天数,
       NVL(T.ACTUALPAYPRINCIPALAMT, 0) SHBJ, -- AS 实还本金,
       NVL(T.ACTUALPAYINTEAMT, 0) SHLX, -- AS 实还利息,
       NVL(T.PA7, 0) SHCWGLF, -- AS 实还财务管理费,
       NVL(T.PA12, 0) SHZZFWF, --  AS 实还增值服务费,
       NVL(T.PA9, 0) SHTQHKSXF, -- AS 实还提前还款手续费,
       NVL(T.PA10, 0) SHZNJ, -- AS 实还滞纳金,
       NVL(T.PA11, 0) SHYHS, -- AS 实还印花税,
       NVL(T.PA17, 0) SHCSF, -- AS 实还催收费,
       NVL(T.PA19, 0) SHTQCSF, -- AS 实还提前催收费,
       
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN AL
 WHERE AL.SERIALNO = T.LOANSERIALNO
   
 	-- AND T.PAYDATE <= '2018/10/31'
     <#if PAYDATE??> 
    	AND T.PAYDATE <= '${StringUtil.escapeSql(PAYDATE)}'
    </#if>  
 
   AND NVL(T.TOTALACTUALBALANCE, 0) > 0
UNION ALL
SELECT 
			 T.PUTOUTNO , -- 合同号,
       '公式' HXRQ, --  AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, --  AS 产品名称,
       '公式' SF, -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       (CASE  WHEN NVL(T.PA9, 0) > 0 THEN  '提前结清'  ELSE  '一般还款' END) HKLX, -- 还款类型,
       (CASE  WHEN T.SEQID = (SELECT MAX(MAS.SEQID)
                           FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN T2
                          WHERE T2.PUTOUTNO = MAS.PUTOUTNO
                            AND T.PUTOUTNO = MAS.PUTOUTNO
                            AND T2.CANCELTYPE IS NOT NULL) THEN  '是'   ELSE  '否'   END) SFQXFQ, -- 是否取消分期,
       '公式' SSJFL, -- AS 十四级分类,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
      -- '公式' BZF, -- AS 保证方,
       (CASE when T.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       -- '公式' SFDQDQ, -- AS 是否当期到期,
       (CASE when substr(REPLACE(T.PAYDATE,'/',''),0,6) = to_char(ADD_MONTHS(sysdate,-1),'yyyyMM') THEN '是' else '否' END) SFDQDQ, -- AS 是否当期到期,
       T.PAYDATE , -- 实还月,
       nvl(al.CPDDAYS, 0) YQTS, -- 逾期天数,
       NVL(T.ACTUALPAYPRINCIPALAMT, 0)  SHBJ, -- AS 实还本金,
       NVL(T.ACTUALPAYINTEAMT, 0) SHLX, --  AS 实还利息,
       NVL(T.PA7, 0) SHCWGLF, -- AS 实还财务管理费,
       NVL(T.PA12, 0) SHZZFWF, -- AS 实还增值服务费,
       NVL(T.PA9, 0) SHTQHKSXF, -- AS 实还提前还款手续费,
       NVL(T.PA10, 0) SHZNJ, --  AS 实还滞纳金,
       NVL(T.PA11, 0) SHYHS, -- AS 实还印花税,
       NVL(T.PA17, 0) SHCSF, -- AS 实还催收费,
       NVL(T.PA19, 0) SHTQCSF, -- AS 实还提前催收费,
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS T, ACCT_LOAN AL
 WHERE AL.SERIALNO = T.LOANSERIALNO
   
 	-- AND T.PAYDATE > '2018/10/31'
    <#if PAYDATE??> 
    	AND T.PAYDATE > '${StringUtil.escapeSql(PAYDATE)}'
    </#if>
 
   AND NVL(T.TOTALACTUALBALANCE, 0) > 0
   AND AL.LOANSTATUS = '3'
) A LEFT JOIN 
(
select 
		t.SERIALNO ,-- as 合同号,
		to_char(sysdate,'yyyy-MM-dd') JZRQ , -- 记账期间,
		to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJRQ ,-- as 统计日期,
		t.PUTOUTDATE , -- as 合同注册日期 ,
		'PC' QD, -- as 客户渠道,
		'单一信托' YWMS, --as 业务模式,
		a.SALES_POINT_NAME , -- as 商户, 
		'商户服务分期' CPZLX, -- 产品子类型,
		a.APPLY_PRODUCT_CODE , -- as 产品名称,
		a.CITY_NAME , -- as 省,
		null CITY  , -- 市
		a.CITY_CODE , -- 城市编码,
		'中信信托' ZJF , -- as 资金方,
		t.BUSINESSSUM  -- as 贷款本金

  from BUSINESS_CONTRACT t, crs_bla_applicant_info a
 where t.serialno = a.contract_no and t.contractstatus != '100'
   and a.contract_no is not null
) B on A.PUTOUTNO=B.SERIALNO 