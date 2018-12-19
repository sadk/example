package org.lsqt.rst.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserEntryInfo;
import org.lsqt.rst.model.UserEntryInfoQuery;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.UserEntryInfoService;




@Controller(mapping={"/rst/user_entry_info"})
public class UserEntryInfoController {
	
	@Inject private UserEntryInfoService userEntryInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserEntryInfo getById(Long id) throws IOException {
		return userEntryInfoService.getById(id);
	}
	
	@RequestMapping(mapping = { "/get_by_user_code", "/m/get_by_user_code" })
	public UserEntryInfo getByUserCode(String userCode) throws IOException {
		UserEntryInfoQuery query = new UserEntryInfoQuery();
		query.setUserCode(userCode);
		UserEntryInfo model= db.queryForObject("queryForPage", UserEntryInfo.class, query);
		
		return model;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserEntryInfo> queryForPage(UserEntryInfoQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return userEntryInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserEntryInfo> getAll() {
		return userEntryInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserEntryInfo saveOrUpdate(UserEntryInfo form) {
		return userEntryInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userEntryInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/batch_short_update", "/m/batch_short_update" }, text = "批量修改用户所在厂区和离职在职状态")
	public void batchShortUpdate(String userIds, Integer entryStatus, String companyName, String companyCode,String entryTime) throws ParseException {
		if (StringUtil.isNotBlank(userIds)) {
			for (String userCode : StringUtil.split(userIds, ",")) {
				UserEntryInfoQuery query = new UserEntryInfoQuery();
				query.setUserCode(userCode);
				UserEntryInfo entry = db.queryForObject("queryForPage", UserEntryInfo.class, query);
				
				UserQuery uq = new UserQuery();
				uq.setCode(userCode);
				User user = db.queryForObject("queryForPage", User.class, uq);
				
				if (entry == null) {
					entry = new UserEntryInfo(); //管理员操作入职,非用户发起
				}
				
				entry.setBirthday(user.getBirthday());
				entry.setPhone(user.getMobile());
				entry.setSex(user.getSex());
				entry.setUserCode(userCode);
				entry.setUserName(user.getRealName());
				entry.setTenantCode(ContextUtil.getLoginTenantCode());
				if (StringUtil.isBlank(entryTime)) {
					entry.setEntryTime(new Date());
				} else {
					entry.setEntryTime(new SimpleDateFormat("yyyy-MM-dd").parse(entryTime));
				}
				entry.setEntryStatus(entryStatus);
				entry.setCompanyCode(companyCode);
				entry.setCompanyName(companyName);
				
				if (UserEntryInfo.ENTRY_STATUS_已入职 == entry.getEntryStatus()) {
					entry.setLeaveTime(null);
				}
				
				if (UserEntryInfo.ENTRY_STATUS_已离职 == entry.getEntryStatus()) {
					//entry.setCompanyCode(null);
					//entry.setCompanyName(null);
					entry.setEntryTime(null);
					entry.setLeaveTime(new SimpleDateFormat("yyyy-MM-dd").parse(entryTime));
				}
				
				db.saveOrUpdate(entry);
			}
		}
	}
	 
	
	@RequestMapping(mapping = { "/tree", "/m/tree" })
	public List<Node> getTree(UserEntryInfoQuery query) throws IOException {
		List<Node> list = new ArrayList<>();
		if (StringUtil.isBlank(query.getUserCode())) {
			return list;
		}
		Node root = new Node();
		root.id = "0";
		root.pid = "-1";
		root.name = "资料文件";
		root.type = "0";
		
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		UserEntryInfo model = db.queryForObject("queryForPage", UserEntryInfo.class, query);
		if (model!=null) {
			if(StringUtil.isNotBlank(model.getIdCardUrl0())) {
				Node n1 = new Node();
				n1.id = "1";
				n1.pid = "0";
				n1.name = "身份证正面";
				n1.type = "1";
				n1.url = model.getIdCardUrl0();
				list.add(n1);
			}
			
			if(StringUtil.isNotBlank(model.getIdCardUrl1())) {
				Node n1 = new Node();
				n1.id = "2";
				n1.pid = "0";
				n1.name = "身份证反面";
				n1.type = "2";
				n1.url = model.getIdCardUrl1();
				list.add(n1);
			}
			
			if(StringUtil.isNotBlank(model.getFaceUrl())) {
				Node n1 = new Node();
				n1.id = "3";
				n1.pid = "0";
				n1.name = "人脸识别文件";
				n1.type = "3";
				n1.url = model.getFaceUrl();
				list.add(n1);
			}
			
			if(StringUtil.isNotBlank(model.getBankUrl())) {
				Node n1 = new Node();
				n1.id = "4";
				n1.pid = "0";
				n1.name = "银行卡号文件";
				n1.type = "4";
				n1.url = model.getBankUrl();
				list.add(n1);
			}
		}
		
		return list;
	}
	
	public static class Node {
		public String id;
		public String pid;
		public String name;
		public String url;
		public String type; //0=其它 1=身份证正面 2=反面 3=人脸文件 4=银行卡号文件  
	}
	
	@RequestMapping(mapping = { "/download_img", "/m/download_img" })
	public void downloadImg(String url) throws IOException {
		HttpServletResponse response = ContextUtil.getResponse();
		
		URL ul = new URL(url);
		try (DataInputStream input = new DataInputStream(ul.openStream());
				ServletOutputStream output = response.getOutputStream()) {
			String endFix = url.substring(url.lastIndexOf("."),url.length());
			response.addHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis()+endFix);

			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;

			while ((len = input.read(buffer)) > 0) { // 循环将输入流中的内容读取到缓冲区当中

				output.write(buffer, 0, len);// 输出缓冲区的内容到浏览器，实现文件下载
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
}
