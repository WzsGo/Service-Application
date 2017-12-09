package com.wzs.mooc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.*;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class KuaidiFindcom_radio extends Activity {

	private RadioGroup group = null;

	private RadioButton radio_zto, radio_sto, radio_ht, radio_yd, radio_yt,
			radio_ems, radio_tt, radio_sf;
	private Button btn_save_back;
	private TextView kuaidicomTextView;
	public static String kuaidicom_radio;
	private Button btn_back;
	public static String kuaidicom = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.kuaidi_radiobutton);

		kuaidicomTextView = (TextView) findViewById(R.id.viewkuaidi_com_radio);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		radio_zto = (RadioButton) findViewById(R.id.radio_zto);
		radio_sto = (RadioButton) findViewById(R.id.radio_sto);
		radio_ht = (RadioButton) findViewById(R.id.radio_ht);
		radio_yd = (RadioButton) findViewById(R.id.radio_yd);
		radio_yt = (RadioButton) findViewById(R.id.radio_yt);
		radio_ems = (RadioButton) findViewById(R.id.radio_ems);
		radio_tt = (RadioButton) findViewById(R.id.radio_tt);
		radio_sf = (RadioButton) findViewById(R.id.radio_sf);
		btn_save_back = (Button) findViewById(R.id.btnkuaid_save);
		btn_back = (Button)findViewById(R.id.btn_kuaidi_back);
		Intent mIntent = getIntent();

		if (mIntent != null) {

			kuaidicomTextView.setText("您还没有选择快递公司");

			group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int arg1) {
					int id = group.getCheckedRadioButtonId();
					switch (id) {
					case R.id.radio_zto:
						kuaidicom = "zto";
						kuaidicom_radio = radio_zto.getText().toString();

						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_sto:
						kuaidicom = "sto";
						kuaidicom_radio = radio_sto.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_ht:
						kuaidicom = "ht";
						kuaidicom_radio = radio_ht.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_yd:
						kuaidicom = "yd";
						kuaidicom_radio = radio_yd.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_yt:
						kuaidicom = "yt";
						kuaidicom_radio = radio_yt.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_ems:
						kuaidicom = "ems";
						kuaidicom_radio = radio_ems.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_tt:
						kuaidicom = "tt";
						kuaidicom_radio = radio_tt.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;
					case R.id.radio_sf:
						kuaidicom = "sf";
						kuaidicom_radio = radio_sf.getText().toString();
						kuaidicomTextView.setText("您选择的快递公司是："
								+ kuaidicom_radio);
						break;

					}
				}
			});
		}
		btn_save_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent _Intent = new Intent(KuaidiFindcom_radio.this,
						KuaidiActivity.class);
				/*_Intent.putExtra("kuaidicom", kuaidicom);
				_Intent.putExtra("kuaidicom_radio", kuaidicom_radio);*/
				startActivity(_Intent);
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}

}
