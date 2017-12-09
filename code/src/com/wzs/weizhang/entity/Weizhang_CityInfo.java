package com.wzs.weizhang.entity;

import java.io.Serializable;

public class Weizhang_CityInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String city_code;
	public String abbr;
	public int engine;
	public int engineno;
	public int classa;	
	public int classno;	
	public Weizhang_CityInfo()
	{
		
	}
	public Weizhang_CityInfo(String city_code,String abbr,int engine,int engineno,int classa,
			int classno)
	{
		this.city_code = city_code;
		this.abbr = abbr;
		this.engine = engine;
		this.engineno = engineno;
		this.classa = classa;	
		this.classno = classno;
		
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public int getEngine() {
		return engine;
	}
	public void setEngine(int engine) {
		this.engine = engine;
	}
	public int getEngineno() {
		return engineno;
	}
	public void setEngineno(int engineno) {
		this.engineno = engineno;
	}
	public int getClassa() {
		return classa;
	}
	public void setClassa(int classa) {
		this.classa = classa;
	}
	public int getClassno() {
		return classno;
	}
	public void setClassno(int classno) {
		this.classno = classno;
	}
	

	
}
