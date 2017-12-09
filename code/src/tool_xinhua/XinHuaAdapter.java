package tool_xinhua;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzs.mooc.R;

import entity_xinhua.XinHuaEntity;

public class XinHuaAdapter extends ArrayAdapter<XinHuaEntity>
{

	private LayoutInflater mInflater;
	private int  mResource;
	public XinHuaAdapter(Context context, int resource,
			List<XinHuaEntity> objects) {
		super(context, resource, objects);
		this.mInflater=LayoutInflater.from(context);		
		this.mResource=resource;
	}

	public View getView(int position,View conerView,ViewGroup parent){
		
		LinearLayout view=null;
		if (conerView==null) {
			view=(LinearLayout)mInflater.inflate(mResource, null);
			}
		else 
		{
			view=(LinearLayout)conerView;	
			}		
		XinHuaEntity xinHuaEntity= getItem(position);
		
		TextView xiangjieView=(TextView)view.findViewById(R.id.xiangjie);
		
		xiangjieView.setText(xinHuaEntity.XiangJie);
		return view;
		}

	

}
