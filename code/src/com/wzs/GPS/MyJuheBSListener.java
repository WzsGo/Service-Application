package com.wzs.GPS;

import cn.juhe.bs.JuheBSListener;
import cn.juhe.bs.JuheLocation;

public class MyJuheBSListener implements JuheBSListener {

	@Override
	public void onReceiveLocation(JuheLocation jl) {
		// TODO Auto-generated method stub		
		//JuheLocation��װ�˾ۺϻ�վ��λ����Ϣ��
		
		StringBuffer sb = new StringBuffer(); 		
		sb.append("������:" + jl.getResponseCode() + "\n");
		sb.append("���������� " + jl.getResponse() + "\n");
		sb.append("γ��: " + jl.getLat() + "\n");
		sb.append("����: " + jl.getLng() + "\n");
		sb.append("��վ���ǰ뾶�� " + jl.getRadius() + "��\n");
		sb.append("����λ�ã� " + jl.getAddress() + "\n");
		System.out.println(sb.toString());
}
	}
