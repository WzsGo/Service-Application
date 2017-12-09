package com.wzs.GPS;

import cn.juhe.bs.JuheBSListener;
import cn.juhe.bs.JuheLocation;

public class MyJuheBSListener implements JuheBSListener {

	@Override
	public void onReceiveLocation(JuheLocation jl) {
		// TODO Auto-generated method stub		
		//JuheLocation封装了聚合基站的位置信息，
		
		StringBuffer sb = new StringBuffer(); 		
		sb.append("返回码:" + jl.getResponseCode() + "\n");
		sb.append("返回描述： " + jl.getResponse() + "\n");
		sb.append("纬度: " + jl.getLat() + "\n");
		sb.append("经度: " + jl.getLng() + "\n");
		sb.append("基站覆盖半径： " + jl.getRadius() + "米\n");
		sb.append("地理位置： " + jl.getAddress() + "\n");
		System.out.println(sb.toString());
}
	}
