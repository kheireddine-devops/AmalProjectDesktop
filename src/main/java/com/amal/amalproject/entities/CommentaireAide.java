package com.amal.amalproject.entities;

import java.time.LocalDate;
import java.util.Objects;

public class CommentaireAide {
	
	private int idCommentaire;;
	private String txtCommentaire;
	private LocalDate dateCommentaire;
	private int idCompte;
	private int idDemande;
	private String nom;
	private String prenom;
	public CommentaireAide() {
		
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public CommentaireAide(int idCommentaire, String txtCommentaire, LocalDate dateCommentaire, int idCompte,
						   int idDemande, String nom, String prenom) {
		super();
		this.idCommentaire = idCommentaire;
		this.txtCommentaire = txtCommentaire;
		this.dateCommentaire = dateCommentaire;
		this.idCompte = idCompte;
		this.idDemande = idDemande;
		this.nom = nom;
		this.prenom = prenom;
	}
	public CommentaireAide(int idCommentaire, String txtCommentaire,LocalDate dateCommentaire, int idCompte, int idDemande) {
		super();
		this.idCommentaire = idCommentaire;
		this.txtCommentaire = txtCommentaire;
		this.dateCommentaire = dateCommentaire;
		this.idCompte = idCompte;
		this.idDemande = idDemande;
	}
	public int getIdCommentaire() {
		return idCommentaire;
	}
	public void setIdCommentaire(int idCommentaire) {
		this.idCommentaire = idCommentaire;
	}
	public String getTxtCommentaire() {
		return txtCommentaire;
	}
	public void setTxtCommentaire(String txtCommentaire) {
		this.txtCommentaire = txtCommentaire;
	}
	
	public LocalDate getDateCommentaire() {
		return dateCommentaire;
	}
	public void setDateCommentaire(LocalDate dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}
	public int getIdCompte() {
		return idCompte;
	}
	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}
	public int getIdDemande() {
		return idDemande;
	}
	public void setIdDemande(int idDemande) {
		this.idDemande = idDemande;
	}
	@Override
	public String toString() {
		return "CommentaireAide [idCommentaire=" + idCommentaire + ", txtCommentaire=" + txtCommentaire + ", idCompte="
				+ idCompte + ", idDemande=" + idDemande + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idCommentaire);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentaireAide other = (CommentaireAide) obj;
		return idCommentaire == other.idCommentaire;
	}
	
	

}
