package com.wzs.weather1;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.weather.cs.FindWeather;
import com.wzs.weather.cs.FindWeatherImpl;
import com.wzs.weather.entity.Weather_Info;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

public class FindWeatherActivity extends Activity {
	Intent mintent = getIntent();
	private EditText txtcityname;
	private Button btnfindweather;
	private Button btnfindweather2;
	private Button btn_back;
	private static CustomProgressDialog progressDialog = null;
	public double latitude = 0.0;
	public double longitude = 0.0;
	public static String cityname_location;
	public static List<Weather_Info> weather_info_list = new ArrayList<Weather_Info>();
	public static Weather_Info weather_info_Today = null;
	public static Weather_Info weather_info_Today1 = null;
	public static Weather_Info weather_info_Today2 = null;
	public static Weather_Info weather_info_Today3 = null;
	public static Weather_Info weather_info_Today4 = null;
	public static Weather_Info weather_info_Today5 = null;
	FindWeather findWeather = new FindWeatherImpl();

	public void intit() {
		txtcityname = (EditText) findViewById(R.id.txtcityname);
		btnfindweather = (Button) findViewById(R.id.btnfindweather);
		btnfindweather2 = (Button) findViewById(R.id.btnfindweather2);
		btn_back = (Button) findViewById(R.id.btn_weather_back);
	}

	public void toastShow(String message) {
		Toast.makeText(FindWeatherActivity.this, message, Toast.LENGTH_SHORT)
				.show();
	}

	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage("");
		}

		progressDialog.show();
	}

	private static void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void startResult_qingActivity() {
		Intent _Intent = new Intent(FindWeatherActivity.this,
				Result_qingActivity.class);
		startActivity(_Intent);
	}

	public void startResult_yuActivity() {
		Intent _Intent = new Intent(FindWeatherActivity.this,
				Result_yuActivity.class);
		startActivity(_Intent);
	}

	public void startResult_leiyuActivity() {
		Intent _Intent = new Intent(FindWeatherActivity.this,
				Result_leiyuActivity.class);
		startActivity(_Intent);
	}

	public void startResult_yinActivity() {
		Intent _Intent = new Intent(FindWeatherActivity.this,
				Result_yinActivity.class);
		startActivity(_Intent);
	}

	public void locationShow(String cityname_location) {
		txtcityname.setText(cityname_location);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_find);
		intit();
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnfindweather.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final String cityname = txtcityname.getText().toString();
				if (cityname.equals("")) {
					toastShow("城市不能为空");
				} else {
					startProgressDialog();
					findWeather_service(cityname);
				}

			}
		});
		btnfindweather2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getLocation();
				if (latitude == 0.0 & longitude == 0.0) {
					toastShow("未获得位置，请确认是否打开GPS");
				} else {
					findWeather_location(latitude, longitude);
				}
			}
		});

	}

	public void findWeather_service(final String cityname) {
		Thread _Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					FindWeatherActivity.weather_info_list = findWeather
							.getWeather_Info(cityname);
					FindWeatherActivity.weather_info_Today = weather_info_list
							.get(0);
					FindWeatherActivity.weather_info_Today1 = weather_info_list
							.get(1);
					FindWeatherActivity.weather_info_Today2 = weather_info_list
							.get(2);
					FindWeatherActivity.weather_info_Today3 = weather_info_list
							.get(3);
					FindWeatherActivity.weather_info_Today4 = weather_info_list
							.get(4);
					FindWeatherActivity.weather_info_Today5 = weather_info_list
							.get(5);
					String flag = weather_info_Today.weather_id;
					Log.i("wzs", "try成功");
					if (flag.equals("00")) {
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_QING);
					} else if (flag.equals("04")) {
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_LEIYU);

					} else if (flag.equals("01") || flag.equals("02")
							|| flag.equals("29") || flag.equals("30")
							|| flag.equals("31") || flag.equals("53")) {
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_YIN);

					} else {
						handler.sendEmptyMessage(Constant_weather.FLAG_WEATHER_YU);
					}

				} catch (ServiceRuleException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR", messgae.getMessage());
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (ConnectTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
							ConstantActivity.MSG_CONNECT_TIMEOUT);
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (SocketTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
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

	public void findWeather_location(final double latitude,
			final double longitude) {
		Thread _Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					cityname_location = findWeather.getWeather_Location(
							latitude, longitude);
					handler.sendEmptyMessage(Constant_weather.FLAG_LOCATION_SUCCESS);

				} catch (ServiceRuleException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR", messgae.getMessage());
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (ConnectTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
							ConstantActivity.MSG_CONNECT_TIMEOUT);
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				} catch (SocketTimeoutException messgae) {

					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",
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

	public void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			} else {
				LocationListener locationListener = new LocationListener() {

					// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						toastShow("GPS状态已改变");
					}

					// Provider被enable时触发此函数，比如GPS被打开
					@Override
					public void onProviderEnabled(String provider) {
						toastShow("GPS正在开启");
					}

					// Provider被disable时触发此函数，比如GPS被关闭
					@Override
					public void onProviderDisabled(String provider) {
						toastShow("请开启GPS");
					}

					// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
					@Override
					public void onLocationChanged(Location location) {
						if (location != null) {
							Log.e("Map",
									"Location changed : Lat: "
											+ location.getLatitude() + " Lng: "
											+ location.getLongitude());
							latitude = location.getLatitude(); // 经度
							longitude = location.getLongitude(); // 纬度
						}
					}
				};
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 1000, 0,
						locationListener);
				Location location1 = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location1 != null) {
					latitude = location1.getLatitude(); // 经度
					longitude = location1.getLongitude(); // 纬度
				}
			}
		}
	}

	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivtiy;

		public IHandler(FindWeatherActivity activity) {
			mActivtiy = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			stopProgressDialog();
			int flag = msg.what;
			switch (flag) {
			case Constant_weather.FLAG_WEATHER_QING:
				((FindWeatherActivity) mActivtiy.get())
						.startResult_qingActivity();
				break;
			case Constant_weather.FLAG_WEATHER_YU:
				((FindWeatherActivity) mActivtiy.get())
						.startResult_yuActivity();

				break;
			case Constant_weather.FLAG_WEATHER_YIN:
				Log.i("wzs", "case接受成功");
				((FindWeatherActivity) mActivtiy.get())
						.startResult_yinActivity();

				break;
			case Constant_weather.FLAG_WEATHER_LEIYU:
				((FindWeatherActivity) mActivtiy.get())
						.startResult_leiyuActivity();

				break;
			case Constant_weather.FLAG_LOCATION_SUCCESS:
				((FindWeatherActivity) mActivtiy.get())
						.locationShow(cityname_location);
				break;
			case 0:
				String ERRORMSG = msg.getData().getSerializable("ERROR")
						.toString();
				((FindWeatherActivity) mActivtiy.get()).toastShow(ERRORMSG);
				break;
			}
		}

	}

	IHandler handler = new IHandler(this);

}
