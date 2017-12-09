package com.wzs.caipu;


import java.util.List;
import java.util.Map;

import com.wzs.mooc.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class ResultCaipu_ListviewresultActivity extends Activity
{
	public ListView listView_caipuResult_item;
	//public TextView txt_title_steps;
	//List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();		
	public void intit()
	{
		listView_caipuResult_item = (ListView)findViewById(R.id.listView_caipuResult_item);
		//txt_title_steps = (TextView)findViewById(R.id.txt_title_steps);
	}
	class MyViewBinder implements ViewBinder
	{
	    /**
	     * view：要板顶数据的视图
	     * data：要绑定到视图的数据
	     * textRepresentation：一个表示所支持数据的安全的字符串，结果是data.toString()或空字符串，但不能是Null
	     * 返回值：如果数据绑定到视图返回真，否则返回假
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
			setContentView(R.layout.caipu_result_item_list);
			intit();
			Intent _Intent = getIntent();
			if(_Intent != null)
			{
				Bundle _Bundle = new Bundle();
				_Bundle = _Intent.getBundleExtra("_Bundle");
				int arg2 = _Bundle.getInt("arg2");
				String _title = _Bundle.getString("_title");
				Log.i("wzs", arg2+"");
				Log.i("wzs", _title);
				//txt_title_steps.setText(_title+"的制作步骤");		
				Log.i("wzs", "上一句木有异常");
				
				List<Map<String, Object>> list  = CaipuFindActivity.caipuStepInfo_list.get(arg2);												
				SimpleAdapter adapter = new SimpleAdapter(ResultCaipu_ListviewresultActivity.this,
														list, 
														R.layout.caipu_result_item_list_item, 
														new String[]{"img","step"}, 
														new int[]{R.id.imgview_caipu_steps,R.id.view_caipu_steps});
				adapter.setViewBinder(new MyViewBinder());
				listView_caipuResult_item.setAdapter(adapter);
			}

	}
	
}
