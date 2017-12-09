package com.wzs.mooc.service;

import java.util.List;
import com.wzs.mooc.entity.KuaidiEntity;

public interface FindKuaidi 
{
	public List<KuaidiEntity> getKuaidiInfo(String kuaidicom,String kuaidino) throws Exception;
}
