package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.VedioVoteYeared;
import org.lsqt.rst.model.VideoYear;
import org.lsqt.rst.model.VideoYearQuery;
import org.lsqt.rst.service.VideoYearService;

@Service
public class VideoYearServiceImpl implements VideoYearService{
	
	@Inject private Db db;
	
	public VideoYear getById(Long id) {
		return db.getById(VideoYear.class, id) ;
	}
	
	public List<VideoYear> queryForList(VideoYearQuery query) {
		return db.queryForList("queryForPage", VideoYear.class, query);
	}
	
	public Page<VideoYear> queryForPage(VideoYearQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), VideoYear.class, query);
	}

	public List<VideoYear> getAll(){
		  return db.queryForList("getAll", VideoYear.class);
	}
	
	public VideoYear saveOrUpdate(VideoYear model) {
		Date now = new Date();
		model.setUpdateDate(now);
		model.setUploadTime(now);

		if (model.getId() == null) {
			model.setCreateDate(now);
		}
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		int cnt = db.deleteById(VideoYear.class, Arrays.asList(ids).toArray());
		String sql = String.format("delete from %s where vedio_id in (%s)", db.getFullTable(VedioVoteYeared.class),
				StringUtil.join(Arrays.asList(ids)));
		int cnt2 = db.executeUpdate(sql);
		return cnt + cnt2;
	}
}
