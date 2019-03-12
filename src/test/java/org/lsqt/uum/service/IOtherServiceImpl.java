package org.lsqt.uum.service;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.test.IOtherService;

@Component
public class IOtherServiceImpl implements IOtherService {

	@Override
	public void hello() {
		System.out.println("OtherServiceImpl ...");
	}

}
