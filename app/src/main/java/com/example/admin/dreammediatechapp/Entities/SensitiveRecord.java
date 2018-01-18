package com.example.admin.dreammediatechapp.Entities;

public class SensitiveRecord {
	private int srId;
	private int operator;
	private String srContent;
	private String srTime;
	public int getSrId() {
		return srId;
	}
	public void setSrId(int srId) {
		this.srId = srId;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getSrContent() {
		return srContent;
	}
	public void setSrContent(String srContent) {
		this.srContent = srContent;
	}
	public String getSrTime() {
		return srTime;
	}
	public void setSrTime(String srTime) {
		this.srTime = srTime;
	}
}
