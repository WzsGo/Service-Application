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

	//定义一个布局管理器
	private LayoutInflater mInflater ;
	private int mResurce;
	public Tool_Kuaidi_Adapter(Context context, int resource,List<KuaidiEntity> objects)
	{
		super(context, resource, objects);
		//从上下文中获得一个布局管理器
		this.mInflater = LayoutInflater.from(context);
		//获得当前资源的id 下面创建view要用
		this.mResurce = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{		
		//return super.getView(position, convertView, parent);
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
		//绑定一个数据到Item对象
		KuaidiEntity kuaidiEntity = getItem(position);
		//获得LInearLayout对象中德textview对象
		Log.i("wzs", "Adapter 组建实例化成功");
		TextView viewDatetime = (TextView)view.findViewById(R.id.item_kuaididatetime);
		TextView viewRemark = (TextView)view.findViewById(R.id.item_kuadiremark);
		
		Log.i("wzs", kuaidiEntity.Datetime);
		 viewDatetime.setText(kuaidiEntity.Datetime);
		 viewRemark.setText(kuaidiEntity.Remark);
		 
		 
		 
		
		return view;
	}
	
	
	
}
