package com.example.admin.dreammediatechapp.Entities;

public class VideoProviderRequest {
	private int vprId;
	private User user;
	private String vprState;
	private User vprRespinsibility;
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
	public User getVprRespinsibility() {
		return vprRespinsibility;
	}
	public void setVprRespinsibility(User vprRespinsibility) {
		this.vprRespinsibility = vprRespinsibility;
	}
	public String getVprState() {
		return vprState;
	}
	public void setVprState(String vprState) {
		this.vprState = vprState;
	}

	
}
