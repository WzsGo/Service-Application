package com.wzs.weizhang.adapter;

import java.util.List;

import com.wzs.mooc.R;
import com.wzs.weizhang.entity.Weizhang_ResultInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeizhangResultAdapter extends ArrayAdapter<Weizhang_ResultInfo>{

	private LayoutInflater mInflater ;
	private int mResurce;
	public WeizhangResultAdapter(Context context, int resource,
			List<Weizhang_ResultInfo> objects) {
		super(context, resource, objects);
		this.mInflater = LayoutInflater.from(context);
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
		Weizhang_ResultInfo info = getItem(position);
		TextView dateView = (TextView)view.findViewById(R.id.txtweizhang_date);
		TextView actView = (TextView)view.findViewById(R.id.txtweizhang_act);
		TextView areaView = (TextView)view.findViewById(R.id.txtweizhang_area);
		TextView fenView = (TextView)view.findViewById(R.id.txtweizhang_fen);
		TextView moneyView = (TextView)view.findViewById(R.id.txtweizhang_money);
		dateView.setText(info.date);
		actView.setText("�¹ʣ�"+info.act);
		areaView.setText("�ص㣺"+info.area);
		fenView.setText("�۷֣�"+info.fen+"��");
		moneyView.setText("���"+info.money+"Ԫ");
		Log.i("wzs", "��ֵ�ɹ�");
		return view;
	}
	
}
