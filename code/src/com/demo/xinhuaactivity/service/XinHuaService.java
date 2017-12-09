package com.demo.xinhuaactivity.service;

import java.util.List;

import entity_xinhua.XinHuaEntity;


public interface XinHuaService {
	public String[] xinhuaInfo(String askword) throws Exception;
	public String  xinhauServiceInfo(String askword) throws Exception;
	public List<XinHuaEntity> getXiangjieInfo(String askword) throws Exception;

}
