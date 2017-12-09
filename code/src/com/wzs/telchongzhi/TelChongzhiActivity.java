package com.wzs.telchongzhi;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.UUID;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
//import com.wzs.mooc.R.id;
import com.wzs.mooc.service.ServiceRuleException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TelChongzhiActivity extends Activity  {

	Intent minIntent = getIntent();
	private EditText txtphoneno ;
	private RadioGroup radiogroup_cardnum ;
	@SuppressWarnings("unused")
	private RadioButton radio_tel_10,radio_tel_20,radio_tel_30,radio_tel_50,radio_tel_100,radio_tel_300;	
	private TextView viewgame_area;	
	private TextView viewinprice ;
	private Button btntel_chongzhi;
	private static String phoneno;
	private static String cardnum;
	private String orderId;
	private String phoneno2;
	private String sign;
	private Button btn_back;
	private static CustomProgressDialog progressDialog = null;
	public static  int step2_flag = 0;
	Telchongzhi_cs telChongzhi_tep = new TelChongzhi_csImpl();
	public static TelChongzhiEntity telChongzhiEntity_tep2 = null;
	public static TelChongzhiEntity telChongzhiEntity_tep3 = null;
	UID uid = new UID();
	public void intit()
	{
		txtphoneno = (EditText)findViewById(R.id.txtphoneno);
		viewgame_area = (TextView)findViewById(R.id.viewgame_area);
		radiogroup_cardnum = (RadioGroup)findViewById(R.id.radioGroup_tel);
		radio_tel_10 = (RadioButton)findViewById(R.id.radiotel_10);
		radio_tel_20 = (RadioButton)findViewById(R.id.radiotel_20);
		radio_tel_30 = (RadioButton)findViewById(R.id.radiotel_30);
		radio_tel_50 = (RadioButton)findViewById(R.id.radiotel_50);
		radio_tel_100 = (RadioButton)findViewById(R.id.radiotel_100);
		radio_tel_300 = (RadioButton)findViewById(R.id.radiotel_300);
		viewinprice = (TextView)findViewById(R.id.viewinprice);
		btntel_chongzhi = (Button)findViewById(R.id.btntelchongzhi);
		btn_back = (Button)findViewById(R.id.btn_chongzhi_back);
	}
	public void toastTip(String meaaage)
	{
		Toast.makeText(TelChongzhiActivity.this, meaaage, Toast.LENGTH_LONG).show();		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_telchongzhi);
		intit();
		viewinprice.setText("您还没有选择充值金额");
		btn_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		radiogroup_cardnum.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int arg1) {
				int id = group.getCheckedRadioButtonId();
				switch (id)
				{
				case R.id.radiotel_10:
					
					 phoneno = txtphoneno.getText().toString();
					// cardnum = radio_tel_10.getText().toString();
					 cardnum = "10";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
						
					 }			
					break;
				case R.id.radiotel_20:
					phoneno = txtphoneno.getText().toString();
					 //cardnum = radio_tel_20.getText().toString();
					cardnum = "20";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
					 }			
					break;
					
				case R.id.radiotel_30:
					phoneno = txtphoneno.getText().toString();
					// cardnum = radio_tel_30.getText().toString();
					cardnum = "30";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
					 }	
					break;
				case R.id.radiotel_50:
					phoneno = txtphoneno.getText().toString();
					// cardnum = radio_tel_50.getText().toString();
					cardnum = "50";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
					 }	
					break;
				case R.id.radiotel_100:
					 phoneno = txtphoneno.getText().toString();
					// cardnum = radio_tel_100.getText().toString();
					 cardnum = "100";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
					 }	
					break;
				case R.id.radiotel_300:
					phoneno = txtphoneno.getText().toString();
					//cardnum = radio_tel_300.getText().toString();
					cardnum = "300";
					 if (phoneno.equals(""))
					 {
						 toastTip("手机号不能为空");	
					 }
					 else
					 {
						 step2_flag = 1;
						 telChongzhi_tep2();
					 }	
					break;
				}
			}
		});

		
	}
	public void telChongzhi_tep2()//public void telChongzhi_tep2(final String phoneno,final String cardnum)
	{
		 	
		Thread _thread = new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				try
				{
					int flag = telChongzhi_tep.telChongzhi_tep1(phoneno, cardnum);
					if(flag == 1)
					{
						telChongzhiEntity_tep2 = telChongzhi_tep.telChongzhi_tep2(phoneno, cardnum);
						handler.sendEmptyMessage(ConstantActivity.FLAG_STEP_SUCCES);		
					}	
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
						date.putSerializable("ERROR", ConstantActivity.MSG_SERVICE_ERROR);						
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
    	public IHandler(TelChongzhiActivity activity )
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
				case ConstantActivity.FLAG_STEP_SUCCES:					
					((TelChongzhiActivity)mActivtiy.get()).step2reult_Show();
					((TelChongzhiActivity)mActivtiy.get()).telChongzhi_setp3();
					break;
				case ConstantActivity.FLAG_STEP3_SUCCES:					
					((TelChongzhiActivity)mActivtiy.get()).telChongzhi_tep4();
					Log.i("wzs", "接到命令 启动step4");
					break;
				
				case 0:					
					String ERRORMSG = msg.getData().getSerializable("ERROR").toString();
					((TelChongzhiActivity)mActivtiy.get()).toastTip(ERRORMSG);
					
					break;
			}
    	}
    	
    }
	IHandler handler  = new IHandler(this);
	
	public void step2reult_Show()
 {
	 viewgame_area.setText(telChongzhiEntity_tep2.game_area);
	 viewinprice.setText(telChongzhiEntity_tep2.inprice+"元");
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

	public void telChongzhi_setp3 ()
	{			
			btntel_chongzhi.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					phoneno2 = txtphoneno.getText().toString();
					
					if (phoneno2.equals(phoneno))
					{
						Log.i("wzs", "下一句错了");
						orderId = UUID.randomUUID().toString().replaceAll("-", "");
						Log.i("wzs", "上一句没有执行"+orderId);
						sign ="JH2b73142496e594008c03624c81c27339"+"4520c1a8adbe7d398785eb5eac062699"+phoneno2+cardnum+orderId;
						Log.i("wzs", sign);
						Log.i("ws", orderId);
						startProgressDialog();
						Thread _thread2 = new Thread(new Runnable() {												
							@Override
							public void run()
							{								
								try 
								{
									telChongzhiEntity_tep3 = telChongzhi_tep.telChongzhi_tep3(phoneno2, cardnum, orderId, sign);
									handler.sendEmptyMessage(ConstantActivity.FLAG_STEP3_SUCCES);
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
									date.putSerializable("ERROR", ConstantActivity.weizhang);
									//date.putSerializable("ERROR", e.getMessage());
									msg.setData(date);
									handler.sendMessage(msg);
									e.printStackTrace();
								}	
								
							}
						});
						_thread2.start();
					}
					else
					{
						phoneno = phoneno2;
						 if (phoneno.equals(""))
						 {
							 toastTip("手机号不能为空");	
						 }
						 else
						 {
							 step2_flag = 1;
							 telChongzhi_tep2();
						 }	
					}
				}					
			});
		}
	public void telChongzhi_tep4()
	{
		btntel_chongzhi.setText("等待充值");
		
	}
}
