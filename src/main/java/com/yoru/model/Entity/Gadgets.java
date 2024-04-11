package com.yoru.model.Entity;

import java.util.List;

public class Gadgets extends Prodotto {

	private String modello;
	private String marchio;
	private List<String> materiali;

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
    
    

	public List<String> getMateriali() {
		return materiali;
	}

	public void setMateriali(List<String> materiali) {
		this.materiali = materiali;
	}

	@Override
	public String toString() {
		return  super.toString() ;
	}

    
}
