package com.example.admin.dreammediatechapp.Entities;



import java.io.Serializable;
import java.util.List;

public class VideoType  implements Serializable {
	private int vtId;
	private String vtName;
	private String vtImage;
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
	
	public String getVtImage() {
		return vtImage;
	}
	public void setVtImage(String vtImage) {
		this.vtImage = vtImage;
	}
	public List<VideoType> getSubTypes() {
		return subTypes;
	}
	public void setSubTypes(List<VideoType> subTypes) {
		this.subTypes = subTypes;
	}



}
