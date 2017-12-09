package com.wzs.caipu;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.caipu.cs.CaipuFindCs;
import com.wzs.caipu.cs.CaipuFindCsImpl;
import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import com.wzs.mooc.service.ServiceRuleException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CaipuFindActivity extends Activity
{
	Intent _Intent = getIntent();
	private EditText txtmenu;
	private Button btnfindcaipu;
	public String menu;
	CaipuFindCs caipuFindCs = new CaipuFindCsImpl();
	private static CustomProgressDialog progressDialog = null;
	public static List<Map<String, Object>> caipuInfo_list = new ArrayList<Map<String, Object>>();
	public static List<List<Map<String , Object>>> caipuStepInfo_list = new ArrayList<List<Map<String,Object>>>();
	public void intit()
	{
		txtmenu = (EditText)findViewById(R.id.txtmenu);
		btnfindcaipu = (Button)findViewById(R.id.btnfindcaipu);		
	}
	public void toastShow(String message)
	{
		Toast.makeText(CaipuFindActivity.this, message, Toast.LENGTH_LONG).show();
	}
	public void startResultCaipuActivity()
	{
		Intent _Intent = new Intent(CaipuFindActivity.this, ResultCaipuActivity.class);
		startActivity(_Intent);
	}
	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage("");
		}

		progressDialog.show();
	}

	private static void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.caipu_find);
		intit();
		btnfindcaipu.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0)
			{
				startProgressDialog();
				menu = txtmenu.getText().toString();
				if (menu.equals(""))
				{
					toastShow("菜谱名不能为空");
				}else 
				{
					getCaipu();
				}
				
			}
		});
	}
	public void getCaipu()
	{
		Thread _thread = new Thread(new Runnable() {			
			@Override
			public void run() {
			
				try 
				{
					caipuInfo_list = caipuFindCs.getCaifuInfo(menu);
					caipuStepInfo_list = caipuFindCs.getCaipuStepInfo(menu);
					handler.sendEmptyMessage(ConstantActivity.FLAG_CAIPU_FIND_SUCCES);
					
				} 
				catch(ServiceRuleException messgae)
				{
					Log.i("wzs", "自定义异常");
					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR", messgae.getMessage());
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				}
				catch(ConnectTimeoutException messgae)
				{
					Log.i("wzs", "请求超时");
					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR",ConstantActivity.MSG_CONNECT_TIMEOUT );
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				}
				catch(SocketTimeoutException messgae)
				{
					
					Message msg = new Message();
					Bundle date = new Bundle();
					date.putSerializable("ERROR", ConstantActivity.MSG_RESPONSE_TIMEOUT);
					msg.setData(date);
					handler.sendMessage(msg);
					messgae.printStackTrace();
				}
				catch(Exception e)
				{
					Log.i("wzs", "总异常");
					Message msg = new Message();
					Bundle date = new Bundle();
					//date.putSerializable("ERROR", ConstantActivity.MSG_SERVICE_ERROR);
					date.putSerializable("ERROR",e.getMessage());
					msg.setData(date);
					handler.sendMessage(msg);
					e.printStackTrace();
				}	
			}
		});
		_thread.start();
	}
	private static class IHandler extends Handler
    {
    	private final WeakReference<Activity> mActivtiy;
    	public IHandler(CaipuFindActivity activity )
    	{
    		mActivtiy = new WeakReference<Activity>(activity);
    	}
    	@Override
		public void handleMessage(Message msg) 
    	{
    		stopProgressDialog();			
			int flag = msg.what;
			switch(flag)
			{
				case ConstantActivity.FLAG_CAIPU_FIND_SUCCES:					
					((CaipuFindActivity)mActivtiy.get()).startResultCaipuActivity();
					break;				
				
				case 0:
					Log.i("wzs", "接收到异常消息");
					String ERRORMSG = msg.getData().getSerializable("ERROR").toString();
					((CaipuFindActivity)mActivtiy.get()).toastShow(ERRORMSG);
					Log.i("wzs", ERRORMSG);
					break;
			}
    	}
    	
    }
 IHandler handler  = new IHandler(this);
}
