package com.example.admin.dreammediatechapp.Entities;

import java.util.List;

public class VideoType {
	private int vtId;
	private String vtName;
	private int vtFather;
	private List<VideoType> subTypes;
	public int getVtId() {
		return vtId;
	}
	public void setVtId(int vtId) {
		this.vtId = vtId;
	}
	public String getVtName() {
		return vtName;
	}
	public void setVtName(String vtName) {
		this.vtName = vtName;
	}
	public int getVtFather() {
		return vtFather;
	}
	public void setVtFather(int vtFather) {
		this.vtFather = vtFather;
	}
	public List<VideoType> getSubTypes() {
		return subTypes;
	}
	public void setSubTypes(List<VideoType> subTypes) {
		this.subTypes = subTypes;
	}
	
	
}
