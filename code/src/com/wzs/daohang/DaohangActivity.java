package com.wzs.daohang;

import com.demo.translationactivity.TranslationActivity;
import com.demo.xinhuaactivity.XinHuaActivity;
import com.wzs.caipu.CaipuFindActivity;
import com.wzs.coach.FindCoachActivity;
import com.wzs.mooc.FindTrainActivity;
import com.wzs.mooc.KuaidiActivity;
import com.wzs.mooc.R;
import com.wzs.telchongzhi.TelChongzhiActivity;
import com.wzs.weather1.FindWeatherActivity;
import com.wzs.weizhang.WeizhangFindActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.ImageButton;

public class DaohangActivity extends Activity 
{

	private ImageButton btn_weizhang ;
	private ImageButton btn_coach ;
	private ImageButton btn_train ;
	private ImageButton btn_translation ;
	private ImageButton btn_xinhua ;
	private ImageButton btn_weather ;
	private ImageButton btn_kuaidi ;
	private ImageButton btn_caipu ;
	private ImageButton btn_chongzhi ;
	public void init()
	{
		btn_weizhang = (ImageButton)findViewById(R.id.btn_weizhang);
		btn_coach = (ImageButton)findViewById(R.id.btn_coach);
		btn_train = (ImageButton)findViewById(R.id.btn_train);
		btn_translation = (ImageButton)findViewById(R.id.btn_translation);
		btn_xinhua = (ImageButton)findViewById(R.id.btn_xinhua);
		btn_weather = (ImageButton)findViewById(R.id.btn_weather);
		btn_kuaidi = (ImageButton)findViewById(R.id.btn_kuaidi);
		btn_caipu = (ImageButton)findViewById(R.id.btn_caipu);
		btn_chongzhi = (ImageButton)findViewById(R.id.btn_chongzhi);				
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.daohang_activity);
		init();
		
		btn_weizhang.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,WeizhangFindActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_coach.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,FindCoachActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_train.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,FindTrainActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_translation.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,TranslationActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_xinhua.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,XinHuaActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_weather.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,FindWeatherActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_kuaidi.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,KuaidiActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_caipu.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,CaipuFindActivity.class);
				startActivity(_Intent);
				
			}
		});
		btn_chongzhi.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(DaohangActivity.this,TelChongzhiActivity.class);
				startActivity(_Intent);
				
			}
		});
		
	}
	 @SuppressWarnings("deprecation")
	@Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	            // 创建退出对话框  
	            AlertDialog isExit = new AlertDialog.Builder(this).create();  
	            // 设置对话框标题  
	            isExit.setTitle("系统提示");  
	            // 设置对话框消息  
	            isExit.setMessage("        退出程序");  
	            // 添加选择按钮并注册监听  
	            isExit.setButton("确定", listener);
	            isExit.setButton2("取消", listener);
	           /* isExit.setButton("确定", listener);  
	            isExit.setButton2("取消", listener);  */
	            // 显示对话框  
	            isExit.show();  
	  
	        }  
	          
	        return false;  
	          
	    }  
	    /**监听对话框里面的button点击事件*/  
	    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
	    {  
	        public void onClick(DialogInterface dialog, int which)  
	        {  
	            switch (which)  
	            {  
	            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
	                finish();  
	                break;  
	            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
	                break;  
	            default:  
	                break;  
	            }  
	        }  
	    };    
}
