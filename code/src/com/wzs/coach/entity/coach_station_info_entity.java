package com.wzs.coach.entity;

import java.io.Serializable;

public class coach_station_info_entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String tel;
	public String adds;
	public coach_station_info_entity()
	{
		
	}
	public coach_station_info_entity(String name,String tel,String adds)
	{
		this.name = name;
		this.tel = tel;
		this.adds = adds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAdds() {
		return adds;
	}
	public void setAdds(String adds) {
		this.adds = adds;
	}

}
