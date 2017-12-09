package com.wzs.caipu.cs;

import java.util.List;
import java.util.Map;

public interface CaipuFindCs
{
	public List<Map<String, Object>> getCaifuInfo(String menu) throws Exception;
	public List<List<Map<String , Object>>> getCaipuStepInfo(String menu) throws Exception;
}
