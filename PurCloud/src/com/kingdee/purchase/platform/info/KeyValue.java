package com.kingdee.purchase.platform.info;

public class KeyValue {

	private String key;
	private Object value;
	
	public KeyValue(){
		super();
	}
	
	public KeyValue(String key,Object value){
		super();
		setKey(key);
		setValue(value);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}