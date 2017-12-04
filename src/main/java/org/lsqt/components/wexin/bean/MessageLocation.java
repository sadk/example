package org.lsqt.components.wexin.bean;

/**
 * 地理位置消息
 * @author yuanmm
 *
 */
public class MessageLocation {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="location";
	public Double locationX; //	地理位置维度
	public Double locationY; // 地理位置经度
	public Integer scale; // 地图缩放大小
	public String label; // 地理位置信息
	public Long msgId;
}
