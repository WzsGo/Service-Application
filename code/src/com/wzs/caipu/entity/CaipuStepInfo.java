package com.wzs.caipu.entity;

import java.io.Serializable;

public class CaipuStepInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String urlimg ;
	public String step ;
	public CaipuStepInfo()
	{
		
	}
	public CaipuStepInfo(String urlimg,String step)
	{
		this.urlimg = urlimg;
		this.step = step;
	}
	public String getUrlimg() {
		return urlimg;
	}
	public void setUrlimg(String urlimg) {
		this.urlimg = urlimg;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	
}
