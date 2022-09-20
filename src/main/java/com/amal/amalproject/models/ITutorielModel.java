package com.amal.amalproject.models;

import java.util.List;

import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.Video;

public interface ITutorielModel {
	
	 
	void addTutoriel(Video video);
	
	 List<Video> getAllVideo();


}
