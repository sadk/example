package org.lsqt.sms.service;

import org.lsqt.components.db.Page;
import org.lsqt.sms.model.LaunchMarketingInfo;
import org.lsqt.sms.model.Result;
import org.lsqt.sms.model.SmsLaunchInfo;
import org.lsqt.sms.model.SmsLaunchInfoQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SmsLaunchInfoService {
	
	List<SmsLaunchInfo> queryForList(SmsLaunchInfoQuery query);
	
	Page<SmsLaunchInfo> queryForPage(SmsLaunchInfoQuery query);

	String saveOrUpdate(SmsLaunchInfo model);

	int deleteById(Long... ids);
	
	Collection<SmsLaunchInfo> getAll();

	String updateLaunchStatus(Long id);

	/**
	 * 获取投放回执详情
	 * @param launchId
	 * @return
	 */
	String getSmsData(Integer launchId);

	String checkBeforeSave(SmsLaunchInfo form);

	public String cancelLaunch(Long launchId);

	public Result<Map<String, Object>> getMarketingDataFilePath(Long[] launchIds, String bgnTime, String endTime);

	public Result<List<LaunchMarketingInfo>> queryMarketingData(Long[] launchIds, String bgnTime, String endTime);

}
