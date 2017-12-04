package org.lsqt.cms.controller;

import java.io.IOException;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.ContentQuery;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Controller(mapping={"/content"})
public class ContentController {
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Content> queryForPage(ContentQuery query) throws IOException {
		return db.queryForPage("queryForPage",query.getPageIndex(),query.getPageSize(),Content.class,query);
	}
	
}
