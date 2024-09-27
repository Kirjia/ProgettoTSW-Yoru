package com.yoru.model.Entity;



public class CartItem {

	private int cartId;
	private int SKU;
	private int quantity;
	private float prezzo;
	private String item_name;
	
	
	public CartItem(int cartId, int itemSKU, int quantity, float prezzo, String item_name) {
		this.cartId = cartId;
		this.SKU = itemSKU;
		this.quantity = quantity;
		this.item_name = item_name;
		this.prezzo = prezzo;
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
	
	public String getNome() {
		return item_name;
	}
	
	public void setNome(String item_name) {
		this.item_name = item_name;
	}
	
	public float getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	
	
	
}
