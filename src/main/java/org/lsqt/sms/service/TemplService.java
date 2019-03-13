package org.lsqt.sms.service;

import org.lsqt.components.db.Page;
import org.lsqt.sms.model.Templ;
import org.lsqt.sms.model.TemplQuery;

public interface TemplService {
	Page<Templ> queryForPage(TemplQuery query);
	String saveOrUpdate(Templ model);
	String delete(Long[] ids,Long[] templIds);
}
