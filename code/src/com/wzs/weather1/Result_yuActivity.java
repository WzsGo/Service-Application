package com.wzs.weather1;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.weather.cs.FindWeather;
import com.wzs.weather.cs.FindWeatherImpl;
import com.wzs.weather.entity.Weather_Info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Result_yuActivity extends Activity{
	private TextView view_updatetime;
	private TextView view_temp;
	private TextView view_temperature;
	private TextView view_city;
	private TextView view_weather;
	private TextView view_wind;
	private TextView view_exercise_index;
	private TextView view_uv_index;
	private TextView view_xiangxi_date;
	private TextView txtview_weather_1_2;
	private TextView txtview_weather_2_1;
	private TextView txtview_weather_2_2;
	private TextView txtview_weather_3_1;
	private TextView txtview_weather_3_2;
	private TextView txtview_weather_4_1;
	private TextView txtview_weather_4_2;
	private TextView txtview_weather_5_1;
	private TextView txtview_weather_5_2;
	private ImageButton imgbtn_weather_1,imgbtn_weather_2,imgbtn_weather_3,imgbtn_weather_4,imgbtn_weather_5;
	private Button btnupate;
	private Button btn_find_weather;
	private static ProgressDialog mDialog;
	LoadWeather_image loadWeather_image = new LoadWeather_image();
	FindWeather findWeather = new FindWeatherImpl();
	public void toastShow(String message)
	{
		Toast.makeText(Result_yuActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	public void dialogshow()
	{
		if (mDialog == null) {

			mDialog = new ProgressDialog(Result_yuActivity.this);
			mDialog.setTitle("查询中");

			mDialog.setMessage("请稍候...");
			mDialog.setCancelable(true);
			mDialog.show();
		}
	}
	public void startResult_qingActivity()
	{
		Intent _Intent = new Intent(Result_yuActivity.this,Result_qingActivity.class);
		startActivity(_Intent);
	}
	public void startResult_yuActivity()
	{
		Intent _Intent = new Intent(Result_yuActivity.this,Result_yuActivity.class);
		startActivity(_Intent);
	}
	public void startResult_leiyuActivity()
	{
		Intent _Intent = new Intent(Result_yuActivity.this,Result_leiyuActivity.class);
		startActivity(_Intent);
	}
	public void startResult_yinActivity()
	{
		Intent _Intent = new Intent(Result_yuActivity.this,Result_yinActivity.class);
		startActivity(_Intent);
	}
	public void init()
	{
		this.view_updatetime = (TextView)findViewById(R.id.view_updatetime);
		this.view_temp = (TextView)findViewById(R.id.view_temp);
		this.view_temperature = (TextView)findViewById(R.id.view_temperature);
		this.view_city = (TextView)findViewById(R.id.view_city);
		this.view_weather = (TextView)findViewById(R.id.view_weather);
		this.view_wind = (TextView)findViewById(R.id.view_wind);
		this.view_exercise_index = (TextView)findViewById(R.id.view_exercise_index);
		this.view_uv_index = (TextView)findViewById(R.id.view_uv_index);
		this.view_xiangxi_date = (TextView)findViewById(R.id.view_xiangxi_date);
		
		this.txtview_weather_1_2 = (TextView)findViewById(R.id.txtview_weather_1_2);
		this.txtview_weather_2_1 = (TextView)findViewById(R.id.txtview_weather_2_1);
		this.txtview_weather_2_2 = (TextView)findViewById(R.id.txtview_weather_2_2);
		this.txtview_weather_3_1 = (TextView)findViewById(R.id.txtview_weather_3_1);
		this.txtview_weather_3_2 = (TextView)findViewById(R.id.txtview_weather_3_2);
		this.txtview_weather_4_1 = (TextView)findViewById(R.id.txtview_weather_4_1);
		this.txtview_weather_4_2 = (TextView)findViewById(R.id.txtview_weather_4_2);
		this.txtview_weather_5_1 = (TextView)findViewById(R.id.txtview_weather_5_1);		
		this.txtview_weather_5_2 = (TextView)findViewById(R.id.txtview_weather_5_2);
		this.imgbtn_weather_1 = (ImageButton)findViewById(R.id.imgview_weather_1);
		this.imgbtn_weather_2 = (ImageButton)findViewById(R.id.imgview_weather_2);
		this.imgbtn_weather_3 = (ImageButton)findViewById(R.id.imgview_weather_3);
		this.imgbtn_weather_4 = (ImageButton)findViewById(R.id.imgview_weather_4);
		this.imgbtn_weather_5 = (ImageButton)findViewById(R.id.imgview_weather_5);
		btn_find_weather = (Button)findViewById(R.id.btn_find_weather);
		btnupate = (Button)findViewById(R.id.btn_update);
	}
	public void loadTodayInfo(Weather_Info info)
	{
		view_updatetime.setText("更新于"+info.date_y+" "+info.time);
		view_temp.setText(info.temp);
		view_temperature.setText(info.temperature);
		Log.i("wzs", info.weather);
		view_weather.setText(info.weather);
		
		view_exercise_index.setText("晨练指数 "+info.exercise_index);
		view_wind.setText(info.wind);
		view_city.setText(info.city);
		view_uv_index.setText("紫外线强度 "+info.uv_index);
		view_xiangxi_date.setText(info.date_y+" "+info.week);
	}
	public void loadFutureInfo_single(Weather_Info info)
	{
		//LinearLayout iv = new LinearLayout(Result_yinActivity.this);
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		LinearLayout iv = (LinearLayout) layoutInflater.inflate(R.layout.weather_yin, null) ;
		String flag = info.weather_id1;
		Log.i("wzs", flag+"点击未来天气是的id");
		if (flag.equals("00"))
		{
			Log.i("wzs", flag+"点击已经进拉了");						
			iv.setBackgroundResource(R.drawable.weather_qing);
			Log.i("wzs", flag+"设值背景成功");	
			//Result_yinActivity.getBackgroundView().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg2));
		}else if (flag.equals("04"))
		{
			
			Log.i("wzs", flag+"点击已经进拉了");	
			iv.setBackgroundResource(R.drawable.weather_leiyu);
			Log.i("wzs", flag+"设值背景成功");
		}
		else if (flag.equals("01")||flag.equals("02")||flag.equals("29")||flag.equals("30")||flag.equals("31")||flag.equals("53"))
		{
		
			Log.i("wzs", flag+"点击已经进拉了");	
		iv.setBackgroundResource(R.drawable.weather_yin);
		Log.i("wzs", flag+"设值背景成功");

		}else
		{
			
			Log.i("wzs", flag+"点击已经进拉了");	
			iv.setBackgroundResource(R.drawable.weath_yu);
			Log.i("wzs", flag+"设值背景成功");
		}
		view_temperature.setText(info.temperature1);
		view_weather.setText(info.weather1);
		view_wind.setText(info.wind1);
		String yuan_date = info.date1;
		char[] ch = yuan_date.toCharArray();
		String stander_date = ch[0]+ch[1]+ch[2]+ch[3]+"年"+ch[4]+ch[5]+"月"+ch[6]+ch[7]+"日";
		view_xiangxi_date.setText(stander_date+" "+info.week1);
		
		
	}
	public void loadFutureInfo(Weather_Info info1,Weather_Info info2,Weather_Info info3,Weather_Info info4,Weather_Info info5)
	{
		txtview_weather_1_2.setText(info1.temperature1); 
		txtview_weather_2_1.setText(info2.week1);
		txtview_weather_2_2.setText(info2.temperature1); 
		txtview_weather_3_1.setText(info3.week1); 
		txtview_weather_3_2 .setText(info3.temperature1);
		txtview_weather_4_1 .setText(info4.week1);
		txtview_weather_4_2 .setText(info4.temperature1);
		txtview_weather_5_1 .setText(info5.week1);		
		txtview_weather_5_2 .setText(info5.temperature1);
		
		imgbtn_weather_1.setImageResource(R.drawable.d00);
		loadWeather_image.getImage(imgbtn_weather_1, info1.weather_id1);
		loadWeather_image.getImage(imgbtn_weather_2, info2.weather_id1);
		loadWeather_image.getImage(imgbtn_weather_3, info3.weather_id1);
		loadWeather_image.getImage(imgbtn_weather_4, info4.weather_id1);
		loadWeather_image.getImage(imgbtn_weather_5, info5.weather_id1);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_yin);
		init();
		Intent _Intent = getIntent();
		
			if (_Intent != null)
			{
				loadTodayInfo(FindWeatherActivity.weather_info_Today);
				loadFutureInfo(FindWeatherActivity.weather_info_Today1,
						FindWeatherActivity.weather_info_Today2,
						FindWeatherActivity.weather_info_Today3,
						FindWeatherActivity.weather_info_Today4,
						FindWeatherActivity.weather_info_Today5);	
				btn_find_weather.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent _Intent = new Intent(Result_yuActivity.this,FindWeatherActivity.class);
						startActivity(_Intent);										
					}
				});
				btnupate.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						final String cityname = view_city.getText().toString();
						findWeather_service(cityname);				
					}
				});
				imgbtn_weather_2.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						loadFutureInfo_single(FindWeatherActivity.weather_info_Today2);
						
					}
				});
				imgbtn_weather_3.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						loadFutureInfo_single(FindWeatherActivity.weather_info_Today3);
						
					}
				});
				imgbtn_weather_4.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						loadFutureInfo_single(FindWeatherActivity.weather_info_Today4);
						
					}
				});
				imgbtn_weather_5.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						loadFutureInfo_single(FindWeatherActivity.weather_info_Today5);
						
					}
				});
				
			}					
	}	
	public void findWeather_service(final String cityname)
	{
		Thread _Thread = new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				try 
				{
					FindWeatherActivity.weather_info_list = findWeather.getWeather_Info(cityname);
					FindWeatherActivity.weather_info_Today = FindWeatherActivity.weather_info_list.get(0);
					FindWeatherActivity.weather_info_Today1 =FindWeatherActivity.weather_info_list.get(1);
					FindWeatherActivity.weather_info_Today2 = FindWeatherActivity.weather_info_list.get(2);
					FindWeatherActivity.weather_info_Today3 = FindWeatherActivity.weather_info_list.get(3);
					FindWeatherActivity.weather_info_Today4 = FindWeatherActivity.weather_info_list.get(4);
					FindWeatherActivity.weather_info_Today5 = FindWeatherActivity.weather_info_list.get(5);
					String flag = FindWeatherActivity.weather_info_Today.weather_id;
					Log.i("wzs", "try成功");
					if (flag.equals("00"))
					{
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_QING);
					}else if (flag.equals("04"))
					{
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_LEIYU);

					}
					else if (flag.equals("01")||flag.equals("02")||flag.equals("29")||flag.equals("30")||flag.equals("31")||flag.equals("53"))
					{
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_YIN);

					}else
					{
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_YU);
					}
					
				} 
				catch (ServiceRuleException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
							messgae.getMessage());
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (ConnectTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable(
							"ERROR",
							ConstantActivity.MSG_CONNECT_TIMEOUT);
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (SocketTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable(
							"ERROR",
							ConstantActivity.MSG_RESPONSE_TIMEOUT);
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (Exception e) {
					Log.i("wzs", "总异常");
					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
						ConstantActivity.MSG_SERVICE_ERROR);
					msg.setData(date);
					handler.sendMessage(msg);
					e.printStackTrace();
				}

				
			}
		});
		_Thread.start();
	}
	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivtiy;

		public IHandler(Result_yuActivity activity) {
			mActivtiy = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (mDialog != null) {
				mDialog.dismiss();
				mDialog = null;

			}
			int flag = msg.what;
			switch (flag) {
			case Constant_weather.FLAG_WEATHER_QING:
				((Result_yuActivity) mActivtiy.get()).startResult_qingActivity();
				break;
			case Constant_weather.FLAG_WEATHER_YU:
				((Result_yuActivity) mActivtiy.get()).startResult_yuActivity();
	
				break;
			case Constant_weather.FLAG_WEATHER_YIN:
				Log.i("wzs", "case接受成功");
				((Result_yuActivity) mActivtiy.get()).startResult_yinActivity();

				break;
			case Constant_weather.FLAG_WEATHER_LEIYU:
				((Result_yuActivity) mActivtiy.get()).startResult_leiyuActivity();

				break;
			case 0:
				String ERRORMSG = msg.getData().getSerializable("ERROR")
						.toString();
				((Result_yuActivity) mActivtiy.get()).toastShow(ERRORMSG);
				break;
			}
		}

	}

	IHandler handler = new IHandler(this);
	
}
