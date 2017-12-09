package com.wzs.weather.entity;

import java.io.Serializable;
public class Weather_Info implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String temp ;
	public String time ;
	public String temperature ;
	public String weather;
	public String weather_id;
	public String wind;
	public String week;
	public String city;
	public String date_y;
	public String uv_index;
	public String exercise_index;
	
	public String temperature1 ;
	public String weather1;
	public String weather_id1;
	public String wind1;
	public String week1;
	public String date1;
	;
	public Weather_Info()
	{
		
	}
	public Weather_Info(String temp,String time,String temperature,String weather,String weather_id,
			String wind,String week,String city,String date_y,String uv_index,String exercise_index)
	{
		this.temp = temp;
		this.time = time;
		this.temperature = temperature;
		this.weather = weather;
		this.weather_id = weather_id;
		this.wind = wind;
		this.week = week;
		this.city = city;
		this.date_y = date_y;
		this.uv_index = uv_index;
		this.exercise_index = exercise_index;
	}
	public Weather_Info(String temperature1,String weather1,String weather_id1,
			String wind1,String week1,String date1)
	{
		
		this.temperature1 = temperature1;
		this.weather1 = weather1;
		this.weather_id1 = weather_id1;
		this.wind1 = wind1;
		this.week1 = week1;
		this.date1 = date1;
	}
	
	
}
