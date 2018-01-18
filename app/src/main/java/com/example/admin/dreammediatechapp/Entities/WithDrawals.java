package com.example.admin.dreammediatechapp.Entities;

public class WithDrawals {
	private int wId;
	private User user;
	private int money;
	private String wTime;
	public int getwId() {
		return wId;
	}
	public void setwId(int wId) {
		this.wId = wId;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getwTime() {
		return wTime;
	}
	public void setwTime(String wTime) {
		this.wTime = wTime;
	}
	
}
