package com.wzs.caipu;

import java.util.Map;

import com.wzs.caipu.adapter.CaipuResult_Adapter;
import com.wzs.mooc.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter.ViewBinder;

public class ResultCaipuActivity extends Activity {

	public ListView resultListView ;
	public Button btnFindCaipu_resulu;
	
	private void intit() 
	{
		resultListView = (ListView)findViewById(R.id.resultListView);
		btnFindCaipu_resulu = (Button)findViewById(R.id.btnFindCaipu_resulu);

	}
	class MyViewBinder implements ViewBinder
	{
	    /**
	     * view��Ҫ�嶥���ݵ���ͼ
	     * data��Ҫ�󶨵���ͼ������
	     * textRepresentation��һ����ʾ��֧�����ݵİ�ȫ���ַ����������data.toString()����ַ�������������Null
	     * ����ֵ��������ݰ󶨵���ͼ�����棬���򷵻ؼ�
	     */
	    public boolean setViewValue(View view, Object data,
	            String textRepresentation) {
	        if((view instanceof ImageView)&(data instanceof Bitmap))
	        {
	            ImageView iv = (ImageView)view;
	            Bitmap bmp = (Bitmap)data;
	            iv.setImageBitmap(bmp);
	            return true;
	        }
	        return false;
	    }	   	    
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.caipu_result);
		intit();
		btnFindCaipu_resulu.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(ResultCaipuActivity.this, CaipuFindActivity.class);
				startActivity(_Intent);				
			}
		});
		CaipuResult_Adapter adapter = new CaipuResult_Adapter(ResultCaipuActivity.this,
															CaipuFindActivity.caipuInfo_list, 
															R.layout.caipu_result_item,
															new String[]{"title","ingredients","burden","albums"},
															new int[]{R.id.txttitle,R.id.txtingredients,R.id.txtburden,R.id.imgview_albums});
		adapter.setViewBinder(new MyViewBinder());
		resultListView.setAdapter(adapter);
		resultListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent _Intent = new Intent(ResultCaipuActivity.this, ResultCaipu_ListviewresultActivity.class);
				Map< String , Object> _map = CaipuFindActivity.caipuInfo_list.get(arg2);
				String _title = _map.get("title").toString();
				Log.i("wzs", arg2+"��һ��");
				Log.i("wzs", _title+"��һ��");
				Bundle _Bundle = new Bundle();
				_Bundle.putInt("arg2", arg2);
				_Bundle.putString("_title", _title);
				_Intent.putExtra("_Bundle", _Bundle);
				startActivity(_Intent);
				
				
			}
		});
	}
	
	
}
