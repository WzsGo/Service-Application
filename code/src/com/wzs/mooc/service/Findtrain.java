package com.wzs.mooc.service;

import java.util.List;

import com.wzs.mooc.entity.TrainCheciInfo;
import com.wzs.mooc.entity.TrainZhanzhan_Piaojia_Info;
import com.wzs.mooc.entity.TrainZhanzhan_Yupiao_Info;

public interface Findtrain
{
	public TrainCheciInfo getTrain_info(String checi) throws Exception;
	public List<TrainCheciInfo> getTrain_List (String checi) throws Exception;
	public List<TrainZhanzhan_Yupiao_Info> getTrainYupiao_List (String from,String too,String date) throws Exception;
	public List<TrainZhanzhan_Piaojia_Info> getTrainPiaojia_list(String from,String to) throws Exception; 
}
