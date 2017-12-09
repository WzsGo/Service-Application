package com.wzs.mooc.tool;

import android.app.Activity;
import android.app.ProgressDialog;

public class Tool_Dialog extends Activity{
	public ProgressDialog dialog;
	public String txtTtitle;
	public String txtMessage;
	public Tool_Dialog()
	{
		
	}
	public Tool_Dialog(String title,String message)
	{
		this.txtTtitle = title;
		this.txtMessage = message;
	}
	public void setDialog(String txtTtitle,String txtMessage)
	{
		dialog = new ProgressDialog(this);
		dialog.setTitle(txtTtitle);
		dialog.setMessage(txtMessage);
		dialog.setCancelable(true);
		dialog.show();
	}

}
