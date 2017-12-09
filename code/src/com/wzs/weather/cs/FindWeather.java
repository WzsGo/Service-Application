package com.wzs.weather.cs;

import java.util.List;


import com.wzs.weather.entity.Weather_Info;




public interface FindWeather  {
	
  public List<Weather_Info> getWeather_Info(String cityname) throws Exception;
  public String  getWeather_Location(double latitude,double longitude) throws Exception;

  
}
