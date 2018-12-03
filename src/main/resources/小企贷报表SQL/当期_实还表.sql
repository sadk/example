select 
		A.PUTOUTNO , -- 合同号
		A.HXRQ, 		-- 核消日期
    	A.JZRQ, 		-- 记账日期
		A.TJY, 			-- 统计月
		A.KHQD , 		-- 客户渠道
		A.YWMS, 		-- 业务模式
		A.CPZLX, 		-- 产品子类型
		B.APPLY_PRODUCT_CODE CPMC, 		-- 产品名称
		B.city_name as SF,		 		-- 省
		A.CS, 			-- 市
		B.CITY_CODE as CSBM, 		-- 城市编码
		A.SFQXFQQC, -- 是否取消分期期次
		A.HKLX, 		-- 还款类型
		A.SSJFL, 		-- 十四级分类
		A.DDF, 			-- 代垫方
		A.ZCSSF, 		-- 资产所属方
		A.BZF, 			-- 保证方
		A.BDBF, 		-- 被担保方
		A.DBFS, 		-- 担保方式
    	A.SFDQDQ, 	-- 是否当期到期
		A.PAYDATE, 	-- 实还月
		A.YQTS, 		-- 逾期天数
		
		A.SHBJ, 		-- 实还本金
		A.SHLX, 		-- 实还利息
		A.SHCWGLF, 	-- 实还财务管理费
		A.SHZZFWF, 	-- 实还增值服务费
		A.SHTQHKSXF, -- 实还提前还款手续费
		A.SHZNJ, 		-- 实还滞纳金
		A.SHWWCSF, 	-- 实还委外催收费
		A.SHTQWWF, 	-- 实还提前委外费
		A.SHYHS, 		-- 实还印花税
		(A.SHBJ + A.SHLX + A.SHCWGLF + A.SHZZFWF + A.SHTQHKSXF + A.SHZNJ + A.SHWWCSF + A.SHTQWWF + A.SHYHS) as HJ

