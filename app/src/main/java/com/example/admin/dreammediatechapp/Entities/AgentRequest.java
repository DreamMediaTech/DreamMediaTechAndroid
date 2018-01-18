package com.example.admin.dreammediatechapp.Entities;

public class AgentRequest {
	private int arId;
	private int uId;
	private String arState;
	private int arResponsibility;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getArId() {
		return arId;
	}
	public void setArId(int arId) {
		this.arId = arId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getArState() {
		return arState;
	}
	public void setArState(String arState) {
		this.arState = arState;
	}
	public int getArResponsibility() {
		return arResponsibility;
	}
	public void setArResponsibility(int arResponsibility) {
		this.arResponsibility = arResponsibility;
	}
	
}
