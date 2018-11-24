package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.PositionDeliveryRecord;
import org.lsqt.rst.model.PositionDeliveryRecordQuery;
import org.lsqt.rst.service.PositionDeliveryRecordService;




@Controller(mapping={"/rst/position_delivery_record"})
public class PositionDeliveryRecordController {
	
	@Inject private PositionDeliveryRecordService positionDeliveryRecordService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public PositionDeliveryRecord getById(Long id) throws IOException {
		return positionDeliveryRecordService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionDeliveryRecord> queryForPage(PositionDeliveryRecordQuery query) throws IOException {
		return positionDeliveryRecordService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionDeliveryRecord> getAll() {
		return positionDeliveryRecordService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionDeliveryRecord saveOrUpdate(PositionDeliveryRecord form) {
		return positionDeliveryRecordService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionDeliveryRecordService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
