package com.amal.amalproject.entities;

public class Organization {

    private int userId;
    private String matriculeFiscale;
    private String nom;
    private String formJuridique;
    private String adresse;
    private Compte compte;


    /* Start Section Constructors */
    public Organization() {
    }
    /* End Section Constructors */


    /* Start Section Getters & Setters */

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFormJuridique() {
        return formJuridique;
    }

    public void setFormJuridique(String formJuridique) {
        this.formJuridique = formJuridique;
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
    /* End Section Getters & Setters */


    /* Start Section Equals */

    @Override
    public String toString() {
        return "Organization{" +
                "userId='" + userId + '\'' +
                "matriculeFiscale='" + matriculeFiscale + '\'' +
                ", nom='" + nom + '\'' +
                ", formJuridique='" + formJuridique + '\'' +
                ", adresse='" + adresse + '\'' +
                ", compte=" + compte +
                '}';
    }
    /* End Section Equals */
}
