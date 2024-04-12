package com.yoru.model.Entity;


public class Autore {

	
	public static final String TABLE_NAME = "autori";
    public static final String COLMUNLABEL1 = "ID_autore";
    public static final String COLMUNLABEL2 = "nome";
    public static final String COLMUNLABEL3 = "cognome";
    public static final String COLMUNLABEL4 = "nArte";

    private int ID;
    private String nome;
    private String cognome;
    private String nomeArte;

    public Autore(){
    }

    public Autore(int ID, String nome, String cognome, String nomeArte){
        this.ID=ID;
        this.nome=nome;
        this.cognome=cognome;
        this.nomeArte=nomeArte;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNomeArte() {
        return nomeArte;
    }

    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    @Override
    public String toString() {
        return "Autore{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nomeArte='" + nomeArte + '\'' +
                '}';
    }
}
