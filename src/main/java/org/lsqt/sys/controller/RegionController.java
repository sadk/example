package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Region;
import org.lsqt.sys.model.RegionCity;
import org.lsqt.sys.model.RegionDistrict;
import org.lsqt.sys.model.RegionProvince;
import org.lsqt.sys.model.RegionQuery;
import org.lsqt.sys.service.RegionService;

@Controller(mapping={"/region"})
public class RegionController {
	@Inject private RegionService regionService;
	@Inject private Db db;
	
	private static final Collection<Region> REGION_LIST_ALL = new LinkedList<>();
	
	@RequestMapping(mapping = { "/init", "/m/init" },text="初使化国省市区数据到一张表")
	public void initRegion() {
		/*if(!REGION_LIST_ALL.isEmpty()) {
			return ;
		}*/
		
		List<RegionProvince> provinceList = db.queryForList("getAll", RegionProvince.class);
		List<RegionCity> cityList = db.queryForList("getAll", RegionCity.class);
		List<RegionDistrict> districtList = db.queryForList("getAll", RegionDistrict.class);
		
		IdGenerator codeGen = new ORMappingIdGenerator();
		long idNation = 1;
		if (provinceList != null) {
			// 清空数据
			db.executeUpdate(String.format("truncate table %s ",db.getFullTable(Region.class)));
			
			// 国家
			Region regionCn = new Region();
			regionCn.setId(idNation++);
			regionCn.setPid(-1L);
			regionCn.setAppCode(Application.APP_CODE_DEFAULT);
			regionCn.setCode(codeGen.getUUID58().toString());
			regionCn.setName("中国");
			regionCn.setNodePath(regionCn.getId().toString());
			regionCn.setSn(1);
			regionCn.setType(Region.TYPE_NATIONAL);
			db.save(regionCn);
			
			Region region = new Region();
			region.setId(idNation++);
			region.setPid(-1L);
			region.setAppCode(Application.APP_CODE_DEFAULT);
			region.setCode(codeGen.getUUID58().toString());
			region.setName("俄罗斯");
			region.setNodePath(region.getId().toString());
			region.setSn(2);
			region.setType(Region.TYPE_NATIONAL);
			db.save(region);
			
			region = new Region();
			region.setId(idNation++);
			region.setPid(-1L);
			region.setAppCode(Application.APP_CODE_DEFAULT);
			region.setCode(codeGen.getUUID58().toString());
			region.setName("朝鲜");
			region.setNodePath(region.getId().toString());
			region.setSn(3);
			region.setType(Region.TYPE_NATIONAL);
			db.save(region);
			
			// 省
			for (RegionProvince p : provinceList) {
				region = new Region();
				region.setId(p.getId());
				region.setPid(regionCn.getId());
				region.setName(p.getName());
				region.setNodePath(regionCn.getId()+","+region.getId());
				
				region.setAppCode(Application.APP_CODE_DEFAULT);
				region.setCode(codeGen.getUUID58().toString());
				region.setType(Region.TYPE_PROVINCE);
				region.setSn(0);
				
				db.save(region);
			}
			
			//市
			if(cityList!=null) {
				for(RegionCity e: cityList) {
					region = new Region();
					region.setId(e.getId());
					System.out.println(e.getProvinceId());
					region.setPid(e.getProvinceId());
					region.setName(e.getName());
					region.setNodePath(regionCn.getId()+","+e.getProvinceId()+","+region.getId());
					
					region.setAppCode(Application.APP_CODE_DEFAULT);
					region.setCode(codeGen.getUUID58().toString());
					region.setType(Region.TYPE_CITY);
					region.setSn(0);
					db.save(region);
				}
			}
			
			//区
			if(districtList!=null) {
				List<Region> areaList = new ArrayList<>();
				for(RegionDistrict e: districtList) {
					region = new Region();
					region.setId(e.getId());
					region.setPid(e.getCityId());
					region.setName(e.getName());
					//region.setNodePath(regionCn.getId()+","+e.getProvinceId()+","+region.getId());
					
					region.setAppCode(Application.APP_CODE_DEFAULT);
					region.setCode(codeGen.getUUID58().toString());
					region.setType(Region.TYPE_DISTRICT);
					region.setSn(0);
					db.save(region);
					
					areaList.add(region);
				}
				
				
				for(Region reg: areaList) {
					processNodePath(reg);
				}
			}
		}
		
		
		processMunicipality(); // 处理直辖市数据
		
		processNodePathText();
	}
	
	private void processNodePathText() {
		List<Region> all = db.queryForList("getAll", Region.class);
		if (all != null) {
			REGION_LIST_ALL.addAll(all);
		}

		for(Region e: REGION_LIST_ALL) {
			if(StringUtil.isNotBlank(e.getNodePath())){
				String [] pids = e.getNodePath().split(",");
				List<String> texts = new ArrayList<>();
				for(String id: pids){
					for(Region t : REGION_LIST_ALL) {
						if(id.equals(t.getId().toString())) {
							texts.add(t.getName());
							break;
						}
					}
				}
				e.setNodePathText(StringUtil.join(texts, ","));
				String sql=String.format("update %s set node_path_text=? where id=?",db.getFullTable(Region.class));
				db.executeUpdate(sql, e.getNodePathText(),e.getId());
			}
		}
	}

	// 处理四个直辖市和台湾省节点，直接挂载到"中国"结点
	private void processMunicipality() {
		final Long idChina = 1L;
		
		final Long idBJ=200001L;
		final Long idTJ=200002L;
		final Long idSH=200073L;
		final Long idCQ=200234L;
		final Long idTW=100034L;
		processChild(idChina, idBJ);
		processChild(idChina, idTJ);
		processChild(idChina, idSH);
		processChild(idChina, idCQ);
		//processChild(idChina, idTW);
		
		// 处理排序提前
		db.executeUpdate("update sys_region set sn=-1 where 1=1 and (name like '%北京%' or name like '%上海%' or name like '%天津%' or name like '%重庆%')");
		
		db.executeUpdate("delete from sys_region where id in(100001,100002,100009,100022,200345)");
	}

	private void processChild(final Long idChina, final Long idBJ) {
		Region bj = db.queryForObject("queryById",Region.class, idBJ);
		bj.setPid(idChina);
		
		String updateSQL=String.format("update %s set pid=? where id=?",db.getFullTable(Region.class));
		db.executeUpdate(updateSQL,bj.getPid(),bj.getId());
		
		RegionQuery query = new RegionQuery();
		query.setNodePath(bj.getNodePath());
		List<Region> list = db.queryForList("queryForPage", Region.class, query);
		if(list!=null) {
			for(Region r: list) {
				processNodePath(r);
			}
		}
	}

	private void processNodePath(Region reg) {
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(reg.getId());
		
		Region parent = db.queryForObject("queryById",Region.class, reg.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());
			System.out.println(parent.getPid());
			parent = db.queryForObject("queryById",Region.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			reg.setNodePath(StringUtil.join(parentIds, ","));
			db.executeUpdate("update "+db.getFullTable(Region.class)+" set node_path=? where id=?", reg.getNodePath(),reg.getId());
			// db.update(reg, "nodePath"); 因为ORO没有配置id!!!
		}
	}
	
	/**/
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Region> queryForPage(RegionQuery query) throws IOException {
		return regionService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Region> all(RegionQuery query) throws IOException {
		query.setPageSize(Integer.MAX_VALUE);
		return regionService.queryForPage(query).getData();
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public void saveOrUpdate(Region region) throws IOException {
		 regionService.saveOrUpdate(region);
	}
}
