package com.amal.amalproject.entities;
import java.sql.Date;
public class MappedCandidature {
private int id_emploi;
private int id_compte;
private Date date_postule;
private String url_cv;
private String niveau;
private String message;
private String ref_emploi;

public String getNiveau() {
	return niveau;
}
public void setNiveau(String niveau) {
	this.niveau = niveau;
}
public int getId_emploi() {
	return id_emploi;
}
public void setId_emploi(int id_emploi) {
	this.id_emploi = id_emploi;
}
public int getId_compte() {
	return id_compte;
}
public void setId_compte(int id_compte) {
	this.id_compte = id_compte;
}
public Date getDate_postule() {
	return date_postule;
}
public void setDate_postule(Date date_postule) {
	this.date_postule = date_postule;
}
public String getUrl_cv() {
	return url_cv;
}
public void setUrl_cv(String url_cv) {
	this.url_cv = url_cv;
}

public String getMessage() {
	return message;
} 
public void setMessage(String message) {
	this.message = message;
}

public String getRef_emploi() {
	return ref_emploi;
}
public void setRef_emploi(String ref_emploi) {
	this.ref_emploi = ref_emploi;
}


public MappedCandidature(int id_emploi, int id_compte, Date date_postule, String url_cv, String niveau, String message,
		String ref_emploi) {
	super();
	this.id_emploi = id_emploi;
	this.id_compte = id_compte;
	this.date_postule = date_postule;
	this.url_cv = url_cv;
	this.niveau = niveau;
	this.message = message;
	this.ref_emploi = ref_emploi;
}
public MappedCandidature(int id_emploi, int id_compte, Date date_postule, String url_cv, String niveau) {
	
	this.id_emploi = id_emploi;
	this.id_compte = id_compte;
	this.date_postule = date_postule;
	this.url_cv = url_cv;
	this.niveau = niveau;
}
public MappedCandidature(int id_emploi, int id_compte, Date date_postule, String url_cv, String niveau, String message) {
	super();
	this.id_emploi = id_emploi;
	this.id_compte = id_compte;
	this.date_postule = date_postule;
	this.url_cv = url_cv;
	this.niveau = niveau;
	this.message = message;
}
@Override
public String toString() {
	return "Candidature [id_emploi=" + id_emploi + ", id_compte=" + id_compte + ", date_postule=" + date_postule
			+ ", url_cv=" + url_cv + ", niveau=" + niveau + ", message=" + message + "]";
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id_compte;
	result = prime * result + id_emploi;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	MappedCandidature other = (MappedCandidature) obj;
	if (id_compte != other.id_compte)
		return false;
	if (id_emploi != other.id_emploi)
		return false;
	return true;
}
 
}
