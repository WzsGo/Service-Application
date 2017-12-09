package com.demo.xinhuaactivity;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;
import tool_xinhua.XinHuaAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import com.demo.xinhuaactivity.service.ServiceRuleException;
import com.demo.xinhuaactivity.service.XinHuaSericeImpl;
import com.demo.xinhuaactivity.service.XinHuaService;

import entity_xinhua.XinHuaEntity;

public class XinHuaActivity extends Activity {
	Intent mIntent = getIntent();
	private EditText edtText;//�����
	private Button btnFind;//��ѯ
	private TextView txtBiHua;//�ʻ�
	private TextView txtPinYin;//ƴ��
	private TextView txtBuShou;//����
	private ListView listView;//item
	private TextView view_xinhua_jijie;
	private LinearLayout liner_xinhua;
	private static CustomProgressDialog progressDialog = null;
    private XinHuaService xinHuaService=new XinHuaSericeImpl();//�ӿ�ʵ��
	private String[] resultStrings=new String[3];
	private XinHuaAdapter adapter;
	//private static List<XinHuaEntity> listXinHuaEntities=new ArrayList<XinHuaEntity>();
	private String jijieString ;
	
	public static List<XinHuaEntity> xiangjie_List = new ArrayList<XinHuaEntity>();
	
	private void init(){
		this.edtText=(EditText)findViewById(R.id.editText1);//�����
		this.btnFind=(Button)findViewById(R.id.butfind);//��ѯ
		this.txtBiHua=(TextView)findViewById(R.id.pinyintext);//ƴ��
		this.txtBuShou=(TextView)findViewById(R.id.bushoutext);//����
		this.txtPinYin=(TextView)findViewById(R.id.bihuatext);//�ʻ�
		this.listView=(ListView)findViewById(R.id.listview);//item
		this.view_xinhua_jijie = (TextView)findViewById(R.id.view_xinhua_jijie);
		this.liner_xinhua = (LinearLayout)findViewById(R.id.linearLayout_xinhua);
	}
	 public void loadLtViewTranslate(){
		
		 view_xinhua_jijie.setText("��飺"+'\n'+"        "+jijieString);
		 
	 }
	 public void showResult()
	 {
			Log.i("lxy", resultStrings[0]);
			txtBiHua.setText("�ʻ���"+resultStrings[0]);
			txtBuShou.setText("���ף�"+resultStrings[1]);
			txtPinYin.setText("ƴ����"+resultStrings[2]);
			adapter = new XinHuaAdapter(XinHuaActivity.this, R.layout.xinhua_item, xiangjie_List);
			listView.setAdapter(adapter);
			
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
	/* public void showXiangjieList()
	 {
		 
	 }*/
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinhua_activity);
		this.init();
		
		btnFind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				 final String askword=edtText.getText().toString();
				if (askword.equals("")) {
					Log.i("lxy", askword+"1");
					Log.i("lxy", "��д���ݲ�����");
					Toast.makeText(getApplicationContext(), "��������Ҫ��ѯ������",Toast.LENGTH_LONG ).show();;
				}else {
					startProgressDialog();
					liner_xinhua.setBackgroundResource(R.drawable.xinhua_yangshi2);
					Thread thread=new Thread(new Runnable() {
						
						@Override
						public void run() {
						
							try {
								
								resultStrings=xinHuaService.xinhuaInfo(askword);
								
								jijieString = xinHuaService.xinhauServiceInfo(askword);
								
								xiangjie_List = xinHuaService.getXiangjieInfo(askword);
								
								handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_SUCCES);
							
								
							} 
							catch(ServiceRuleException messgae){
								Log.i("wzs", "�Զ����쳣");

								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error", messgae.getMessage());
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							}catch (ConnectTimeoutException messgae) {
								Log.i("wzs", "����ʱ");
								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error",ConstantActivity.MSG_CONNECT_TIMEOUT );
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
								}catch (SocketTimeoutException messgae) {
									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable("Error", ConstantActivity.MSG_RESPONSE_TIMEOUT);
									msg.setData(date);
									handler.sendMessage(msg);
									messgae.printStackTrace();

								}
							catch (Exception e) {
								Log.i("lxy"," ���쳣");
								Message msg=new Message();
								Bundle data=new Bundle();
								data.putSerializable("Error", ConstantActivity.MSG_SERVICE_ERROR);
								msg.setData(data);
								handler.handleMessage(msg);
								e.printStackTrace();

							}
							
						}
					});
					thread.start();
				}
				
			}
		});
	}
	public void ErrorTip(String string){
		Log.i("lxy","error" );
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
	}
	
	
	
	public static class IHandler extends Handler{
		private final WeakReference<Activity> mActivity;
		public IHandler(XinHuaActivity activity){
			
			mActivity=new WeakReference<Activity>(activity);
			
		}
		public void handleMessage(Message msg){
			
			
			stopProgressDialog();
			int flag=msg.what;
			
			switch (flag) {
			case ConstantActivity.FLAG_FIND_SUCCES:
				Log.i("lxy", "ConstantActivity.FLAG_FIND_SUCCES");
				((XinHuaActivity)mActivity.get()).showResult();
				((XinHuaActivity)mActivity.get()).loadLtViewTranslate();
				//((XinHuaActivity)mActivity.get()).startResult_phone();
				
				break;
			case 0:
				Log.i("lxy", "���յ��쳣");
				String ERRORMSG=msg.getData().getSerializable("Error").toString();
				Log.i("lxy", ERRORMSG);
				((XinHuaActivity)mActivity.get()).ErrorTip(ERRORMSG);
				
				break;

			default:
				break;
			}
		}
	}
    IHandler handler=new IHandler(this);
}
