package com.amal.amalproject.entities;


import java.time.LocalDate;

public class User {
    protected int userId;
    protected String nom;
    protected String prenom;
    protected LocalDate dateNaissance;
    protected String sexe;
    protected String adresse;

    protected Compte compte;


    /* Start Section Constructor */
    public User() {
    }
    /* End Section Constructor */


    /* Start Section Getters & Setters */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    /* Start Section Getters & Setters */

   /* Start Section ToString */

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", sexe='" + sexe + '\'' +
                ", adresse='" + adresse + '\'' +
                ", compte=" + compte +
                '}';
    }
    /* End Section ToString */
    
}
