/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Faintinger
 */

public class FXMLDocumentController implements Initializable {
    public ArrayList<File> fList = new ArrayList<File>();
    
    @FXML
    private Label lLabel;
    
    private Stage getStage(ActionEvent event) {
        Button btnButton = (Button) event.getSource();
        Stage stStage = (Stage) btnButton.getScene().getWindow();
        return stStage;
    }
    
    private void setPanel(Stage stStage){
        ScrollPane spAux = (ScrollPane) stStage.getScene().lookup("#Panel");
        ListView<String> lvContent = new ListView();
        ArrayList<String> sListFNames = new ArrayList<String>();
        for (int i = 0; i < fList.size(); i++)
            sListFNames.add(fList.get(i).getName());
        ObservableList<String> obList = FXCollections.observableArrayList(sListFNames);
        lvContent.setItems(obList);
        VBox vbContent = new VBox();
        vbContent.getChildren().addAll(lvContent);
        VBox.setVgrow(lvContent, Priority.ALWAYS);
        vbContent.setPrefWidth(spAux.getWidth());
        spAux.setContent(vbContent);
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        lLabel.setText("Hello World!");
    }
    
    @FXML
    private void deleteSelectFile(ActionEvent event) {
        Stage stStage = getStage(event);
        ScrollPane spAux = (ScrollPane) stStage.getScene().lookup("#Panel");
        VBox vbContent = (VBox) spAux.getContent();
        if(vbContent != null) {
            ListView<String> lvContent = (ListView<String>) vbContent.getChildren().get(0);
            List<File> fListAux = new ArrayList<File>();
            String sFile = lvContent.getSelectionModel().getSelectedItem();
            for(int i = 0; i < fList.size(); i++) {
                if(fList.get(i).getName().equals(sFile)) {
                    System.out.println(fList.get(i).getName());
                    fList.remove(i);
                    break;
                }
            }
            setPanel(stStage);
        } 
    }
    
    @FXML
    private void fileChoose(ActionEvent event) {
        /*
        Button btnChoose = (Button) event.getSource();
        Stage stStage = (Stage) btnChoose.getScene().getWindow();
        */
        Stage stStage = getStage(event);
        FileChooser fcFileChooser = new FileChooser();
        fList = new ArrayList<File>(fcFileChooser.showOpenMultipleDialog(stStage));
        setPanel(stStage);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    public void nextScene(ActionEvent event) throws Exception {
        if(fList.size() > 0) {
            Parent pRoot = FXMLLoader.load(getClass().getResource("CriteriaFill_Activity.fxml"));
            Scene scene = new Scene(pRoot);
            Button btnNext = (Button) event.getSource();
            Stage stStage = (Stage) btnNext.getScene().getWindow();

            stStage.setScene(scene);
            stStage.show();
        } else {
            JOptionPane.showMessageDialog(null, 
                    "The are no files selected...");
        }
        
    }
    
}
