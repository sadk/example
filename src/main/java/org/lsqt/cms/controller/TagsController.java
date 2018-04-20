package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.List;

import org.lsqt.cms.model.Tags;
import org.lsqt.cms.model.TagsQuery;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/tags"})
public class TagsController {
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Tags saveOrUpdate(Tags tags) throws IOException {
		db.saveOrUpdate(tags);
		return tags;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Tags> queryForPage(TagsQuery query) throws IOException {
		Page<Tags> page = db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Tags.class, query);
		return page;
	}
	
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public void updateShort(String data) throws IOException {
		if (org.lsqt.components.util.lang.StringUtil.isNotBlank(data)) {
			List<Tags> tagsList = JSON.parseArray(data, Tags.class);
			for(Tags t:tagsList) {
					db.update(t, "name");
			}
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		if(ArrayUtil.isNotBlank(list)) {
			 db.deleteById(Tags.class, list.toArray());
		}
		return 0;
	}
}
