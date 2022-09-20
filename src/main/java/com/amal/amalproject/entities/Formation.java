package com.amal.amalproject.entities;


import java.time.LocalDate;

import java.util.Objects;


public class Formation {
    private int id_formation;
    private String theme;
    private String descriptif;
    private LocalDate date_debut;
    private LocalDate dateFin;
    private String  nbr_jours;
    private String  Nbr_personnes;
   private int id_compte ;
    
    
    public Formation() {
    }
    
    public Formation(String theme, String descriptif) {
      
        this.theme = theme;
        this.descriptif = descriptif;
     
    }
    public Formation(String theme, String descriptif,LocalDate date_debut) {
        
        this.theme = theme;
        this.descriptif = descriptif;
        this.date_debut = date_debut;
     
    }
    public Formation(int id_formation, String theme, String descriptif) {
        this.id_formation = id_formation;
        this.theme = theme;
        this.descriptif = descriptif;
     
    }
    public Formation(int id_formation, String theme, String descriptif, LocalDate date_debut) {
        this.id_formation = id_formation;
        this.theme = theme;
        this.descriptif = descriptif;
        this.date_debut = date_debut;
    }


 

    
    
    public Formation(String theme, String descriptif, LocalDate date_debut, LocalDate dateFin, String nbr_jours,
			String Nbr_personnes) {
		super();
		this.theme = theme;
		this.descriptif = descriptif;
		this.date_debut = date_debut;
		this.dateFin = dateFin;
		this.nbr_jours = nbr_jours;
		this.Nbr_personnes = Nbr_personnes;
	}

	public Formation(int id_formation, String theme, String descriptif, LocalDate date_debut, LocalDate dateFin,
    		String nbr_jours, String Nbr_personnes, int id_compte) {
		super();
		this.id_formation = id_formation;
		this.theme = theme;
		this.descriptif = descriptif;
		this.date_debut = date_debut;
		this.dateFin = dateFin;
		this.nbr_jours = nbr_jours;
		this.Nbr_personnes = Nbr_personnes;
		this.id_compte = id_compte;
	}


	

	public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }



    public LocalDate getDate_publication() {
		return  date_debut;
	}

    public void setDate_publication(LocalDate localDate) {
		date_debut = localDate;
	}

    public LocalDate getDate_publicationf() {
		return  dateFin;
	}

    public void setDate_publicationf(LocalDate localDate) {
    	this.dateFin = localDate;
	}


	public String getNbr_jours() {
		return nbr_jours;
	}

	public void setNbr_jours(String nbr_jours) {
		this.nbr_jours = nbr_jours;
	}

	public String getNbr_personnes() {
		return Nbr_personnes;
	}

	public void setNbr_personnes(String Nbr_personnes) {
		this.Nbr_personnes = Nbr_personnes;
	}

	public int getId_compte() {
		return id_compte;
	}

	public void setId_compte(int id_compte) {
		this.id_compte = id_compte;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date_debut, dateFin, descriptif, id_formation, nbr_jours, Nbr_personnes, id_compte, theme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formation other = (Formation) obj;
		return Objects.equals(date_debut, other.date_debut) && Objects.equals(dateFin, other.dateFin)
				&& Objects.equals(descriptif, other.descriptif) && id_formation == other.id_formation
				&& Objects.equals(nbr_jours, other.nbr_jours) && Objects.equals(Nbr_personnes, other.Nbr_personnes)
				&& Objects.equals(id_compte, other.id_compte) && Objects.equals(theme, other.theme);
	}

	@Override
	public String toString() {
		return "Formation [id_formation=" + id_formation + ", theme=" + theme + ", descriptif=" + descriptif
				+ ", date_debut=" + date_debut + ", dateFin=" + dateFin + ", nbr_jours=" + nbr_jours + ", Nbr_personnes="
				+ Nbr_personnes + ", id_compte=" + id_compte + "]";
	}



  


}