package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.ProjectQuery;
import org.lsqt.sys.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Inject private Db db;
	
	public Page<Project>  queryForPage(ProjectQuery query) {
			return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Project.class, query);
	}
	
	public List<Project> getAll(){
		  return db.queryForList("queryForPage", Project.class); 
	}
	
	public Project saveOrUpdate(Project model) {
		if (StringUtil.isBlank(model.getAppCode())) {
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Project.class, Arrays.asList(ids).toArray());
	}

	public List<Project> getAllFormDb(ProjectQuery query){
		return db.queryForList("getAllFromDb", Project.class, query);
	}
}
