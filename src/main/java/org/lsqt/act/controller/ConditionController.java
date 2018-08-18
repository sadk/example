package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Condition;
import org.lsqt.act.model.ConditionQuery;
import org.lsqt.act.service.ConditionService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;




@Controller(mapping={"/act/condition"})
public class ConditionController {
	
	@Inject private ConditionService conditionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Condition> queryForPage(ConditionQuery query) throws IOException {
		return conditionService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Condition> queryForList(ConditionQuery query) throws IOException {
		return conditionService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Condition> getAll() {
		return conditionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Condition saveOrUpdate(Condition form) {
		return conditionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return conditionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/auto_list", "/m/auto_list" },text="用户点击条件，如果没有添加条件，系统自动添加")
	public List<Condition> queryForAutoList(ConditionQuery query) throws IOException {
		List<Condition> rs = new ArrayList<>();

		if (StringUtil.isNotBlank(query.getDefinitionId(), query.getCode())) {
			String conditionName = query.getName();

			query.setName(null); // 不查询name

			List<Condition> list = conditionService.queryForList(query);
			if (ArrayUtil.isBlank(list)) {
				// 添加记录
				Condition model = new Condition();
				model.setCode(query.getCode());
				model.setDefinitionId(query.getDefinitionId());
				if (StringUtil.isNotBlank(conditionName)) {
					model.setName(conditionName);
				} else {
					model.setName("条件" + model.getCode());
				}
				db.save(model);

				return Arrays.asList(model);
			} else if (list.size() == 1) {
				return list;
			}
		}

		return rs;
	}
}
