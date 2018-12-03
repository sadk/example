select 
	   A.PUTOUTNO ,--,AS 合同号,
       A.HXRQ, -- AS 核销日期,
       A.CPDDAYS, --  AS 逾期天数,
       A.PAYDATE ,-- AS 到期日,
       A.JZR, -- AS 记账日期, --当天做报表的时间
       A.TJY, -- AS 统计月, --统计该月报表的时间
       A.KHQD, -- AS 客户渠道,
       A.YWMS, -- AS 业务模式,
       A.CPZLX, -- AS 产品子类型,
       B.APPLY_PRODUCT_CODE CPMC, 		-- 产品名称  
       B.city_name as SF,		 		-- 省
       A.CS , -- 市,
       B.CITY_CODE as CSBM, 		-- 城市编码
       A.YQTS, -- AS 逾期天数,
       A.SSJFL, -- AS 十四级分类,
       A.SFQXFQ, -- AS 是否取消分期,
       A.DDF, -- AS 代垫方,
       A.ZCSSF, -- AS 资产所属方,
       A.BZF, -- AS 保证方,
       A.BDBF, -- AS 被担保方,
       A.DBFS, -- AS 担保方式,
       A.SFDQDQ, -- AS  是否当期到期--全部为是 ,
       
       A.YQBJ, -- AS 逾期本金,
       A.YQLX, -- AS 逾期利息,
       A.YQZHGLF, -- AS 逾期账户管理费,
       A.YQZZFWF, -- AS 逾期增值服务费,
       A.YQZNJ, -- AS 逾期滞纳金,
       (A.YQBJ + A.YQLX + A.YQZHGLF + A.YQZZFWF + A.YQZNJ) HJ -- AS 合计

from (
SELECT 
		MAS.PUTOUTNO ,--,AS 合同号,
       '公式' HXRQ, -- AS 核销日期,
       AL.CPDDAYS CPDDAYS, --  AS 逾期天数,
       
       MAS.PAYDATE ,-- AS 到期日,
       
       to_char(sysdate,'yyyy-MM-dd') JZR, -- AS 记账日期, --当天做报表的时间
       to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
       'PC' KHQD, -- AS 客户渠道,
       '单一信托' YWMS, -- AS 业务模式,
       '商户服务分期' CPZLX, -- AS 产品子类型,
       '公式' CPMC, -- AS 产品名称,
       '公式' SF, -- AS 省,
       '' AS CS , -- 市,
       '公式' CSBM, -- AS 城市编码,
       '公式' SSJFL, -- AS 十四级分类,
       (CASE
         WHEN MAS.SEQID = (SELECT MAX(T.SEQID)
                             FROM ACCT_PAYMENT_SCHEDULE_MAS T, ACCT_LOAN T2
                            WHERE T2.PUTOUTNO = T.PUTOUTNO
                              AND T.PUTOUTNO = MAS.PUTOUTNO
                              AND T2.CANCELTYPE IS NOT NULL) THEN  '是'  ELSE  '否'  END) SFQXFQ, -- AS 是否取消分期,
       '佰仟' DDF, -- AS 代垫方,
       '中信信托' ZCSSF, -- AS 资产所属方,
				(CASE when MAS.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
       '中信信托' BDBF, -- AS 被担保方,
       '差补' DBFS, -- AS 担保方式,
       '是' SFDQDQ, -- AS  是否当期到期--全部为是 ,
       
       SUM(NVL(MAS.PAYPRINCIPALAMT, 0) - NVL(MAS.ACTUALPAYPRINCIPALAMT, 0)) YQBJ, -- AS 逾期本金,
       SUM(NVL(MAS.PAYINTEAMT, 0) - NVL(MAS.ACTUALPAYINTEAMT, 0)) YQLX, -- AS 逾期利息,
       SUM(NVL(MAS.A7, 0) - NVL(MAS.PA7, 0)) YQZHGLF, -- AS 逾期账户管理费,
       SUM(NVL(MAS.A12, 0) - NVL(MAS.PA12, 0)) YQZZFWF, -- AS 逾期增值服务费,
       SUM(NVL(MAS.A10, 0) - NVL(MAS.PA10, 0)) YQZNJ, -- AS 逾期滞纳金,
       '公式' HJ -- AS 合计

  FROM ACCT_LOAN AL, ACCT_PAYMENT_SCHEDULE_MAS MAS
 WHERE MAS.LOANSERIALNO = AL.SERIALNO
   AND (MAS.FINISHDATE = '' OR MAS.FINISHDATE IS NULL)
   
   -- AND MAS.PAYDATE <= '2018/10/31'
   <#if PAYDATE??> 
   		AND MAS.PAYDATE <= '${StringUtil.escapeSql(PAYDATE)}'
   </#if>
   
   
   AND MAS.RESIDUALBALANCE != 0
 GROUP BY AL.CPDDAYS,
          AL.CPDSTARTDATE,
          MAS.PAYDATE,
          MAS.PUTOUTNO,
          MAS.SEQID,
          ''
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
