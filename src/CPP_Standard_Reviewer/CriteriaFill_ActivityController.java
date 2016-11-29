/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import static CPP_Standard_Reviewer.FileSave_ActivityController.fList;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Faintinger
 */
//&p-CriteriaFill
public class CriteriaFill_ActivityController implements Initializable {
    public static int iArrCriteria [];
    public static ArrayList<File> fList;
    /**
     * Initializes the controller class.
     */
    //&i
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fList = FXMLDocumentController.fList;
        //for (int i = 0; i < fList.size(); i++) {
        //    System.out.println(fList.get(i).getName());
        //}
    }
    //&i
    private Stage getStage(ActionEvent event) {
        Button btnButton = (Button) event.getSource();
        Stage stStage = (Stage) btnButton.getScene().getWindow();
        return stStage;
    }
    //&i
    private int toInt(String sAux) {
        try {
            int iAux = Integer.parseInt(sAux);
            return iAux;
        }catch(Exception ex) {
            return 0;
        }
    }
    //&i
    private Boolean validateFields(ActionEvent event) {
        Stage stStage = getStage(event);
        TextField tfCampo = (TextField) stStage.getScene().lookup("#FileName_Criteria");
        int iFName = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Variable_Criteria");
        int iVariable = toInt(tfCampo.getText());
        //tfCampo = (TextField) stStage.getScene().lookup("#Constant_Criteria");
        //int iConstant = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Program_Criteria");
        int iProgram = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#I_Comments_Criteria");
        int iIComments = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Library_Criteria");
        int iLibrary = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#C_Functions_Criteria");
        int iCFun = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#F_Header_Criteria");
        int iFHeader = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Indentation_Criteria");
        int iIndentation = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Comments_Criteria");
        int iComments = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Instructions_Criteria");
        int iInstructions = toInt(tfCampo.getText());
        tfCampo = (TextField) stStage.getScene().lookup("#Blank_Criteria");
        int iBlank = toInt(tfCampo.getText());
        int iArr[] = {iFName, iVariable, /*iConstant*/0, iProgram, iIComments
                , iLibrary, iCFun, iFHeader, iIndentation, iComments 
                , iInstructions, iBlank};
        iArrCriteria = iArr;
        return(100 == (iFName + iVariable + /*iConstant*/0 + iProgram + iIComments
                + iLibrary + iCFun + iFHeader + iIndentation + iComments 
                + iInstructions + iBlank));
    }
    //&i
    @FXML
    public void Next(ActionEvent event) throws Exception {
        if(validateFields(event)) {
            Parent root = FXMLLoader.load(getClass().getResource("FileSave_Activity.fxml"));
            Scene scene = new Scene(root);
            Button btnNext = (Button) event.getSource();
            Stage stage = (Stage) btnNext.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } else {
            //JOptionPane.showMessageDialog(null, 
            //        "The amount of the fields do not sum a total of 100 points.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CPP Analizer");
            alert.setHeaderText(null);
            alert.setContentText("The amount of the fields do not sum a total of 100 points.");
            alert.showAndWait();
        }
        
    }
    //&i
    @FXML
    public void Back(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Button btnNext = (Button) event.getSource();
        Stage stage = (Stage) btnNext.getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
    }
    
}
