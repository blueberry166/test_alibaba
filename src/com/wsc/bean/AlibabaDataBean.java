package com.wsc.bean;

public class AlibabaDataBean {
	private String user_id;
	private String brand_id;
	private String type;
	private float prefer;//偏好程度，评分
	private String date;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getPrefer() {
		return prefer;
	}
	public void setPrefer(float prefer) {
		this.prefer = prefer;
	}
	public String toString(){
		return user_id+","+brand_id+","+prefer+","+date+"\r\n";
	}
}