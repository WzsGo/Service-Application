package com.wzs.telchongzhi;

public interface Telchongzhi_cs 
{
	public int telChongzhi_tep1(String phoneno,String cardnum) throws Exception;
	public TelChongzhiEntity telChongzhi_tep2(String phoneno,String cardnum) throws Exception;
	public TelChongzhiEntity telChongzhi_tep3(String phoneno,String cardnum,String orderid,String sign)throws Exception;
}
