package com.wzs.mooc.tool;

import java.util.List;

import com.wzs.mooc.R;
import com.wzs.mooc.entity.TrainZhanzhan_Piaojia_Info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tool_ResultPiaojia_adapter extends ArrayAdapter<TrainZhanzhan_Piaojia_Info> {

	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_ResultPiaojia_adapter(Context context, int resource,List<TrainZhanzhan_Piaojia_Info> objects) {
		super(context, resource, objects);
		this.mInflater = LayoutInflater.from(context);
		//获得当前资源的id 下面创建view要用
		this.mResurce = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		//获得 LInearLayout对象 如果没有 创建一个
		if(convertView ==null)
		{
			view = (LinearLayout)mInflater.inflate(mResurce, null);
		}
		else
		{
			view = (LinearLayout)convertView;
		}	
		TrainZhanzhan_Piaojia_Info trainZhanzhan_Piaojia_Info =  getItem(position);
		
		TextView train_noView = (TextView)view.findViewById(R.id.viewtrain_no_piaojia);
		TextView start_timeView= (TextView)view.findViewById(R.id.viewstart_time_piaojia);
		TextView arrive_timeView= (TextView)view.findViewById(R.id.viewarrive_time_piaojia);
		TextView train_typeView = (TextView)view.findViewById(R.id.viewtrain_type_piaojia);
		TextView run_timeiView = (TextView)view.findViewById(R.id.viewrun_time_piaojia);
		TextView price1View = (TextView)view.findViewById(R.id.viewpia0jia1);
		TextView price2View = (TextView)view.findViewById(R.id.viewpiaoji2);
		Log.i("wzs", "Adapter view 实例化成功");
		train_noView.setText(trainZhanzhan_Piaojia_Info.train_no);
		train_typeView.setText(trainZhanzhan_Piaojia_Info.train_type);
		arrive_timeView.setText(trainZhanzhan_Piaojia_Info.arrive_time);
		start_timeView.setText(trainZhanzhan_Piaojia_Info.start_time);
		run_timeiView.setText(trainZhanzhan_Piaojia_Info.run_time);
		price1View.setText(trainZhanzhan_Piaojia_Info.price_type1+" "+trainZhanzhan_Piaojia_Info.price1);
		price2View.setText(trainZhanzhan_Piaojia_Info.price_type2+" "+trainZhanzhan_Piaojia_Info.price2);
		Log.i("wzs", "Adapter 设值成功成功");

		
	return view;
	}
}
