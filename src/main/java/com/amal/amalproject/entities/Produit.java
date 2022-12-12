package com.amal.amalproject.entities;

import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Objects;

public class Produit extends Application {
    int id_produit;
    String libele;
    float  prix_produit;
    //double
    String description_produit;
    String photo_produit;
    int numVendeur;
    private String cathegorie;
    private LocalDate dateP;
    private int id_beneficier;


    public Produit() {
    }
    public Produit(int id_produit, String libele, float prix_produit, String description_produit, String photo_produit, int numVendeur) {
        this.id_produit = id_produit;
        this.libele = libele;
        this.prix_produit = prix_produit;
        this.description_produit = description_produit;
        this.photo_produit = photo_produit;
        this.numVendeur = numVendeur;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
    @Override
    public int hashCode() {
        return Objects.hash(id_produit, libele, prix_produit, description_produit, photo_produit, numVendeur);
    }

    public int getId_produit() {

        return id_produit;
    }

    public void setId_produit(int id_produit) {

        this.id_produit = id_produit;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public String getDescription_produit() {

        return description_produit;
    }

    public void setDescription_produit(String description_produit) {

        this.description_produit = description_produit;
    }

    public String getPhoto_produit() {

        return photo_produit;
    }

    public void setPhoto_produit(String photo_produit) {

        this.photo_produit = photo_produit;
    }

    public int getNumVendeur() {
        return numVendeur;
    }

    public void setNumVendeur(int numVendeur) {
        this.numVendeur = numVendeur;
    }

    public String getCathegorie() {
        return cathegorie;
    }

    public void setCathegorie(String cathegorie) {
        this.cathegorie = cathegorie;
    }

    public LocalDate getDateP() {
        return dateP;
    }

    public void setDateP(LocalDate dateP) {
        this.dateP = dateP;
    }

    public int getId_beneficier() {
        return id_beneficier;
    }

    public void setId_beneficier(int id_beneficier) {
        this.id_beneficier = id_beneficier;
    }

    @Override
    public String toString() {
        return "Boutique{" +
                "id_produit=" + id_produit +
                ", libele='" + libele + '\'' +
                ", prix_produit=" + prix_produit +
                ", description_produit='" + description_produit + '\'' +
                ", photo_produit='" + photo_produit + '\'' +
                ", numVendeur=" + numVendeur +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Produit))
            return false;
        Produit produit = (Produit) o;
        return id_produit == produit.id_produit && Float.compare(produit.prix_produit, prix_produit) == 0 && numVendeur == produit.numVendeur && libele.equals(produit.libele) && description_produit.equals(produit.description_produit) && photo_produit.equals(produit.photo_produit);
    }


}
