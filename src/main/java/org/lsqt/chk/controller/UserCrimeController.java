package org.lsqt.chk.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.chk.ifc.IFCClient;
import org.lsqt.chk.ifc.model.ResultIFC;
import org.lsqt.chk.model.UserCrime;
import org.lsqt.chk.model.UserCrimeQuery;
import org.lsqt.chk.mq.UserCrimeProducer;
import org.lsqt.chk.mq.UserCrimeRequest;
import org.lsqt.chk.service.UserCrimeService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/chk/user_crime"})
public class UserCrimeController {
	
	@Inject private UserCrimeService userCrimeService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserCrime getById(Long id) throws IOException {
		return userCrimeService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserCrime> queryForPage(UserCrimeQuery query) throws IOException {
		return userCrimeService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserCrime> getAll() {
		return userCrimeService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserCrime saveOrUpdate(UserCrime form) {
		return userCrimeService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userCrimeService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/start_check", "/m/start_check" },text="发起核查")
	public int startCheck(String ids) { // 测试用 : 陈秀娟 - 321183199007030038
		int cnt = 0;
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		if (ArrayUtil.isBlank(list)) {
			return cnt;
		}
		
		for (Long id : list) {
			UserCrime model = db.getById(UserCrime.class, id);
			if (model != null) {
				
				try {
					String json = IFCClient.getIdCardInfo(model.getName(), model.getIdcard());
					ResultIFC resultIFC = JSON.parseObject(json, ResultIFC.class);
					
					model.setMatchResCode(resultIFC.code);
					model.setMatchResDesc(resultIFC.desc);
					
					if (!ResultIFC.CODE_OK.equals(resultIFC.code)) { // 异常断路!!
						db.update(model, "matchResCode","matchResDesc");
						continue ;
					}
					
					
					if (resultIFC.result != null) {
						model.setMatchBizCode(resultIFC.result.resultCode);
						model.setMatchBizDesc(resultIFC.result.resultMsg);
						
						if (ResultIFC.Result.RESULT_CODE_MATCH_OK.equals(resultIFC.result.resultCode)) { // 匹配成功,发送消息获取是否有犯罪记录
							//获取性别和头像、签 证机关数据	
							model.setSex(resultIFC.result.sex);
							model.setPhoto(resultIFC.result.photo);
							model.setPoliceAddress(resultIFC.result.policeadd);
							
							UserCrimeRequest request = new UserCrimeRequest();
							request.business_id = id.toString(); // 发送的是主键,便于查询
							request.id_no = model.getIdcard();
							request.mobile_no = "18218148802"; // 风控平台接口，手机号要必填，我固定自己的手机号
							request.name = model.getName();
							
							String message = JSON.toJSONString(request);
							UserCrimeProducer.produce(message);
						} 
					}
					db.update(model, "matchResCode","matchResDesc","matchBizCode","matchBizDesc","sex","photo","policeAddress") ;
					
					
					cnt++;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		
		return cnt;
	}
}
