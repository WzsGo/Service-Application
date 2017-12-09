package com.wzs.mooc;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.ConnectTimeoutException;
import progressdialog.CustomProgressDialog;
import com.wzs.mooc.service.FindKuaidi;
import com.wzs.mooc.service.FindKuaidiImpl;
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.mooc.entity.KuaidiEntity;
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
import android.widget.TextView;
import android.widget.Toast;

public class KuaidiActivity extends Activity {
	Intent mIntent = getIntent();
	private Button btnKuaidi;
	private Button btnkuaidifindcom;
	private EditText txtKuaidino;
	private TextView viewkuaidi_com;
	private static String kuaidino;
	private static CustomProgressDialog progressDialog = null;
	public static List<KuaidiEntity> listKuaidiEntities = new ArrayList<KuaidiEntity>();
	private Button btn_back;
	private FindKuaidi findKuaidi = new FindKuaidiImpl();
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

	public void intit() {
		this.btnKuaidi = (Button) findViewById(R.id.btnkuaidifind);
		this.txtKuaidino = (EditText) findViewById(R.id.txtkuaidino);
		this.btnkuaidifindcom = (Button) findViewById(R.id.btnkuaidifindcom);
		this.viewkuaidi_com = (TextView) findViewById(R.id.viewkuaidi_com);
		this.btn_back = (Button) findViewById(R.id.btn_kuaidi_back);
	}

	public void ErrorTip(String string) {
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT)
				.show();
	}

	public void startKuaidi_result() {
		Log.i("wzs", listKuaidiEntities + "在主Activity中");
		Intent mintent = new Intent(KuaidiActivity.this, Kuaidi_result.class);
		startActivity(mintent);
		KuaidiActivity.this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kuaidi);
		intit();
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		btnkuaidifindcom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent _Intent = new Intent(KuaidiActivity.this,
						KuaidiFindcom_radio.class);
				startActivity(_Intent);

			}
		});

		Intent mmIntent = getIntent();
		if (mmIntent != null) {

			viewkuaidi_com.setText(KuaidiFindcom_radio.kuaidicom_radio);

			btnKuaidi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					kuaidino = txtKuaidino.getText().toString();
					Log.i("wzs", KuaidiFindcom_radio.kuaidicom);
					if (KuaidiFindcom_radio.kuaidicom.equals("")
							|| kuaidino.equals("")) {

						Toast.makeText(getApplicationContext(), "请将信息填写完整",
								Toast.LENGTH_SHORT).show();
					} else {
						startProgressDialog();
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {

									listKuaidiEntities = findKuaidi
											.getKuaidiInfo(
													KuaidiFindcom_radio.kuaidicom,
													kuaidino);
									handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_SUCCES);

								} catch (ServiceRuleException messgae) {

									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable("ERROR",
											messgae.getMessage());
									msg.setData(date);
									handler.sendMessage(msg);
									messgae.printStackTrace();
								} catch (ConnectTimeoutException messgae) {

									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable(
											"ERROR",
											ConstantActivity.MSG_CONNECT_TIMEOUT);
									msg.setData(date);
									handler.sendMessage(msg);
									messgae.printStackTrace();
								} catch (SocketTimeoutException messgae) {

									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable(
											"ERROR",
											ConstantActivity.MSG_RESPONSE_TIMEOUT);
									msg.setData(date);
									handler.sendMessage(msg);
									messgae.printStackTrace();
								} catch (Exception e) {
									Log.i("wzs", "总异常");
									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable("ERROR",
											ConstantActivity.MSG_SERVICE_ERROR);
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
	}

	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivtiy;

		public IHandler(KuaidiActivity activity) {
			mActivtiy = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			stopProgressDialog();
			int flag = msg.what;
			switch (flag) {
			case ConstantActivity.FLAG_FIND_SUCCES:
				((KuaidiActivity) mActivtiy.get()).startKuaidi_result();
				break;
			case 0:
				String ERRORMSG = msg.getData().getSerializable("ERROR")
						.toString();
				((KuaidiActivity) mActivtiy.get()).ErrorTip(ERRORMSG);
				break;
			}
		}

	}

	IHandler handler = new IHandler(this);

}
