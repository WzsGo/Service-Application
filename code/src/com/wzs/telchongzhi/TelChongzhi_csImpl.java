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
		Log.i("wzs", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 208502) {
				Log.i("wzs", "请求手机号和面值查询商品信息失败，请重试");
				throw new ServiceRuleException("请求手机号和面值查询商品信息失败，请重试");
			} else if (error_code == 208501) {
				Log.i("wzs", "不允许充值的手机号码及金额");
				throw new ServiceRuleException("不允许充值的手机号码及金额");
			} else if (error_code == 208503) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "错误的手机号码");
				throw new ServiceRuleException("错误的手机号码");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "充值失败");
				throw new ServiceRuleException("充值失败");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "请求充值失败，请重试");
				throw new ServiceRuleException("请求充值失败，请重试");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "错误的订单号");
				throw new ServiceRuleException("错误的订单号");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "请求订单状态失败，请重试");
				throw new ServiceRuleException("请求订单状态失败，请重试");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "充值中");
				throw new ServiceRuleException("充值中");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "充值失败,已退款");
				throw new ServiceRuleException("充值失败,已退款");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "此订单不存在");
				throw new ServiceRuleException("此订单不存在");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("当前账户可用余额不足");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "不合规范的订单号（8-32位）");
				throw new ServiceRuleException("不合规范的订单号（8-32位）");
			}
			else if (error_code == 0)
			{
				flag = 1;
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
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
		Log.i("wzs", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 208502) {
				Log.i("wzs", "请求手机号和面值查询商品信息失败，请重试");
				throw new ServiceRuleException("请求手机号和面值查询商品信息失败，请重试");
			} else if (error_code == 208501) {
				Log.i("wzs", "不允许充值的手机号码及金额");
				throw new ServiceRuleException("不允许充值的手机号码及金额");
			} else if (error_code == 208503) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "错误的手机号码");
				throw new ServiceRuleException("错误的手机号码");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "充值失败");
				throw new ServiceRuleException("充值失败");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "请求充值失败，请重试");
				throw new ServiceRuleException("请求充值失败，请重试");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "错误的订单号");
				throw new ServiceRuleException("错误的订单号");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "请求订单状态失败，请重试");
				throw new ServiceRuleException("请求订单状态失败，请重试");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "充值中");
				throw new ServiceRuleException("充值中");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "充值失败,已退款");
				throw new ServiceRuleException("充值失败,已退款");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "此订单不存在");
				throw new ServiceRuleException("此订单不存在");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("当前账户可用余额不足");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "不合规范的订单号（8-32位）");
				throw new ServiceRuleException("不合规范的订单号（8-32位）");
			}
			else if (error_code == 0)
			{
				JSONObject _Object = result_object.getJSONObject("result");
				String cardid = _Object.getString("cardid");
				String cardname = _Object.getString("cardname");
				String inprice = _Object.getString("inprice");
				String game_area = _Object.getString("game_area");
				Log.i("wzs", "充值的卡类"+cardid);
				Log.i("wzs", "充值名称"+cardname);
				Log.i("wzs", "售价"+inprice);
				Log.i("wzs", "归属地"+game_area);
				telChongzhiEntity = new TelChongzhiEntity(cardid, cardname, inprice, game_area);
			}
			else if(error_code != 0)
			{
				throw new Exception("系统错误");
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
		Log.i("wzs", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzs", requestCode + "");

		if (requestCode != HttpStatus.SC_OK) 
		{
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} 
		else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 208502) {
				Log.i("wzs", "请求手机号和面值查询商品信息失败，请重试");
				throw new ServiceRuleException("请求手机号和面值查询商品信息失败，请重试");
			} else if (error_code == 208501) {
				Log.i("wzs", "不允许充值的手机号码及金额");
				throw new ServiceRuleException("不允许充值的手机号码及金额");
			} else if (error_code == 208503) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208504) {
				Log.i("wzs", "运营商地区维护，暂不能充值");
				throw new ServiceRuleException("运营商地区维护，暂不能充值");
			}
			else if (error_code == 208505) {
				Log.i("wzs", "错误的手机号码");
				throw new ServiceRuleException("错误的手机号码");
			}
			else if (error_code == 208507) {
				Log.i("wzs", "充值失败");
				throw new ServiceRuleException("充值失败");
			}
			else if (error_code == 208508) {
				Log.i("wzs", "请求充值失败，请重试");
				throw new ServiceRuleException("请求充值失败，请重试");
			}
			else if (error_code == 208509) {
				Log.i("wzs", "错误的订单号");
				throw new ServiceRuleException("错误的订单号");
			}
			else if (error_code == 208510) {
				Log.i("wzs", "请求订单状态失败，请重试");
				throw new ServiceRuleException("请求订单状态失败，请重试");
			}
			else if (error_code == 208511) {
				Log.i("wzs", "充值中");
				throw new ServiceRuleException("充值中");
			}
			else if (error_code == 208512) {
				Log.i("wzs", "充值失败,已退款");
				throw new ServiceRuleException("充值失败,已退款");
			}
			else if (error_code == 208513) {
				Log.i("wzs", "此订单不存在");
				throw new ServiceRuleException("此订单不存在");
			}
			else if (error_code == 208517) {
				
				throw new ServiceRuleException("当前账户可用余额不足");
			}
			else if (error_code == 208514) {
				Log.i("wzs", "不合规范的订单号（8-32位）");
				throw new ServiceRuleException("不合规范的订单号（8-32位）");
			}
			else if (error_code == 208515) {
				Log.i("wzs", "校验值sign错误");
				throw new ServiceRuleException("校验值sign错误");
			}
			else if (error_code == 0)
			{
				Log.i("wzs", "error_code == 0");
				JSONObject _Object = result_object.getJSONObject("result");
				String cardid2 = _Object.getString("cardid");	
				Log.i("wzs", "充值的卡类"+cardid2);
				
				String cardname2 = _Object.getString("cardname");
				Log.i("wzs", "充值名称"+cardname2);
				//String sporder_id = _Object.getString("sporder_id");
				//Log.i("wzs", "聚合订单号"+sporder_id);
				String uorderid = _Object.getString("uorderid");
				Log.i("wzs", "商户自定义订单号"+uorderid);
				String game_userid = _Object.getString("game_userid");
				Log.i("wzs", "充值手机号"+game_userid);
				String game_state = _Object.getString("game_state");																			
				Log.i("wzs", "充值状态"+game_state);
				//String ordercash = _Object.getString("ordercash");
				//Log.i("wzs", "进货价格"+ordercash);
				telChongzhiEntity = new TelChongzhiEntity(cardid2, null, cardname2, null, uorderid, game_userid, game_state);

			}
			else if(error_code != 0)
			{
				Log.i("wzs", "系统错误");
				throw new Exception("系统错误");
			}
		}
		Log.i("wzs", "step3 返回值成功");
		return telChongzhiEntity;
	}

}
