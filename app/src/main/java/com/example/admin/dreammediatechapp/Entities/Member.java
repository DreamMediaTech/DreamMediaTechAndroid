package com.example.admin.dreammediatechapp.Entities;

public class Member {
	private int mId;
	private String birthday;
	private String mail;
	private String alipay;
	private String wechat;
	private float reBate;
	private int introducer;
	private int firstSuperior;
	private int secondSuperior;
	private float bonusIntegral;
	private float consumptionIntegral;
	private float sharingIntegral;
	private int iId;
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getiId() {
		return iId;
	}
	public void setiId(int iId) {
		this.iId = iId;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public float getReBate() {
		return reBate;
	}
	public void setReBate(float reBate) {
		this.reBate = reBate;
	}
	public int getIntroducer() {
		return introducer;
	}
	public void setIntroducer(int introducer) {
		this.introducer = introducer;
	}
	public int getFirstSuperior() {
		return firstSuperior;
	}
	public void setFirstSuperior(int firstSuperior) {
		this.firstSuperior = firstSuperior;
	}
	public int getSecondSuperior() {
		return secondSuperior;
	}
	public void setSecondSuperior(int secondSuperior) {
		this.secondSuperior = secondSuperior;
	}
	
	public float getBonusIntegral() {
		return bonusIntegral;
	}
	public void setBonusIntegral(float bonusIntegral) {
		this.bonusIntegral = bonusIntegral;
	}
	public float getConsumptionIntegral() {
		return consumptionIntegral;
	}
	public void setConsumptionIntegral(float consumptionIntegral) {
		this.consumptionIntegral = consumptionIntegral;
	}
	public float getSharingIntegral() {
		return sharingIntegral;
	}
	public void setSharingIntegral(float sharingIntegral) {
		this.sharingIntegral = sharingIntegral;
	}
}
