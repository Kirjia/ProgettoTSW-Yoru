package com.shiru.model.Entity;



public class CartItem {

	private int cartId;
	private int SKU;
	private int quantity;
	
	
	public CartItem(int cartId, int itemSKU, int quantity) {
		this.cartId = cartId;
		this.SKU = itemSKU;
		this.quantity = quantity;
	}
	
	
	public int getIdcartId() {
		return cartId;
	}
	public void setIdcartId(int idcartId) {
		this.cartId = idcartId;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int sKU) {
		SKU = sKU;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
}
