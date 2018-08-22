package org.lsqt.report.service.impl.support;

import org.lsqt.components.db.Page;

public interface SelectorData<T> {
	Page<T> getData();
}
