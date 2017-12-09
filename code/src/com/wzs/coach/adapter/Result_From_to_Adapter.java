package com.wzs.coach.adapter;

import java.util.List;

import com.wzs.coach.entity.coach_from_to_entity;
import com.wzs.mooc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Result_From_to_Adapter extends ArrayAdapter<coach_from_to_entity> {

	private LayoutInflater mInflater ;
	private int mResurce;
	public Result_From_to_Adapter(Context context, int resource,
			List<coach_from_to_entity> objects) {
		super(context, resource, objects);
		
		//���������л��һ�����ֹ�����
		this.mInflater = LayoutInflater.from(context);
		//��õ�ǰ��Դ��id ���洴��viewҪ��
		this.mResurce = resource;
		
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		//��� LInearLayout���� ���û�� ����һ��
		if(convertView ==null)
		{
			view = (LinearLayout)mInflater.inflate(mResurce, null);
		}
		else
		{
			view = (LinearLayout)convertView;
		}
		coach_from_to_entity coach_result = getItem(position);
		TextView startTextView = (TextView)view.findViewById(R.id.viewcoach_start_item);
		TextView arriveTextView = (TextView)view.findViewById(R.id.viewcoach_arrive_item);
		TextView dateTextView = (TextView)view.findViewById(R.id.viewcoach_date_item);
		TextView timetTextView = (TextView)view.findViewById(R.id.viewcoach_time_item);
		TextView priceTextView = (TextView)view.findViewById(R.id.viewcoach_price_item);
		
		startTextView.setText(coach_result.start);
		arriveTextView.setText(coach_result.arrive);
		dateTextView.setText(coach_result.date);
		timetTextView.setText(coach_result.time+"                  Ԥ��");
		priceTextView.setText(coach_result.price);
		return view ;
	}
}
