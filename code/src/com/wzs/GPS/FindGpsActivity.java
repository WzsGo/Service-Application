package com.wzs.GPS;

import android.app.Activity;
import android.os.Bundle;
import cn.juhe.bs.JuheBSParams;
import cn.juhe.bs.JuheBaseStation;
public class FindGpsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JuheBaseStation jhBS = JuheBaseStation.getJhBSInstance();	
		
		jhBS.setAppkey("af6fc0be830e90a405a5a623892f2756");		 //�����������key
		jhBS.registerLocationListener(new MyJuheBSListener());   //ע����յ���վ��Ϣ�ļ���
		
		//�������ò�ѯ����
		JuheBSParams params = new JuheBSParams();
		//������������    GOOGLE���ȸ����꣨���ùȸ��ͼ���ߵµ�ͼ��QQ��ͼ��    BAIDU���ٶ����꣨���ðٶȵ�ͼ��     GPS:gps����
		params.setCoor(JuheBSParams.GOOGLE); 
		
		
		//������������     CMCC���ƶ�     UNICOM����ͨ    TELECOM: ����
		
		//���Network���ó�CMCC �� UNICOM, ��Ҫ��������������
		params.setNetwork(JuheBSParams.CMCC);  
		params.setLac(17695);                       //��վС����
		params.setCell(28655);                      //��վ��

		//���Network���ó�TELECOM, ��Ҫ��������������
//		params.setNetwork(JuheBSParams.TELECOM);    //��ѯ���Ż�վ
//		params.setBid(29282);                       //С����
//		params.setNid(11);                          //�����ʶ
//		params.setSid(14175);                       //ϵͳ��ʶ

		jhBS.setParams(params);

		jhBS.startRequest();

	}

	
}
