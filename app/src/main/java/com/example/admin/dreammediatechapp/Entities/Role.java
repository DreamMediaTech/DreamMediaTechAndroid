package com.example.admin.dreammediatechapp.Entities;

import java.util.List;

public class Role {
	private int rId;
	private String rName;
	private String rDescribe;
	private List<Function> functions;
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getrDescribe() {
		return rDescribe;
	}
	public void setrDescribe(String rDescribe) {
		this.rDescribe = rDescribe;
	}
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	
}
