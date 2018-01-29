package com.example.admin.dreammediatechapp.Entities;

import java.util.List;

public class User {
	private int uId;
	private String uName;
	private String uNickName;
	private String uImage;
	private String uImageAddress;
	private String uSex;
	private String uPhone;
	private String uPassword;
	private String ymId;
	private Member memberInformation;
	private Agents agentsInformation;
	private String uRegisterTime;
	private String uType;
	private List<Role> roles;
	private List<Function> functions;
	public String getYmId() {
		return ymId;
	}
	public void setYmId(String ymId) {
		this.ymId = ymId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuSex() {
		return uSex;
	}
	public void setuSex(String uSex) {
		this.uSex = uSex;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Member getMemberInformation() {
		return memberInformation;
	}
	public void setMemberInformation(Member memberInformation) {
		this.memberInformation = memberInformation;
	}
	public Agents getAgentsInformation() {
		return agentsInformation;
	}
	public void setAgentsInformation(Agents agentsInformation) {
		this.agentsInformation = agentsInformation;
	}
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	public String getuNickName() {
		return uNickName;
	}
	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
	}
	public String getuImage() {
		return uImage;
	}
	public void setuImage(String uImage) {
		this.uImage = uImage;
	}
	public String getuImageAddress() {
		return uImageAddress;
	}
	public void setuImageAddress(String uImageAddress) {
		this.uImageAddress = uImageAddress;
	}
	public String getuRegisterTime() {
		return uRegisterTime;
	}
	public void setuRegisterTime(String uRegisterTime) {
		this.uRegisterTime = uRegisterTime;
	}
	public String getuType() {
		return uType;
	}
	public void setuType(String uType) {
		this.uType = uType;
	}
	
	
	
	
}
