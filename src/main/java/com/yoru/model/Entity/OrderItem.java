package com.yoru.model.Entity;

public class OrderItem {
	
	public static final String TABLE_NAME = "order_items";
	

	private int SKU;
	private int quantity;
	private String nome;
	private float prezzo;
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "SKU=" + SKU + ", Nome= " +
                " , quantity=" + quantity +
                '}';
    }
}
