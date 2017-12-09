package com.demo.phonenumberactivity;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import com.demo.phonenumberactivity.service.ConstantActivity;
import com.demo.phonenumberactivity.service.PhoneService;
import com.demo.phonenumberactivity.service.UserServiceSub;
import com.wzs.mooc.R;

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
import android.widget.Toast;

public class PhoneNumberActivity extends Activity {
	
	public PhoneService phoneService=new UserServiceSub();
	
	private String[] result_Strings = new String[5];
	
	public static ProgressDialog mdialog = null;
	
	private EditText txtName;
	private Button btnRequire;
	private EditText txtResult0;
	private EditText txtResult2;
	private EditText txtResult3;
	private EditText txtResult4;
	
	private void init(){
		this.txtName=(EditText)findViewById(R.id.txtname);
		this.btnRequire=(Button)findViewById(R.id.button_inquire);
	//	this.btnReset=(Button)findViewById(R.id.button_reset);
		this.txtResult0=(EditText)findViewById(R.id.text_result0);
		this.txtResult2=(EditText)findViewById(R.id.text_result2);
		this.txtResult3=(EditText)findViewById(R.id.text_result3);
		this.txtResult4=(EditText)findViewById(R.id.text_result4);}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonenumber_activity);
		
		init(); 
		
		btnRequire.setOnClickListener(new OnClickListener() {
		
			
			@Override
			public void onClick(View view) {
				
				final String txtname = txtName.getText().toString();//得到地址
				Log.i("lxy", txtname+"1");
				
				
				if (txtname.equals(""))
				{
					Toast.makeText(txtName.getContext(), "电话不能为空",Toast.LENGTH_LONG).show();
				}
				else{	
					if(mdialog == null)
					{
						
						mdialog = new ProgressDialog(PhoneNumberActivity.this);
						mdialog.setTitle("查询中");						
						mdialog.setMessage("请稍候...");
						mdialog.setCancelable(true);
						mdialog.show();
					}
				
					Thread thread = new Thread(new Runnable() {//线程
						@Override
						public void run() 
 {
							try {

								Log.i("lxy", txtname+"2");								
								result_Strings = phoneService.userService(txtname);								
								Log.i("lxy", "String后");				
								handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_SUCCES);
								
								Log.i("wzs", "zhengde成功");
							}
						

						catch(ServiceRuleException e){
							Log.i("lxy", "自定义异常");						
							e.printStackTrace();
							Message msg=new Message();
							Bundle data=new Bundle();
							data.putSerializable("ERROR",e.getMessage());
							handler.sendMessage(msg);
							msg.setData(data);}
						catch(SocketTimeoutException e){
							Log.i("lxy", "连接超时异常");						
								e.printStackTrace();
								Message msg=new Message();
								Bundle data=new Bundle();
								data.putSerializable("ERROR","连接超时");
								handler.sendMessage(msg);
								msg.setData(data);}
						 catch (Exception e) {
							 
								Log.i("lxy", "系统错误");
								Log.i("lxy",e.toString());
								e.printStackTrace();
								Message msg=new Message();
								Bundle data=new Bundle();
								data.putSerializable("ERROR", e.getMessage());
								msg.setData(data);
								Log.i("lxy",e.getMessage());
								handler.sendMessage(msg);
							}
						
					}
				});
	
					thread.start();		
				}
			}
		});	
		
	
	init();
	
		/*btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				//设置空字符串
				txtName.setText("");
			}
		});*/
		
	}

	
	public void showResultException(String string){
		txtResult0.setText(string);//第一行显示错误
	}
	
	public  void showResult()     //showTip
	{
		Log.i("wzs",result_Strings[0] );
		
		txtResult0.setText( result_Strings[0]+result_Strings[1]);
		txtResult2.setText( result_Strings[2]);
		txtResult3.setText(result_Strings[3]);
		txtResult4.setText(result_Strings[4]);
		
	}
	

	public static class IHandler extends Handler {
		
		
		
		
		private final WeakReference<Activity> mActivity;
		public IHandler(PhoneNumberActivity activity){
			mActivity=new WeakReference<Activity>(activity);
		}

		
		@Override
		public void handleMessage(Message msg) {
			
		
			if (mdialog != null) {
				mdialog.dismiss();

			}

			int flag = msg.what;
			switch (flag) {
			case ConstantActivity.FLAG_FIND_SUCCES:

				// String
				// errorMsg=(String)msg.getData().getSerializable("ErrorMsg");
				// ((PhoneNumberActivity)mActivity.get()).showResultException(errorMsg);
				// String
				// errorMsg=(String)msg.getData().getSerializable("ErrorMsg");

				((PhoneNumberActivity) mActivity.get()).showResult();

				
				break;

			case 0:
				Log.i("lxy", "接收到异常");
				String ERRORMSG = msg.getData().getSerializable("ERROR")
						.toString();
				// String
				// errorMsg=(String)msg.getData().getSerializable("ErrorMsg");

				// ((PhoneNumberActivity)mActivity.get()).showResult();

				((PhoneNumberActivity) mActivity.get()).showResultException(ERRORMSG);
				break;
				default:
					break;
			}

		}

	}

	IHandler handler = new IHandler(this);
	
	
	

}
