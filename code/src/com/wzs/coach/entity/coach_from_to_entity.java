package com.wzs.coach.entity;

import java.io.Serializable;
public class coach_from_to_entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String start;
	public String arrive;
	public String date;
	public String price;
	public String time;
	public coach_from_to_entity()
	{
		
	}
	public coach_from_to_entity(String start,String arrive,String date,String price,String time)
	{
		this.start = start;
		this.arrive = arrive;
		this.date = date;
		this.price = price;
		this.time = time;
		
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
	
