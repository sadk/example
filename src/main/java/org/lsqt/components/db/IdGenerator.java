package org.lsqt.components.db;

import java.io.Serializable;

/**
 * 数据库id生成器
 * @author yuanmm
 *
 */
public interface IdGenerator {
	
	String ID_TYPE_AUTO="auto";
	String ID_TYPE_UUID64="uuid64";
	String ID_TYPE_UUID58="uuid58";
	String ID_TYPE_NANOTIME="nanotime";
	
	/**
	 * 生成
	 * @return
	 */
	Serializable getUUID64() ;

	Serializable getUUID58() ;
	
	Serializable getSequence();
}