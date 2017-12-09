package com.wzs.coach.cs;

import java.util.ArrayList;
import java.util.List;

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

import com.wzs.coach.entity.coach_from_to_entity;
import com.wzs.coach.entity.coach_station_info_entity;
import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.service.ServiceRuleException;

public class FindCoachImpl implements FindCoach{
/*******************************************汽车信息查询***********************************************************************/
	@Override
	public List<coach_station_info_entity> getCoach_station_info_list(
			String station) throws Exception {
		List<coach_station_info_entity> station_result_List = new ArrayList<coach_station_info_entity>();		
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
		String uri = "http://op.juhe.cn/onebox/bus/query?station=" +station+ "&key=b03b6a9b5b1fbfbebffe55cd224fe092";
		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzs", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 208201) {
				Log.i("wzs", "查询不到该汽车站相关信息");
				throw new ServiceRuleException("查询不到该汽车站相关信息");
			} else if (error_code == 208203) {
				Log.i("wzs", "网络错误，请重试");
				throw new ServiceRuleException("网络错误，请重试");
			} else if (error_code == 208202) {
				Log.i("wzs", "查询不到该汽车站相关信息");
				throw new ServiceRuleException("查询不到该汽车站相关信息");
			}
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONObject station_result_obj = result_object.getJSONObject("result");
				JSONArray station_result_Arr = station_result_obj.getJSONArray("list");
				int length = station_result_Arr.length();
				for (int i = 0; i <length; i++) 
				{
					JSONObject single_result_obj = station_result_Arr.getJSONObject(i);
					String name = single_result_obj.getString("name");
					String tel = single_result_obj.getString("tel");
					String adds = single_result_obj.getString("adds");
					Log.i("wzs", adds);
					station_result_List.add(new coach_station_info_entity(name, tel, adds));
				}
				

			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		
		return station_result_List;
	}
/********************************************汽车站站查询********************************************************************/
	@Override
	public List<coach_from_to_entity> getCoach_from_to_list(String from,
			String too, String time) throws Exception {
		List<coach_from_to_entity> from_result_list = new ArrayList<coach_from_to_entity>();
		
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
		String uri = "http://op.juhe.cn/onebox/bus/query_ab?from=" + from+"&to="+too+ "&key=b03b6a9b5b1fbfbebffe55cd224fe092";
		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzs", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 208201) {
				Log.i("wzs", "查询不到该汽车站相关信息");
				throw new ServiceRuleException("查询不到该汽车站相关信息");
			} else if (error_code == 208203) {
				Log.i("wzs", "网络错误，请重试");
				throw new ServiceRuleException("网络错误，请重试");
			}
			else if (error_code == 208205) {
				Log.i("wzs", "查询不到该汽车行程相关信息");
				throw new ServiceRuleException("查询不到该汽车行程相关信息");
			}						
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONObject station_result_obj = result_object.getJSONObject("result");
				JSONArray station_result_Arr = station_result_obj.getJSONArray("list");
				int length = station_result_Arr.length();
				for (int i = 0; i <length; i++) 
				{
					JSONObject single_result_obj = station_result_Arr.getJSONObject(i);
					String start = single_result_obj.getString("start");
					String arrive = single_result_obj.getString("arrive");
					String date = single_result_obj.getString("date");
					String price = single_result_obj.getString("price");
					Log.i("wzs", price +"汽车站站查询");
					String time1 = time;
					from_result_list.add(new coach_from_to_entity(start, arrive, date, price, time1));
				}
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		return from_result_list;
		}
	}
}
