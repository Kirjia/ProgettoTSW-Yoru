package com.yoru.model.Entity;

import java.util.List;

public class Libro extends Prodotto {

    public static final String COLUMNLABEL7 = "ISBN";
    public static final String COLUMNLABEL8 = "pagine";
    public static final String COLUMNLABEL9 = "lingua";
    public static final String TABLE_NAME = "Libro";

    private int pagine;
    private String ISBN;
    private String lingua;
    private List<Autore> autori;


    public int getNumeroPagine() {
        return pagine;
    }

    public void setNumeroPagine(int pagine) {
        this.pagine = pagine;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }
    
    

    public List<Autore> getAutori() {
		return autori;
	}

	public void setAutori(List<Autore> autori) {
		this.autori = autori;
	}

	@Override
    public String toString() {
        return super.toString();
    }
}
