package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;
import org.lsqt.rst.service.PositionDefinitionService;

@Service
public class PositionDefinitionServiceImpl implements PositionDefinitionService{
	
	@Inject private Db db;
	
	@SuppressWarnings("unchecked")
	public PositionDefinition getById(Long id) {
		PositionDefinition model = db.getById(PositionDefinition.class, id) ;
		
		Map<String,Object> row = db.executeQueryForObject("select * from bu_red_packet where job_id=?", Map.class, model.getCode());
		if(row!=null) {
			Object amount = row.get("red_packet_amount");
			Object rule = row.get("red_packet_rule");
			
			if (amount != null) {
				model.setRedAmount(amount.toString());
			}
			if (rule != null) {
				model.setAmountRule(rule.toString());
			}
		}
		return model;
	}
	
	public List<PositionDefinition> queryForList(PositionDefinitionQuery query) {
		return db.queryForList("queryForPage", PositionDefinition.class, query);
	}
	
	public Page<PositionDefinition> queryForPage(PositionDefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionDefinition.class, query);
	}

	public List<PositionDefinition> getAll(){
		  return db.queryForList("getAll", PositionDefinition.class);
	}
	
	public PositionDefinition saveOrUpdate(PositionDefinition model) {
		if (StringUtil.isNotBlank(model.getWelfareItemNos(), model.getCode())) {
			db.executeUpdate("delete from bu_job_welfare_relationship where job_id=?", model.getCode());

			List<String> itemNos = StringUtil.split(model.getWelfareItemNos(), ",");
			for (String itemNo : itemNos) {
				String sql = "insert into bu_job_welfare_relationship (job_id,welfare_id) values(?,?)";
				db.executeUpdate(sql, model.getCode(), itemNo);
			}
		}
		
		db.saveOrUpdate(model);

		if (StringUtil.isNotBlank(model.getAmountRule()) || StringUtil.isNotBlank(model.getRedAmount())) {
			db.executeUpdate("delete from bu_red_packet where job_id=?", model.getCode());
			
			String sql = "insert into bu_red_packet (job_id,red_packet_amount,red_packet_rule,tenant_code) values(?,?,?,?)";
			db.executeUpdate(sql, model.getCode(), model.getRedAmount(), model.getAmountRule(), model.getTenantCode());
		}
		return model;
	}

	public int deleteById(Long ... ids) {
		if (ids != null && ids.length > 0) {
			for (Long id : ids) {
				PositionDefinition position = getById(id);
				if (position != null) {
					String sql = "delete from bu_job_address_relationship where job_id=?"; // 级联删除职位的“工作地址”
					db.executeUpdate(sql, position.getCode());

					String sql2 = "delete from bu_job_video where job_id=?"; // 级联删除职位的视频
					db.executeUpdate(sql2, position.getCode());
				}
			}
		}
		return db.deleteById(PositionDefinition.class, Arrays.asList(ids).toArray());
	}
}
