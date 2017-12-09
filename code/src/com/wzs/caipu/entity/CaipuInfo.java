package com.wzs.caipu.entity;

import java.io.Serializable;

public class CaipuInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public String tags;
	public String ingredients;
	public String burden;
	public String urlalbums;
	public CaipuInfo()
	{
		
	}
	public CaipuInfo(String title,String tags,String ingredients,String burden,String urlalbums)
	{
		this.title = title;
		this.tags = tags;
		this.ingredients = ingredients;
		this.burden = burden;
		this.urlalbums = urlalbums;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getBurden() {
		return burden;
	}
	public void setBurden(String burden) {
		this.burden = burden;
	}
	public String getUrlalbums() {
		return urlalbums;
	}
	public void setUrlalbums(String urlalbums) {
		this.urlalbums = urlalbums;
	}
	
}
