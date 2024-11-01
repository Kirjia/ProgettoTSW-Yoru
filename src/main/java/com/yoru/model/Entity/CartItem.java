package com.yoru.model.Entity;



public class CartItem {

	private int userId;
	private int SKU;
	private int quantity;
	private float prezzo;
	private String nome;
	
	
	public CartItem(int userId, int itemSKU, int quantity, float prezzo, String item_name) {
		this.userId = userId;
		this.SKU = itemSKU;
		this.quantity = quantity;
		this.nome = item_name;
		this.prezzo = prezzo;
	}
	
	
	public int getIdcartId() {
		return userId;
	}
	public void setIdcartId(int idcartId) {
		this.userId = idcartId;
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
		return nome;
	}
	
	public void setNome(String item_name) {
		this.nome = item_name;
	}
	
	public float getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	
	
	
}
