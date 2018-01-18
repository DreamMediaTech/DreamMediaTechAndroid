package com.example.admin.dreammediatechapp.Entities;

public class Video {
	private int vId;
	private String vTitle;
	private String vContent;
	private String vIntroduce;
	private int vNum;
	private int vPrice;
	private User author;
	private String vState;
	private int vComment;
	private VideoType firstType;
	public int getvId() {
		return vId;
	}
	public void setvId(int vId) {
		this.vId = vId;
	}
	public String getvTitle() {
		return vTitle;
	}
	public void setvTitle(String vTitle) {
		this.vTitle = vTitle;
	}
	public String getvContent() {
		return vContent;
	}
	public void setvContent(String vContent) {
		this.vContent = vContent;
	}
	public String getvIntroduce() {
		return vIntroduce;
	}
	public void setvIntroduce(String vIntroduce) {
		this.vIntroduce = vIntroduce;
	}
	public int getvNum() {
		return vNum;
	}
	public void setvNum(int vNum) {
		this.vNum = vNum;
	}
	public int getvPrice() {
		return vPrice;
	}
	public void setvPrice(int vPrice) {
		this.vPrice = vPrice;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getvState() {
		return vState;
	}
	public void setvState(String vState) {
		this.vState = vState;
	}
	public int getvComment() {
		return vComment;
	}
	public void setvComment(int vComment) {
		this.vComment = vComment;
	}
	public VideoType getFirstType() {
		return firstType;
	}
	public void setFirstType(VideoType firstType) {
		this.firstType = firstType;
	}
	
}
