package entity;

import java.io.Serializable;

public class TranslationEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	public String Value;
	public String Key;//扩展内容
	public  TranslationEntity(){//构造函数
		}
	
	
	public TranslationEntity(String value,String key) {
		this.Value=value;
		this.Key=key;
	}

	
	public String getKey() {
		return Key;
	}
	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public void setKey(String key) {
		Key = key;
	}

}
