-- by mm 注意: 核消日期有引用 “合同维度”表的 核消日期,暂未处理.

select 	A.PUTOUTNO , -- 合同号
				'公式' as HXRQ, -- 核消日期
				A.JZRQ, -- 记账日期
				A.TJY,  -- 统计月
				A.KHQD, -- 客户渠道
				A.YWMS, -- 业务模式
				A.CPZLX,-- 产品子类型
				B.APPLY_PRODUCT_CODE as CPMC, -- 产品名称
        B.city_name as SF,		-- 省
				A.CS,		-- 市
				B.CITY_CODE as CSBM,	-- 城市编码
				A.PAYDATE, -- 应还款月
				A.SEQID, -- 期次
				A.SFQXFQQC, -- 是否取消分期期次
				A.HKLX,  -- 还款类型
				A.DDF, -- 代垫方
				A.ZCSSF, -- 资产所属方
				A.BZF, -- 保证方
				A.BDBF, -- 被担保方
				A.DBFS, -- 担保方式
				A.YHBJ, -- 应还本金
				A.YHLX, -- 应还利息
				A.YHZHGLF, -- 应还账户管理费
				A.YHZZFWF, -- 应还增值服务费
				A.YHTQHKSXF, -- 应还提前还款手续费
				A.YHZNJ, -- 应还滞纳金
				A.YHCSF, -- 应还催收费
				A.YHTQCSF, -- 应还提前催收费
				A.YHYHS , -- 应还印花税
				(A.YHBJ + A.YHLX + A.YHZHGLF + A.YHZZFWF + A.YHTQHKSXF + A.YHZNJ + A.YHCSF + A.YHTQCSF + A.YHYHS ) HJ -- 合计
