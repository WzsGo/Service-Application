package com.demo.phonenumberactivity.service;

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

import com.demo.phonenumberactivity.ServiceRuleException;

public class UserServiceSub implements PhoneService {
	
	//public String[] resultStrings = null;

	@Override
	public String[] userService(String txtname) throws Exception {
		Log.i("lxy", txtname+"3");
		 
		
		 String[] resultStrings = null;		
		//����һ��params ����
		HttpParams params = new BasicHttpParams();
		//ͨ��params ��������ʱʱ�� ---��ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		//ͨ��params ������Ӧ��ʱʱ�� ---��SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);		
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
	
		
		 
		 Log.i("lxy", txtname+"4");
		String uir= "http://apis.juhe.cn/mobile/get?phone="+txtname+"&key=60359d1b4c42b2fe929880097fd8c5ee";		
		Log.i("lxy",uir);
		
		Log.i("lxy", "����getǰ");
		HttpGet get=new HttpGet(uir);
		Log.i("lxy", "ִ��getǰ");
		HttpResponse response = client.execute(get);
		Log.i("lxy", "�õ�requestCodeǰ");
		int requestCode = response.getStatusLine().getStatusCode();		
		Log.i("lxy", requestCode + "");//200
		if (requestCode != HttpStatus.SC_OK) {
			Log.i("lxy", "�׳��쳣");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {

			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");	
			Log.i("lxy", error_code+"");
		  if(error_code == 0)
		 {
			 Log.i("lxy", "error_code ==0");
			 JSONObject ip_result = result_object.getJSONObject("result");
			 String ip_result_province = ip_result.getString("province");
			 String ip_result_city = ip_result.getString("city");
			 String  ip_result_areacode=ip_result.getString("areacode");
			 String ip_result_zip = ip_result.getString("zip");
			 String ip_result_company = ip_result.getString("company");
			 
			 resultStrings = new String[5];
			 
			 
			 resultStrings[0] = ip_result_province;

			 Log.i("lxy",  resultStrings[0]+"");
			 resultStrings[1] = ip_result_city ;
			 
			 resultStrings[2] = ip_result_areacode;
			
			 resultStrings[3] = ip_result_zip ;
			 
			
			 resultStrings[4] = ip_result_company  ;
		 }
		  else if(error_code ==201101 )
		 {
			 Log.i("lxy",error_code+"" );
			
			 throw new ServiceRuleException("�ֻ����벻��Ϊ��");
		 }
		 else if(error_code == 201101)
		 {
			 throw new ServiceRuleException("������ֻ�����");
		 }
		 
		 else if(error_code == 201103)
		 {
			 throw new ServiceRuleException("��ѯ�޽��");
		 }
	
			
		
		}
		return resultStrings;
		
	}
}
	
	

	



