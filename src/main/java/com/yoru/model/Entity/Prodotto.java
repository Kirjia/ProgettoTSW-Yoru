package com.yoru.model.Entity;


public class Prodotto {

    public static final String COLUMNLABEL1 = "SKU";
    public static final String COLUMNLABEL2 = "nome";
    public static final String COLUMNLABEL3 = "peso";
    public static final String COLUMNLABEL4 = "prezzo";
    public static final String COLUMNLABEL5 = "quantità";
    public static final String COLUMNLABEL6 = "ID_casa_produttrice";
    public static final String TABLE_NAME = "Prodotto";

    private int SKU;
    private String nome;
    private float peso;
    private float prezzo;
    private int quantità;
    private int id_produttore;
    private ItemType itemType;
    
    public enum ItemType{
    	Libro,
    	Gadget
    }

    public  Prodotto(){
        SKU=-1;
        nome=null;
        id_produttore=-1;
        peso=-1;
    }


    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    public int getId_produttore() {
        return id_produttore;
    }

    public void setId_produttore(int id_produttore) {
        this.id_produttore = id_produttore;
    }
    

    public ItemType getItemType() {
		return itemType;
	}


	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = ItemType.valueOf(itemType);
	}


	@Override
    public String toString() {
        return this.getClass().getName() + "(" + SKU + "): " + nome + " " + peso + " " + prezzo + " " + quantità + " " + id_produttore;
    }
}
