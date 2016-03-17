package com.valulz.close.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * This class control the scene Interface.fxml.
 */
public class InterfaceController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private SpinnerValueFactory.DoubleSpinnerValueFactory txtSupport;

    @FXML
    private TextArea txtDisplay;

    @FXML
    private Button btnSaveResult;

    @FXML
    private Button btnExecuteClose;

    private File file;

    /**
     * Allow user to choose a file to execute the close algorithm on it.
     *
     * Change the title of the stage, to the file name.
     */
    public void openFile(){
        Stage stage = (Stage) borderPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir Fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte","*.txt"));

        this.file = fileChooser.showOpenDialog(stage);

        stage.setTitle(file.getName());
        btnExecuteClose.setDisable(false);
   }

    /**
     * Execute the Close algorithm on the file chosen by the user.
     */
    public void executeClose() {

    }

    /**
     * Save the Result present in the TextArea into a file choose by the user.
     */
    public void saveResult() {

    }
}
