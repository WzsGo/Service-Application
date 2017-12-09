package entity_xinhua;

import java.io.Serializable;

public class XinHuaEntity implements Serializable{
	private static final long serialVersionUID=1L;
	
	public String XiangJie;
	public XinHuaEntity()
	{
		
	}
	public XinHuaEntity(String xiangjie)
	{
		
		this.XiangJie=xiangjie;
	}
	public String getXiangJie() {
		return XiangJie;
	}
	public void setXiangJie(String xiangJie) {
		XiangJie = xiangJie;
	}


}
