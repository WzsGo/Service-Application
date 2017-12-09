package com.wzs.coach;



import com.wzs.coach.adapter.Result_From_to_Adapter;
import com.wzs.mooc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Result_Coach_from_to extends Activity {

	TextView viewcoach_title ;
	
	ListView coach_resultListView ;
	Result_From_to_Adapter adapter ;
	Button btn_back;
	public void intit()
	{
		
		this.viewcoach_title = (TextView)findViewById(R.id.viewcoach_titl);		
		this.coach_resultListView = (ListView)findViewById(R.id.listViewcoach_result);
		this.btn_back = (Button)findViewById(R.id.btn_coach_back);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resultfind_coach);
		intit();
		Intent _Intent = getIntent();
		if(_Intent != null)
		{
			Log.i("wzs", "接收到intent 站站查询");
			viewcoach_title.setText(FindCoachActivity.from+"-"+FindCoachActivity.to);
			Log.i("wzs", "上一句错了 站站查询");
			
			
			adapter = new Result_From_to_Adapter(Result_Coach_from_to.this, R.layout.coach_from_to_item, FindCoachActivity.coach_from_to_list);
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
