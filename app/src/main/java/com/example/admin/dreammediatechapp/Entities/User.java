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


//
//	 class Member {
//		private int mId;
//		private String birthday;
//		private String mail;
//		private String alipay;
//		private String wechat;
//		private float reBate;
//		private int introducer;
//		private int firstSuperior;
//		private int secondSuperior;
//		private float bonusIntegral;
//		private float consumptionIntegral;
//		private float sharingIntegral;
//		private int iId;
//		private User user;
//
//		public User getUser() {
//			return user;
//		}
//		public void setUser(User user) {
//			this.user = user;
//		}
//		public int getiId() {
//			return iId;
//		}
//		public void setiId(int iId) {
//			this.iId = iId;
//		}
//		public int getmId() {
//			return mId;
//		}
//		public void setmId(int mId) {
//			this.mId = mId;
//		}
//		public String getBirthday() {
//			return birthday;
//		}
//		public void setBirthday(String birthday) {
//			this.birthday = birthday;
//		}
//		public String getMail() {
//			return mail;
//		}
//		public void setMail(String mail) {
//			this.mail = mail;
//		}
//		public String getAlipay() {
//			return alipay;
//		}
//		public void setAlipay(String alipay) {
//			this.alipay = alipay;
//		}
//		public String getWechat() {
//			return wechat;
//		}
//		public void setWechat(String wechat) {
//			this.wechat = wechat;
//		}
//		public float getReBate() {
//			return reBate;
//		}
//		public void setReBate(float reBate) {
//			this.reBate = reBate;
//		}
//		public int getIntroducer() {
//			return introducer;
//		}
//		public void setIntroducer(int introducer) {
//			this.introducer = introducer;
//		}
//		public int getFirstSuperior() {
//			return firstSuperior;
//		}
//		public void setFirstSuperior(int firstSuperior) {
//			this.firstSuperior = firstSuperior;
//		}
//		public int getSecondSuperior() {
//			return secondSuperior;
//		}
//		public void setSecondSuperior(int secondSuperior) {
//			this.secondSuperior = secondSuperior;
//		}
//
//		public float getBonusIntegral() {
//			return bonusIntegral;
//		}
//		public void setBonusIntegral(float bonusIntegral) {
//			this.bonusIntegral = bonusIntegral;
//		}
//		public float getConsumptionIntegral() {
//			return consumptionIntegral;
//		}
//		public void setConsumptionIntegral(float consumptionIntegral) {
//			this.consumptionIntegral = consumptionIntegral;
//		}
//		public float getSharingIntegral() {
//			return sharingIntegral;
//		}
//		public void setSharingIntegral(float sharingIntegral) {
//			this.sharingIntegral = sharingIntegral;
//		}
//	}
//	class Agents {
//		private int agId;
//		private User user;
//		private com.example.admin.dreammediatechapp.Entities.Member member;
//		private String area;
//		private float firstRebate;
//		private float secondRebate;
//
//		public User getUser() {
//			return user;
//		}
//		public void setUser(User user) {
//			this.user = user;
//		}
//		public com.example.admin.dreammediatechapp.Entities.Member getMember() {
//			return member;
//		}
//		public void setMember(com.example.admin.dreammediatechapp.Entities.Member member) {
//			this.member = member;
//		}
//		public int getAgId() {
//			return agId;
//		}
//		public void setAgId(int agId) {
//			this.agId = agId;
//		}
//		public String getArea() {
//			return area;
//		}
//		public void setArea(String area) {
//			this.area = area;
//		}
//		public float getFirstRebate() {
//			return firstRebate;
//		}
//		public void setFirstRebate(float firstRebate) {
//			this.firstRebate = firstRebate;
//		}
//		public float getSecondRebate() {
//			return secondRebate;
//		}
//		public void setSecondRebate(float secondRebate) {
//			this.secondRebate = secondRebate;
//		}
//
//	}
//	class Role {
//		private int rId;
//		private String rName;
//		private String rDescribe;
//		public int getrId() {
//			return rId;
//		}
//		public void setrId(int rId) {
//			this.rId = rId;
//		}
//		public String getrName() {
//			return rName;
//		}
//		public void setrName(String rName) {
//			this.rName = rName;
//		}
//		public String getrDescribe() {
//			return rDescribe;
//		}
//		public void setrDescribe(String rDescribe) {
//			this.rDescribe = rDescribe;
//		}
//
//	}
//	class Function {
//		private int fId;
//		private String Fname;
//		private String Fdescribe;
//		private int Fstate;
//
//		public int getfId() {
//			return fId;
//		}
//		public void setfId(int fId) {
//			this.fId = fId;
//		}
//		public String getFname() {
//			return Fname;
//		}
//		public void setFname(String fname) {
//			Fname = fname;
//		}
//		public String getFdescribe() {
//			return Fdescribe;
//		}
//		public void setFdescribe(String fdescribe) {
//			Fdescribe = fdescribe;
//		}
//		public int getFstate() {
//			return Fstate;
//		}
//		public void setFstate(int fstate) {
//			Fstate = fstate;
//		}
//
//
//
//	}


}
