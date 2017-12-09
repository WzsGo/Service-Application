package com.wzs.mooc.entity;

import java.io.Serializable;

public class TrainZhanzhan_Piaojia_Info implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String train_no ;
	public String train_type;
	public String start_time;
	public String arrive_time;
	public String run_time;
	public String price_type1;
	public String price1;
	public String price_type2;
	public String price2;
	public TrainZhanzhan_Piaojia_Info()
	{
		
	}
	public TrainZhanzhan_Piaojia_Info(String train_no,String train_type,String start_time,String arrive_time,String run_time,
			String price_type1,String price1,String price_type2,String price2 )
	{
		this.train_no = train_no;
		this.train_type = train_type;
		this.start_time = start_time;
		this.arrive_time = arrive_time;
		this.run_time = run_time;
		this.price_type1 = price_type1;
		this.price1 = price1;
		this.price_type2 = price_type2;
		this.price2 = price2;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getTrain_type() {
		return train_type;
	}
	public void setTrain_type(String train_type) {
		this.train_type = train_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return arrive_time;
	}
	public void setEnd_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getRun_time() {
		return run_time;
	}
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}
	public String getPrice_type1() {
		return price_type1;
	}
	public void setPrice_type1(String price_type1) {
		this.price_type1 = price_type1;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice_type2() {
		return price_type2;
	}
	public void setPrice_type2(String price_type2) {
		this.price_type2 = price_type2;
	}
	public String getPrice2() {
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
	}
	

}
