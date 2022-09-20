package com.amal.amalproject.entities;

import java.time.LocalDate;

import java.util.Objects;

import javafx.collections.ObservableList;

import java.sql.*;
public class Video extends Formation {
	    private int id_video;
	    private String url;
		
		
	
	    public Video( String url) {
			super();
			
			this.url = url;
		}


		public Video(int id_video, String url) {
			super();
			this.id_video = id_video;
			this.url = url;
		}


		public Video() {
			super();
			// TODO Auto-generated constructor stub
		}


		public Video(int id_formation, String theme, String descriptif, LocalDate date_debut, LocalDate dateFin,
				String nbr_jours, String Nbr_personnes, int id_compte) {
			super(id_formation, theme, descriptif, date_debut, dateFin, nbr_jours, Nbr_personnes, id_compte);
			// TODO Auto-generated constructor stub
		}


		public Video(int id_formation, String theme, String descriptif, LocalDate date_debut) {
			super(id_formation, theme, descriptif, date_debut);
			// TODO Auto-generated constructor stub
		}


		public Video(int id_formation, String theme, String descriptif) {
			super(id_formation, theme, descriptif);
			// TODO Auto-generated constructor stub
		}


		public Video(String theme, String descriptif, LocalDate date_debut, LocalDate dateFin, String nbr_jours,
				String Nbr_personnes) {
			super(theme, descriptif, date_debut, dateFin, nbr_jours, Nbr_personnes);
			// TODO Auto-generated constructor stub
		}


		public Video(String theme, String descriptif, LocalDate date_debut) {
			super(theme, descriptif, date_debut);
			// TODO Auto-generated constructor stub
		}


		public Video(String theme, String descriptif) {
			super(theme, descriptif);
			// TODO Auto-generated constructor stub
		}


		public int getId_video() {
			return id_video;
		}


		public void setId_video(int id_video) {
			this.id_video = id_video;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + Objects.hash(id_video, url);
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Video other = (Video) obj;
			return id_video == other.id_video && Objects.equals(url, other.url);
		}


		@Override
		public String toString() {
			return "Video [id_video=" + id_video + ", url=" + url + "]";
		}
	
	
		
	  


}
