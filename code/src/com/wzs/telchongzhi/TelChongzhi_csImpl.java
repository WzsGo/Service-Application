package com.wzs.telchongzhi;

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
import com.wzs.mooc.service.ServiceRuleException;


public class TelChongzhi_csImpl implements Telchongzhi_cs{

	@Override
	public int telChongzhi_tep1(String phoneno, String cardnum)
			throws Exception {
		int flag = 0;
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
		String uri = "http://op.juhe.cn/ofpay/mobile/telcheck?phoneno=" +phoneno+"&cardnum="+cardnum+ "&key=0b498a2e9a6b47a44ae24fce42c88b4e";
		Log.i("wzs", uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzs", "��ʼ����");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "�׳�ϵͳ�쳣");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "���յ�������");
			if (error_code == 208502) {
				Log.i("wzs", "�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
				throw new ServiceRuleException("�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
			} else if (error_code == 208501) {
				Log.i("wzs", "�������ֵ���ֻ����뼰���");
				throw new ServiceRuleException("�������ֵ���ֻ����뼰���");
			} else if (error_code == 208503) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "������ֻ�����");
				throw new ServiceRuleException("������ֻ�����");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "��ֵʧ��");
				throw new ServiceRuleException("��ֵʧ��");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "�����ֵʧ�ܣ�������");
				throw new ServiceRuleException("�����ֵʧ�ܣ�������");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "����Ķ�����");
				throw new ServiceRuleException("����Ķ�����");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "���󶩵�״̬ʧ�ܣ�������");
				throw new ServiceRuleException("���󶩵�״̬ʧ�ܣ�������");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "��ֵ��");
				throw new ServiceRuleException("��ֵ��");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "��ֵʧ��,���˿�");
				throw new ServiceRuleException("��ֵʧ��,���˿�");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "�˶���������");
				throw new ServiceRuleException("�˶���������");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("��ǰ�˻���������");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "���Ϲ淶�Ķ����ţ�8-32λ��");
				throw new ServiceRuleException("���Ϲ淶�Ķ����ţ�8-32λ��");
			}
			else if (error_code == 0)
			{
				flag = 1;
			}
			else if(error_code != 0)
			{
				throw new Exception("ϵͳ����");
			}
		}
		return flag;
	}

	@Override
	public TelChongzhiEntity telChongzhi_tep2(String phoneno, String cardnum)
			throws Exception {
		TelChongzhiEntity telChongzhiEntity = null;
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
		String uri = "http://op.juhe.cn/ofpay/mobile/telquery?phoneno=" +phoneno+"&cardnum="+cardnum+ "&key=0b498a2e9a6b47a44ae24fce42c88b4e";
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzs", "��ʼ����");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "�׳�ϵͳ�쳣");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "���յ�������");
			if (error_code == 208502) {
				Log.i("wzs", "�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
				throw new ServiceRuleException("�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
			} else if (error_code == 208501) {
				Log.i("wzs", "�������ֵ���ֻ����뼰���");
				throw new ServiceRuleException("�������ֵ���ֻ����뼰���");
			} else if (error_code == 208503) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "������ֻ�����");
				throw new ServiceRuleException("������ֻ�����");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "��ֵʧ��");
				throw new ServiceRuleException("��ֵʧ��");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "�����ֵʧ�ܣ�������");
				throw new ServiceRuleException("�����ֵʧ�ܣ�������");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "����Ķ�����");
				throw new ServiceRuleException("����Ķ�����");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "���󶩵�״̬ʧ�ܣ�������");
				throw new ServiceRuleException("���󶩵�״̬ʧ�ܣ�������");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "��ֵ��");
				throw new ServiceRuleException("��ֵ��");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "��ֵʧ��,���˿�");
				throw new ServiceRuleException("��ֵʧ��,���˿�");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "�˶���������");
				throw new ServiceRuleException("�˶���������");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("��ǰ�˻���������");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "���Ϲ淶�Ķ����ţ�8-32λ��");
				throw new ServiceRuleException("���Ϲ淶�Ķ����ţ�8-32λ��");
			}
			else if (error_code == 0)
			{
				JSONObject _Object = result_object.getJSONObject("result");
				String cardid = _Object.getString("cardid");
				String cardname = _Object.getString("cardname");
				String inprice = _Object.getString("inprice");
				String game_area = _Object.getString("game_area");
				Log.i("wzs", "��ֵ�Ŀ���"+cardid);
				Log.i("wzs", "��ֵ����"+cardname);
				Log.i("wzs", "�ۼ�"+inprice);
				Log.i("wzs", "������"+game_area);
				telChongzhiEntity = new TelChongzhiEntity(cardid, cardname, inprice, game_area);
			}
			else if(error_code != 0)
			{
				throw new Exception("ϵͳ����");
			}
		}
		return telChongzhiEntity;
	}

	@Override
	public TelChongzhiEntity telChongzhi_tep3(String phoneno, String cardnum,
			String orderid, String sign) throws Exception {
		
		TelChongzhiEntity telChongzhiEntity = null;
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
		String uri = "http://op.juhe.cn/ofpay/mobile/telquery?phoneno="+phoneno+"&cardnum="+cardnum+"&orderid="+orderid+"&sign="+sign+"&key=0b498a2e9a6b47a44ae24fce42c88b4e";
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzs", "��ʼ����");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "�׳�ϵͳ�쳣");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "���յ�������");
			if (error_code == 208502) {
				Log.i("wzs", "�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
				throw new ServiceRuleException("�����ֻ��ź���ֵ��ѯ��Ʒ��Ϣʧ�ܣ�������");
			} else if (error_code == 208501) {
				Log.i("wzs", "�������ֵ���ֻ����뼰���");
				throw new ServiceRuleException("�������ֵ���ֻ����뼰���");
			} else if (error_code == 208503) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
				throw new ServiceRuleException("��Ӫ�̵���ά�����ݲ��ܳ�ֵ");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "������ֻ�����");
				throw new ServiceRuleException("������ֻ�����");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "��ֵʧ��");
				throw new ServiceRuleException("��ֵʧ��");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "�����ֵʧ�ܣ�������");
				throw new ServiceRuleException("�����ֵʧ�ܣ�������");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "����Ķ�����");
				throw new ServiceRuleException("����Ķ�����");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "���󶩵�״̬ʧ�ܣ�������");
				throw new ServiceRuleException("���󶩵�״̬ʧ�ܣ�������");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "��ֵ��");
				throw new ServiceRuleException("��ֵ��");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "��ֵʧ��,���˿�");
				throw new ServiceRuleException("��ֵʧ��,���˿�");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "�˶���������");
				throw new ServiceRuleException("�˶���������");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("��ǰ�˻���������");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "���Ϲ淶�Ķ����ţ�8-32λ��");
				throw new ServiceRuleException("���Ϲ淶�Ķ����ţ�8-32λ��");
			}
			else if (error_code == 208515) {
				Log.i("wzs", "У��ֵsign����");
				throw new ServiceRuleException("У��ֵsign����");
			}
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONObject _Object = result_object.getJSONObject("result");
				String cardid2 = _Object.getString("cardid");	
				Log.i("wzs", "��ֵ�Ŀ���"+cardid2);
				
				String cardname2 = _Object.getString("cardname");
				Log.i("wzs", "��ֵ����"+cardname2);
				//String sporder_id = _Object.getString("sporder_id");
				//Log.i("wzs", "�ۺ϶�����"+sporder_id);
				String uorderid = _Object.getString("uorderid");
				Log.i("wzs", "�̻��Զ��嶩����"+uorderid);
				String game_userid = _Object.getString("game_userid");
				Log.i("wzs", "��ֵ�ֻ���"+game_userid);
				String game_state = _Object.getString("game_state");																			
				Log.i("wzs", "��ֵ״̬"+game_state);
				//String ordercash = _Object.getString("ordercash");
				//Log.i("wzs", "�����۸�"+ordercash);
				telChongzhiEntity = new TelChongzhiEntity(cardid2, null, cardname2, null, uorderid, game_userid, game_state);

			}
			else if(error_code != 0)
			{
				Log.i("wzs", "ϵͳ����");
				throw new Exception("ϵͳ����");
			}
		}
		Log.i("wzs", "step3 ����ֵ�ɹ�");
		return telChongzhiEntity;
	}

}
