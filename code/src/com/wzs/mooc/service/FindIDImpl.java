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
		//����һ��params ����
		HttpParams params = new BasicHttpParams();
		//ͨ��params ��������ʱʱ�� ---��ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		//ͨ��params ������Ӧ��ʱʱ�� ---��SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);
		//����һ������ע����
		SchemeRegistry schreg = new SchemeRegistry();		
		//ע���������ӷ��� HTTP��https�����ܴ��䣩
		schreg.register(new Scheme("http",PlainSocketFactory.getSocketFactory(), 80) );
		schreg.register(new Scheme("https",PlainSocketFactory.getSocketFactory(), 433) );
		//ͨ��params����schemeRegistry�����ӷ���ע�ᣩ
		ClientConnectionManager conman = new ThreadSafeClientConnManager(params , schreg );
		//DefaultHttpClient ��Ҫ������������һ����clientConnectinmannager���ͻ������ӹ���������params��������
		HttpClient client = new DefaultHttpClient(conman , params);				
		String uri = "http://apis.juhe.cn/ip/ip2addr?ip="+IpAdress+"&key=8896f7c96ac1e83f7e343952a1ba2deb";		
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		int requestCode = response.getStatusLine().getStatusCode();
		if (requestCode != HttpStatus.SC_OK)
		{
			Log.i("wzs", "�׳�ϵͳ�쳣");
			 throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		}
		else
		{
			 String result = EntityUtils.toString(response.getEntity());
			 JSONObject result_object = new JSONObject(result);
			 int error_code = result_object.getInt("error_code");			 
			
			 if(error_code == 200102)
			 {
				 Log.i("wzs", "�����ip");
				 throw new ServiceRuleException("�����IP��ַ");
			 }
			 else if(error_code == 200103)
			 {
				 throw new ServiceRuleException("��ѯ�޽��");
			 }
			 
			 else if(error_code == 200105)
			 {
				 throw new ServiceRuleException("��ѯ�޽��");
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
					throw new Exception("ϵͳ����");
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
				throw new Exception("�������������");
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
