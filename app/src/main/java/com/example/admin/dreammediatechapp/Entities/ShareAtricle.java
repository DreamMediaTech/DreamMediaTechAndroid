package com.example.admin.dreammediatechapp.Entities;

public class ShareAtricle {
	private int saId;
	private Article article;
	private User user;
	private int clicks;
	public int getSaId() {
		return saId;
	}
	public void setSaId(int saId) {
		this.saId = saId;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
