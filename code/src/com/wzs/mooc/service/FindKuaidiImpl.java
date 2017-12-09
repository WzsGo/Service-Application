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

		// 定义一个params 参数
		HttpParams params = new BasicHttpParams();
		// 通过params 设置请求超时时间 ---〉ConnectTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// 通过params 设置响应超时时间 ---〉SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);
		// 定义一个方案注册类
		SchemeRegistry schreg = new SchemeRegistry();
		// 注册两个链接方案 HTTP和https（加密传输）
		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));
		// 通过params设置schemeRegistry（链接方案注册）
		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);
		// DefaultHttpClient
		// 需要设置两个参数一个是clientConnectinmannager（客户端链接管理器）和params（参数）
		HttpClient client = new DefaultHttpClient(conman, params);
		// String uri =
		// "http://apis.juhe.cn/ip/ip2addr?ip="+IpAdress+"&key=8896f7c96ac1e83f7e343952a1ba2deb";
		Log.i("wzs", kuaidicom + "  服务端");
		Log.i("wzs", kuaidino + "  服务端");
		// String uri =
		// "http://v.juhe.cn/exp/index？key=ed13b0c26c76547fb494091aadf893e2&dtype=json&com="+kuaidicom+"&no="+kuaidino;
		String uri = "http://v.juhe.cn/exp/index?com=" + kuaidicom + "&no="
				+ kuaidino + "&key=ed13b0c26c76547fb494091aadf893e2";
		Log.i("wzs", uri);

		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		Log.i("wzslog", "开始请求");
		int requestCode = response.getStatusLine().getStatusCode();
		Log.i("wzslog", requestCode + "");
		if (requestCode != HttpStatus.SC_OK) {
			Log.i("wzs", "抛出系统异常");
			throw new Exception(ConstantActivity.MSG_LOGIN_ERROR);
		} else {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject result_object = new JSONObject(result);
			int error_code = result_object.getInt("error_code");
			Log.i("wzs", error_code + "接收到错误码");
			if (error_code == 204301) {
				Log.i("wzs", "未被识别的快递公司");
				throw new ServiceRuleException("信息错误");
			} else if (error_code == 204302) {
				Log.i("wzs", "请填写正确的快递单号");
				throw new ServiceRuleException("请填写正确的快递单号");
			}

			else if (error_code == 204303) {
				Log.i("wzs", "加载类库失败");
				throw new ServiceRuleException("加载类库失败");
			} else if (error_code == 204304) {
				Log.i("wzs", "查询失败");
				throw new ServiceRuleException("查询失败");
			} 
			else if (error_code == 0) {
				Log.i("wzs", "error_code == 0");
				JSONObject kuaidi_result = result_object
						.getJSONObject("result");
				JSONArray kuadi_Array = kuaidi_result.getJSONArray("list");
				int length = kuadi_Array.length();
				Log.i("wzslog", "得到数据");
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
				throw new Exception("系统错误");
			}
			return listKuaidiEntities;
		}
	}
}
