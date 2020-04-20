package com.example.jesusyx.carritocompras;

public class Usuario {

    private int cod;
    private String nom, cor, pas;


    public Usuario(int cod, String nom, String cor, String pas) {
        this.cod = cod;
        this.nom = nom;
        this.cor = cor;
        this.pas = pas;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}
