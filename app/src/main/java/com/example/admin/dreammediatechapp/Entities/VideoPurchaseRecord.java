package com.example.admin.dreammediatechapp.Entities;

public class VideoPurchaseRecord {
	private int vprId;
	private User user;
	private Video video;
	private int count;
	private int buyCount;
	private String time;
	private String endTime;
	public int getVprId() {
		return vprId;
	}
	public void setVprId(int vprId) {
		this.vprId = vprId;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
