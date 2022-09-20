package com.amal.amalproject.utils;

import java.io.IOException;

import com.amal.amalproject.MainApplication;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Navigate {

public static FXMLLoader changerScene( Event event ,String url,String titre) throws IOException {
	 FXMLLoader loader =new FXMLLoader(MainApplication.class.getResource(url));
	Parent viewParent=loader.load();
	//Parent viewParent = FXMLLoader.load(MainApplication.class.getResource(url));
	
	Scene viewScene = new Scene(viewParent);
    /*Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();*/
    Stage window = new Stage();
    window.setScene(viewScene);
    window.show();
    window.setTitle(titre);
    return loader;
    
}	
}
