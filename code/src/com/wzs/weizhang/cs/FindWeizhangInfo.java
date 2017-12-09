package com.wzs.weizhang.cs;

import java.util.List;
import java.util.Map;

import com.wzs.weizhang.entity.Weizhang_ResultInfo;

public interface FindWeizhangInfo  {
	public Map<String , Object> getWeizhangCityInfo(String province_id) throws Exception;
	public List<Weizhang_ResultInfo> getWeizhangResult(String city,String hphm,String engineno,String classno) throws Exception;
}
