package com.wzs.mooc.tool;

import java.util.List;

import com.wzs.mooc.R;
import com.wzs.mooc.entity.KuaidiEntity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tool_Kuaidi_Adapter extends ArrayAdapter<KuaidiEntity>{

	//����һ�����ֹ�����
	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_Kuaidi_Adapter(Context context, int resource,List<KuaidiEntity> objects)
	{
		super(context, resource, objects);
		//���������л��һ�����ֹ�����
		this.mInflater = LayoutInflater.from(context);
		//��õ�ǰ��Դ��id ���洴��viewҪ��
		this.mResurce = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{		
		//return super.getView(position, convertView, parent);
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
		//��һ�����ݵ�Item����
		KuaidiEntity kuaidiEntity = getItem(position);
		//���LInearLayout�����е�textview����
		Log.i("wzs", "Adapter �齨ʵ�����ɹ�");
		TextView viewDatetime = (TextView)view.findViewById(R.id.item_kuaididatetime);
		TextView viewRemark = (TextView)view.findViewById(R.id.item_kuadiremark);
		
		Log.i("wzs", kuaidiEntity.Datetime);
		 viewDatetime.setText(kuaidiEntity.Datetime);
		 viewRemark.setText(kuaidiEntity.Remark);
		 
		 
		 
		
		return view;
	}
	
	
	
}