from (
SELECT MAS.PUTOUTNO PUTOUTNO ,-- AS 合同号,
       '公式' HXRQ, --AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY ,-- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX,-- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF , -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID , -- AS 期次,
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN
          '是'
         ELSE
          '否'
       END) SFQXFQQC, -- 是否取消分期期次,
       
       (CASE
         WHEN NVL(MAS.A9, 0) > 0 THEN
          '提前结清'
         ELSE
          '一般还款'
       END) HKLX, -- 还款类型,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
        (CASE when MAS.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方, 
       '中信信托' BDBF , -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       
       NVL(MAS.PAYPRINCIPALAMT, 0) YHBJ, -- AS 应还本金,
       NVL(MAS.PAYINTEAMT, 0) YHLX, -- AS 应还利息,
       NVL(MAS.A7, 0) YHZHGLF, -- AS 应还账户管理费,
       NVL(MAS.A12, 0) YHZZFWF,-- AS 应还增值服务费,
       NVL(MAS.A9, 0) YHTQHKSXF,-- AS 应还提前还款手续费,
       NVL(MAS.A10, 0) YHZNJ, -- AS 应还滞纳金,
       NVL(MAS.A17, 0) YHCSF,-- AS 应还催收费,
       NVL(MAS.A19, 0) YHTQCSF, -- AS 应还提前催收费,
       NVL(MAS.A11, 0) YHYHS, -- AS 应还印花税,
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN AL
 WHERE AL.SERIALNO = MAS.LOANSERIALNO
   -- AND MAS.PAYDATE <= '2018/10/31'
   -- AND MAS.PAYDATE >= '2018/10/01'
   
   <#if PAYDATEBegin?? && PAYDATEEnd??> 
		AND MAS.PAYDATE >= '${StringUtil.escapeSql(PAYDATEBegin)}'
		AND MAS.PAYDATE <= '${StringUtil.escapeSql(PAYDATEEnd)}'
   </#if>
 
   
UNION ALL
SELECT MAS.PUTOUTNO ,-- AS 合同号,
       '公式' HXRQ, -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ,-- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD,-- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF , -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID , -- AS 期次,
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN
          '是'
         ELSE
          '否'
       END) SFQXFQQC, -- 是否取消分期期次,
       (CASE
         WHEN NVL(MAS.A9, 0) > 0 THEN
          '提前结清'
         ELSE
          '一般还款'
       END) HKLX, -- 还款类型,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
        (CASE when MAS.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方, 
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
   -- AND MAS.PAYDATE >= '2018/10/01'
   
  <#if PAYDATEBegin?? && PAYDATEEnd??> 
		AND MAS.PAYDATE >= '${StringUtil.escapeSql(PAYDATEBegin)}'
		AND MAS.PAYDATE <= '${StringUtil.escapeSql(PAYDATEEnd)}'
  </#if>
  
  AND NOT EXISTS (SELECT 1
          FROM DUAL
         WHERE AL.LOANSTATUS = '3'
           AND AL.SETTLEDATE <= '2018/06/30')
UNION ALL
SELECT MAS.PUTOUTNO ,-- AS 合同号,
       '公式' HXRQ, -- AS 核销日期,
       to_char(sysdate,'yyyy-MM-dd') JZRQ,-- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY,-- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       '' CS, -- AS 市,
       '公式' CSBM, -- AS 城市编码,
       MAS.PAYDATE , -- AS 应还款月,
       MAS.SEQID , -- AS 期次,
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN
          '是'
         ELSE
          '否'
       END) SFQXFQQC, -- 是否取消分期期次,
       
       (CASE
         WHEN NVL(MAS.A9, 0) > 0 THEN
          '提前结清'
         ELSE
          '一般还款'
       END) HKLX, --还款类型,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
        (CASE when MAS.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方, 
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       
       NVL(MAS.PAYPRINCIPALAMT, 0) YHBJ, -- AS 应还本金,
       NVL(MAS.PAYINTEAMT, 0) YHLX, -- AS 应还利息,
       NVL(MAS.A7, 0) YHZHGLF, -- AS 应还账户管理费,
       NVL(MAS.A12, 0) YHZZFWF, -- AS 应还增值服务费,
       NVL(MAS.A9, 0) YHTQHKSXF, --  AS 应还提前还款手续费,
       NVL(MAS.A10, 0) YHZNJ, -- AS 应还滞纳金,
       NVL(MAS.A17, 0) YHCSF, -- AS 应还催收费,
       NVL(MAS.A19, 0) YHTQCSF, -- AS 应还提前催收费,
       NVL(MAS.A11, 0) YHYHS, -- AS 应还印花税,
       '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS MAS, ACCT_LOAN AL
 WHERE AL.PUTOUTNO = MAS.PUTOUTNO
   -- AND MAS.PAYDATE > '2018/10/31'
   
  <#if PAYDATEBegin?? && PAYDATEEnd??> 
		AND MAS.PAYDATE > '${StringUtil.escapeSql(PAYDATEEnd)}'
  </#if>   
   
   AND AL.LOANSTATUS = '3'
) A 

left join 

(
select 
		t.SERIALNO ,-- as 合同号,
		to_char(sysdate,'yyyy-MM-dd') JZRQ , -- 记账期间,
		to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJRQ ,-- as 统计日期,
		t.putoutdate , -- as 合同注册日期 ,
		'PC' QD, -- as 客户渠道,
		'单一信托' YWMS, --as 业务模式,
		a.SALES_POINT_NAME , -- as 商户, 
		'商户服务分期' CPZLX, -- 产品子类型,
		a.APPLY_PRODUCT_CODE , -- as 产品名称,
		a.city_name , -- as 省,
		null city  , -- 市
		a.city_code , -- 城市编码,
		'中信信托' zjf , -- as 资金方,
		t.businesssum  -- as 贷款本金

  from BUSINESS_CONTRACT t, crs_bla_applicant_info a
 where t.serialno = a.contract_no and t.contractstatus != '100'
   and a.contract_no is not null
) B  
on A.PUTOUTNO = B.SERIALNO

