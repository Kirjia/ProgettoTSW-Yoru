package com.shiru.model.Entity;

import java.sql.Timestamp;

public class Cart {

	private int id;
	private int userId;
	private double total;
	private Timestamp createdTime;
	private Timestamp modifieadTime;
	
	
	public Cart(int id, int userId, int total, Timestamp createTime, Timestamp modTime) {
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.createdTime = createTime;
		this.modifieadTime = modTime;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public Timestamp getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}


	public Timestamp getModifieadTime() {
		return modifieadTime;
	}


	public void setModifieadTime(Timestamp modifieadTime) {
		this.modifieadTime = modifieadTime;
	}
	
	
	
	
}