from (
SELECT DISTINCT T.PUTOUTNO , -- AS 合同号,
ATR.TRANSDATE,
                '公式' HXRQ , -- AS 核销日期,
                to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
                to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
                'PC' KHQD, -- AS 客户渠道,
                '单一信托' YWMS , -- AS 业务模式,
                '商户服务分期' CPZLX, -- AS 产品子类型,
                '公式' CPMC, -- AS 产品名称,
                '公式' SF, -- AS 省,
                '' CS, -- AS 市,
                '公式' CSBM , --  AS 城市编码，
								(CASE WHEN T.SEQID =
                       (SELECT MAX(MAS.SEQID)
                          FROM ACCT_PAYMENT_SCHEDULE_MAS MAS,
                               ACCT_LOAN                 T2
                         WHERE T2.PUTOUTNO = MAS.PUTOUTNO
                           AND T.PUTOUTNO = MAS.PUTOUTNO
                           AND T2.CANCELTYPE IS NOT NULL) THEN
                   '是'
                  ELSE
                   '否'
                END) SFQXFQQC, -- 是否取消分期期次,
                (CASE   WHEN NVL(T.PA9, 0) > 0 THEN  '提前结清'  ELSE  '一般还款' END) HKLX, -- 还款类型,
                '' SSJFL, -- AS 十四级分类,
                '佰仟' DDF, -- AS 代垫方,
                '中信信托' ZCSSF, -- AS 资产所属方,
                 (CASE when T.PAYDATE>= '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
                '中信信托' BDBF, -- AS 被担保方,
                '差补' DBFS, -- AS 担保方式,
                 (CASE when substr(REPLACE(T.PAYDATE,'/',''),0,6) = to_char(ADD_MONTHS(sysdate,-1),'yyyyMM') THEN '是' else '否' END) SFDQDQ, -- AS 是否当期到期,
                T.PAYDATE , -- 实还月,
                NVL(AL.CPDDAYS, 0) YQTS, --逾期天数,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND (SUB_APL.PAYTYPE = '1' OR SUB_APL.PAYTYPE = '5')
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHBJ, -- AS 实还本金,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYINTEAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND (SUB_APL.PAYTYPE = '1' OR SUB_APL.PAYTYPE = '5')
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHLX, -- AS 实还利息,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A7'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHCWGLF, -- AS 实还财务管理费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A12'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHZZFWF, -- AS 实还增值服务费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A9'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHTQHKSXF, -- AS 实还提前还款手续费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A10'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHZNJ, -- AS 实还滞纳金,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A17'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHWWCSF, -- AS 实还委外催收费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A19'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHTQWWF, -- AS 实还提前委外费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A11'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHYHS, -- AS 实还印花税,
                '公式' HJ -- 合计

  FROM ACCT_PAYMENT_SCHEDULE_MAS_HIS T,
       ACCT_TRANSACTION              ATR,
       ACCT_LOAN                     AL,
       ACCT_PAYMENT_LOG              APL
 WHERE AL.SERIALNO = T.LOANSERIALNO
   AND ATR.RELATIVEOBJECTNO = T.LOANSERIALNO
   AND ATR.TRANSDATE >= '2018/11/01'
   AND ATR.TRANSDATE <= '2018/11/31'
   AND ATR.TRANSSTATUS = '1'
   AND ATR.TRANSCODE IN ('0050', '0055')
   AND NVL(T.TOTALACTUALBALANCE, 0) > 0
   AND ATR.SERIALNO = APL.TRANSSERIALNO
   AND APL.PAYDATE = T.PAYDATE
UNION ALL
SELECT DISTINCT T.PUTOUTNO , -- 合同号,
ATR.TRANSDATE,
                '公式' HXRQ, -- AS 核销日期,
                to_char(sysdate,'yyyy-MM-dd') JZRQ, -- AS 记账日期, --当天做报表的时间
                to_char(ADD_MONTHS(SYSDATE, -1),'yyyy-MM') TJY, -- AS 统计月, --统计该月报表的时间
                'PC' KHQD, -- AS 客户渠道,
                '单一信托' YWMS, -- AS 业务模式,
                '商户服务分期' CPZLX, -- AS 产品子类型,
                '公式' CPMC, -- AS 产品名称,
                '公式' SF, -- AS 省,
                ''  CS, -- AS 市,
                '公式' CSBM, -- AS 城市编码，
								(CASE  WHEN T.SEQID =
                       (SELECT MAX(MAS.SEQID)
                          FROM ACCT_PAYMENT_SCHEDULE_MAS MAS, ACCT_LOAN T2
                         WHERE T2.PUTOUTNO = MAS.PUTOUTNO
                           AND T.PUTOUTNO = MAS.PUTOUTNO
                           AND T2.CANCELTYPE IS NOT NULL) THEN
                   '是'
                  ELSE
                   '否'
                END) SFQXFQQC, -- 是否取消分期期次,
                (CASE  WHEN NVL(T.PA9, 0) > 0 THEN  '提前结清'  ELSE  '一般还款'  END) HQLX, -- 还款类型,
                '' SSJFL, -- AS 十四级分类,
                '佰仟' DDF, -- AS 代垫方,
                '中信信托' ZCSSF, -- AS 资产所属方,
                (CASE when T.PAYDATE> '2018/01' THEN '佰仟' else '贵州佰诚' END)  BZF , -- AS 保证方,   
                '中信信托' BDBF, -- AS 被担保方,
                '差补' DBFS, -- AS 担保方式,
               (CASE when substr(REPLACE(T.PAYDATE,'/',''),0,6) = to_char(ADD_MONTHS(sysdate,-1),'yyyyMM') THEN '是' else '否' END) SFDQDQ, -- AS 是否当期到期,
 
                T.PAYDATE , -- 实还月,
                NVL(AL.CPDDAYS, 0) YQTS, -- 逾期天数,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND (SUB_APL.PAYTYPE = '1' OR SUB_APL.PAYTYPE = '5')
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHBJ, -- AS 实还本金,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYINTEAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND (SUB_APL.PAYTYPE = '1' OR SUB_APL.PAYTYPE = '5')
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHLX, -- AS 实还利息,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A7'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHCWGLF, -- AS 实还财务管理费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A12'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHZZFWF, -- AS 实还增值服务费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A9'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHTQHKSXF, -- AS 实还提前还款手续费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A10'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHZRJ, -- AS 实还滞纳金,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A17'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHWWCSF, -- AS 实还委外催收费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A19'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHTQWWF, -- AS 实还提前委外费,
                NVL((SELECT SUM(NVL(SUB_APL.ACTUALPAYPRINCIPALAMT, 0))
                      FROM ACCT_PAYMENT_LOG SUB_APL
                     WHERE APL.TRANSSERIALNO = SUB_APL.TRANSSERIALNO
                       AND SUB_APL.PAYTYPE = 'A11'
                       AND SUB_APL.PAYDATE = T.PAYDATE),
                    0) SHYHS, -- AS 实还印花税,
                '公式' HJ -- 合计
  FROM ACCT_PAYMENT_SCHEDULE_MAS T,
       ACCT_TRANSACTION          ATR,
       ACCT_LOAN                 AL,
       ACCT_PAYMENT_LOG          APL
 WHERE AL.SERIALNO = T.LOANSERIALNO
   AND ATR.RELATIVEOBJECTNO = T.LOANSERIALNO
   AND ATR.TRANSDATE >= '2018/11/01'
   AND ATR.TRANSDATE <= '2018/11/31'
   AND ATR.TRANSSTATUS = '1'
   AND ATR.TRANSCODE IN ('0050', '0055')
   AND NVL(T.TOTALACTUALBALANCE, 0) > 0
   AND ATR.SERIALNO = APL.TRANSSERIALNO
   AND APL.PAYDATE = T.PAYDATE
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

