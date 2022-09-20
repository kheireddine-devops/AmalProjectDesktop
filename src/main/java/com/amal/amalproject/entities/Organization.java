package com.amal.amalproject.entities;

public class Organization {

    private int userId;
    private String matriculeFiscale;
    private String nom;
    private String formJuridique;
    private String numPhone;
    private String email;
    private String adresse;
    private Compte compte;

    private String photo;


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

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
                ", numPhone='" + numPhone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", compte=" + compte +
                '}';
    }
    /* End Section Equals */
}
