package com.yoru.model.Entity;

public class Indirizzo {

	
	private int id;
	private String email;
	private String via;
	private String provincia;
	private String city;
	private String CAP;
	
	public Indirizzo(int id, String email, String via, String provincia, String city, String CAP) {
		this.id = id;
		this.email = email;
		this.via = via;
		this.provincia = provincia;
		this.city = city;
		this.CAP = CAP;
	}

	public Indirizzo() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCAP() {
		return CAP;
	}

	public void setCAP(String cAP) {
		CAP = cAP;
	}
	
	

}
