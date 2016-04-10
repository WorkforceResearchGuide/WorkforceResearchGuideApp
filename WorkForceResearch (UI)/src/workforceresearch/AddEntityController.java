/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workforceresearch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEntityController implements Initializable 
{
    @FXML
    private Button addRelationButton,addFileRelationButton,removeRelationButton,saveNewFBButton,cancelAddNewButton;
    
    @FXML
    private ListView associationsListView;
    
    private AppHandler appHandler;
    
    @FXML
    private void handleBeliefRadioButton(ActionEvent event)
    {
        
    }
    
    @FXML
    private void handleAddFileRelationButton(ActionEvent event)
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        //chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(new Stage());
    }
    
    @FXML
    private void handleRemoveRelationButton(ActionEvent event)
    {
        //gets the selected item
        associationsListView.getSelectionModel().getSelectedItem();
        
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteAssociationVerify.fxml")); 
        DeleteAssociationVerifyController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<DeleteAssociationVerifyController>getController();
            fxmlFound = true;
        }  
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("WARNING");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(removeRelationButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
        }
    }
    
    @FXML
    private void handleAddRelationButton (ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("associateEntity.fxml")); 
        AssociateEntityController associateControl = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            associateControl = loader.<AssociateEntityController>getController();
            fxmlFound = true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Associate a Fact/Belief");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addRelationButton.getScene().getWindow());
            associateControl.setAppHandler(appHandler);
            stage.showAndWait();
        }
    }
    
    @FXML
    private void handleSaveNewFBButton(ActionEvent event)
    {
        //TODO: Handle adding fact/belief
        Stage stage = (Stage) saveNewFBButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCancelNewButton(ActionEvent event)
    {
        Stage stage = (Stage) cancelAddNewButton.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
}
