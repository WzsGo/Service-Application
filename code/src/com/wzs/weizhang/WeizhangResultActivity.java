package com.wzs.weizhang;

import java.util.List;

import com.wzs.mooc.R;
import com.wzs.weizhang.adapter.WeizhangResultAdapter;
import com.wzs.weizhang.entity.Weizhang_ResultInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WeizhangResultActivity extends Activity 
{

	private ListView  listview_weizhangt_result ;
	Intent _Intent = getIntent();
	public TextView hphmTextView;
	public TextView jiluTextView ;
	public TextView fenTextView ;
	public TextView moneyTextView;
	private Button btn_back;
	public void init()
	{
		jiluTextView = (TextView)findViewById(R.id.view_result_jilu);
		hphmTextView = (TextView)findViewById(R.id.view_result_hphm);
		fenTextView = (TextView)findViewById(R.id.view_result_fen);
		moneyTextView = (TextView)findViewById(R.id.view_result_money);
		listview_weizhangt_result = (ListView)findViewById(R.id.listview_weizhangt_result);
		btn_back = (Button)findViewById(R.id.btn_weizhang_back);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weizhangreault);
		init();		 
		 if (_Intent!=null) 
		 {
			 Log.i("wzs", "Æô¶¯Activity³É¹¦");
			 hphmTextView.setText(WeizhangFindActivity.hphm);
			 int len = WeizhangFindActivity.resultList.size();
			 List<Weizhang_ResultInfo> _List = WeizhangFindActivity.resultList;
			 jiluTextView.setText(len);
			 int fen = 0;
			 int money = 0;
			 for (int i = 0; i < len; i++) 
			 {
				Weizhang_ResultInfo resultInfo = _List.get(i);
				String fenString = resultInfo.fen;
				int fen1 = Integer.parseInt(fenString);
				fen = fen +fen1;
				String moneyString = resultInfo.money;
				int money1 = Integer.parseInt(moneyString);
				money = money + money1;													
			}
			 fenTextView.setText(fen+"");
			 moneyTextView.setText(money+"");
			 Log.i("wzs", money+"");
			 WeizhangResultAdapter adapter = new WeizhangResultAdapter(WeizhangResultActivity.this, 
					 R.layout.weizhang_result_item,
					 WeizhangFindActivity.resultList);
			 listview_weizhangt_result.setAdapter(adapter);
			 btn_back.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					finish();
					
				}
			});
		}
	}


	
}
