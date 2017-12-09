package com.wzs.weizhang;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import progressdialog.CustomProgressDialog;

import com.wzs.mooc.ConstantActivity;
import com.wzs.mooc.R;
import com.wzs.mooc.service.ServiceRuleException;
import com.wzs.weizhang.cs.FindWeizhangInfo;
import com.wzs.weizhang.cs.FindWeizhangInfoImpl;
import com.wzs.weizhang.entity.Weizhang_CityInfo;
import com.wzs.weizhang.entity.Weizhang_ResultInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WeizhangFindActivity extends Activity
{
	Intent mIntent = getIntent();
	private int flag = 0;
	private int flag2 = 0;
	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Spinner abbrSpinner;
	private Spinner zimuSpinner;
	private EditText txthphm_num;
	
	/*private EditText txtengineno;
	private EditText txtclassno;*/
	
	private EditText txt_eng;
	private EditText txt_cla;
	private TextView view_eng;
	private TextView view_cla;
	private Button btn_back;
	private Button btn_weizhangfind;
	private LinearLayout linear1;
	private LinearLayout linear2;
	ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> abbrAdapter = null;
    ArrayAdapter<String> zimuAdapter = null;
    private static CustomProgressDialog progressDialog = null;
    private String[] province = new String[] {
    		"北京","上海","四川","浙江","福建","吉林",
    		"辽宁","山东","河南","江苏","陕西","青海",
    		"广东","湖北","黑龙江","安徽","云南","山西",
    		"海南","贵州","新疆","甘肃","宁夏","湖南"
    		,"西藏","重庆","广西"
    };
    private String[] citys ;
    private String[] abbrs = new String[]{
    	"京","沪","川","浙","闽","吉","辽","鲁","豫",	"苏","陕","青",
    	"粤","鄂","黑","皖","云","晋",	
    	"琼","贵","新","甘","宁","湘","藏","重","桂"	
    };
    private String[] zimus = new String[]{
    		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
    		"R","S","T","U","V","W","X","Y","Z"
    };   
    private String province_id; 
    private String hphm_abbr ;
    private String hphm_zimu;
    private String hphm_num;
    public static String hphm;
    private String city;
    private String engineno;
    private String classno;
    public Map<String, Object> cityMap = new HashMap<String, Object>();
    private List<Weizhang_CityInfo> cityList = new ArrayList<Weizhang_CityInfo>();  
    public static List<Weizhang_ResultInfo> resultList = new ArrayList<Weizhang_ResultInfo>(); 
    FindWeizhangInfo findWeizhangInfo = new FindWeizhangInfoImpl();
	public void init()
	{
		provinceSpinner = (Spinner)findViewById(R.id.spin_province);
		citySpinner = (Spinner)findViewById(R.id.spin_city);
		abbrSpinner = (Spinner)findViewById(R.id.spin_abbr);
		zimuSpinner = (Spinner)findViewById(R.id.spin_zimu);
		txthphm_num = (EditText)findViewById(R.id.txt_hphm_num);
		/*txtengineno = (EditText)findViewById(R.id.txt_engineno);
		txtclassno = (EditText)findViewById(R.id.txt_classno);*/
		btn_weizhangfind = (Button)findViewById(R.id.btn_weizhangfind);
		TextPaint tp = btn_weizhangfind.getPaint();
		tp.setFakeBoldText(true); //字体加粗
		linear1 = (LinearLayout)findViewById(R.id.linearLayout1);
		linear2 = (LinearLayout)findViewById(R.id.linearLayout2);
		
		view_eng = new TextView(WeizhangFindActivity.this);
		view_cla = new TextView(WeizhangFindActivity.this);
		txt_eng = new EditText(WeizhangFindActivity.this);
		txt_cla = new EditText(WeizhangFindActivity.this);
		drawTextEdit(txt_cla);
		drawTextEdit(txt_eng);
		drawTextView(view_cla);
		drawTextView(view_eng);
		btn_back = (Button)findViewById(R.id.btn_weizahng_back);
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
	public void drawTextView(TextView view)
	{
		view.setTextSize(20);		
	}
	public void drawTextEdit(EditText text)
	{
		text.setHeight(71);
		text.setWidth(269);		
		text.setBackgroundResource(R.drawable.bg_edittext_focused);
		text.setSingleLine(true);				
	}
	
	public void toastShow(String eRRORMSG) 
	{
		Toast.makeText(WeizhangFindActivity.this, eRRORMSG, Toast.LENGTH_LONG).show();;
		
	}
	public void loadCitySpinner()
	{
		 cityAdapter = new ArrayAdapter<String>(WeizhangFindActivity.this,
				 R.layout.simple_spinner_item_shengfen, citys);
		  citySpinner.setAdapter(cityAdapter);
		  citySpinner.setSelection(0,true);
	}
	public void setAbbrApinner(int position)
	{
		abbrAdapter = new ArrayAdapter<String>(WeizhangFindActivity.this,
	                R.layout.simple_spinner_item_shengfen,abbrs);
		abbrSpinner.setAdapter(abbrAdapter);
		abbrSpinner.setSelection(position,true);
	}
	public void setZimuApinner()
	{
		zimuAdapter = new ArrayAdapter<String>(WeizhangFindActivity.this,
				R.layout.simple_spinner_item_shengfen,zimus);
		zimuSpinner.setAdapter(zimuAdapter);
		zimuSpinner.setSelection(0,true);
	}
	public void StartWeizhangResultActivity() 
	{
		Intent _Intent = new Intent(WeizhangFindActivity.this,WeizhangResultActivity.class);
		startActivity(_Intent);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weizhangfind_activty);
		init();
		setSpinner();
		getResult();
		btn_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
			finish();				
			}
		});
		
	}
	public void getResult()
	{
		btn_weizhangfind.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
			hphm_num = txthphm_num.getText().toString();
			/*engineno = txt.getText().toString();
			classno = txtclassno.getText().toString();*/
			if (flag == 1)
			{
				engineno = txt_eng.getText().toString();
				classno = txt_cla.getText().toString();
				if (hphm_num.equals("")||engineno.equals("")||classno.equals("")) 
				{
					toastShow("填写信息不完整");
					flag2 = 1;
					
				}
			}
			if (flag == 2)
			{
				engineno = txt_eng.getText().toString();
				classno = "";
				if (hphm_num.equals("")||engineno.equals("")) 
				{
					toastShow("填写信息不完整");
					flag2 = 1;
				}
			}
			if (flag == 3)
			{
				engineno = "";
				classno = txt_cla.getText().toString();
				if (hphm_num.equals("")||classno.equals("")) 
				{
					toastShow("填写信息不完整");
					flag2 = 1;
				}
			}
			if (flag == 0) 
			{
				toastShow("未选择城市");
			}		
			if(flag != 0&&flag2 != 1)
			{
				
				hphm = hphm_abbr+hphm_zimu+hphm_num;
				startProgressDialog();
				Thread _Thread = new Thread(new Runnable() {					
					@Override
					public void run() {
					try 
					{
						resultList = findWeizhangInfo.getWeizhangResult(city, hphm, engineno, classno);
						Log.i("wzs", "违章结果查询返回成功");
						handler.sendEmptyMessage(2);
					} catch(ServiceRuleException messgae)
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
				_Thread.start();
				
			}
			
			}
		});
	}
	public void setSpinner()
	{
		  provinceAdapter = new ArrayAdapter<String>(WeizhangFindActivity.this,
	                R.layout.simple_spinner_item_shengfen, province);
		  provinceSpinner.setAdapter(provinceAdapter);
	      provinceSpinner.setSelection(0,false);	      
	      provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	 
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) 
			{
				 //position为当前省级选中的值的序号
				switch (position) {
				case 0:province_id = "BJ";break;
				case 1:province_id = "SH";break;
				case 2:province_id = "SC";break;
				case 3:province_id = "ZJ";break;					 
				case 4:province_id = "FJ";break;
				case 5:province_id = "JL";break;
				case 6:province_id = "LN";break;
				case 7:province_id = "SD";break;
				case 8:province_id = "HN";break;
				case 9:province_id = "JS";break;
				case 10:province_id = "SX";break;
				case 11:province_id = "QH";break;
				case 12:province_id = "GD";break;
				case 13:province_id = "HB";break;
				case 14:province_id = "HLJ";break;				
				case 15:province_id = "AH";break;
				case 16:province_id = "YN";break;
				case 17:province_id = "SX";break;
				case 18:province_id = "HAN";break;
				case 19:province_id = "GZ";break;
				case 20:province_id = "XJ";break;
				case 21:province_id = "GS";break;
				case 22:province_id = "NX";break;
				case 23:province_id = "HUN";break;
				case 24:province_id = "XZ";break;
				case 25:province_id = "CQ";break;
				case 26:province_id = "GX";break;				
				}
				getCityInfo(province_id);				
				setAbbrApinner(position);		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
	      citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressWarnings("unchecked")
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {				
				cityList = (List<Weizhang_CityInfo>) cityMap.get("city_list");
				Weizhang_CityInfo info = cityList.get(position);
				city = info.city_code;
				Log.i("wzs", "发动机"+info.engine+"车架号"+info.classa);
				if (info.engine == 1&&info.classa == 1) 
				{
					linear1.removeAllViews();
					linear2.removeAllViews();
					flag = 1;
					view_eng.setText("发动机号：  ");
					view_cla.setText("车架号    ：  ");
					
					if (info.engineno == 0 ) 
						txt_eng.setHint("请填写完整发动机号");											
					else 
						txt_eng.setHint("请填写后"+info.engineno+"位发动机号");											
					if(info.classno == 0) 
						txt_cla.setHint("请填写完整车架号");	
					else 
						txt_cla.setHint("请填写后"+ info.classno+"位车架号");						
					linear1.addView(view_eng);
					linear1.addView(txt_eng);					
					linear2.addView(view_cla);
					linear2.addView(txt_cla);
								
					
				}
				else if (info.engine == 1&&info.classa == 0) 
				{
					linear1.removeAllViews();	
					linear2.removeAllViews();
					flag = 2;
					view_eng.setText("发动机号：  ");

					if (info.engineno == 0 ) 
						txt_eng.setHint("请填写完整发动机号");											
					else 
						txt_eng.setHint("请填写后"+info.engineno+"位发动机号");	
					linear1.addView(view_eng);
					linear1.addView(txt_eng);
				}	
				else if(info.engine == 0&&info.classa == 1)
				{
					linear1.removeAllViews();
					linear2.removeAllViews();
					flag = 3;
					view_cla.setText("车架号    ：  ");
					if(info.classno == 0) 
						txt_cla.setHint("请填写完整车架号");	
					else 
						txt_cla.setHint("请填写后"+ info.classno+"位车架号");	
					linear1.addView(view_cla);
					linear1.addView(txt_cla);										
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	      abbrSpinner.setOnItemSelectedListener(new  OnItemSelectedListener() {		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				hphm_abbr = abbrs[position];				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
	      setZimuApinner();
	      zimuSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				hphm_zimu = zimus[position];				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});	      
	} 
	public void getCityInfo(final String province_id)
	{
		Thread _thread = new Thread(new Runnable() {			
			@Override
			public void run() {
			
				try 
				{
					cityMap = findWeizhangInfo.getWeizhangCityInfo(province_id);
					citys = (String[]) cityMap.get("city_name");
					Log.i("wzs", "城市列表接受成功"+citys[0]);
					handler.sendEmptyMessage(1);
					
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
					//date.putSerializable("ERROR",e.getMessage());
					Log.i("wzs",e.getMessage());
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
    	public IHandler(WeizhangFindActivity activity )
    	{
    		mActivtiy = new WeakReference<Activity>(activity);
    	}
    	@Override
		public void handleMessage(Message msg) 
    	{
						
			int flag = msg.what;
			stopProgressDialog();
			switch(flag)
			{
				case 1:					
					((WeizhangFindActivity)mActivtiy.get()).loadCitySpinner();
					break;	
				case 2:				 	
					((WeizhangFindActivity)mActivtiy.get()).StartWeizhangResultActivity();
					break;
				case 0:
					Log.i("wzs", "接收到异常消息");
					String ERRORMSG = msg.getData().getSerializable("ERROR").toString();
					((WeizhangFindActivity)mActivtiy.get()).toastShow(ERRORMSG);
					Log.i("wzs", ERRORMSG);
					break;
			}
    	}
    	
    }
	IHandler handler  = new IHandler(this);


}
