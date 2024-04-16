package com.yoru.model.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cart {

	private int id;
	private int userId;
	private float total;
	private Timestamp createdTime;
	private Timestamp modifieadTime;
	private Collection<CartItem> items;
	
	
	public Cart(int id, int userId, int total, Timestamp createTime, Timestamp modTime) {
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.createdTime = createTime;
		this.modifieadTime = modTime;
		items = new ArrayList<>();
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


	public float getTotal() {
		return total;
	}
	
	


	public Collection<CartItem> getItems() {
		return items;
	}


	public void setItems(Collection<CartItem> items) {
		this.items =  items;
	}


	public void setTotal(float total) {
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
