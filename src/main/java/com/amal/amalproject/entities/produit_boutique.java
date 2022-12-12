package com.amal.amalproject.entities;

import java.util.Objects;

public class produit_boutique {

    private int id_produit;
    private String libele;
    private double prix_produit;
    private String description_produit;
    private String photo_produit;


    public produit_boutique() {

    }

    public produit_boutique(int id_produit, String libele, double prix_produit, String description_produit, String photo_produit) {
        this.id_produit = id_produit;
        this.libele = libele;
        this.prix_produit = prix_produit;
        this.description_produit = description_produit;
        this.photo_produit = photo_produit;

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

    public double getPrix_produit() {

        return prix_produit;
    }

    public void setPrix_produit(double prix_produit) {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof produit_boutique)) return false;
        produit_boutique that = (produit_boutique) o;
        return getId_produit() == that.getId_produit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_produit());
    }

    @Override
    public String toString() {
        return "produit_boutique{" +
                "id_produit=" + id_produit +
                ", libele='" + libele + '\'' +
                ", prix_produit=" + prix_produit +
                ", description_produit='" + description_produit + '\'' +
                ", photo_produit='" + photo_produit + '\'' +
                '}';
    }
}
