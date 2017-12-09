package com.wzs.mooc;


import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.mooc.entity.TrainCheciInfo;
import com.wzs.mooc.entity.TrainZhanzhan_Piaojia_Info;
import com.wzs.mooc.entity.TrainZhanzhan_Yupiao_Info;
import com.wzs.mooc.service.Findtrain;
import com.wzs.mooc.service.FindtrainImpl;
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
import android.widget.Toast;

public class FindTrainActivity extends Activity{
	Intent mIntent = getIntent();
	private Button btnFindCheci;
	private EditText txtCheci;	
	private static CustomProgressDialog progressDialog = null;
	public static List<TrainCheciInfo> Train_List = new ArrayList<TrainCheciInfo>();	
	public  static TrainCheciInfo trainCheciInfo ;
	
	private Findtrain findtrain = new FindtrainImpl();

	private EditText txtfrom;
	private EditText  txtto;
	private EditText txtdate;
	private Button btnfindyupiao;
	private Button btnfindpiaojiaButton;
	
	public static List<TrainZhanzhan_Yupiao_Info> TrainYupiao_List = new ArrayList<TrainZhanzhan_Yupiao_Info>();
	public static List<TrainZhanzhan_Piaojia_Info> TrainPiaojia_list = new ArrayList<TrainZhanzhan_Piaojia_Info>();
	private Button btn_back;
	public static String from;
	public static String to;
	public static String date;
	public void intit()
	{
		btnFindCheci = (Button)findViewById(R.id.btnfinfcheci);
		txtCheci = (EditText)findViewById(R.id.txtcheci);	
		
		txtfrom = (EditText)findViewById(R.id.txtfrom);
		txtto = (EditText)findViewById(R.id.txtto);
		txtdate = (EditText)findViewById(R.id.txtdate);
		btnfindyupiao = (Button)findViewById(R.id.btnfindyupiao);
		btnfindpiaojiaButton = (Button)findViewById(R.id.btnfindpiaojia);
		btn_back = (Button)findViewById(R.id.btn_train_find_back);
		
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
	
	public void toastTip(String messagrTip)
	{
		Log.i("wzs", "开始显示toast"+messagrTip);
		Toast.makeText(FindTrainActivity.this, messagrTip, Toast.LENGTH_LONG).show();
	}
	public void startResult_Findtraincheci()
	{
		Intent mintent = new Intent(FindTrainActivity.this,Result_Findtraincheci.class );		
		Log.i("wzs", trainCheciInfo.getName());
		Log.i("wzs", Train_List.toString());	
		Log.i("wzs", "启动Activity成功  1");
		startActivity(mintent);
		Log.i("wzs", "启动Activity成功  2");
	}
	public void startResult_FindtrainYupiao()
	{
		Log.i("wzs", "准备启动Result_FindtrainYupiao");
		Intent _intent = new Intent(FindTrainActivity.this, Result_FindtrainYupiao.class);
		startActivity(_intent);
	}
	public void startResult_FindtrainPiaojia()
	{
		Intent _Intent = new Intent(FindTrainActivity.this,Result_FindtrainPiaojia.class);
		startActivity(_Intent);

	}
	private static class IHandler extends Handler
    {
    	private final WeakReference<Activity> mActivtiy;
    	public IHandler(FindTrainActivity activity )
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
				case ConstantActivity.FLAG_FIND_SUCCES:
					Log.i("wzs", "case接收成功");
					((FindTrainActivity)mActivtiy.get()).startResult_Findtraincheci();
					break;
				case ConstantActivity.FLAG_FIND_ZHANZHAN_SUCCES:
					((FindTrainActivity)mActivtiy.get()).startResult_FindtrainYupiao();
					Log.i("wzs", "站占余票查询接收成功");
					break;
				case ConstantActivity.FLAG_FIND_ZHANZHAN_PIAOJIA_SUCCES:
					((FindTrainActivity)mActivtiy.get()).startResult_FindtrainPiaojia();
					Log.i("wzs", "站占票价查询接收成功");
					break;
				case 0:
					Log.i("wzs", "接收到异常消息");
					String ERRORMSG = msg.getData().getSerializable("ERROR").toString();
					Log.i("wzs", ERRORMSG);
					break;
			}
    	}
    	
    }
 IHandler handler  = new IHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findtrain);
		intit();
		btn_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
			finish();				
			}
		});
/*******************************************************车次查询*******************************************************************/
		
		btnFindCheci.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{			
				final String checi = txtCheci.getText().toString();
				Log.i("wzs", checi+"1");
				if(checi.equals(""))
				{
					toastTip("填写的信息不完整");
				}
				else
				{
					startProgressDialog();
					Thread thread = new Thread(new Runnable() 
					{			
						@Override
						public void run() 
						{
							try
							{
								trainCheciInfo = findtrain.getTrain_info(checi);
								Train_List = findtrain.getTrain_List(checi);								
								handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_SUCCES);								
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
					thread.start();
				}
			
			}
		});
/*******************************************************站站查询——余票查询****************************************************************/		
		
		
		btnfindyupiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{			
				 from = txtfrom.getText().toString();
				 to = txtto.getText().toString();
				 date = txtdate.getText().toString();
				if(!from.equals("")&&!to.equals("")&&!date.equals(""))
				{
					int chars = 0;
					int num = 0;
					char[] ch = date.toCharArray();
					for (int i = 0; i < ch.length; i++)
					{
						
						if(ch[i]=='-')
						{
							 ++chars;
						}
						if(ch[i]>='0'&&ch[i]<='9')
						{
							++num;
						}
					}
					Log.i("wzs", chars+"");
					Log.i("wzs", num+"");
					if(num==8&&chars==2)
					{
						startProgressDialog();
						
						Thread thread = new Thread(new Runnable() 
						{			
							@Override
							public void run() 
							{
								try
								{
									Log.i("wzs", from+"2");									
									TrainYupiao_List = findtrain.getTrainYupiao_List(from, to, date);																		
									Log.i("wzs", "接收成功");
									handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_ZHANZHAN_SUCCES);
									
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
						thread.start();
					}
					else 
					{
						toastTip("您输入的日期格式不正确");
					}
				}
				else
				{
					toastTip("您输入的信息不完整");
				}
				
				
			}
		});
/*******************************************************站站查询——票价查询****************************************************************/		
		btnfindpiaojiaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{			
				 from = txtfrom.getText().toString();
				 to = txtto.getText().toString();
				 date = txtdate.getText().toString();
				if(!from.equals("")&&!to.equals("")&&!date.equals(""))
				{
					
					char[] ch = date.toCharArray();
					int chars = 0;
					int num = 0;
					for (int i = 0; i < ch.length; i++)
					{
						if(ch[i]=='-')
						{
							 ++chars;
						}
						if(ch[i]>='0'&&ch[i]<='9')
						{
							++num;
						}
					}
					Log.i("wzs", chars+"");
					Log.i("wzs", num+"");
					if(num==8&&chars==2)
					{
						startProgressDialog();
						
						Thread thread = new Thread(new Runnable() 
						{			
							@Override
							public void run() 
							{
								try
								{
									Log.i("wzs", from+"2");									
									TrainPiaojia_list = findtrain.getTrainPiaojia_list(from, to);	
									
									Log.i("wzs", "接收成功");
									handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_ZHANZHAN_PIAOJIA_SUCCES);
									
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
						thread.start();
					}
					else 
					{
						toastTip("您输入的日期格式不正确");
					}
				}
				else
				{
					toastTip("您输入的信息不完整");
				}
				
				
			}
		});
	}
	
}
