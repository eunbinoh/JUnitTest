package com.kh.sharethevision.member.model.vo;

import java.sql.Date;

public class Member {
	private String id;
	private String pwd;
	private String name;
	private String nickName;
	private String email;
	private String gender;
	private int age;
	private String phone;
	private Date birthDay;
	private String address;
	private Date enrollDate;
	private Date updateDate;
	private String memberStatus;
	
	public Member() {}

	public Member(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}

	public Member(String id, String pwd, String name, String nickName, String email, String gender, int age,
			String phone, Date birthDay, String address, Date enrollDate, Date updateDate, String memberStatus) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.nickName = nickName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.birthDay = birthDay;
		this.address = address;
		this.enrollDate = enrollDate;
		this.updateDate = updateDate;
		this.memberStatus = memberStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", pwd=" + pwd + ", name=" + name + ", nickName=" + nickName + ", email=" + email
				+ ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", birthDay=" + birthDay + ", address="
				+ address + ", enrollDate=" + enrollDate + ", updateDate=" + updateDate + ", memberStatus="
				+ memberStatus + "]";
	}

	
	
}
