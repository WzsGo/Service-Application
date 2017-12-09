package com.demo.translationactivity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import com.wzs.mooc.ConstantActivity;


import entity.TranslationEntity;

public class TranslationServiceImpl implements TranslationService {

	@Override
	public Map<String, Object> translateInfo(String wordask) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, 3000);

		HttpConnectionParams.setSoTimeout(params, 3000);

		SchemeRegistry schreg = new SchemeRegistry();

		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));

		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);

		HttpClient client = new DefaultHttpClient(conman, params);

		String url = "http://japi.juhe.cn/youdao/dictionary.from?word="
				+ wordask
				+ "&key=afdda07f4218a184219cff390fe91147&dtype=json&only=" + "";

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("lxy", requestCode + "");
		if (requestCode != HttpStatus.SC_OK) {

			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_Object = new JSONObject(result);
			int error_code = result_Object.getInt("error_code");

			Log.i("lxy", error_code + "接收到错误码1");
			if (error_code == 211101) {
				throw new ServiceRuleException("输入的文本无法解析");
			} else if (error_code == 211102) {
				throw new ServiceRuleException("字符长度不能超过200个字符");
			} else if (error_code == 211103) {
				throw new ServiceRuleException("仅支持中英文互译");
			} else if (error_code == 211104) {
				throw new ServiceRuleException("查询无结果");
			} else if (error_code == 211105) {
				throw new ServiceRuleException("URI参数word错误或者为空");
			} else if (error_code == 211106) {
				throw new ServiceRuleException("网络异常无结果");
			} else if (error_code == 0)
			{
				JSONObject resultObject2 = result_Object.getJSONObject("result");
				JSONObject dataObject = resultObject2.getJSONObject("data");
				JSONObject basicObject  = dataObject.getJSONObject("basic");
				
				map.put("us-phonetic", basicObject.getString("us-phonetic"));
				
				map.put("uk-phonetic", basicObject.getString("uk-phonetic"));
				Log.i("wzs", "上一句木有问题");
				JSONArray explainsArray = basicObject.getJSONArray("explains");
				int len1 = explainsArray.length();
				String[] explation = new  String[len1];				
				for (int i = 0; i < len1; i++)
				{					
					explation[i] = explainsArray.getString(i)	;				  
					  Log.i("wzs", explation[i]);
					  
				}
				StringBuffer explationbBuffer = new StringBuffer();
				for (int i = 0; i < len1; i++) 
				{
					explationbBuffer.append(explation[i]);
					explationbBuffer.append('\n');
					explationbBuffer.append("            ");
				}
				map.put("explation", explationbBuffer.toString());
			}
			else if (error_code != 0) {
				throw new ServiceRuleException("系统错误");
			}
			return map;
		}
	}

	@Override
	public List<TranslationEntity> translateServiceInfo(String wordask)
			throws Exception {
		// TODO Auto-generated method stub
		
		String key = null;
		List<TranslationEntity> listTranslateEntities = new ArrayList<TranslationEntity>();
		// 定义一个params 参数
		HttpParams params = new BasicHttpParams();
		// 通过params 设置请求超时时间 ---〉ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// 通过params 设置响应超时时间 ---〉SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);
		// 定义一个方案注册类
		SchemeRegistry schreg = new SchemeRegistry();
		// 注册两个链接方案 HTTP和https（加密传输）
		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));
		// 通过params设置schemeRegistry（链接方案注册）
		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);
		// DefaultHttpClient
		// 需要设置两个参数一个是clientConnectinmannager（客户端链接管理器）和params（参数）

		HttpClient client = new DefaultHttpClient(conman, params);
		String url = "http://japi.juhe.cn/youdao/dictionary.from?word="
				+ wordask + "&key=2aa95d7c34766a9fff5a1eda2485e11b&only=" + "";
		Log.i("lxy", url);
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		int requestCode = response.getStatusLine().getStatusCode();
		if (requestCode != HttpStatus.SC_OK) {
			Log.i("lxy", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");// 得到code
			Log.i("lxy", error_code + "");
			if (error_code == 10014) {
				throw new ServiceRuleException("请求错误，请重试");
			} else if (error_code == 10004) {
				throw new ServiceRuleException("未知的请求源");
			} else if (error_code == 200103) {
				throw new ServiceRuleException("查询无结果");
			} else if (error_code == 0) 
			{
				
				JSONObject result_Object_result = result_object
						.getJSONObject("result");				
				JSONObject result_Object_data = result_Object_result
						.getJSONObject("data");				
				JSONArray result_array_web = result_Object_data
						.getJSONArray("web");				
				TranslationEntity result_array_web_resul;

				
				for (int i = 0; i < result_array_web.length(); i++) {					
					JSONObject result_array_web_Object = result_array_web
							.getJSONObject(i);					
					key = result_array_web_Object.getString("key");
					Log.i("lxy", key);
					
					JSONArray result_value = result_array_web_Object
							.getJSONArray("value");	
					int len = result_value.length();
					String[] strings = new String[len];
					for (int j = 0; j <len; j++) {
						strings[j] = result_value.getString(j);
					}
					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < len; j++) 
					{
						stringBuffer.append(strings[j]);
						stringBuffer.append(";");
					}
					
					result_array_web_resul = new TranslationEntity(stringBuffer.toString(), key);
					listTranslateEntities.add(result_array_web_resul);
					
				}
				
			}else if (error_code != 0) {
				throw new ServiceRuleException("系统错误");
			}

			Log.i("lxy", "yijingkaishhifanhui");
			return listTranslateEntities;
		}

	}

	@Override
	public Map<String, Object> translateHanziInfo(String wordask)
			throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, 3000);

		HttpConnectionParams.setSoTimeout(params, 3000);

		SchemeRegistry schreg = new SchemeRegistry();

		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));

		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);

		HttpClient client = new DefaultHttpClient(conman, params);

		String url = "http://japi.juhe.cn/youdao/dictionary.from?word="
				+ wordask
				+ "&key=afdda07f4218a184219cff390fe91147&dtype=json&only=" + "";

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("lxy", requestCode + "");
		if (requestCode != HttpStatus.SC_OK) {

			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_Object = new JSONObject(result);
			int error_code = result_Object.getInt("error_code");

			Log.i("lxy", error_code + "接收到错误码1");
			if (error_code == 211101) {
				throw new ServiceRuleException("输入的文本无法解析");
			} else if (error_code == 211102) {
				throw new ServiceRuleException("字符长度不能超过200个字符");
			} else if (error_code == 211103) {
				throw new ServiceRuleException("仅支持中英文互译");
			} else if (error_code == 211104) {
				throw new ServiceRuleException("查询无结果");
			} else if (error_code == 211105) {
				throw new ServiceRuleException("URI参数word错误或者为空");
			} else if (error_code == 211106) {
				throw new ServiceRuleException("网络异常无结果");
			} else if (error_code == 0)
			{
				JSONObject resultObject2 = result_Object.getJSONObject("result");
				JSONObject dataObject = resultObject2.getJSONObject("data");
				JSONObject basicObject  = dataObject.getJSONObject("basic");
				
				map.put("phonetic", basicObject.getString("phonetic"));
				
				Log.i("wzs", "上一句木有问题");
				JSONArray explainsArray = basicObject.getJSONArray("explains");
				int len1 = explainsArray.length();
				String[] explation = new  String[len1];				
				for (int i = 0; i < len1; i++)
				{					
					explation[i] = explainsArray.getString(i)	;				  
					  Log.i("wzs", explation[i]);
					  
				}
				StringBuffer explationbBuffer = new StringBuffer();
				for (int i = 0; i < len1; i++) 
				{
					explationbBuffer.append(explation[i]);
					explationbBuffer.append('\n');
					explationbBuffer.append("            ");
				}
				map.put("explation", explationbBuffer.toString());
			}
			else if (error_code != 0) {
				throw new ServiceRuleException("系统错误");
			}
			return map;
		}
	}

	@Override
	public String translateJuziInfo(String wordask)
			throws Exception {
		
		String map = null;

		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, 3000);

		HttpConnectionParams.setSoTimeout(params, 3000);

		SchemeRegistry schreg = new SchemeRegistry();

		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));

		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);

		HttpClient client = new DefaultHttpClient(conman, params);

		String url = "http://japi.juhe.cn/youdao/dictionary.from?word="
				+wordask
				+"&key=afdda07f4218a184219cff390fe91147&dtype=json&only=" + "";

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("lxy", requestCode + "");
		if (requestCode != HttpStatus.SC_OK) {

			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_Object = new JSONObject(result);
			int error_code = result_Object.getInt("error_code");

			Log.i("lxy", error_code + "接收到错误码1");
			if (error_code == 211101) {
				throw new ServiceRuleException("输入的文本无法解析");
			} else if (error_code == 211102) {
				throw new ServiceRuleException("字符长度不能超过200个字符");
			} else if (error_code == 211103) {
				throw new ServiceRuleException("仅支持中英文互译");
			} else if (error_code == 211104) {
				throw new ServiceRuleException("查询无结果");
			} else if (error_code == 211105) {
				throw new ServiceRuleException("URI参数word错误或者为空");
			} else if (error_code == 211106) {
				throw new ServiceRuleException("网络异常无结果");
			} else if (error_code == 0)
			{
				JSONObject resultObject2 = result_Object.getJSONObject("result");
				JSONObject dataObject = resultObject2.getJSONObject("data");
				JSONArray translationArray = dataObject.getJSONArray("translation");
				int len3 = translationArray.length();
				String[] str = new String[len3];
				
				for (int i = 0; i < len3; i++)
				{
					str[i] = translationArray.getString(i);
				}
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < len3; i++) 
				{
					stringBuffer.append(str[i]);					
					stringBuffer.append('\n');
					stringBuffer.append("            ");
				}
				
				map = 	stringBuffer.toString();													
			}
			else if (error_code != 0) {
				throw new ServiceRuleException("系统错误");
			}
			return map;
		}
	}
}
