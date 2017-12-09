package com.wzs.weather.cs;

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

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.weather.entity.Weather_Info;

import android.util.Log;

public class FindWeatherImpl implements FindWeather
{

	@Override
	public List<Weather_Info> getWeather_Info(String cityname)
			throws Exception {	
		List<Weather_Info> weather_Info_list = new ArrayList<Weather_Info>();
		
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
		
		String uri = "http://v.juhe.cn/weather/index?cityname=" +cityname
				+"&format=2"+"&key=03429692c907c8e5d37d69e8f9e3f58a";
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
			if (error_code == 203902) {
				Log.i("wzs", "查询不到该城市的天气");
				throw new ServiceRuleException("查询不到该城市的天气");
			} 
			else if (error_code == 203903) {
				Log.i("wzs", "查询出错，请重试");
				throw new ServiceRuleException("查询出错，请重试");
			} else if(error_code == 0)
			{
				JSONObject result_object2 = result_object.getJSONObject("result");
				JSONObject sk_object = result_object2.getJSONObject("sk");
				String temp = sk_object.getString("temp");
				String time = sk_object.getString("time");
				JSONObject today_object = result_object2.getJSONObject("today");
				String temperature = today_object.getString("temperature");
				//String temperature = getStanderTemperature(no_temperature);
				Log.i("wzs",temperature+"当天天气" );
				String weather = today_object.getString("weather");
				
				Log.i("wzs",weather );
				
				JSONObject weather_id_object = today_object.getJSONObject("weather_id");
				String weather_id = weather_id_object.getString("fa");
				String wind = today_object.getString("wind");
				String week = today_object.getString("week");
				String city = today_object.getString("city");
				String date_y = today_object.getString("date_y");
				String uv_index = today_object.getString("uv_index");
				String exercise_index = today_object.getString("exercise_index");
				Weather_Info weather_Info_today = new Weather_Info(temp, time, temperature, weather, weather_id, wind, week, city, date_y, uv_index, exercise_index);
				weather_Info_list.add(weather_Info_today);
				JSONArray future_array = result_object2.getJSONArray("future");
				int length = future_array.length();
				for (int i = 0; i < length; i++) 
				{
					JSONObject single_object = future_array.getJSONObject(i);
					String temperature1 = single_object.getString("temperature");
					//String temperature1 = getStanderTemperature(no_temperature1);
					String weather1 = single_object.getString("weather");					
					JSONObject single_weather_id_object = single_object.getJSONObject("weather_id");
					String weather_id1 = single_weather_id_object.getString("fa");
					Log.i("wzs", weather_id1+"链接服务中");
					String wind1 = single_object.getString("wind");
					String week1 = single_object.getString("week");
					String date1 = single_object.getString("date");
					weather_Info_list.add(new Weather_Info(temperature1, weather1, weather_id1, wind1, week1, date1));
					
				}
				
			}
			else if (error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		Log.i("wzs", "返回成功");
		return weather_Info_list;
	}

	@Override
	public String getWeather_Location(double latitude, double longitude)
			throws Exception {
		String cityname = null;
		String lat = latitude+"";
		String lon = longitude+"";
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
		
		String uri = "http://v.juhe.cn/weather/geo?lon=" +lon
				+"&lat="+lat+"&format=2"+"&key=03429692c907c8e5d37d69e8f9e3f58a";
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
			if (error_code == 203902) {
				Log.i("wzs", "查询不到该城市的天气");
				throw new ServiceRuleException("查询不到该城市的天气");
			} 
			else if (error_code == 203903) {
				Log.i("wzs", "查询出错，请重试");
				throw new ServiceRuleException("查询出错，请重试");
			} 
			else if (error_code == 203904||error_code == 203905) {
				
				throw new ServiceRuleException("定位错误，暂支持国内");
			}else if(error_code == 0)
			{
				JSONObject result_object2 = result_object.getJSONObject("result");
						
				JSONObject today_object = result_object2.getJSONObject("today");																				
				cityname = today_object.getString("city");
			
			}
			else if (error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return cityname;
	}


}


	

