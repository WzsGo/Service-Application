package tool;
import entity.*;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzs.mooc.R;

public class TranslationAdapter extends ArrayAdapter<TranslationEntity>{
	//定义一个布局函数
	private LayoutInflater mInflater;
	private int mResurce;
	public TranslationAdapter(Context context,int resource,List<TranslationEntity> objects) {
		super(context, resource,objects);
		// 从上下文中获得一个布局管理器
		this.mInflater=LayoutInflater.from(context);
		//获得当前资源的id，下面创建view要用
		this.mResurce=resource;
	}
		public View getView(int position,View convertView,ViewGroup parent) {
		LinearLayout view=null;
		//获得LinearLayout对象，如果没有，创建一个
		if (convertView==null) {
			view=(LinearLayout)mInflater.inflate(mResurce, null);
		}
		else
		{
			view=(LinearLayout)convertView;
		}
		//绑定一个数据到Item对象
			TranslationEntity translationEntity=getItem(position);
			TextView viewValue=(TextView)view.findViewById(R.id.item_value);
			TextView viewKey=(TextView)view.findViewById(R.id.item_key);
			viewKey.setText("例句："+translationEntity.Key);
			viewValue.setText("翻译："+translationEntity.Value);
		return view;
		
	}
	
	
	

}
