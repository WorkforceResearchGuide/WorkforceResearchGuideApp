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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class AddTemplateMetricController implements Initializable {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField newItemField;
    @FXML
    private Label detailsLabel;
    @FXML
    private HBox actionParent;
    @FXML
    private Button addItemButton;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox okParent;
    
    private AppHandler appHandler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddItemButton(ActionEvent event) {
		if (!(newItemField.getText().equals(""))) {
			appHandler.addMetric(newItemField.getText());
			Stage stage = (Stage) addItemButton.getScene().getWindow();
			stage.close();
		}
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
}
