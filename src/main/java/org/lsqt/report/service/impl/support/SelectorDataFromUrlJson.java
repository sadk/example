package org.lsqt.report.service.impl.support;

import java.util.Map;

import org.lsqt.components.db.Page;

public class SelectorDataFromUrlJson implements SelectorData<Map<String,Object>>{
	private String url;
	public SelectorDataFromUrlJson (String url) {
		this.url = url;
	}
	@Override
	public Page<Map<String, Object>> getData() {
		Page<Map<String, Object>> page = new Page.PageModel<Map<String, Object>>();
		
		return page;
	}

}
