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
	private Button btnFind;// 查询
	private EditText editText;// 输入框
	private TextView ediPhonetic;
	private TextView ediUs_Phonetic;
	private TextView edtResult;// 输出框
	private ListView listViewTranslate;// item

	private static CustomProgressDialog progressDialog = null;
	private TranslationService translationService = new TranslationServiceImpl();// 接口实现
	private Map<String, Object> resultStrings = new HashMap<String, Object>();
	private String juziTranslation;
	private TranslationAdapter adapter;
	public static List<TranslationEntity> listTranslateEntities = new ArrayList<TranslationEntity>();

	private void init() {
		this.btnFind = (Button) findViewById(R.id.btnfind);// 查询
		this.editText = (EditText) findViewById(R.id.edit_text);// 输入框
		this.edtResult = (TextView) findViewById(R.id.edt_result);// 输出
		this.listViewTranslate = (ListView) findViewById(R.id.listview);// ListView
		this.ediPhonetic = (TextView) findViewById(R.id.edi_phonetic);// 英式发音
		this.ediUs_Phonetic = (TextView) findViewById(R.id.edi_us_phonetic);
	}// 美式发音
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
					Log.i("lxy", "填写内容不完整判断");
					Toast.makeText(getApplicationContext(), "请输入您要查询的内容",
							Toast.LENGTH_LONG).show();
				} else {

					startProgressDialog();

					}

					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {

							try {
								//是否是判断汉字
								if (wordask.getBytes().length == wordask.length()) {  
									//判断句子或者单词
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
								Log.i("wzs", "自定义异常");

								Message msg = new Message();
								Bundle date = new Bundle();
								date.putSerializable("Error",
										messgae.getMessage());
								msg.setData(date);
								handler.sendMessage(msg);
								messgae.printStackTrace();
							} catch (ConnectTimeoutException messgae) {
								Log.i("wzs", "请求超时");
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
								Log.i("lxy", " 总异常");
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
		edtResult.setText("翻译：\n" + "            "
				+explation);
		ediPhonetic.setText("英： " + resultStrings.get("uk-phonetic").toString());
		ediUs_Phonetic.setText("美： " + resultStrings.get("us-phonetic").toString());

	}

	public void showHanziResult()
	{
		String explation = (String) resultStrings.get("explation");
		edtResult.setText("翻译：\n" + "            "
				+explation);
		ediPhonetic.setText("拼音： " + resultStrings.get("phonetic").toString());
	}
	public void showJuziResult()
	{
		edtResult.setText("翻译：\n" + "            "
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
				Log.i("lxy", "接收到异常");
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