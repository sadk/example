package org.lsqt.syswin.authority.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Range;
import org.lsqt.syswin.authority.model.RangeQuery;
import org.lsqt.syswin.authority.service.RangeService;

@Service
public class RangeServiceImpl implements RangeService{
	
	@Inject private PlatformDb db;
	
	public Page<Range>  queryForPage(RangeQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Range.class, query);
	}

	public List<Range> getAll(){
		  return db.queryForList("getAll", Range.class);
	}
	
	public Range saveOrUpdate(Range model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if(ids==null || ids.length==0)return 0;
		
		db.executePlan(new Plan(){
			@Override
			public void doExecutePlan() throws DbException {
				for(Long id: ids) {
					String sql = "delete from t_power_role_range_res where range_id=?";
					db.executeUpdate(sql, id);
				}
				db.deleteById(Range.class, Arrays.asList(ids).toArray());
			}
		});
		return ids.length;
	}
}
