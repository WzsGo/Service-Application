package com.wzs.weather.location;

import java.lang.ref.WeakReference;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class GetLocation extends Activity {

	private double latitude = 0.0;
	private double longitude = 0.0;
	private TextView info;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		info = (TextView) findViewById(R.id.info);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			getLocation();
			// gps�Ѵ�
		}
		/*else {
			toggleGPS();
			new Handler() {
			}.postDelayed(new Runnable() {
				@Override
				public void run() {
					getLocation();
				}

			}, 2000);

		}*/
		else 
		{
			toggleGPS();
			handler.sendEmptyMessage(ConstantActivity.FLAG_OPEN_GPS);
		}
	}
	 private static class IHandler extends Handler
	    {
		 	

	    	private final WeakReference<Activity> mActivtiy;
	    	public IHandler(GetLocation activity )
	    	{
	    		mActivtiy = new WeakReference<Activity>(activity);
	    	}
	    	@Override
			public void handleMessage(Message msg) 
	    	{			
				int flag = msg.what;
				switch(flag)
				{
					case ConstantActivity.FLAG_OPEN_GPS:
						((GetLocation)mActivtiy.get()).getLocation();
						break;
					
				}
	    	}
	    	
	    }
	 IHandler handler  = new IHandler(this);
	

	private void toggleGPS() {

		Intent gpsIntent = new Intent();

		gpsIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");

		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");

		gpsIntent.setData(Uri.parse("custom:3"));

		try {

			PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();

		} catch (CanceledException e) {

			e.printStackTrace();

			locationManager
					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
							1000, 0, locationListener);

			Location location1 = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if (location1 != null) {

				latitude = location1.getLatitude(); // ����

				longitude = location1.getLongitude(); // γ��

			}

		}

	}

	private void getLocation() {

		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location != null) {

			latitude = location.getLatitude();

			longitude = location.getLongitude();

		} else {

			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

		}

		info.setText("γ�ȣ�" + latitude + "\n" + "���ȣ�" + longitude);
		

	}

	LocationListener locationListener = new LocationListener() {

		// Provider��״̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		// Provider��enableʱ�����˺���������GPS����

		@Override
		public void onProviderEnabled(String provider) {

			Log.e("wzs", provider);

		}

		// Provider��disableʱ�����˺���������GPS���ر�

		@Override
		public void onProviderDisabled(String provider) {

			Log.e("wzs", provider);

		}

		// ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����

		@Override
		public void onLocationChanged(Location location) {

			if (location != null) {

				Log.e("Map",
						"Location changed : Lat: " + location.getLatitude()
								+ " Lng: " + location.getLongitude());

				latitude = location.getLatitude(); // ����

				longitude = location.getLongitude(); // γ��

			}
		}
	};
}
