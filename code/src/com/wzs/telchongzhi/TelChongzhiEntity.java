package com.wzs.telchongzhi;

import java.io.Serializable;

public class TelChongzhiEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String cardid;
	public String cardname;
	public String inprice;
	public String game_area;
	
	public String cardid2;
	
	public String ordercash;
	public String cardname2;	
	public String sporder_id;
	public String uorderid;
	public String game_userid;
	public String game_state;
	
	public TelChongzhiEntity ()
	{
		
	}
	public  TelChongzhiEntity(String cardid,String cardname,String inprice,String game_area)
	{
		this.cardid = cardid;
		this.cardname = cardname;
		this.inprice = inprice;
		this.game_area = game_area;
	}
	public TelChongzhiEntity(String cardid2,String ordercash,String cardname2,
			String sporder_id,String uorderid,String game_userid,String game_state)
	{
		this.cardid2 = cardid2;
		
		this.ordercash = ordercash;
		this.cardname2 = cardname2;
		this.sporder_id = sporder_id;
		this.uorderid = uorderid;
		this.game_userid = game_userid;
		this.game_state = game_state;
		
	}
}
