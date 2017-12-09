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
	//����һ�����ֺ���
	private LayoutInflater mInflater;
	private int mResurce;
	public TranslationAdapter(Context context,int resource,List<TranslationEntity> objects) {
		super(context, resource,objects);
		// ���������л��һ�����ֹ�����
		this.mInflater=LayoutInflater.from(context);
		//��õ�ǰ��Դ��id�����洴��viewҪ��
		this.mResurce=resource;
	}
		public View getView(int position,View convertView,ViewGroup parent) {
		LinearLayout view=null;
		//���LinearLayout�������û�У�����һ��
		if (convertView==null) {
			view=(LinearLayout)mInflater.inflate(mResurce, null);
		}
		else
		{
			view=(LinearLayout)convertView;
		}
		//��һ�����ݵ�Item����
			TranslationEntity translationEntity=getItem(position);
			TextView viewValue=(TextView)view.findViewById(R.id.item_value);
			TextView viewKey=(TextView)view.findViewById(R.id.item_key);
			viewKey.setText("���䣺"+translationEntity.Key);
			viewValue.setText("���룺"+translationEntity.Value);
		return view;
		
	}
	
	
	

}
