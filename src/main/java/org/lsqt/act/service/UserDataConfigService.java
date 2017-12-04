package org.lsqt.act.service;

import org.lsqt.act.model.ApproveObjectData;

public interface UserDataConfigService {
	
	void deleteById(Long ...ids);
	
	ApproveObjectData viewData(Long configId);
	
}
