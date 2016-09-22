/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Faintinger
 */
public class FileSave_ActivityController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void Report(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Results_Activity.fxml"));
        Scene scene = new Scene(root);
        Button btnNext = (Button) event.getSource();
        Stage stage = (Stage) btnNext.getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void Back(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CriteriaFill_Activity.fxml"));
        Scene scene = new Scene(root);
        Button btnNext = (Button) event.getSource();
        Stage stage = (Stage) btnNext.getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
    }
    
}