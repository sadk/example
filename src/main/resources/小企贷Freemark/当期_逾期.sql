---当期逾期表
select 
	A.PUTOUTNO ,-- as 合同号,
	A.HXRQ,-- as 核销日期,
	A.CPDDAYS ,-- as 逾期天数,
	A.PAYDATE, -- as 应还日,
	      
	A.JZRQ , -- as 记账日期,
	A.TJY, -- 统计月,
	A.KHQD, -- 客户渠道,
	A.YWMS , -- 业务模式,
	A.CPZLX, -- 产品子类型 ,
	B.APPLY_PRODUCT_CODE CPMC, 		-- 产品名称  
	B.city_name as SF,		 		-- 省
	A.CS , -- 市,
	B.CITY_CODE as CSBM, 		-- 城市编码
	A.ZJF, -- 资金方,
	A.SSJFL, -- 十四级分类,
    A.SFQXFQQC, -- 是否为取消分期期次,
	A.DDF, -- 代垫方,
	A.ZCSSF, -- as 资产所属方,
	A.BZF, -- as 保证方,
	A.BDBF, -- as 被担保方,
	A.DBFS, -- as 担保方式,
	A.SFDQDQ, -- as 是否当期到期_全部为是,

	A.YQBJ, -- as 逾期本金,
	A.YQLX, -- as 逾期利息,
    A.YQZHGLF, -- as 逾期账户管理费,
	A.YQZZFWF, -- as 逾期增值服务费,
	A.YQZNJ, -- as 逾期滞纳金,
	(A.YQBJ + A.YQLX + A.YQZHGLF + A.YQZZFWF + A.YQZNJ)  HJ -- as 合计
from (
select 
	mas.PUTOUTNO ,-- as 合同号,
	'' HXRQ,-- as 核销日期,
	al.CPDDAYS  ,-- as 逾期天数,
	mas.PAYDATE , -- as 应还日,
	      
	to_char(sysdate,'yyyy-MM-dd') JZRQ , -- as 记账日期,
	to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- 统计月,
	'PC' KHQD, -- 客户渠道,
	'单一信托' YWMS , -- 业务模式,
	'商户服务分期' CPZLX, -- 产品子类型 ,
	'公式' CPMC, -- 产品名称,
	'公式' SF, -- 省,
	 null CS , -- 市,
	'公式' CSBM, --  城市编码,
	'中信信托' ZJF, -- 资金方,
	'公式' SSJFL, -- 十四级分类,
  (CASE  WHEN mas.seqid = (select max(t.seqid)
			     from acct_payment_schedule_mas t, acct_loan t2
			    where t2.putoutno = t.putoutno
			      and t.putoutno = mas.putoutno
			      and t2.CANCELTYPE is not null) THEN  '是'  ELSE '否'  END) SFQXFQQC, -- 是否为取消分期期次,
	'佰仟' DDF, -- 代垫方,
	'中信信托' ZCSSF, -- as 资产所属方,
  (CASE when mas.paydate> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
	'中信信托' BDBF, -- as 被担保方,
	'差补' DBFS, -- as 担保方式,
	'是' SFDQDQ, -- as 是否当期到期_全部为是,

	sum(nvl(mas.PAYPRINCIPALAMT, 0) - nvl(mas.ACTUALPAYPRINCIPALAMT, 0)) YQBJ, -- as 逾期本金,
	sum(nvl(mas.PAYINTEAMT, 0) - nvl(mas.ACTUALPAYINTEAMT, 0)) YQLX, -- as 逾期利息,
  sum(nvl(mas.A7, 0) - nvl(mas.PA7, 0)) YQZHGLF, -- as 逾期账户管理费,
	sum(nvl(mas.A12, 0) - nvl(mas.PA12, 0)) YQZZFWF, -- as 逾期增值服务费,
	sum(nvl(mas.A10, 0) - nvl(mas.PA10, 0)) YQZNJ, -- as 逾期滞纳金,
	'excel公式' HJ -- as 合计
 
  from acct_loan al, acct_payment_schedule_MAS mas
 where mas.loanserialno = al.serialNo
   and (mas.finishdate = '' or mas.finishdate is null)
   
   -- and mas.PAYDATE >= '2018/10/01'
   -- and mas.PAYDATE <= '2018/10/31'
   
   <#if PAYDATEBegin?? && PAYDATEEnd??> 
   		and mas.PAYDATE >= '${StringUtil.escapeSql(PAYDATEBegin)}'
  		and mas.PAYDATE <= '${StringUtil.escapeSql(PAYDATEEnd)}'
   </#if>
   
   and mas.RESIDUALBALANCE != 0
 group by al.cpddays,
          al.CPDSTARTDATE,
          mas.paydate,
          mas.putoutno,
          '',
          mas.seqid
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
) B on A.PUTOUTNO = B.SERIALNO