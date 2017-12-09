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
		
		jhBS.setAppkey("af6fc0be830e90a405a5a623892f2756");		 //填入你申请的key
		jhBS.registerLocationListener(new MyJuheBSListener());   //注册接收到基站信息的监听
		
		//以下设置查询参数
		JuheBSParams params = new JuheBSParams();
		//返回坐标类型    GOOGLE：谷歌坐标（适用谷歌地图，高德地图，QQ地图）    BAIDU：百度坐标（适用百度地图）     GPS:gps坐标
		params.setCoor(JuheBSParams.GOOGLE); 
		
		
		//设置网络类型     CMCC：移动     UNICOM：联通    TELECOM: 电信
		
		//如果Network设置成CMCC 或 UNICOM, 需要填以下两个参数
		params.setNetwork(JuheBSParams.CMCC);  
		params.setLac(17695);                       //基站小区号
		params.setCell(28655);                      //基站号

		//如果Network设置成TELECOM, 需要填以下三个参数
//		params.setNetwork(JuheBSParams.TELECOM);    //查询电信基站
//		params.setBid(29282);                       //小区号
//		params.setNid(11);                          //网络标识
//		params.setSid(14175);                       //系统标识

		jhBS.setParams(params);

		jhBS.startRequest();

	}

	
}
