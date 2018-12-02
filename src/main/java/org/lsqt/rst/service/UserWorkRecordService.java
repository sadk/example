package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;

public interface UserWorkRecordService {
	
	UserWorkRecord getById(Long id);
	
	List<UserWorkRecord> queryForList(UserWorkRecordQuery query);
	
	Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query);

	/**
	 * <pre>
	 * 考勤的规则是: 
	 * 1.用户必须要填上班工时(周六、周天除外）
	 * 2.周一至周五填的工时未满8小时， 是不能填加班的；周一至周五请假以天为最小单位（数据库存储的是请假8小时，以适应后续可以请假0.5天的租户）
	 * 3.周六、周天只能是“加班” ， 用户只能操作保存“加班工时”，  不能操作保存“正常工时”和请假
	 * 
	 * </pre>
	 * @param model
	 * @return
	 */
	UserWorkRecord saveOrUpdate(UserWorkRecord model);

	int deleteById(Long... ids);
	
	Collection<UserWorkRecord> getAll();
}
