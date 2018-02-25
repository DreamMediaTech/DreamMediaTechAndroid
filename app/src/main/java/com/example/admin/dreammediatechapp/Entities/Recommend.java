package com.example.admin.dreammediatechapp.Entities;

import java.util.List;

public class Recommend {
	private int recId;
	private String recName;
	private List<Video> videos;
	
	
	public int getRecId() {
		return recId;
	}
	public void setRecId(int recId) {
		this.recId = recId;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	
}
