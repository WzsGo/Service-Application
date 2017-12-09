package com.wzs.mooc.entity;

import java.io.Serializable;
public class TrainZhanzhan_Yupiao_Info implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String train_no;
	public String start_time;
	public String arrive_time;
	public String train_class_name;
	public String lishi;
	
	public String rw_num;
	public String rz_num;
	public String tz_num;
	public String wz_num;
	public String yw_num;
	public String yz_num;
	public String ze_num;
	public String zy_num;
	public String swz_num;
	public TrainZhanzhan_Yupiao_Info(String train_no,String start_time,String arrive_time,String train_class_name,String lishi,
			String rw_num,String rz_num,String tz_num,String wz_num,String yw_num,String yz_num,String ze_num,String zy_num,String swz_num)
	{
		this.train_no = train_no;
		this.start_time = start_time;
		this.arrive_time = arrive_time;
		this.train_class_name = train_class_name;
		this.lishi = lishi;
		this.rw_num = rw_num;
		this.rz_num = rz_num ;
		this.tz_num = tz_num;
		this.wz_num = wz_num;
		this.yw_num = yw_num;
		this.yz_num = yz_num;
		this.ze_num = ze_num;
		this.zy_num = zy_num;
		this.swz_num = swz_num;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getTrain_class_name() {
		return train_class_name;
	}
	public void setTrain_class_name(String train_class_name) {
		this.train_class_name = train_class_name;
	}
	public String getLishi() {
		return lishi;
	}
	public void setLishi(String lishi) {
		this.lishi = lishi;
	}
	public String getRw_num() {
		return rw_num;
	}
	public void setRw_num(String rw_num) {
		this.rw_num = rw_num;
	}
	public String getRz_num() {
		return rz_num;
	}
	public void setRz_num(String rz_num) {
		this.rz_num = rz_num;
	}
	public String getTz_num() {
		return tz_num;
	}
	public void setTz_num(String tz_num) {
		this.tz_num = tz_num;
	}
	public String getWz_num() {
		return wz_num;
	}
	public void setWz_num(String wz_num) {
		this.wz_num = wz_num;
	}
	public String getYw_num() {
		return yw_num;
	}
	public void setYw_num(String yw_num) {
		this.yw_num = yw_num;
	}
	public String getYz_num() {
		return yz_num;
	}
	public void setYz_num(String yz_num) {
		this.yz_num = yz_num;
	}
	public String getZe_num() {
		return ze_num;
	}
	public void setZe_num(String ze_num) {
		this.ze_num = ze_num;
	}
	public String getZy_num() {
		return zy_num;
	}
	public void setZy_num(String zy_num) {
		this.zy_num = zy_num;
	}
	public String getSwz_num() {
		return swz_num;
	}
	public void setSwz_num(String swz_num) {
		this.swz_num = swz_num;
	}
	
	

}
