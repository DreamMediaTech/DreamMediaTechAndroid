package com.example.admin.dreammediatechapp.Entities;

public class Rebate {
	private int reId;
	private PackagePurchaseRecord packagePurchaseRecord;
	private int mId;
	private String reTyep;
	
	public int getReId() {
		return reId;
	}
	public void setReId(int reId) {
		this.reId = reId;
	}
	
	public PackagePurchaseRecord getPackagePurchaseRecord() {
		return packagePurchaseRecord;
	}
	public void setPackagePurchaseRecord(PackagePurchaseRecord packagePurchaseRecord) {
		this.packagePurchaseRecord = packagePurchaseRecord;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public String getReTyep() {
		return reTyep;
	}
	public void setReTyep(String reTyep) {
		this.reTyep = reTyep;
	}
	
}
