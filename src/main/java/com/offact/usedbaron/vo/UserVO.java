package com.offact.usedbaron.vo;

/**
 * @author 4530
 *
 */
public class UserVO extends AbstractVO {
	private String userId;
	private String userName;
	private String inPassword;
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInPassword() {
		return inPassword;
	}
	public void setInPassword(String inPassword) {
		this.inPassword = inPassword;
	}

}
