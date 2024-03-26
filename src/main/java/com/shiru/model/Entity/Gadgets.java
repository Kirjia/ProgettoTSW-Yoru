package com.shiru.model.Entity;

public class Gadgets extends Prodotto {

	private String modello;
	private String marchio;

    public Gadgets(){
        super();
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getMarchio() {
        return marchio;
    }

    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }

	@Override
	public String toString() {
		return  super.toString() ;
	}

    
}
