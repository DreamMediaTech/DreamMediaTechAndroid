package com.example.admin.dreammediatechapp.Entities;

public class CommonProblem {
	private int cpId;
	private String cpTitle;
	private String address;
	private String cpContent;
	private User author;
	
	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	public String getCpTitle() {
		return cpTitle;
	}
	public void setCpTitle(String cpTitle) {
		this.cpTitle = cpTitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCpContent() {
		return cpContent;
	}
	public void setCpContent(String cpContent) {
		this.cpContent = cpContent;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	
}
