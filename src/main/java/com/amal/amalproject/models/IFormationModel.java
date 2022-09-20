package com.amal.amalproject.models;

import java.sql.SQLException;
import java.util.List;

import com.amal.amalproject.entities.Formation;

import javafx.collections.ObservableList;

public interface IFormationModel {

	void addFormation(Formation formation);
	public void updateFormation(Formation formation);
	 List<Formation> getAllFormation();


}
