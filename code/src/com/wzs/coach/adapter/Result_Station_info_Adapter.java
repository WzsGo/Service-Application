package com.wzs.coach.adapter;

import java.util.List;

import com.wzs.coach.entity.coach_station_info_entity;
import com.wzs.mooc.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Result_Station_info_Adapter extends ArrayAdapter<coach_station_info_entity>{

	private LayoutInflater mInflater ;
	private int mResurce;
	public Result_Station_info_Adapter(Context context, int resource,
			List<coach_station_info_entity> objects) {
		super(context, resource, objects);
		this.mInflater = LayoutInflater.from(context);
		//获得当前资源的id 下面创建view要用
		this.mResurce = resource;
	}
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
		coach_station_info_entity coach_station_result = getItem(position);
		
		TextView nameTextView  =(TextView)view.findViewById(R.id.viewcoach_name);
		TextView telTextView = (TextView)view.findViewById(R.id.viewcoach_tel);
		TextView addsTextView = (TextView)view.findViewById(R.id.viewcoach_adds);
		Log.i("wzs", "汽车信息activity设值成功");
		nameTextView.setText(coach_station_result.name);
		telTextView.setText(coach_station_result.tel);
		addsTextView.setText(coach_station_result.adds);
		
		
		return view ;
	}
}
