package com.example.admin.dreammediatechapp.Entities;

public class PackagePurchaseRecord {
	private int rId;
	private User user;
	private IntegralPackage integralPackage;
	private String rTime;
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public IntegralPackage getIntegralPackage() {
		return integralPackage;
	}
	public void setIntegralPackage(IntegralPackage integralPackage) {
		this.integralPackage = integralPackage;
	}
	public String getrTime() {
		return rTime;
	}
	public void setrTime(String rTime) {
		this.rTime = rTime;
	}
	
}
