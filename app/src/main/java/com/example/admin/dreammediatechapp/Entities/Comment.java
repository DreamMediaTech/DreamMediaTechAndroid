package com.example.admin.dreammediatechapp.Entities;

public class Comment {
	private int cId;
	private User cUser;
	private User cResponsibility;
	private String cContent;
	private int cState;
	private Video video;
	private String cTime;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	
	public User getcUser() {
		return cUser;
	}
	public void setcUser(User cUser) {
		this.cUser = cUser;
	}
	public User getcResponsibility() {
		return cResponsibility;
	}
	public void setcResponsibility(User cResponsibility) {
		this.cResponsibility = cResponsibility;
	}
	public String getcContent() {
		return cContent;
	}
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	public int getcState() {
		return cState;
	}
	public void setcState(int cState) {
		this.cState = cState;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public String getcTime() {
		return cTime;
	}
	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	
	
	
	
}
