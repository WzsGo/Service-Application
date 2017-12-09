package com.wzs.mooc.entity;

import java.io.Serializable;

public class KuaidiEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Datetime;
	public String Remark;
	public String Zone;
	public int Status;
	public KuaidiEntity()
	{
		
	}
	public KuaidiEntity (String datetime,String remark,String zone)
	{
		Datetime = datetime;
		Remark = remark;
		Zone = zone;
		
	}
	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getZone() {
		return Zone;
	}
	public void setZone(String zone) {
		Zone = zone;
	}


}
