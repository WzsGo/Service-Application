package com.wzs.mooc.service;

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

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.entity.TrainCheciInfo;
import com.wzs.mooc.entity.TrainZhanzhan_Piaojia_Info;
import com.wzs.mooc.entity.TrainZhanzhan_Yupiao_Info;

public class FindtrainImpl implements Findtrain {

/*********************************************************** 车次查询 ********************************************************************/
	@Override
	public TrainCheciInfo getTrain_info(String checi) throws Exception {
		TrainCheciInfo trainCheciInfo = null;
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
		Log.i("wzs", checi + "3");
		String uri = "http://apis.juhe.cn/train/s?name=" + checi
				+ "&key=a55d5a870483dca705f035e5c79a0640";
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
			if (error_code == 202202) {
				Log.i("wzs", "查询不到车次的相关信息");
				throw new ServiceRuleException("查询不到车次的相关信息");
			} else if (error_code == 202204) {
				Log.i("wzs", "查询不到结果");
				throw new ServiceRuleException("查询不到结果");
			}

			else if (error_code == 202208) {
				Log.i("wzs", "加载类库失败");
				throw new ServiceRuleException("错误的请求，请确认传递的参数正确");
			} else if (error_code == 202209) {
				Log.i("wzs", "查询失败");
				throw new ServiceRuleException("请求12306网络错误,请重试");
			} else if (error_code == 202212) {
				Log.i("wzs", "查询出错");
				throw new ServiceRuleException("查询出错");
			} else if (error_code == 0) {
				Log.i("wzs", "error_code == 0");
				JSONObject kuaidi_result = result_object
						.getJSONObject("result");
				JSONObject trainInfo_obj = kuaidi_result
						.getJSONObject("train_info");
				String name = trainInfo_obj.getString("name");
				String start = trainInfo_obj.getString("start");
				String end = trainInfo_obj.getString("end");
				String starttime = trainInfo_obj.getString("starttime");
				String endtime = trainInfo_obj.getString("endtime");
				String Smileage = trainInfo_obj.getString("mileage");
				Log.i("wzs", Smileage);
				trainCheciInfo = new TrainCheciInfo(name, start, end,
						starttime, endtime, Smileage);
				Log.i("wzs", trainCheciInfo.getStart());

			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return trainCheciInfo;
	}

	@Override
	public List<TrainCheciInfo> getTrain_List(String checi) throws Exception {
		List<TrainCheciInfo> Train_List = null;
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
		String uri = "http://apis.juhe.cn/train/s?name=" + checi
				+ "&key=a55d5a870483dca705f035e5c79a0640";

		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzslog", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzslog", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		}
		else 
		{
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 202202) {
				Log.i("wzs", "查询不到车次的相关信息");
				throw new ServiceRuleException("查询不到车次的相关信息");
			} else if (error_code == 2002204) {
				Log.i("wzs", "查询不到结果");
				throw new ServiceRuleException("查询不到结果");
			}

			else if (error_code == 202208) {
				Log.i("wzs", "加载类库失败");
				throw new ServiceRuleException("错误的请求，请确认传递的参数正确");
			} else if (error_code == 202209) {
				Log.i("wzs", "查询失败");
				throw new ServiceRuleException("请求12306网络错误,请重试");
			} else if (error_code == 202212) {
				Log.i("wzs", "查询出错");
				throw new ServiceRuleException("查询出错");
			} else if (error_code == 0) {
				Log.i("wzs", "error_code == 0");
				JSONObject kuaidi_result = result_object
						.getJSONObject("result");
				JSONArray station_list_Array = kuaidi_result
						.getJSONArray("station_list");
				int length = station_list_Array.length();
				JSONObject station_list_object = new JSONObject();
				Train_List = new ArrayList<TrainCheciInfo>();
				TrainCheciInfo trainCheci_resul;
				for (int i = 0; i < length; i++) {
					station_list_object = station_list_Array.getJSONObject(i);
					int train_id = station_list_object.getInt("train_id");
					String station_name = station_list_object
							.getString("station_name");
					String arrived_time = station_list_object
							.getString("arrived_time");
					String leave_time = station_list_object
							.getString("leave_time");
					String stay = station_list_object.getString("stay");
					String mileage = station_list_object.getString("mileage");

					Log.i("wzs", mileage + "第几次" + i);

					trainCheci_resul = new TrainCheciInfo(train_id,
							station_name, arrived_time, leave_time, mileage,
							stay);
					Log.i("wzs", trainCheci_resul.getStay() + "第几次" + i);

					Train_List.add(trainCheci_resul);

				}

			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return Train_List;
	}
/********************************************************站站查询——余票查询 **********************************************************************/
	@Override
	public List<TrainZhanzhan_Yupiao_Info> getTrainYupiao_List(String from,
			String to, String date) throws Exception {
		List<TrainZhanzhan_Yupiao_Info> trainYupiao_list = new ArrayList<TrainZhanzhan_Yupiao_Info>();
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
		String uri = "http://apis.juhe.cn/train/yp?from="+from+"&to="+to+"&date="+date+"&key=a55d5a870483dca705f035e5c79a0640";
		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzslog", requestCode + "");
		
		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		}
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if(error_code==202202)
			{
				Log.i("wzs", "查询不到车次的相关信息");
				throw new ServiceRuleException("查询不到车次的相关信息");
			}
			else if(error_code==202203)
			{
				Log.i("wzs", "出发站或终点站不能为空");
				throw new ServiceRuleException("出发站或终点站不能为空");
			}
			else if(error_code==202204)
			{
				Log.i("wzs", "查询不到结果");
				throw new ServiceRuleException("查询不到结果");
			}
			else if(error_code==202205)
			{
				Log.i("wzs", "错误的始发站名称");
				throw new ServiceRuleException("错误的始发站名称");
			}
			else if(error_code==202206)
			{
				Log.i("wzs", "错误的到达站名称");
				throw new ServiceRuleException("错误的到达站名称");
			}
			else if(error_code==202207)
			{
				Log.i("wzs", "查询不到余票相关数据哦");
				throw new ServiceRuleException("查询不到余票相关数据哦");
			}
			else if(error_code==202208)
			{
				Log.i("wzs", "错误的请求，请确认传递的参数正确");
				throw new ServiceRuleException("错误的请求，请确认传递的参数正确");
			}
			else if(error_code==202209)
			{
				Log.i("wzs", "请求12306网络错误,请重试");
				throw new ServiceRuleException("请求12306网络错误,请重试");
			}
			else if(error_code==2022012)
			{
				Log.i("wzs", "查询出错");
				throw new ServiceRuleException("查询出错");
			}
			else if(error_code==2022015)
			{
				Log.i("wzs", "排队失败");
				throw new ServiceRuleException("排队失败");
			}
					
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONArray result_array = result_object.getJSONArray("result");
				int length = result_array.length();
				for (int i = 0; i <length; i++) 
				{
					JSONObject result_sign_object = result_array.getJSONObject(i);
					String train_no = result_sign_object.getString("train_no");
					String start_time = result_sign_object.getString("start_time");
					String arrive_time = result_sign_object.getString("arrive_time");
					String train_class_name = result_sign_object.getString("train_class_name");
					String lishi = result_sign_object.getString("lishi");
					String rw_num = result_sign_object.getString("rw_num");
					String rz_num = result_sign_object.getString("rz_num");
					String tz_num = result_sign_object.getString("tz_num");
					String wz_num = result_sign_object.getString("wz_num");
					String yw_num = result_sign_object.getString("yw_num");
					String yz_num = result_sign_object.getString("yz_num");
					String ze_num = result_sign_object.getString("ze_num");
					String zy_num = result_sign_object.getString("zy_num");
					String swz_num = result_sign_object.getString("swz_num");
					Log.i("wzs",train_no );
					trainYupiao_list.add(new TrainZhanzhan_Yupiao_Info(train_no, start_time, arrive_time, train_class_name, lishi, rw_num, 
							rz_num, tz_num, wz_num, yw_num, yz_num, ze_num, zy_num, swz_num));
				}
			
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return trainYupiao_list;
	}
/************************************************************站站查询——票价查询*****************************************************/
	@Override
	public List<TrainZhanzhan_Piaojia_Info> getTrainPiaojia_list(String from,
			String to) throws Exception {
		List<TrainZhanzhan_Piaojia_Info>  trainPiaojia_List = new ArrayList<TrainZhanzhan_Piaojia_Info>();
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
		String uri = "http://apis.juhe.cn/train/s2swithprice?start="+from+"&end="+to+"&key=a55d5a870483dca705f035e5c79a0640";
		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzslog", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		}
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			/*switch (error_code) 
			{
			case 202202:
				Log.i("wzs", "查询不到车次的相关信息");
				throw new ServiceRuleException("查询不到车次的相关信息");
			case 202204:
				Log.i("wzs", "查询不到结果");
				throw new ServiceRuleException("查询不到结果");
			case 202205:
				Log.i("wzs", "错误的始发站名称");
				throw new ServiceRuleException("错误的始发站名称");
			case 202206:
				Log.i("wzs", "错误的到达站名称");
				throw new ServiceRuleException("错误的到达站名称");
			case 202207:
				Log.i("wzs", "查询不到余票相关数据哦");
				throw new ServiceRuleException("查询不到余票相关数据哦");
			case 202208:
				Log.i("wzs", "错误的请求，请确认传递的参数正确");
				throw new ServiceRuleException("错误的请求，请确认传递的参数正确");
			case 202209:
				Log.i("wzs", "请求12306网络错误,请重试");
				throw new ServiceRuleException("请求12306网络错误,请重试");
			case 2022012:
				Log.i("wzs", "查询出错");
				throw new ServiceRuleException("查询出错");
			case 2022015:
				Log.i("wzs", "排队失败");
				throw new ServiceRuleException("排队失败");			
			default:
				break;
			
			}
			if (error_code == 0)
			{*/
			if(error_code==202202)
			{
				Log.i("wzs", "查询不到车次的相关信息");
				throw new ServiceRuleException("查询不到车次的相关信息");
			}
			else if(error_code==202203)
			{
				Log.i("wzs", "出发站或终点站不能为空");
				throw new ServiceRuleException("出发站或终点站不能为空");
			}
			else if(error_code==202204)
			{
				Log.i("wzs", "查询不到结果");
				throw new ServiceRuleException("查询不到结果");
			}
			else if(error_code==202205)
			{
				Log.i("wzs", "错误的始发站名称");
				throw new ServiceRuleException("错误的始发站名称");
			}
			else if(error_code==202206)
			{
				Log.i("wzs", "错误的到达站名称");
				throw new ServiceRuleException("错误的到达站名称");
			}
			else if(error_code==202207)
			{
				Log.i("wzs", "查询不到余票相关数据哦");
				throw new ServiceRuleException("查询不到余票相关数据哦");
			}
			else if(error_code==202208)
			{
				Log.i("wzs", "错误的请求，请确认传递的参数正确");
				throw new ServiceRuleException("错误的请求，请确认传递的参数正确");
			}
			else if(error_code==202209)
			{
				Log.i("wzs", "请求12306网络错误,请重试");
				throw new ServiceRuleException("请求12306网络错误,请重试");
			}
			else if(error_code==2022012)
			{
				Log.i("wzs", "查询出错");
				throw new ServiceRuleException("查询出错");
			}
			else if(error_code==2022015)
			{
				Log.i("wzs", "排队失败");
				throw new ServiceRuleException("排队失败");
			}
					
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONObject result_Object2 = result_object.getJSONObject("result");
				JSONArray listArray = result_Object2.getJSONArray("list");
				int length = listArray.length();
				for (int i = 0; i <length; i++) 
				{
					JSONObject result_sign_object = listArray.getJSONObject(i);
					String train_no  = result_sign_object.getString("train_no");
					String start_time = result_sign_object.getString("start_time");
					String arrive_time = result_sign_object.getString("end_time");
					String train_type = result_sign_object.getString("train_type");
					String run_time = result_sign_object.getString("run_time");
					JSONArray price_list = result_sign_object.getJSONArray("price_list");					
					JSONObject price1_Object = price_list.getJSONObject(0);										
					String price_type1 = price1_Object.getString("price_type");
					String price1 = price1_Object.getString("price");					
					JSONObject price2_Object = price_list.getJSONObject(1);										
					String price_type2 = price2_Object.getString("price_type");
					String price2 = price2_Object.getString("price");
					Log.i("wzs", "票价查询结果 列车号："+train_no);
					trainPiaojia_List.add(new TrainZhanzhan_Piaojia_Info(train_no, train_type, start_time, arrive_time, run_time,
							price_type1, price1, price_type2, price2));	
				}			
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}	
	return trainPiaojia_List;
	}
}
