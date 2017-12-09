package com.demo.xinhuaactivity.service;

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

import com.demo.xinhuaactivity.service.ServiceRuleException;
import com.wzs.mooc.ConstantActivity;

import entity_xinhua.XinHuaEntity;


public class XinHuaSericeImpl implements XinHuaService{
	public String[] xinhuaInfo(String askword) throws Exception{
		String[] resultStrings = null;
		HttpParams params=new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 2000);
		HttpConnectionParams.setSoTimeout(params, 2000);
		SchemeRegistry schreg=new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
		schreg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conman=new ThreadSafeClientConnManager(params, schreg);
		HttpClient client=new DefaultHttpClient(conman,params);
		Log.i("lxy",askword+"4" );
		String url="http://v.juhe.cn/xhzd/query?word="+askword+"&key=587c4ef286d2970bd4a9ea72c64c8c42&dtype=json";
		Log.i("lxy", url);
		HttpGet get=new HttpGet(url);
		Log.i("lxy", "get前");
		HttpResponse response=client.execute(get);
		Log.i("lxy", "开始请求");
		int requestCode=response.getStatusLine().getStatusCode();
		Log.i("lxy", requestCode+"");
		if (requestCode!=HttpStatus.SC_OK) {
			Log.i("lxy", "抛出异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
			}
		else {
			String result=EntityUtils.toString(response.getEntity());
			JSONObject result_object=new JSONObject(result);
			int error_code=result_object.getInt("error_code");
			Log.i("lxy", error_code+"接收到错误码");
			if (error_code==10014) {
				Log.i("lxy", error_code+"");
				throw new ServiceRuleException("请求错误，请重试");
				}else if (error_code==10012) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("请求超过次数限制");
				}
			else if (error_code==0) {
				Log.i("lxy", error_code+"");
				JSONObject xinhua_result=result_object.getJSONObject("result");
				resultStrings = new String[3];
				resultStrings[0]=xinhua_result.getString("bihua");
				resultStrings[1]=xinhua_result.getString("bushou");
				resultStrings[2]=xinhua_result.getString("pinyin");
				}
			Log.i("lxy", resultStrings[0]);
			Log.i("lxy", resultStrings[1]);
			Log.i("lxy", resultStrings[2]);
		   return resultStrings;
		}
	}
	public  String xinhauServiceInfo(String askword) throws Exception {
		
		String jijie=null;		
		HttpParams params=new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params,2000);
		HttpConnectionParams.setSoTimeout(params, 2000);
		SchemeRegistry schreg=new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conman=new ThreadSafeClientConnManager(params, schreg);
		HttpClient client=new DefaultHttpClient(conman,params);
		String url="http://v.juhe.cn/xhzd/query?word="+askword+"&key=587c4ef286d2970bd4a9ea72c64c8c42&dtype=json";
		Log.i("lxy", url);
		HttpGet get=new HttpGet(url);
		Log.i("lxy", "get前");
		HttpResponse response=client.execute(get);
		Log.i("lxy", "开始请求");
		int requesCode=response.getStatusLine().getStatusCode();
		Log.i("lxy", requesCode+"");
		if (requesCode!=HttpStatus.SC_OK) {
			Log.i("lxy", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
			}else {
				String result=EntityUtils.toString(response.getEntity());
				JSONObject result_object=new JSONObject(result);
				int error_code=result_object.getInt("error_code");
				Log.i("lxy", error_code+"接收到错误码");
				if (error_code==10014) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("请求错误，请重试");
				}else if (error_code==10012) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("请求超过次数限制");
				}
				else if (error_code==0) {
					Log.i("lxy", error_code+"");
					JSONObject xinhua_result=result_object.getJSONObject("result");
					JSONArray jijieArray =xinhua_result.getJSONArray("jijie");
					//int len1  = jijieArray.length();
					String[] str = new String[5] ;
					for (int i = 0; i < 5; i++)
					{
						str[i] = jijieArray.getString(i);
					
					}
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < 5; i++)
					{
						strBuffer.append(str[i]);
						strBuffer.append('\n');
						strBuffer.append("        ");
					}
					jijie = strBuffer.toString();
				}	
				else if (error_code!= 0) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("系统错误");
				}
			}
			return jijie;
		}
	@Override
	public List<XinHuaEntity> getXiangjieInfo(String askword) throws Exception {
		List<XinHuaEntity> xiangjie = new ArrayList<XinHuaEntity>();
		
		HttpParams params=new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params,2000);
		HttpConnectionParams.setSoTimeout(params, 2000);
		SchemeRegistry schreg=new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conman=new ThreadSafeClientConnManager(params, schreg);
		HttpClient client=new DefaultHttpClient(conman,params);
		String url="http://v.juhe.cn/xhzd/query?word="+askword+"&key=587c4ef286d2970bd4a9ea72c64c8c42&dtype=json";
		Log.i("lxy", url);
		HttpGet get=new HttpGet(url);
		Log.i("lxy", "get前");
		HttpResponse response=client.execute(get);
		Log.i("lxy", "开始请求");
		int requesCode=response.getStatusLine().getStatusCode();
		Log.i("lxy", requesCode+"");
		if (requesCode!=HttpStatus.SC_OK) {
			Log.i("lxy", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
			}else {
				String result=EntityUtils.toString(response.getEntity());
				JSONObject result_object=new JSONObject(result);
				int error_code=result_object.getInt("error_code");
				Log.i("lxy", error_code+"接收到错误码");
				if (error_code==10014) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("请求错误，请重试");
				}else if (error_code==10012) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("请求超过次数限制");
				}
				else if (error_code==0) {
					Log.i("lxy", error_code+"");
					JSONObject xinhua_result=result_object.getJSONObject("result");
					JSONArray xiangjieArray =xinhua_result.getJSONArray("xiangjie");
					int len1  = xiangjieArray.length();
					String[] str = new String[len1] ;
					for (int i = 0; i < len1; i++)
					{
						str[i] = xiangjieArray.getString(i);
						String newString = str[i];
						
						xiangjie.add(new XinHuaEntity(newString));
					}					
				}	
				else if (error_code!= 0) {
					Log.i("lxy", error_code+"");
					throw new ServiceRuleException("系统错误");
				}
			}
		return xiangjie;
	}
		
}
