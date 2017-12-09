package com.wzs.coach;


import com.wzs.coach.adapter.Result_Station_info_Adapter;
import com.wzs.mooc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Result_Coach_station_info extends Activity {
	TextView viewcoach_title ;
	
	ListView coach_resultListView ;
	Result_Station_info_Adapter adapter ;
	Button btn_back;
	public void intit()
	{
		this.viewcoach_title = (TextView)findViewById(R.id.viewcoach_titl1);
		
		this.coach_resultListView = (ListView)findViewById(R.id.listViewcoach_result1);
		this.btn_back = (Button)findViewById(R.id.btn_coach_info_back);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.coach_result_info);
		intit();
		Intent _Intent = getIntent();
		if(_Intent != null)
		{

			viewcoach_title.setText(FindCoachActivity.station+"Æû³µÕ¾");			
			adapter = new Result_Station_info_Adapter(Result_Coach_station_info.this,R.layout.coach_info_item , FindCoachActivity.coach_station_info_list);
			coach_resultListView.setAdapter(this.adapter);
			btn_back.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					finish();
					
				}
			});
			
		}
	}
}
