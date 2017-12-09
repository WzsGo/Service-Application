package com.wzs.coach;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.coach.cs.FindCoach;
import com.wzs.coach.cs.FindCoachImpl;
import com.wzs.coach.entity.coach_from_to_entity;
import com.wzs.coach.entity.coach_station_info_entity;
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
import android.view.Window;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindCoachActivity extends Activity {
	Intent mIntent = getIntent();
	private EditText txtcoach_from;
	private EditText txtcoach_to;
	private EditText txtcoch_time;
	private EditText txtcoach_station;
	Button btnfind_from_to;
	Button btnfind_coach_info;
	private static CustomProgressDialog progressDialog = null;
	private Button btn_back;
	public static List<coach_from_to_entity> coach_from_to_list = new ArrayList<coach_from_to_entity>();
	public static List<coach_station_info_entity> coach_station_info_list = new ArrayList<coach_station_info_entity>();
	public static String from;
	public static String to;
	public static String time;
	public static String station;
	FindCoach findCoach = new FindCoachImpl();

	public void intit() {
		txtcoach_from = (EditText) findViewById(R.id.txtcoach_from);
		txtcoach_to = (EditText) findViewById(R.id.txtcoach_to);
		txtcoch_time = (EditText) findViewById(R.id.txtcoach_time);
		txtcoach_station = (EditText) findViewById(R.id.txtcoach_station);
		btnfind_from_to = (Button) findViewById(R.id.btnfind_coach_from_to);
		btnfind_coach_info = (Button) findViewById(R.id.btnfind_coach_station_info);
		btn_back = (Button) findViewById(R.id.btn_coachfind_back);
	}

	public void toastTip(String Tip) {
		Toast.makeText(FindCoachActivity.this, Tip, Toast.LENGTH_LONG).show();
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

	public void startResult_Coach_from_to() {
		Intent _Intent = new Intent(FindCoachActivity.this,
				Result_Coach_from_to.class);
		startActivity(_Intent);
	}

	public void startResult_Coach_station_info() {
		Log.i("wzs", "启动车站信息 Activity");
		Intent _intent = new Intent(FindCoachActivity.this,
				Result_Coach_station_info.class);
		startActivity(_intent);
	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivtiy;

		public IHandler(FindCoachActivity activity) {
			mActivtiy = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			stopProgressDialog();
			int flag = msg.what;
			switch (flag) {
			case ConstantActivity.FLAG_FIND_COACH_FROM_SUCCES:
				Log.i("wzs", "汽车站站查询已接收到 在case中");
				((FindCoachActivity) mActivtiy.get())
						.startResult_Coach_from_to();
				break;
			case ConstantActivity.FLAG_FIND_COACH_STATION_INFO_SUCCES:
				Log.i("wzs", "汽车站信息查询已接收到 在case中");
				((FindCoachActivity) mActivtiy.get())
						.startResult_Coach_station_info();
				break;

			case 0:
				Log.i("wzs", "接收到异常消息");
				String ERRORMSG = msg.getData().getSerializable("ERROR")
						.toString();
				((FindCoachActivity) mActivtiy.get()).toastTip(ERRORMSG);
				Log.i("wzs", ERRORMSG);
				break;
			}
		}

	}

	IHandler handler = new IHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findcoach);
		intit();
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		/****************************************************** 站站汽车站查询 **************************************************************/
		btnfind_from_to.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				from = txtcoach_from.getText().toString();
				to = txtcoach_to.getText().toString();
				time = txtcoch_time.getText().toString();
				if (!from.equals("") && !to.equals("") && !time.equals("")) {
					int chars = 0;
					int num = 0;
					char[] ch = time.toCharArray();
					for (int i = 0; i < ch.length; i++) {

						if (ch[i] == '-') {
							++chars;
						}
						if (ch[i] >= '0' && ch[i] <= '9') {
							++num;
						}
					}
					Log.i("wzs", chars + "");
					Log.i("wzs", num + "");
					if (num == 8 && chars == 2) {
						startProgressDialog();
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Log.i("wzs", "汽车站站查询 执行try之前");
									coach_from_to_list = findCoach
											.getCoach_from_to_list(from, to,
													time);
									Log.i("wzs", "接收成功");
									handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_COACH_FROM_SUCCES);

								} catch (ServiceRuleException messgae) {
									Log.i("wzs", "自定义异常");
									Message msg = new Message();
									Bundle date = new Bundle();
									date.putSerializable("ERROR",
											messgae.getMessage());
									msg.setData(date);
									handler.sendMessage(msg);
									messgae.printStackTrace();
								} catch (ConnectTimeoutException messgae) {
									Log.i("wzs", "请求超时");
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
					} else {
						toastTip("请正确填写日期格式");

					}
				} else {
					toastTip("填写信息不完整");

				}

			}
		});
		/****************************************************** 汽车站 信息查询 ******************************************************************/
		btnfind_coach_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				station = txtcoach_station.getText().toString();
				if (station.equals("")) {
					toastTip("输入的信息不完整");
				} else {
					startProgressDialog();
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {

								coach_station_info_list = findCoach
										.getCoach_station_info_list(station);
								Log.i("wzs", "接收成功");
								handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_COACH_STATION_INFO_SUCCES);

							} catch (ServiceRuleException messgae) {
								Log.i("wzs", "自定义异常");
								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("ERROR",
										messgae.getMessage());
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (ConnectTimeoutException messgae) {
								Log.i("wzs", "请求超时");
								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("ERROR",
										ConstantActivity.MSG_CONNECT_TIMEOUT);
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (SocketTimeoutException messgae) {

								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("ERROR",
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
