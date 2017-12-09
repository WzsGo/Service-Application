package com.demo.translationactivity.service;

import java.util.List;
import java.util.Map;

import entity.TranslationEntity;


public interface TranslationService {
	public Map<String, Object> translateInfo(String wordask) throws Exception; 
	public List<TranslationEntity> translateServiceInfo(String wordask)throws Exception;
	public Map<String, Object> translateHanziInfo(String wordask) throws Exception;  
	public String  translateJuziInfo(String wordask) throws Exception;
	
}
