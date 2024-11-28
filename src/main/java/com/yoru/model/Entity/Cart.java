package com.yoru.model.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Cart {

	private int userId;
	private float total;
	private Timestamp createdTime;
	private Timestamp modifieadTime;
	private Collection<CartItem> items;
	
	
	public Cart(int userId) {
		this.userId = userId;
		items = new ArrayList<>();
	}



	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public float getTotal() {
		float tot = 0;
		
		for (Iterator<CartItem> iterator = items.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			tot += cartItem.getPrezzo() * cartItem.getQuantity();
			
		}
		return tot;
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
