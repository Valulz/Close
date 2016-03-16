package com.valulz.close.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class InterfaceController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private SpinnerValueFactory.DoubleSpinnerValueFactory support;

    public void openFile(){
        Stage stage = (Stage) borderPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir Fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte","*.txt"));
        File f = fileChooser.showOpenDialog(stage);

        stage.setTitle(f.getName());
   }




}
