package com.wzs.weizhang.entity;

import java.io.Serializable;
public class Weizhang_ResultInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String date;
	public String area;
	public String act;
	public String code;
	public String fen;
	public String money;
	public String handled;
	public  Weizhang_ResultInfo()
	{
		
	}
	public Weizhang_ResultInfo(String date,String area,String act,String code,
			String fen,String money,String handled)
	{
		this.date = date;
		this.area = area;
		this.act = act;
		this.code = code;
		this.fen = fen;
		this.money = money;
		this.handled = handled;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getHandled() {
		return handled;
	}
	public void setHandled(String handled) {
		this.handled = handled;
	}
	
	
}
