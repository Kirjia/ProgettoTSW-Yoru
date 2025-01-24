package com.yoru.model.Entity;


public class Prodotto {

	public static final String CLOUMNLABEL_DELETD = "is_deleted";
    public static final String COLUMNLABEL1 = "SKU";
    public static final String COLUMNLABEL2 = "nome";
    public static final String COLUMNLABEL4 = "prezzo";
    public static final String COLUMNLABEL5 = "quantità";
    public static final String COLUMNLABEL6 = "ID_casa_produttrice";
    public static final String COLUMNLABEL7 = "descrizione";
    public static final String TABLE_NAME = "Prodotto";

    private int SKU;
    private String nome;
    private float prezzo;
    private int quantità;
    private int id_produttore;
    private ItemType itemType;
    private String descrizione;
    private int deleted;
    
    public enum ItemType{
    	LIBRO,
    	GADGET
    }

    public  Prodotto(){
        SKU=-1;
        nome=null;
        id_produttore=-1;
        deleted = 0;
    }

    

    public int getDeleted() {
		return deleted;
	}



	public void setDeleted(int deleted) {
		this.deleted = deleted;
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
        return this.getClass().getName() + "(" + SKU + "): " + nome  + " " + prezzo + " " + quantità + " " + id_produttore;
    }


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
