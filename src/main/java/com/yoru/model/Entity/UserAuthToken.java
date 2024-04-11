package com.yoru.model.Entity;

public class UserAuthToken {
	
	public static final String COLUMN_LABEL1 = "userID";
	public static final String COLUMN_LABEL2 = "validator";
	public static final String COLUMN_LABEL3 = "token";
	
	
	private int UserID;
	private String validator;
	private String Token;
	
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	
	
	

}
