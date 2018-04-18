package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.List;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.ContentQuery;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/content"})
public class ContentController {
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Content saveOrUpdate(Content content) throws IOException {
		ContentQuery query = new ContentQuery();
		query.setObjectId(content.getObjectId());
		
		Long cnt = db.queryForObject(Content.class.getName(), "getCountEnable", Long.class, query);
		if(cnt==null || cnt ==0) {
			content.setEnable(1);
		}else {
			content.setEnable(0);
		}
		db.saveOrUpdate(content);
		return content;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Content> queryForPage(ContentQuery query) throws IOException {
		Page<Content> page = db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Content.class, query);
		/*
		if (ArrayUtil.isNotBlank(page.getData())) {
			for (Content e : page.getData()) {
				if (org.lsqt.components.util.lang.StringUtil.isNotBlank(e.getContent())) {
					e.setContent(StringUtil.XHTMLEnc(e.getContent()));
				}
			}
		}*/
		return page;
	}
	
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public Content updateShort(String data) throws IOException {
		if (org.lsqt.components.util.lang.StringUtil.isNotBlank(data)) {
			List<Content> contentList = JSON.parseArray(data, Content.class);
			for(Content content:contentList) {
				if(content.getObjectId()!=null && content.getEnable()!=null) {
					db.executeUpdate("update cms_content set enable=0 where object_id=? and type=?", content.getObjectId(),Content.TYPE_HTML_TEMPLATE_FREEMARK);
					db.update(content, "enable");
				}
			}
		}
		return null;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		if(ArrayUtil.isNotBlank(list)) {
			 db.deleteById(Content.class, list.toArray());
		}
		return 0;
	}
}
