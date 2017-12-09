package com.wzs.mooc.service;


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
import org.json.JSONObject;

import android.util.Log;

import com.wzs.mooc.ConstantActivity;





public class FindIDImpl implements FindID{

	
	@Override
	public String[] getIDAdress(String IpAdress) throws Exception {
		
		String[] resultStrings = null;
		//定义一个params 参数
		HttpParams params = new BasicHttpParams();
		//通过params 设置请求超时时间 ---〉ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		//通过params 设置响应超时时间 ---〉SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);
		//定义一个方案注册类
		SchemeRegistry schreg = new SchemeRegistry();		
		//注册两个链接方案 HTTP和https（加密传输）
		schreg.register(new Scheme("http",PlainSocketFactory.getSocketFactory(), 80) );
		schreg.register(new Scheme("https",PlainSocketFactory.getSocketFactory(), 433) );
		//通过params设置schemeRegistry（链接方案注册）
		ClientConnectionManager conman = new ThreadSafeClientConnManager(params , schreg );
		//DefaultHttpClient 需要设置两个参数一个是clientConnectinmannager（客户端链接管理器）和params（参数）
		HttpClient client = new DefaultHttpClient(conman , params);				
		String uri = "http://apis.juhe.cn/ip/ip2addr?ip="+IpAdress+"&key=8896f7c96ac1e83f7e343952a1ba2deb";		
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		int requestCode = response.getStatusLine().getStatusCode();
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
			
			 if(error_code == 200102)
			 {
				 Log.i("wzs", "错误的ip");
				 throw new ServiceRuleException("错误的IP地址");
			 }
			 else if(error_code == 200103)
			 {
				 throw new ServiceRuleException("查询无结果");
			 }
			 
			 else if(error_code == 200105)
			 {
				 throw new ServiceRuleException("查询无结果");
			 }
			 else if(error_code == 0)
			 {
				 Log.i("wzs", "error_code == 0");
				 JSONObject ip_result = result_object.getJSONObject("result");
				 String ip_result_area = ip_result.getString("area");
				 String ip_result_location = ip_result.getString("location");
				 resultStrings = new String[2];
				 Log.i("wzs",resultStrings.length+"");
				 resultStrings[0] = ip_result_area;
				 Log.i("wzs",  resultStrings[0]+"");
				 resultStrings[1] = ip_result_location;
			 }
			 else if(error_code != 0)
				{
					throw new Exception("系统错误");
				}

			 
		}
		
		return resultStrings;
		
		
		
		/*HttpURLConnection connection = null;
		URL url = null;
		try
		{
			String urlstring ="http://apis.juhe.cn/ip/ip2addr?ip="+IpAdress+"&key=8896f7c96ac1e83f7e343952a1ba2deb";
			url = new URL(urlstring);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(3000);
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.connect();
			
			
			int request_code = connection.getResponseCode();
			if(request_code != HttpURLConnection.HTTP_OK)
			{
				throw new Exception("服务器请求错误");
			}
			else
			{
				JSONObject JsonResult = connection.get
			}
		}
		finally
		{
			if(connection != null)
			{
				connection.disconnect();
			}
			
		}*/
		
	}	
}
