package com.wzs.mooc.entity;

import java.io.Serializable;
public class TrainCheciInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String start;
	public String end;
	public String starttime;
	public String endtime;
	public String Smileage;
	
	public int train_id;
	public String station_name;
	public String leave_time;
	public String arrived_time;
	public String mileage;
	public String stay;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getSmileage() {
		return Smileage;
	}
	public void setSmileage(String smileage) {
		Smileage = smileage;
	}
	public int getTrain_id() {
		return train_id;
	}
	public void setTrain_id(int train_id) {
		this.train_id = train_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getStay() {
		return stay;
	}
	public void setStay(String stay) {
		this.stay = stay;
	}
	public String getLeave_time() {
		return leave_time;
	}
	public void setLeave_time(String leave_time) {
		this.leave_time = leave_time;
	}
	public String getArrived_time() {
		return arrived_time;
	}
	public void setArrived_time(String arrived_time) {
		this.arrived_time = arrived_time;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public TrainCheciInfo ()
	{
		
	}
	public TrainCheciInfo(String name,String start,String end,String starttime,String endtime,String Smileage)
	{
		this.name = name;  
		this.start = start;
		this.end = end;
		this.starttime = starttime;
		this.endtime = endtime;
		this.Smileage = Smileage;
		
		
	}
	public TrainCheciInfo(int train_id,String station_name,String arrived_time,String  leave_time,String mileage,String stay)
	{
		this.train_id = train_id;
		this.station_name = station_name;
		this.leave_time = arrived_time;
		this.arrived_time = leave_time;
		this.mileage = mileage;
		this.stay  = stay;
	}
	
	
}
