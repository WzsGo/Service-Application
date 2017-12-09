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

	//����һ�����ֹ�����
	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_Resyultchecii_Adapter(Context context, int resource,List<TrainCheciInfo> objects)
	{
		
		super(context, resource, objects);
		//���������л��һ�����ֹ�����
		this.mInflater = LayoutInflater.from(context);
		//��õ�ǰ��Դ��id ���洴��viewҪ��
		this.mResurce = resource;
	}
	@Override
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
		Log.i("wzs","���� view �ɹ�");
		TrainCheciInfo trainCheciInfo =  getItem(position);
		Log.i("wzs","��ȡλ�� �ɹ�");
		TextView train_idTextView = (TextView)view.findViewById(R.id.item_train_id);
		Log.i("wzs","��� ʵ���� �ɹ�1");
		TextView station_nameTextView = (TextView)view.findViewById(R.id.item_station_name);
		Log.i("wzs","��� ʵ���� �ɹ�2");
		TextView arrived_timeTextView = (TextView)view.findViewById(R.id.item_arrived_time);
		Log.i("wzs","��� ʵ���� �ɹ�3");
		TextView leave_timeTextView = (TextView)view.findViewById(R.id.item_leave_time);
		Log.i("wzs","��� ʵ���� �ɹ�4");
		TextView stayTextView = (TextView)view.findViewById(R.id.item_stay);
		Log.i("wzs","��� ʵ���� �ɹ�5");
		TextView mileageTextView = (TextView)view.findViewById(R.id.item_mileage);
		Log.i("wzs","��� ʵ���� �ɹ�6");
		
		train_idTextView.setText(trainCheciInfo.train_id+"");
		
		station_nameTextView.setText(trainCheciInfo.station_name);
		Log.i("wzs","��ֵ �ɹ� 2");
		arrived_timeTextView.setText(trainCheciInfo.arrived_time);
		Log.i("wzs","��ֵ �ɹ� 3");
		leave_timeTextView.setText(trainCheciInfo.leave_time);
		
		stayTextView.setText(trainCheciInfo.stay);
		
		mileageTextView.setText(trainCheciInfo.mileage);
		
		Log.i("wzs","adapter �� �������ݳɹ��������� �����"+trainCheciInfo.mileage);
		return view;
	}


	

	

}
