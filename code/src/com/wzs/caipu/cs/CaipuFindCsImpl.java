package com.wzs.caipu.cs;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.service.ServiceRuleException;

public class CaipuFindCsImpl implements CaipuFindCs
{
	public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
        	Log.i("wzs", e.toString()+"获取URL异常");
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
        	Log.i("wzs", e.toString()+"加载图片异常");
            e.printStackTrace();
        }
        return bitmap;
    }

	@Override
	public List<Map<String, Object>> getCaifuInfo(String menu) throws Exception {
		List<Map<String, Object>> caipuInfo_list = new ArrayList<Map<String, Object>>();
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
		String uri = "http://apis.juhe.cn/cook/query.php?menu=" +menu+"&albums=1"+"&key=4ef69e127000de30d6ae0f86740902f1";
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
			if (error_code == 204602) {
				Log.i("wzs", "	查询不到相关信息");
				throw new ServiceRuleException("查询不到相关信息");
			} else if (error_code == 204603) {
				Log.i("wzs", "菜谱名过长");
				throw new ServiceRuleException("菜谱名过长");
			} else if (error_code == 204605) {
				Log.i("wzs", "查询不到数据");
				throw new ServiceRuleException("查询不到数据");
			}
			else if (error_code == 0)
			{
				JSONObject result_object2 = result_object.getJSONObject("result");
				JSONArray dataArray = result_object2.getJSONArray("data");
				int length = dataArray.length();
				for (int i = 0; i < length; i++) 
				{
					JSONObject single_object = dataArray.getJSONObject(i);
					map = new HashMap<String, Object>(); 
					map.put("title",single_object.getString("title") );
					map.put("tags",single_object.getString("tags") );
					map.put("ingredients",single_object.getString("ingredients") );
					map.put("burden",single_object.getString("burden") );
					String url = single_object.getString("albums");						
					map.put("albums",returnBitMap(url) );					
					caipuInfo_list.add(map);				
				}
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		return caipuInfo_list;
	}

	@Override
	public List<List<Map<String , Object>>> getCaipuStepInfo(String menu) throws Exception {
		//List<Map<String, Object>> caipuStepInfo_list = new ArrayList<Map<String, Object>>();
		List<List<Map<String , Object>>> caipuStepInfo_list = new ArrayList<List<Map<String , Object>>>();
		List<Map<String , Object>> list = new ArrayList<Map<String,Object>>();
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
		String uri = "http://apis.juhe.cn/cook/query.php?menu=" +menu+"&albums=1"+"&key=4ef69e127000de30d6ae0f86740902f1";
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
			if (error_code == 204602) {
				Log.i("wzs", "	查询不到相关信息");
				throw new ServiceRuleException("	查询不到相关信息");
			} else if (error_code == 204603) {
				Log.i("wzs", "菜谱名过长");
				throw new ServiceRuleException("菜谱名过长");
			} else if (error_code == 204605) {
				Log.i("wzs", "查询不到数据");
				throw new ServiceRuleException("查询不到数据");
			}
			else if (error_code == 0)
			{
				JSONObject result_object2 = result_object.getJSONObject("result");
				JSONArray dataArray = result_object2.getJSONArray("data");				
				int length = dataArray.length();
				for (int i = 0; i < length; i++) 
				{
					
					JSONObject single_object = dataArray.getJSONObject(i);
					JSONArray stepsArray = single_object.getJSONArray("steps");
					int length1 = stepsArray.length();
					list = new ArrayList<Map<String,Object>>();
					for (int j = 0; j < length1; j++) 
					{
						JSONObject single_object2 = stepsArray.getJSONObject(j);
						
						
						
						map = new HashMap<String, Object>(); 
						
						map.put("step", single_object2.getString("step"));
						
						String url = single_object2.getString("img");													
																
						map.put("img",returnBitMap(url) );
						
						list.add(map);	
						
					}
					caipuStepInfo_list.add(list);	
					
				}
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
			}
		}
		
		return caipuStepInfo_list;
	}

}
