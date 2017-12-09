package com.wzs.mooc.tool;

import android.app.Activity;
import android.widget.Toast;

public class Tool_Toast extends Activity {
	String tiptext;	
	public Tool_Toast()
	{
		
	}
	public Tool_Toast(String Tip_message)
	{
		this.tiptext = Tip_message;
	}
	public void gettoastTip()
	{
		Toast.makeText(getApplicationContext(), tiptext, Toast.LENGTH_SHORT).show();
	}
}
