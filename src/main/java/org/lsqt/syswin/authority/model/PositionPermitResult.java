package org.lsqt.syswin.authority.model;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.util.lang.StringUtil;

/**
 * 岗位权限结果表
 */
public class PositionPermitResult {

	/** pk */

	private Long id;

	/** 岗位id */

	private Long positionId;

	/** 功能模块编码 */
	private String moduleCode;

	/** 数据ID */

	private Long orgId;

	/** 权限类型：1=数据查询 2=数据使用 */
	private Integer type;
	public static final int TYPE_QUERY=1;
	public static final int TYPE_USENESS=2;
	
	
	
	/** 授权范置：1=本人 2=直属下级 3=所有下级  4=所有*/
	private Integer level;
	public static final int LEVEL_MYSELF=1; 
	public static final int lEVEL_NEXT=2;
	public static final int LEVEL_CHILD=3;
	public static final int LEVEL_ALL=4;
	
	

	
	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 
	 * @param cfg 对应的positionId岗的“数据查询和使用权限配置”对象
	 * @param level 授权类型：1=本人 2=直属下级 3=所有下级
	 * 
	 */
	public static List<PositionPermitResult> prepreResultData(PositionPermitConfig cfg,int level) {
		List<PositionPermitResult> list = new ArrayList<>();
		
		if (cfg != null) {
			// 数据查询
			if (StringUtil.isNotBlank(cfg.getOrgIdsQuery())) {
				List<Long> orgIds = StringUtil.split(Long.class, cfg.getOrgIdsQuery(), ",");

				for (Long orgId : orgIds) {
					PositionPermitResult model = new PositionPermitResult();
					model.setPositionId(cfg.getPositionId());
					model.setLevel(level);
					model.setType(TYPE_QUERY);
					model.setOrgId(orgId);
					list.add(model);
				}
			}

			// 数据使用
			if (StringUtil.isNotBlank(cfg.getOrgIdsUseness())) {
				List<Long> orgIds = StringUtil.split(Long.class, cfg.getOrgIdsUseness(), ",");

				for (Long orgId : orgIds) {
					PositionPermitResult model = new PositionPermitResult();
					model.setPositionId(cfg.getPositionId());
					model.setLevel(level);
					model.setType(TYPE_USENESS);
					model.setOrgId(orgId);
					list.add(model);
				}
			}
		}
		
		return list;
	}
}
