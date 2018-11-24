package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PositionDeliveryRecord;
import org.lsqt.rst.model.PositionDeliveryRecordQuery;

public interface PositionDeliveryRecordService {
	
	PositionDeliveryRecord getById(Long id);
	
	List<PositionDeliveryRecord> queryForList(PositionDeliveryRecordQuery query);
	
	Page<PositionDeliveryRecord> queryForPage(PositionDeliveryRecordQuery query);

	PositionDeliveryRecord saveOrUpdate(PositionDeliveryRecord model);

	int deleteById(Long... ids);
	
	Collection<PositionDeliveryRecord> getAll();
}
