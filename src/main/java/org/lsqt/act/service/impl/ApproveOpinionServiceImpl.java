package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.service.ApproveOpinionService;
import org.lsqt.act.service.support.EkpTaskUtil;

@Service
public class ApproveOpinionServiceImpl implements ApproveOpinionService{
	
	@Inject private Db db;
	
	public Page<ApproveOpinion>  queryForPage(ApproveOpinionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApproveOpinion.class, query);
	}

	public List<ApproveOpinion>  queryForList(ApproveOpinionQuery query) {
		return db.queryForList("queryForPage", ApproveOpinion.class, query);
	}

	
	public List<ApproveOpinion> getAll(){
		  return db.queryForList("getAll", ApproveOpinion.class);
	}
	
	public ApproveOpinion saveOrUpdate(ApproveOpinion model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if(ids!=null && ids.length>0) {
			ApproveOpinionQuery query = new ApproveOpinionQuery();
			query.setIds(StringUtil.join(Arrays.asList(ids)));
			List<ApproveOpinion> list = db.queryForList("queryForPage", ApproveOpinion.class, query);
			if(ArrayUtil.isNotBlank(list)) {
				for (ApproveOpinion m: list) {
					if (StringUtil.isNotBlank(m.getApproveTaskId())) {
						EkpTaskUtil.exeEkpDeleteTask(m.getApproveTaskId());
					}
					
					db.delete(m);
				}
			}
			
			//return db.deleteById(ApproveOpinion.class, Arrays.asList(ids).toArray());
		}
		return 0;
	}
	
	public int deleteBy(String instanceId,String businessKey) {
		ApproveOpinionQuery query = new ApproveOpinionQuery();
		query.setProcessInstanceIds(StringUtil.join(Arrays.asList(instanceId)));
		query.setBusinessKey(businessKey);
		
		List<ApproveOpinion> list = db.queryForList("queryForPage", ApproveOpinion.class, query);
		if(ArrayUtil.isNotBlank(list)) {
			for (ApproveOpinion m: list) {
				if (StringUtil.isNotBlank(m.getApproveTaskId())) {
					EkpTaskUtil.exeEkpDeleteTask(m.getApproveTaskId());
				}
				
				db.delete(m);
			}
		}
		
		//return db.executeUpdate("delete from ext_approve_opinion where process_instance_id=? and business_key=?", instanceId,businessKey);
		return 0;
	}
}
