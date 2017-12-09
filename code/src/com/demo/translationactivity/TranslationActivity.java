package com.demo.translationactivity;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;
import tool.TranslationAdapter;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.translationactivity.service.ServiceRuleException;
import com.demo.translationactivity.service.TranslationService;
import com.demo.translationactivity.service.TranslationServiceImpl;
import com.demo.xinhuaactivity.XinHuaActivity;
import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;

import entity.TranslationEntity;

public class TranslationActivity extends Activity {
	Intent _Intent = getIntent();
	private Button btnFind;// ��ѯ
	private EditText editText;// �����
	private TextView ediPhonetic;
	private TextView ediUs_Phonetic;
	private TextView edtResult;// �����
	private ListView listViewTranslate;// item

	private static CustomProgressDialog progressDialog = null;
	private TranslationService translationService = new TranslationServiceImpl();// �ӿ�ʵ��
	private Map<String, Object> resultStrings = new HashMap<String, Object>();
	private String juziTranslation;
	private TranslationAdapter adapter;
	public static List<TranslationEntity> listTranslateEntities = new ArrayList<TranslationEntity>();

	private void init() {
		this.btnFind = (Button) findViewById(R.id.btnfind);// ��ѯ
		this.editText = (EditText) findViewById(R.id.edit_text);// �����
		this.edtResult = (TextView) findViewById(R.id.edt_result);// ���
		this.listViewTranslate = (ListView) findViewById(R.id.listview);// ListView
		this.ediPhonetic = (TextView) findViewById(R.id.edi_phonetic);// Ӣʽ����
		this.ediUs_Phonetic = (TextView) findViewById(R.id.edi_us_phonetic);
	}// ��ʽ����
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

	public void loadLtViewTranslate() {

		adapter = new TranslationAdapter(TranslationActivity.this,
				R.layout.translation_item, listTranslateEntities);
		listViewTranslate.setAdapter(this.adapter);
	}

	public void startResult_xinhua() {
		Intent intent = new Intent(TranslationActivity.this,
				XinHuaActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.translate_activity);
		init();

		btnFind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final String wordask = editText.getText().toString();
				if (wordask.equals("")) {
					Log.i("lxy", "��д���ݲ������ж�");
					Toast.makeText(getApplicationContext(), "��������Ҫ��ѯ������",
							Toast.LENGTH_LONG).show();
				} else {

					startProgressDialog();

					}

					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {

							try {
								//�Ƿ����жϺ���
								if (wordask.getBytes().length == wordask.length()) {  
									//�жϾ��ӻ��ߵ���
									if (wordask.contains(" ")) 
									{
										String newwordask = wordask.replace(' ','+' );
										juziTranslation = translationService
												.translateJuziInfo(newwordask);
										listTranslateEntities = translationService
												.translateServiceInfo(newwordask); 
										handler.sendEmptyMessage(4);
									}
									else
									{
										resultStrings = translationService
												.translateInfo(wordask);
										listTranslateEntities = translationService
												.translateServiceInfo(wordask);
										handler.sendEmptyMessage(ConstantActivity.FLAG_FIND_SUCCES);  
									}
									
								}
								else 
								{  
															
										resultStrings = translationService
												.translateHanziInfo(wordask);

										listTranslateEntities = translationService
												.translateServiceInfo(wordask); 
										handler.sendEmptyMessage(3);																		
								} 		
							} catch (ServiceRuleException messgae) {
								Log.i("wzs", "�Զ����쳣");

								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error",
										messgae.getMessage());
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (ConnectTimeoutException messgae) {
								Log.i("wzs", "����ʱ");
								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error",
										ConstantActivity.MSG_CONNECT_TIMEOUT);
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (SocketTimeoutException messgae) {

								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error",
										ConstantActivity.MSG_RESPONSE_TIMEOUT);
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (Exception e) {
								Log.i("lxy", " ���쳣");
								Message msg = new Message();
								Bundle data = new Bundle();
								data.putSerializable("Error",
										ConstantActivity.MSG_SERVICE_ERROR);
								/*data.putSerializable("Error",
										e.getMessage());*/
								msg.setData(data);
								handler.sendMessage(msg);
								e.printStackTrace();
							}
						}
					});
					thread.start();
				}			
		});
	}

	public void ErrorTip(String string) {

		Toast.makeText(TranslationActivity.this,string,Toast.LENGTH_LONG)
				.show();
	}

	public void showResult() {

		String explation = (String) resultStrings.get("explation");
		edtResult.setText("���룺\n" + "            "
				+explation);
		ediPhonetic.setText("Ӣ�� " + resultStrings.get("uk-phonetic").toString());
		ediUs_Phonetic.setText("���� " + resultStrings.get("us-phonetic").toString());

	}

	public void showHanziResult()
	{
		String explation = (String) resultStrings.get("explation");
		edtResult.setText("���룺\n" + "            "
				+explation);
		ediPhonetic.setText("ƴ���� " + resultStrings.get("phonetic").toString());
	}
	public void showJuziResult()
	{
		edtResult.setText("���룺\n" + "            "
				+juziTranslation);
	}
	public static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(TranslationActivity activity) {
			mActivity = new WeakReference<Activity>(activity);
			
		}

		public void handleMessage(Message msg) {
			
			stopProgressDialog();
			int flag = msg.what;			
			switch (flag) {
			case ConstantActivity.FLAG_FIND_SUCCES:
				Log.i("lxy", "ConstantActivity.FLAG_FIND_SUCCES");
				((TranslationActivity) mActivity.get()).showResult();
				((TranslationActivity) mActivity.get()).loadLtViewTranslate();
				break;
			case 3:
				Log.i("lxy", "ConstantActivity.FLAG_FIND_SUCCES");
				((TranslationActivity) mActivity.get()).showHanziResult();
				((TranslationActivity) mActivity.get()).loadLtViewTranslate();
				break;	
			case 4:
				Log.i("lxy", "ConstantActivity.FLAG_FIND_SUCCES");
				((TranslationActivity) mActivity.get()).showJuziResult();
				((TranslationActivity) mActivity.get()).loadLtViewTranslate();
				break;
			case 0:
				Log.i("lxy", "���յ��쳣");
				String ERRORMSG = msg.getData().getSerializable("Error")
						.toString();
				((TranslationActivity) mActivity.get()).ErrorTip(ERRORMSG);

			default:
				break;
			}
		}

	}

	IHandler handler = new IHandler(this);
}