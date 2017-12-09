package com.wzs.start;



import com.wzs.daohang.DaohangActivity;
import com.wzs.mooc.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/*import android.view.Menu;
import android.view.WindowManager;*/
/**
 * 
 * @author 阿峰
 *
 */
public class AppStart extends Activity{

	private SharedPreferences preferences;  
	private Editor editor; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.appstart);
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
		//判断是不是首次登录， 
        if(preferences.getBoolean("firststart", true)) {
        	editor = preferences.edit(); 
        	//将登录标志位设置为false，下次登录时不在显示首次登录界面  
            editor.putBoolean("firststart", false);  
            editor.commit(); 
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){	
					Intent intent = new Intent (AppStart.this,Whatsnew.class);			
					startActivity(intent);			
					AppStart.this.finish();
					
				}
			}, 1000);
		}
        
        else
		{
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){	
					Intent intent = new Intent (AppStart.this,DaohangActivity.class);			
					startActivity(intent);			
					AppStart.this.finish();
					
				}
			}, 1000);
		}
		
	
	
   }
}