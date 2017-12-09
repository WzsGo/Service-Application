package com.wzs.mooc.tool;

import java.util.List;

import com.wzs.mooc.R;
import com.wzs.mooc.entity.TrainCheciInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tool_Resyultchecii_Adapter extends ArrayAdapter<TrainCheciInfo> {

	//定义一个布局管理器
	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_Resyultchecii_Adapter(Context context, int resource,List<TrainCheciInfo> objects)
	{
		
		super(context, resource, objects);
		//从上下文中获得一个布局管理器
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
		Log.i("wzs","创建 view 成功");
		TrainCheciInfo trainCheciInfo =  getItem(position);
		Log.i("wzs","获取位置 成功");
		TextView train_idTextView = (TextView)view.findViewById(R.id.item_train_id);
		Log.i("wzs","组件 实例化 成功1");
		TextView station_nameTextView = (TextView)view.findViewById(R.id.item_station_name);
		Log.i("wzs","组件 实例化 成功2");
		TextView arrived_timeTextView = (TextView)view.findViewById(R.id.item_arrived_time);
		Log.i("wzs","组件 实例化 成功3");
		TextView leave_timeTextView = (TextView)view.findViewById(R.id.item_leave_time);
		Log.i("wzs","组件 实例化 成功4");
		TextView stayTextView = (TextView)view.findViewById(R.id.item_stay);
		Log.i("wzs","组件 实例化 成功5");
		TextView mileageTextView = (TextView)view.findViewById(R.id.item_mileage);
		Log.i("wzs","组件 实例化 成功6");
		
		train_idTextView.setText(trainCheciInfo.train_id+"");
		
		station_nameTextView.setText(trainCheciInfo.station_name);
		Log.i("wzs","设值 成功 2");
		arrived_timeTextView.setText(trainCheciInfo.arrived_time);
		Log.i("wzs","设值 成功 3");
		leave_timeTextView.setText(trainCheciInfo.leave_time);
		
		stayTextView.setText(trainCheciInfo.stay);
		
		mileageTextView.setText(trainCheciInfo.mileage);
		
		Log.i("wzs","adapter 中 配置数据成功，数据是 里程数"+trainCheciInfo.mileage);
		return view;
	}


	

	

}
