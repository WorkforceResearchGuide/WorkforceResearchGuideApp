/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workforceresearch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class AssociateEntityController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;

    private AppHandler appHandler;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleConfirmButton(ActionEvent event) {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
}
