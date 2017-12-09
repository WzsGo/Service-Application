package com.wzs.mooc;

import com.wzs.mooc.tool.Tool_ResultPiaojia_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Result_FindtrainPiaojia extends Activity {
	private TextView viewzhanzhanstation;
	private TextView viewdate;
	public ListView listView_result_piaojia;
	private Tool_ResultPiaojia_adapter adapter;
	private Button btn_back;
	public void intit()
	{
		viewzhanzhanstation = (TextView)findViewById(R.id.viewzhanzhanstation);
		viewdate = (TextView)findViewById(R.id.viewdate);
		listView_result_piaojia  = (ListView)findViewById(R.id.listView_result_zhanzhan);
		btn_back = (Button)findViewById(R.id.btn_zhanzhan_back);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resultfind_zhanzhan);
		intit();
		Intent _Intent = getIntent();
		if(_Intent!=null)
		{
			viewzhanzhanstation.setText(FindTrainActivity.from+"¡ª"+FindTrainActivity.to);
			viewdate.setText(FindTrainActivity.date);
			adapter = new Tool_ResultPiaojia_adapter(Result_FindtrainPiaojia.this, R.layout.train_zhanzhan_piaojia_item, FindTrainActivity.TrainPiaojia_list);
			listView_result_piaojia.setAdapter(this.adapter);
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
					
				}
			});
		}
	}
	
}

