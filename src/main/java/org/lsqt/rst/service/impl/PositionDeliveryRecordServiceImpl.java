package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PositionDeliveryRecord;
import org.lsqt.rst.model.PositionDeliveryRecordQuery;
import org.lsqt.rst.service.PositionDeliveryRecordService;

@Service
public class PositionDeliveryRecordServiceImpl implements PositionDeliveryRecordService{
	
	@Inject private Db db;
	
	public PositionDeliveryRecord getById(Long id) {
		return db.getById(PositionDeliveryRecord.class, id) ;
	}
	
	public List<PositionDeliveryRecord> queryForList(PositionDeliveryRecordQuery query) {
		return db.queryForList("queryForPage", PositionDeliveryRecord.class, query);
	}
	
	public Page<PositionDeliveryRecord> queryForPage(PositionDeliveryRecordQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionDeliveryRecord.class, query);
	}

	public List<PositionDeliveryRecord> getAll(){
		  return db.queryForList("getAll", PositionDeliveryRecord.class);
	}
	
	public PositionDeliveryRecord saveOrUpdate(PositionDeliveryRecord model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionDeliveryRecord.class, Arrays.asList(ids).toArray());
	}
}
