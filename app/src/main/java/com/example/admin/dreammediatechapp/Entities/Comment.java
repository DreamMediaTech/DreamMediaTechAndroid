package com.example.admin.dreammediatechapp.Entities;

public class Comment {
	private int cId;
	private int cUser;
	private int cResponsibility;
	private String cContent;
	private int cState;
	private Video video;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getcUser() {
		return cUser;
	}
	public void setcUser(int cUser) {
		this.cUser = cUser;
	}
	public int getcResponsibility() {
		return cResponsibility;
	}
	public void setcResponsibility(int cResponsibility) {
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
	
	
	
}
