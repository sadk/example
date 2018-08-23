package org.lsqt.report.service.impl.support;

import java.util.Map;

import org.lsqt.components.db.Page;

public class SelectorDataFromUrlXml implements SelectorData<Map<String,Object>>{

	@Override
	public Page<Map<String, Object>> getData() {
		Page<Map<String, Object>> page = new Page.PageModel<Map<String, Object>>();
		return page;
	}

}
