package org.lsqt.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.biz.util.HttpClient;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 代扣相关
 * @author mm
 *
 */
@Controller(mapping={"/biz/csp"})
public class WithholdController {
	private static final Logger log = LoggerFactory.getLogger(WithholdController.class);
	
	@Inject private Db db;
	
	private static String API_WITHHOLD;
	
	@OnStarted
	public void initConfig () throws Exception {
		db.executePlan(false, ()->{
			MachineQuery query = new MachineQuery();
			query.setCode("csp_withhold");
			Machine model = db.queryForObject("queryForPage", Machine.class, query);
			if (model != null && model.getStatus() != null && Dictionary.ENABLE_启用 == model.getStatus()) {
				PropertyQuery propQuery = new PropertyQuery();
				propQuery.setParentCode(model.getCode());
				List<Property> list = db.queryForList("queryForPage", Property.class, propQuery);
				for (Property p: list) {
					if("api_withhold".equals(p.getName())) {
						API_WITHHOLD = p.getValue();
					}
				}
			}
		});
		
		log.info("########################################################################");
		log.info("# ");
		log.info("# 代扣配置接口地址 {}",API_WITHHOLD);
		log.info("# ");
		log.info("########################################################################");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/withhold", "/m/withhold" },excludeTransaction = true)
	public Map<String,Object> withhold(String contractNo,String amount) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("contractNo", contractNo);
		paramMap.put("amount", amount);
		
		String resText = HttpClient.post(API_WITHHOLD, JSON.toJSONString(paramMap));
		
		map = JSON.parseObject(resText, Map.class);
		return map;
	}
	
}

