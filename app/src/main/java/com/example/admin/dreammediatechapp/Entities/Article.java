package com.example.admin.dreammediatechapp.Entities;

public class Article {
	private int aId;
	private String aTitle;
	private String aContent;
	private int clicks;
	private User user;
	private String aTime;
	public int getaId() {
		return aId;
	}
	public void setaId(int aId) {
		this.aId = aId;
	}
	public String getaTitle() {
		return aTitle;
	}
	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}
	public String getaContent() {
		return aContent;
	}
	public void setaContent(String aContent) {
		this.aContent = aContent;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public String getaTime() {
		return aTime;
	}
	public void setaTime(String aTime) {
		this.aTime = aTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
