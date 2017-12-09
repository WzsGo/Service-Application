package com.wzs.mooc;


import com.wzs.mooc.tool.Tool_Resyultchecii_Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class Result_Findtraincheci extends Activity 
{
	private TextView nameTextView ;
	private TextView startTextView;
	private TextView endTextView;
	private TextView starttimeTextView;
	private TextView endtimeTextView;
	private TextView smileageTextView;
	private ListView train_checi_list;
	private Intent _intent = null;
	
	//private List<TrainCheciInfo> Train_List1 = new ArrayList<TrainCheciInfo>();
	//private TrainCheciInfo trainCheciInfo = new TrainCheciInfo();/**************这句可能有问题*******************/
	//private TrainCheciInfo trainCheciInfo1;
	Tool_Resyultchecii_Adapter adapter ;
	public void intit()
	{
		nameTextView = (TextView)findViewById(R.id.viewname);
		startTextView = (TextView)findViewById(R.id.viewstart);
		endTextView = (TextView)findViewById(R.id.viewend);
		starttimeTextView = (TextView)findViewById(R.id.viewstarttime);
		endtimeTextView = (TextView)findViewById(R.id.viewendtime);
		smileageTextView = (TextView)findViewById(R.id.viewSmileage);
		train_checi_list = (ListView)findViewById(R.id.train_checi_list);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resultfind_checi);
		intit();
		_intent = this.getIntent();
		if(_intent != null)
		{
			Log.i("wzs", "接收intent成功");
			/*Bundle _bundle = new Bundle();
			_bundle = _intent.getExtras();
			trainCheciInfo = (TrainCheciInfo) _bundle.getSerializable("trainCheciInfo");
			Train_List = (List<TrainCheciInfo>) _bundle.getSerializable("Train_List");*/
			
			//trainCheciInfo1 = FindTrainActivity.trainCheciInfo;
			//Train_List1 = FindTrainActivity.Train_List;
			
			Log.i("wzs", FindTrainActivity.trainCheciInfo.getName());
			Log.i("wzs", FindTrainActivity.Train_List.toString());
			
			nameTextView.setText(FindTrainActivity.trainCheciInfo.name);
			startTextView.setText(FindTrainActivity.trainCheciInfo.start); 
			endTextView.setText(FindTrainActivity.trainCheciInfo.end);
			starttimeTextView.setText("始发："+FindTrainActivity.trainCheciInfo.starttime); 
			endtimeTextView.setText("抵达"+FindTrainActivity.trainCheciInfo.endtime);
			smileageTextView.setText("里程"+FindTrainActivity.trainCheciInfo.Smileage);
			Log.i("wzs","总信息显示成功");
			
			adapter = new Tool_Resyultchecii_Adapter(Result_Findtraincheci.this, R.layout.train_checi_item, FindTrainActivity.Train_List);
			Log.i("wzs","adapter 初始化成功");
			train_checi_list.setAdapter(this.adapter);
			Log.i("wzs","list view 配置成功");
		}
	
	}
	
}
