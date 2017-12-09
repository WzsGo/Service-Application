package com.wzs.mooc;


import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import com.wzs.mooc.service.FindID;
import com.wzs.mooc.service.FindIDImpl;
import com.wzs.mooc.service.ServiceRuleException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FindIDActivity extends Activity
{
	public static ProgressDialog mdialog = null;
	private Button btnFindIp;
	private EditText txtIpAdress;
	private TextView viewIpResult;
	//private NameValuePair resultPair = null;
	//private List<NameValuePair> resultList = new ArrayList<NameValuePair>();
	private String[] result_Strings = new String[2];
	FindID findID = new FindIDImpl();
	public void intit()
	{
		this.btnFindIp = (Button)findViewById(R.id.btnfind);
		this.txtIpAdress = (EditText)findViewById(R.id.txtipadress);
		this.viewIpResult = (TextView)findViewById(R.id.viewipresult);	
	}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findfid);
		intit();
		btnFindIp.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View arg0) {
				final String IpAdress = txtIpAdress.getText().toString();
				if (IpAdress.equals(""))
				{
					viewIpResult.setText("ip地址不能为空");
				}
				else{	
					/*if(mdialog == null)
					{
						Log.i("wzs", "执行提示框1");
						mdialog = new ProgressDialog(FindIDActivity.this);
						mdialog.setTitle("查询中");
						Log.i("wzs", "执行提示框2");
						mdialog.setMessage("请稍候...");
						mdialog.setCancelable(true);
						mdialog.show();
					}*/
				
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() 
						{
							try
							{
								
								result_Strings = findID.getIDAdress(IpAdress);
								
								Log.i("wzs", "成功");
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
		
	
	}
	public void showResultException(String string)
	{
		viewIpResult.setText(string);
	}
	public void showResult()
	{
		Log.i("wzs",result_Strings[0] );
		viewIpResult.setText("位置："+result_Strings[0]+"  运营商："+result_Strings[1]);
	}
	 private static class IHandler extends Handler
	    {
		 	

	    	private final WeakReference<Activity> mActivtiy;
	    	public IHandler(FindIDActivity activity )
	    	{
	    		mActivtiy = new WeakReference<Activity>(activity);
	    	}
	    	@Override
			public void handleMessage(Message msg) 
	    	{
				/*if(mdialog!=null)
				{
					mdialog.dismiss();
					Log.i("wzs","dialog已清空" );
					mdialog = null;
					
				}	*/			
				int flag = msg.what;
				switch(flag)
				{
					case ConstantActivity.FLAG_FIND_SUCCES:
						((FindIDActivity)mActivtiy.get()).showResult();
						break;
					case 0:
						String ERRORMSG = msg.getData().getSerializable("ERROR").toString();
						((FindIDActivity)mActivtiy.get()).showResultException(ERRORMSG);
						break;
				}
	    	}
	    	
	    }
	 IHandler handler  = new IHandler(this);
	
}
