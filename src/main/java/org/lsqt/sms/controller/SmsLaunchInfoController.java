package org.lsqt.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.model.*;
import org.lsqt.sms.service.SmsLaunchInfoService;
import org.lsqt.sms.util.CommonUtils;
import org.lsqt.sms.util.DateUtil;
import org.lsqt.sms.util.ExcelExportUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.lsqt.sms.util.CommonUtils.resp;
import static org.lsqt.sms.util.CommonUtils.split2Array;

/**
 *  投放
 */
@Controller(mapping={"/smsLaunchInfo"})
public class SmsLaunchInfoController {
	
	@Inject
    private SmsLaunchInfoService smsLaunchInfoService;
	
	@Inject
    private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<SmsLaunchInfo> queryForPage(SmsLaunchInfoQuery query) throws IOException {
		return smsLaunchInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<SmsLaunchInfo> getAll() {
		return smsLaunchInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Result<Map<String,Object>> saveOrUpdate(SmsLaunchInfo form) {
		return resp(smsLaunchInfoService.saveOrUpdate(form));
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return smsLaunchInfoService.deleteById(list.toArray(new Long[list.size()]));
	}

	@RequestMapping(mapping = { "/check_before_save", "/m/check_before_save" })
	public Result<Map<String, Object>> checkBeforeSave(SmsLaunchInfo form) {
		return resp(smsLaunchInfoService.checkBeforeSave(form));
	}

	/**
	 * 更新投放状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(mapping = { "/updateLaunchStatus", "/m/updateLaunchStatus" })
	public Result<Map<String,Object>> updateLaunchStatus(String ids){
		if (StringUtils.isEmpty(ids)) {
			return Result.fail("请传入id");
		}
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		String resultMsg = smsLaunchInfoService.updateLaunchStatus(list.get(0));
		if(StringUtil.isBlank(resultMsg)){
			return Result.ok("更新状态成功!");
		}
		return Result.fail(resultMsg);
	}

	/** 暂时注释，未能提供通用实现！！！
	@RequestMapping(mapping = { "/export", "/m/export" })
	public void export(SmsLaunchInfoQuery query, String exportFileType, String exportDataType) {
		Page<SmsLaunchInfo> page = db.getEmptyPage();
		
		// 1.导出excel
		if (Dictionary.EXPORT_FILE_TYPE_EXCEL.equals(exportFileType)) {
			
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				
				page.setData(smsLaunchInfoService.getAll());
				ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
				return ;
			} 
			
			page = smsLaunchInfoService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
			
			return;
		}

		// 2.导出文本文件
		if (Dictionary.EXPORT_FILE_TYPE_TXT.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsLaunchInfoService.getAll());
				ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
				return;
			}

			page = smsLaunchInfoService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
			return;
		}
		
		// 3.导出doc文件
		if (Dictionary.EXPORT_FILE_TYPE_DOC.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsLaunchInfoService.getAll());
				ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
				return ;
			}
			
			page = smsLaunchInfoService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
			return;
		}

		// 4.导出pdf文件
		if (Dictionary.EXPORT_FILE_TYPE_PDF.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsLaunchInfoService.getAll());
				ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
				return ;
			}
			
			page = smsLaunchInfoService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
			return;
		}
		
	}
	**/

	/**
	 * 投放回执详单下载
	 * @param query
	 */
	@RequestMapping(mapping = {"/get_sms_data"})
	public Result<Map<String,Object>> getSmsData(SmsLaunchInfoQuery query){
		Integer launchId = Integer.parseInt(query.getLaunchId());
		String smsData = smsLaunchInfoService.getSmsData(launchId);
		if(StringUtil.isNotBlank(smsData) && smsData.startsWith("{")){
			JSONObject jsbContent = JSON.parseObject(smsData);
			if("50033".equals(jsbContent.getString("ret"))){
				return Result.fail("获取回执详情文件失败");
			}if("2008".equals(jsbContent.getString("ret"))){
				return Result.fail("还没有生成recv状态数据");
			}else{
				return Result.fail(jsbContent.getString("msg"));
			}

		}
		try {
			byte[] gbks = smsData.getBytes("GBK");
			HttpServletRequest request = ContextUtil.getRequest();
			String filePath = request.getServletContext().getRealPath("/") + "/download";
			File uploadPath = new File(filePath);
			// 检查文件夹是否存在 不存在 创建一个
			if (!uploadPath.exists()) {
				uploadPath.mkdir();
			}
			File launchFile = new File(uploadPath,"投放结果详情"+launchId+"_"+System.currentTimeMillis()+".csv");
			Files.write(launchFile.toPath(),gbks);
			return Result.ok(launchFile.getAbsolutePath());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 投放回执详单下载
	 * @param filePath
	 */
	@RequestMapping(mapping = {"/export_sms_data"})
	public Result<Map<String,Object>> exportSmsData(String filePath){

		//Integer launchId = Integer.parseInt(query.getLaunchId());
		//String smsData = smsLaunchInfoService.getSmsData(launchId);

		//String fileName = "投放结果详情"+launchId+".csv";
		HttpServletResponse response = ContextUtil.getResponse();
		try {
			System.out.println(filePath);
			File file = new File(filePath);
			BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream output = response.getOutputStream();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode((file.getName()),"utf-8"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("multipart/form-data");

			int bytesend = 0 ;
			byte[] buff = new byte[1024];
			while ((bytesend = bi.read(buff))!= -1){
				output.write(buff,0,bytesend);
			}
			output.flush();
			//input.close();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//Util4Download.download(path, name);
		return null;
	}
	
	/**
	 * @Description //投放结果下载
	 * @Param [query]
	 **/
	@RequestMapping(mapping = {"/export_launch_data"})
	public void exportLaunchData(SmsLaunchInfoQuery query) {
		HttpServletResponse response = ContextUtil.getResponse();

		try (OutputStream os = response.getOutputStream()) {

			String[] headers = {"投放ID", "号码包ID", "签名ID", "文案ID", "创建时间", "投放状态", "投放成功数量", "投放失败数量", "用户接收成功的数目"};
			ExcelExportUtils<SmsLaunchInfoExport> et = new ExcelExportUtils<>();
			List<SmsLaunchInfo> launchInfos = smsLaunchInfoService.queryForList(query);
			List<SmsLaunchInfoExport> data = new ArrayList<>(launchInfos.size());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			for (SmsLaunchInfo launchInfo : launchInfos) {

				SmsLaunchInfoExport launchExport = new SmsLaunchInfoExport()
						.setLaunchId(launchInfo.getLaunchId())
						.setPackageId(launchInfo.getPackageId())
						.setSignId(CommonUtils.valueOf(launchInfo.getSignId()))
						.setTemplId(CommonUtils.valueOf(launchInfo.getTemplId()))
						.setCreateTime(sdf.format(launchInfo.getCreateTime()))
						.setLaunchStatus(launchInfo.getLaunchStatusTransfered())
						.setSuccNum(CommonUtils.valueOf(launchInfo.getSuccNum()))
						.setFailNum(CommonUtils.valueOf(launchInfo.getFailNum()))
						.setRecvNum(CommonUtils.valueOf(launchInfo.getRecvNum()));
				data.add(launchExport);

			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("投放结果查询导出-" + DateUtil.formatDate(new Date(), DateUtil.DFT) + ".xlsx", "UTF-8")); //文件名expenceReduction自己定义，限英文
			et.excelDowmForData("投放结果查询导出",headers, data, os,0);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(mapping = {"/cancel_launch"})
	public Result<Map<String, Object>> cancelLaunch(String launchId) {
		return resp(smsLaunchInfoService.cancelLaunch(Long.valueOf(launchId)));
	}

	@RequestMapping(mapping = {"/query_marketing_data"})
	public Result<List<LaunchMarketingInfo>> queryMarketingData(String launchIds, String bgnTime, String endTime) {
		if (StringUtils.isEmpty(launchIds)) {
			return Result.fail("请传入投放id");
		}
		return smsLaunchInfoService.queryMarketingData(split2Array(launchIds), bgnTime, endTime);
	}

	@RequestMapping(mapping = {"/generate_marketing_data_file"})
	public Result<Map<String,Object>> generateMarketingDataFile(String launchIds, String bgnTime, String endTime){
		if (StringUtils.isEmpty(launchIds)) {
			return Result.fail("请传入投放id");
		}
		return smsLaunchInfoService.getMarketingDataFilePath(split2Array(launchIds), bgnTime, endTime);
	}

	@RequestMapping(mapping = {"/export_marketing_data"})
	public void downloadMarketingDataFile(String filePath) {
		try {
			CommonUtils.fileDownload(filePath, "营销效果_" + DateUtil.formatDate(new Date(), DateUtil.DFT), "xlsx", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
