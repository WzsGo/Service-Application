package com.wzs.mooc.tool;

import java.util.List;

import com.wzs.mooc.R;

import com.wzs.mooc.entity.TrainZhanzhan_Yupiao_Info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tool_ResultYupiao_adapter extends ArrayAdapter<TrainZhanzhan_Yupiao_Info> {
	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_ResultYupiao_adapter(Context context, int resource,
			List<TrainZhanzhan_Yupiao_Info> objects) {
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
		TrainZhanzhan_Yupiao_Info trainZhanzhan_Yupiao_InfoInfo =  getItem(position);
		
		TextView train_noView = (TextView)view.findViewById(R.id.viewtrain_no);
		TextView start_timeView= (TextView)view.findViewById(R.id.viewstart_time);
		TextView arrive_timeView= (TextView)view.findViewById(R.id.viewarrive_time);
		//TextView train_class_nameView = (TextView)view.findViewById(R.id.viewtrain_class_name);
		TextView lishiView = (TextView)view.findViewById(R.id.viewlishi);
		TextView rw_numView = (TextView)view.findViewById(R.id.viewrw_num);
		//TextView rz_numView = (TextView)view.findViewById(R.id.viewrz_num);
		TextView tz_numView = (TextView)view.findViewById(R.id.viewtz_num);
		TextView yw_numView = (TextView)view.findViewById(R.id.viewyw_num);
		TextView yz_numView = (TextView)view.findViewById(R.id.viewyz_num);
		TextView ze_numView = (TextView)view.findViewById(R.id.viewze_num);
		TextView zy_numView = (TextView)view.findViewById(R.id.viewzy_num);
		TextView swz_numView = (TextView)view.findViewById(R.id.viewswz_num);
		TextView wz_numView = (TextView)view.findViewById(R.id.viewwz_num);
		Log.i("wzs","Adapter view实例化成功");
		train_noView.setText(trainZhanzhan_Yupiao_InfoInfo.train_no);
		start_timeView.setText(trainZhanzhan_Yupiao_InfoInfo.start_time);
		arrive_timeView.setText(trainZhanzhan_Yupiao_InfoInfo.arrive_time);
		//train_class_nameView.setText(trainZhanzhan_Yupiao_InfoInfo.train_class_name);
		lishiView.setText(trainZhanzhan_Yupiao_InfoInfo.lishi);
		//lishiView.setText("预定");
		rw_numView.setText("软卧 "+trainZhanzhan_Yupiao_InfoInfo.rw_num);
		//rz_numView.setText(trainZhanzhan_Yupiao_InfoInfo.rz_num);
		tz_numView.setText("特等座 "+trainZhanzhan_Yupiao_InfoInfo.tz_num);
		yw_numView.setText("硬卧  "+trainZhanzhan_Yupiao_InfoInfo.yw_num);
		yz_numView.setText("硬座 "+trainZhanzhan_Yupiao_InfoInfo.yz_num);
		ze_numView.setText("二等座  "+trainZhanzhan_Yupiao_InfoInfo.ze_num);
		zy_numView.setText("一等座 "+trainZhanzhan_Yupiao_InfoInfo.zy_num);
		swz_numView.setText("商务座  "+trainZhanzhan_Yupiao_InfoInfo.swz_num);
		wz_numView.setText("无座 "+trainZhanzhan_Yupiao_InfoInfo.wz_num);
		Log.i("wzs","Adapter中设值成功");
		return view;
	}

	
}

