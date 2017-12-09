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
import com.wzs.mooc.entity.KuaidiEntity;

public class FindKuaidiImpl implements FindKuaidi {

	@Override
	public List<KuaidiEntity> getKuaidiInfo(String kuaidicom, String kuaidino)
			throws Exception {
		List<KuaidiEntity> listKuaidiEntities = new ArrayList<KuaidiEntity>();

		// ����һ��params ����
		HttpParams params = new BasicHttpParams();
		// ͨ��params ��������ʱʱ�� ---��ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// ͨ��params ������Ӧ��ʱʱ�� ---��SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);
		// ����һ������ע����
		SchemeRegistry schreg = new SchemeRegistry();
		// ע���������ӷ��� HTTP��https�����ܴ��䣩
		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));
		// ͨ��params����schemeRegistry�����ӷ���ע�ᣩ
		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);
		// DefaultHttpClient
		// ��Ҫ������������һ����clientConnectinmannager���ͻ������ӹ���������params��������
		HttpClient client = new DefaultHttpClient(conman, params);
		// String uri =
		// "http://apis.juhe.cn/ip/ip2addr?ip="+IpAdress+"&key=8896f7c96ac1e83f7e343952a1ba2deb";
		Log.i("wzs", kuaidicom + "  �����");
		Log.i("wzs", kuaidino + "  �����");
		// String uri =
		// "http://v.juhe.cn/exp/index��key=ed13b0c26c76547fb494091aadf893e2&dtype=json&com="+kuaidicom+"&no="+kuaidino;
		String uri = "http://v.juhe.cn/exp/index?com=" + kuaidicom + "&no="
				+ kuaidino + "&key=ed13b0c26c76547fb494091aadf893e2";
		Log.i("wzs", uri);

		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzslog", "��ʼ����");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzslog", requestCode + "");
		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "�׳�ϵͳ�쳣");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "���յ�������");
			if (error_code == 204301) {
				Log.i("wzs", "δ��ʶ��Ŀ�ݹ�˾");
				throw new ServiceRuleException("��Ϣ����");
			} else if (error_code == 204302) {
				Log.i("wzs", "����д��ȷ�Ŀ�ݵ���");
				throw new ServiceRuleException("����д��ȷ�Ŀ�ݵ���");
			}

			else if (error_code == 204303) {
				Log.i("wzs", "�������ʧ��");
				throw new ServiceRuleException("�������ʧ��");
			} else if (error_code == 204304) {
				Log.i("wzs", "��ѯʧ��");
				throw new ServiceRuleException("��ѯʧ��");
			} 
			else if (error_code == 0) {
				Log.i("wzs", "error_code == 0");
				JSONObject kuaidi_result = result_object
						.getJSONObject("result");
				JSONArray kuadi_Array = kuaidi_result.getJSONArray("list");
				int length = kuadi_Array.length();
				Log.i("wzslog", "�õ�����");
				for (int i = 0; i < length; i++) {
					JSONObject kuaidi_Info_Object = kuadi_Array
							.getJSONObject(i);
					
					String datetime = kuaidi_Info_Object.getString("datetime");
					String remark = kuaidi_Info_Object.getString("remark");
					String zone = kuaidi_Info_Object.getString("zone");
					Log.i("wzs", datetime);
					KuaidiEntity result1 = new KuaidiEntity(datetime, remark,
							zone);
					listKuaidiEntities.add(result1);
				}
			}
			else if(error_code != 0)
			{
				throw new Exception("ϵͳ����");
			}
			return listKuaidiEntities;
		}
	}
}
