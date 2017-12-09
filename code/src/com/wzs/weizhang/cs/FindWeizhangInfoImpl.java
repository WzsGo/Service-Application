package com.wzs.weizhang.cs;

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
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.weizhang.entity.Weizhang_CityInfo;
import com.wzs.weizhang.entity.Weizhang_ResultInfo;

public class FindWeizhangInfoImpl implements FindWeizhangInfo{
	

	@Override
	public Map<String, Object> getWeizhangCityInfo(String province_id)
			throws Exception {
		Map<String, Object> cityMap = new HashMap<String, Object>();
		List<Weizhang_CityInfo> city_list = new ArrayList<Weizhang_CityInfo>();
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
		String uri = "http://v.juhe.cn/wz/citys?province=" +province_id+"&dtype=&format=&callback=&key=7c531ee998f232e572bdb59149523c57";
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
			if (error_code == 203603) {
				Log.i("wzs", "	查询不到相关信息");
				throw new ServiceRuleException("网络错误请重试");
			} else if (error_code == 203604) {
				Log.i("wzs", "传递参数的格式不正确");
				throw new ServiceRuleException("传递参数的格式不正确");
			} else if (error_code == 203605) {
				Log.i("wzs", "没找到此城市代码或该城市正在维护");
				throw new ServiceRuleException("没找到此城市代码或该城市正在维护");
			}
			else if (error_code == 203606) {
				Log.i("wzs", "车辆信息错误,请确认输入的信息正确");
				throw new ServiceRuleException("车辆信息错误,请确认输入的信息正确");
			}
			else if (error_code == 203607) {
				Log.i("wzs", "交管局网络原因暂时无法查询");
				throw new ServiceRuleException("交管局网络原因暂时无法查询");
			}
			else if (error_code == 203608) {
				Log.i("wzs", "您好,你所查询的城市正在维护或未开通查询");
				throw new ServiceRuleException("您好,你所查询的城市正在维护或未开通查询");
			}
			else if (error_code == 0)
			{
				JSONObject result_Object2 = result_object.getJSONObject("result") ;
				JSONObject result_Object3 = result_Object2.getJSONObject(province_id);
				JSONArray citysArray = result_Object3.getJSONArray("citys");
				int len1 = citysArray.length();
				String[] city_name = new String[len1];
				for (int i = 0; i < len1; i++) 
				{
					JSONObject singleObjecte = citysArray.getJSONObject(i);
					city_name[i] =  singleObjecte.getString("city_name");
					
					String city_code = singleObjecte.getString("city_code");
					String abbr = singleObjecte.getString("abbr");
					int engine = singleObjecte.getInt("engine");
					int engineno = singleObjecte.getInt("engineno");
					int classa = singleObjecte.getInt("classa");					
					int classno = singleObjecte.getInt("classno");
					
					city_list.add(new Weizhang_CityInfo(city_code, abbr, engine, engineno, 
							classa,  classno));		
				}
				cityMap.put("city_list",city_list);
				cityMap.put("city_name", city_name);
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return cityMap;
	}

	public String uri;
	@Override
	public List<Weizhang_ResultInfo> getWeizhangResult(String city, String hphm,
			String engineno, String classno) throws Exception {
		List<Weizhang_ResultInfo> weizhang_ResultInfo_list = new ArrayList<Weizhang_ResultInfo>();
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
		
		if (engineno.equals("")&&!classno.equals("")) 
		{
			 uri = "http://v.juhe.cn/wz/query?city=" +city+"&hphm="+hphm+"&classno="+classno+"&hpzl=02"+
					"&dtype=&format=&callback=&key=7c531ee998f232e572bdb59149523c57";
		}
		else if (!engineno.equals("")&&classno.equals("")) {
			 uri = "http://v.juhe.cn/wz/query?city=" +city+"&hphm="+hphm+"&engineno="+engineno+"&hpzl=02"+
					"&dtype=&format=&callback=&key=7c531ee998f232e572bdb59149523c57";
		}
		else if (!engineno.equals("")&&!classno.equals("")) {
			uri = "http://v.juhe.cn/wz/query?city=" +city+"&hphm="+hphm+"&engineno="+engineno+"&classno="+classno+"&hpzl=02"+
					"&dtype=&format=&callback=&key=7c531ee998f232e572bdb59149523c57";
		}		
		
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
			if (error_code == 203603) {
				Log.i("wzs", "	查询不到相关信息");
				throw new ServiceRuleException("网络错误请重试");
			} else if (error_code == 203604) {
				Log.i("wzs", "传递参数的格式不正确");
				throw new ServiceRuleException("传递参数的格式不正确");
			} else if (error_code == 203605) {
				Log.i("wzs", "没找到此城市代码或该城市正在维护");
				throw new ServiceRuleException("没找到此城市代码或该城市正在维护");
			}
			else if (error_code == 203606) {
				Log.i("wzs", "车辆信息错误,请确认输入的信息正确");
				throw new ServiceRuleException("车辆信息错误,请确认输入的信息正确");
			}
			else if (error_code == 203607) {
				Log.i("wzs", "交管局网络原因暂时无法查询");
				throw new ServiceRuleException("交管局网络原因暂时无法查询");
			}
			else if (error_code == 203608) {
				Log.i("wzs", "您好,你所查询的城市正在维护或未开通查询");
				throw new ServiceRuleException("您好,你所查询的城市正在维护或未开通查询");
			}
			else if (error_code == 0)
			{
				JSONObject resultObject2 = result_object.getJSONObject("result");
				JSONArray listsArray = resultObject2.getJSONArray("lists");
				int len = listsArray.length();
				for (int i = 0; i < len; i++) 
				{
					JSONObject singleObject = listsArray.getJSONObject(i);
					String date = singleObject.getString("date");
					String area = singleObject.getString("area");
					String act = singleObject.getString("act");
					String code = singleObject.getString("code");
					String fen = singleObject.getString("fen");
					String money = singleObject.getString("money");
					String handled = singleObject.getString("handled");
					weizhang_ResultInfo_list.add(new Weizhang_ResultInfo(date, area, act, code,
							fen, money, handled));
				}
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return weizhang_ResultInfo_list;
	}

}
