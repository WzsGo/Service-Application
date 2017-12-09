package com.wzs.mooc;

import com.wzs.mooc.tool.Tool_Kuaidi_Adapter;
import android.view.View.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class Kuaidi_result extends Activity {

	private ListView list;
	private Button btn_back;
	Intent mintent = getIntent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resultfind_kuaidi);
		btn_back = (Button)findViewById(R.id.btn_kuadi_result_back);
		list = (ListView) findViewById(R.id.ltviewkuaidi);
		Tool_Kuaidi_Adapter adapter = new Tool_Kuaidi_Adapter(
				Kuaidi_result.this, R.layout.kuaidi_item,
				KuaidiActivity.listKuaidiEntities);
		list.setAdapter(adapter);
		btn_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}

}
