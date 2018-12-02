select 
				A.PUTOUTNO ,-- as 合同号,
				A.JZRQ, -- as 记账日期,
				A.TJY , -- 统计月,
				A.KHQD, -- 客户渠道,
				A.YWMS, -- 业务模式,
				A.DKLX, -- 贷款类型 ,
				A.CPZLX, -- 产品子类型,
				B.APPLY_PRODUCT_CODE CPMC , -- A.CPMC, -- 产品名称,
				B.city_name as SF ,-- A.SF, -- 省,
			  A.CS, -- 市,
				B.CITY_CODE as CSBM, -- A.CSBM, -- 城市编码,
				A.ZJF, -- 资金方,
				A.DDF, -- 代垫方,
				A.ZCSSF, -- 资产所属方,
				A.BZF, -- 保证方,
				A.BDBF, -- 被担保方,
				A.DBFS, -- 担保方式,
				A.SSJFL, -- 十四级分类,
			  A.YQTS, -- as 逾期天数,


       A.WDQBJ, -- as 未到期本金,
       A.WDQLX, -- as 未到期利息,
       (A.WDQBJ + A.WDQLX) HJ -- as 合计

from 
(
select 
				mas.PUTOUTNO ,-- as 合同号,
				mas.PAYDATE, -- 查询条件
				to_char(sysdate,'yyyy-MM-dd') JZRQ, -- as 记账日期,
				to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY , -- 统计月,
				'PC' KHQD, -- 客户渠道,
				'单一信托' YWMS, -- 业务模式,
				'小贷' DKLX, -- 贷款类型 ,
				'商户服务分期' CPZLX, -- 产品子类型,
				'excel公式' CPMC, -- 产品名称,
				'excel公式' SF, -- 省,
			  null CS, -- 市,
				'excel公式' CSBM, -- 城市编码,
				'中信信托' ZJF, -- 资金方,
				'佰仟' DDF, -- 代垫方,
				'中信信托' ZCSSF, -- 资产所属方,
				'佰仟' BZF, -- 保证方,
				'中信信托' BDBF, -- 被担保方,
				'差补' DBFS, -- 担保方式,
				'公式' SSJFL, -- 十四级分类,
			  nvl(al.cpddays, 0) YQTS, -- as 逾期天数,


       nvl(mas.PAYPRINCIPALAMT, 0) WDQBJ, -- as 未到期本金,
       nvl(mas.PAYINTEAMT, 0) WDQLX, -- as 未到期利息,
       nvl(Mas.Totalpaybalance, 0) HJ -- as 合计

  from ACCT_PAYMENT_SCHEDULE_MAS mas, acct_loan al 
where mas.loanserialno = al.serialNo  and mas.PAYDATE > '2018/10/31'

union all
select 
				mas.PUTOUTNO , -- as 合同号,
				mas.PAYDATE, -- 查询条件
				to_char(sysdate,'yyyy-MM-dd') JZRQ, -- as 记账日期,
				to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, --  统计月,
				'PC' KHQD, -- 客户渠道,
				'单一信托' YWMS, -- 业务模式,
				'小贷' DKLX, -- 贷款类型 ,
				'商户服务分期' CPZLX, --  产品子类型,
				'excel公式' CPMC, -- 产品名称,
				'excel公式' SF, -- 省,
			  null CS, -- 市,
				'excel公式' CSBM, -- 城市编码,
				'中信信托' ZJF, -- 资金方,
				'佰仟' DDF, -- 代垫方,
				'中信信托' ZCSSF, -- 资产所属方,
				'佰仟' BZF, -- 保证方,
				'中信信托' BDBF, -- 被担保方,
				'差补' DBFS, -- 担保方式,
				'公式' SSJFL, -- 十四级分类,
				nvl(al.cpddays, 0) YQTS, -- as 逾期天数,



       nvl(mas.PAYPRINCIPALAMT, 0) WDQBJ, -- as 未到期本金,
       nvl(mas.PAYINTEAMT, 0) WDQLX, -- as 未到期利息,
       nvl(Mas.Totalpaybalance, 0) HJ -- as 合计

  from ACCT_PAYMENT_SCHEDULE_MAS_his mas, acct_loan al
 where al.SERIALNO = mas.LOANSERIALNO
 
   -- and mas.PAYDATE > '2018/10/31' -- 一定要是月最后一天
   <#if PAYDATE??> 
   		and mas.PAYDATE > '${StringUtil.escapeSql(PAYDATE)}'  
   </#if>
 
   and al.LOANSTATUS != '3'

) A 
left join 
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
) B 
on A.PUTOUTNO = B.SERIALNO 

