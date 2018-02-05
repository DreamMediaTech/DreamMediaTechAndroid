package com.example.admin.dreammediatechapp.Entities;

public class Video {
	private int vId;
	private String vTitle;
	private String vIntroduce;
	private int vNum;
	private int vPrice;
	private int vCount;//买一次能播多少次
	private User author;
	private String vState;
	private int vProfit;
	private int vComment;
	private int vCommentCount;
	private VideoType firstType;
	private byte[] vTop;
	private byte[] vScoure;
	private byte[] vScoure_sd;
	private byte[] vScoure_hd;
	private byte[] vScoure_ud;
	private String vAddress;
	private String vAddress_sd;
	private String vAddress_hd;
	private String vAddress_ud;
	private String vTopAddress;
	
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
	public byte[] getvTop() {
		return vTop;
	}
	public void setvTop(byte[] vTop) {
		this.vTop = vTop;
	}
	public byte[] getvScoure() {
		return vScoure;
	}
	public void setvScoure(byte[] vScoure) {
		this.vScoure = vScoure;
	}
	public byte[] getvScoure_sd() {
		return vScoure_sd;
	}
	public void setvScoure_sd(byte[] vScoure_sd) {
		this.vScoure_sd = vScoure_sd;
	}
	public byte[] getvScoure_hd() {
		return vScoure_hd;
	}
	public void setvScoure_hd(byte[] vScoure_hd) {
		this.vScoure_hd = vScoure_hd;
	}
	public byte[] getvScoure_ud() {
		return vScoure_ud;
	}
	public void setvScoure_ud(byte[] vScoure_ud) {
		this.vScoure_ud = vScoure_ud;
	}
	public String getvAddress() {
		return vAddress;
	}
	public void setvAddress(String vAddress) {
		this.vAddress = vAddress;
	}
	public String getvAddress_sd() {
		return vAddress_sd;
	}
	public void setvAddress_sd(String vAddress_sd) {
		this.vAddress_sd = vAddress_sd;
	}
	public String getvAddress_hd() {
		return vAddress_hd;
	}
	public void setvAddress_hd(String vAddress_hd) {
		this.vAddress_hd = vAddress_hd;
	}
	
	public String getvAddress_ud() {
		return vAddress_ud;
	}
	public void setvAddress_ud(String vAddress_ud) {
		this.vAddress_ud = vAddress_ud;
	}
	public String getvTopAddress() {
		return vTopAddress;
	}
	public void setvTopAddress(String vTopAddress) {
		this.vTopAddress = vTopAddress;
	}
	public int getvCommentCount() {
		return vCommentCount;
	}
	public void setvCommentCount(int vCommentCount) {
		this.vCommentCount = vCommentCount;
	}
	public int getvCount() {
		return vCount;
	}
	public void setvCount(int vCount) {
		this.vCount = vCount;
	}
	public int getvProfit() {
		return vProfit;
	}
	public void setvProfit(int vProfit) {
		this.vProfit = vProfit;
	}
	
	
	
	
}
