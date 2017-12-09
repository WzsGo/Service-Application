package com.wzs.coach.cs;

import java.util.List;

import com.wzs.coach.entity.coach_from_to_entity;
import com.wzs.coach.entity.coach_station_info_entity;

public interface FindCoach 
{
	public List<coach_station_info_entity> getCoach_station_info_list(String station) throws Exception;
	public List<coach_from_to_entity> getCoach_from_to_list(String from,String too,String time) throws Exception;
}
