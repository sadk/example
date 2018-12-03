select  

	   A.PUTOUTNO , -- AS 合同号,
       A.HXRQ , --  AS 核销日期,
       A.JZRQ , -- AS 记账日期, --当天做报表的时间
       A.TJY, -- AS 统计月, --统计该月报表的时间
       A.KHQD, -- AS 客户渠道,
       A.YWMS, -- AS 业务模式,
       A.CPZLX, -- AS 产品子类型,
       B.APPLY_PRODUCT_CODE CPMC, 		-- 产品名称  
       B.city_name as SF,		 		-- 省
       A.CS, -- AS 市,
       B.CITY_CODE as CSBM, 		-- 城市编码
       
       A.PAYDATE , -- AS 应还款月,
       A.SEQID , --  AS 期次,
       
       A.SFQXFQQC, -- 是否取消分期期次,
       
       A.HKLX, -- 还款类型,

       A.DDF, -- AS 代垫方,
       A.ZCSSF, -- AS 资产所属方,
       A.BZF, -- AS 保证方,
       A.BDFF, -- AS 被担保方,
       A.DBFS, -- AS 担保方式,
       
       A.YHBJ, -- AS 应还本金,
       A.YHLX, -- AS 应还利息,
       A.YHZHGLF, -- AS 应还账户管理费,
       A.YHZZFWF, -- AS 应还增值服务费,
       A.YHTQHKSXF, -- AS 应还提前还款手续费,
       A.YHZNJ, -- AS 应还滞纳金,
       A.YHCSF, -- AS 应还催收费,
       A.YHTQCSF, -- AS 应还提前催收费,
       A.YHYHS, -- AS 应还印花税,
       (A.YHBJ+A.YHLX+A.YHZHGLF+A.YHZZFWF+A.YHTQHKSXF+A.YHZNJ+A.YHCSF+A.YHTQCSF+A.YHYHS) HJ -- 合计

from 
(
SELECT 
			 MAS.PUTOUTNO , -- AS 合同号,
       '公式' HXRQ , --  AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ , -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM , -- AS 城市编码,
       
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID , --  AS 期次,
       
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN  '是' ELSE  '否'  END) SFQXFQQC, -- 是否取消分期期次,
       
       (CASE WHEN NVL(MAS.A9, 0) > 0 THEN  '提前结清'  ELSE  '一般还款' END) HKLX, -- 还款类型,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
       -- '公式' BZF, -- AS 保证方,
        (CASE when MAS.PAYDATE>= '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDFF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       
       NVL(MAS.PAYPRINCIPALAMT, 0) YHBJ, -- AS 应还本金,
       NVL(MAS.PAYINTEAMT, 0) YHLX, -- AS 应还利息,
       NVL(MAS.A7, 0) YHZHGLF, -- AS 应还账户管理费,
       NVL(MAS.A12, 0) YHZZFWF, -- AS 应还增值服务费,
       NVL(MAS.A9, 0) YHTQHKSXF, -- AS 应还提前还款手续费,
       NVL(MAS.A10, 0) YHZNJ, -- AS 应还滞纳金,
       NVL(MAS.A17, 0) YHCSF, -- AS 应还催收费,
       NVL(MAS.A19, 0) YHTQCSF, -- AS 应还提前催收费,
       NVL(MAS.A11, 0) YHYHS, -- AS 应还印花税,
       '公式' HJ -- 合计

  FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN AL
 WHERE AL.SERIALNO = MAS.LOANSERIALNO
   
 -- AND MAS.PAYDATE <= '2018/10/31'
   <#if PAYDATE??> 
  		AND MAS.PAYDATE <= '${StringUtil.escapeSql(PAYDATE)}'
   </#if>
 
UNION ALL
SELECT 
				MAS.PUTOUTNO ,-- AS 合同号,
       '公式' HXRQ, -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID , --  AS 期次,
       
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN  '是' ELSE  '否'  END) SFQXFQQC, -- 是否取消分期期次,
       
       (CASE  WHEN NVL(MAS.A9, 0) > 0 THEN '提前结清' ELSE  '一般还款'  END) HKLX, -- 还款类型,
       
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
       -- '公式' BZF, -- AS 保证方,
        (CASE when MAS.PAYDATE>= '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       
       NVL(MAS.PAYPRINCIPALAMT, 0) YHBJ, -- AS 应还本金,
       NVL(MAS.PAYINTEAMT, 0) YHLX, -- AS 应还利息,
       NVL(MAS.A7, 0) YHZHGLF, -- AS 应还账户管理费,
       NVL(MAS.A12, 0) YHZZFWF, -- AS 应还增值服务费,
       NVL(MAS.A9, 0) YHTQHKSXF, -- AS 应还提前还款手续费,
       NVL(MAS.A10, 0) YHZNJ, -- AS 应还滞纳金,
       NVL(MAS.A17, 0) YHCSF, -- AS 应还催收费,
       NVL(MAS.A19, 0) YHTQCSF, -- AS 应还提前催收费,
       NVL(MAS.A11, 0) YHYHS, -- AS 应还印花税,
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS MAS, ACCT_LOAN AL
 WHERE AL.PUTOUTNO = MAS.PUTOUTNO
   -- AND MAS.PAYDATE <= '2018/10/31'
   <#if PAYDATE??> 
  		AND MAS.PAYDATE <= '${StringUtil.escapeSql(PAYDATE)}'
   </#if>
   
UNION ALL
SELECT MAS.PUTOUTNO , -- AS 合同号,
       '公式' HXRQ , -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       ''  CS, -- 市,
       '公式' CSBM, -- AS 城市编码,
       
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID, -- AS 期次,
       
      (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN
          '是'  ELSE '否'  END) SFQXFQQC, -- 是否取消分期期次,
 
       (CASE WHEN NVL(MAS.A9, 0) > 0 THEN  '提前结清'  ELSE  '一般还款'  END) HKLX, -- 还款类型,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
       -- '公式' BZF, -- AS 保证方,
        (CASE when MAS.PAYDATE>= '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       
       NVL(MAS.PAYPRINCIPALAMT, 0) YHBJ, -- AS 应还本金,
       NVL(MAS.PAYINTEAMT, 0) YHLX, -- AS 应还利息,
       NVL(MAS.A7, 0) YHZHGLF, -- AS 应还账户管理费,
       NVL(MAS.A12, 0) YHZZFWF, -- AS 应还增值服务费,
       NVL(MAS.A9, 0) YHTQHKSXF, -- AS 应还提前还款手续费,
       NVL(MAS.A10, 0) YHZNJ, -- AS 应还滞纳金,
       NVL(MAS.A17, 0) YHCSF, -- AS 应还催收费,
       NVL(MAS.A19, 0) YHTQCSF, -- AS 应还提前催收费,
       NVL(MAS.A11, 0) YHYHS, -- AS 应还印花税,
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS MAS, ACCT_LOAN AL
 WHERE AL.SERIALNO = MAS.LOANSERIALNO
   -- AND MAS.PAYDATE > '2018/10/31'
   
   <#if PAYDATE??> 
  		AND MAS.PAYDATE > '${StringUtil.escapeSql(PAYDATE)}'
   </#if>
   
   AND NVL(MAS.TOTALACTUALBALANCE, 0) > 0
   AND AL.LOANSTATUS = '3'
) A left join 
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
) B on A.PUTOUTNO = B.SERIALNO 
