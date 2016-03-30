package com.valulz.close.controller;

import com.valulz.close.model.CloseParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class control the scene Interface.fxml.
 */
public class InterfaceController {

    @FXML
    private Spinner spinner;

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

        if(this.file != null){
            fileChooser.setInitialDirectory(this.file.getParentFile());
        }

        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            this.file = file;
            stage.setTitle(file.getName());
            btnExecuteClose.setDisable(false);
        }

   }

    /**
     * Execute the Close algorithm on the file chosen by the user.
     */
    public void executeClose() {
        if(file == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez préciser un fichier pour executer l'algorithme");
            alert.showAndWait()
                .filter(response -> response == ButtonType.OK);

            return;
        }

        txtDisplay.setText("Execution de l'algorithme ....");

        CloseParser closeParser = new CloseParser();

        String result;
        try{
            result = closeParser.executeClose(file, txtSupport.getValue());

            txtDisplay.setText(result);
            btnSaveResult.setDisable(false);
        } catch(IllegalArgumentException ex){
            txtDisplay.setText("Erreur lors de l'exécution de l'algorithme Close");
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait().filter(response -> response == ButtonType.OK);
        }

    }

    /**
     * Save the Result present in the TextArea into a file choose by the user.
     */
    public void saveResult() {

        if(txtDisplay.getText().isEmpty()){
            return;
        }

        Stage stage = (Stage) borderPane.getScene().getWindow();

        String results = txtDisplay.getText();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Texte", "*.txt"));
        File fileSave = fileChooser.showSaveDialog(stage);

        if(fileSave != null){
            try {
                FileWriter writer = new FileWriter(fileSave);
                writer.write(results);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
