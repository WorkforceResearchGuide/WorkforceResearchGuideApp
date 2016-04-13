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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */
public class MainScreenController implements Initializable {
    
    private boolean entitySelected; //Should return true if the user has selected a fact/belief from the list, false otherwise
    
    @FXML
    private Label label;
    
    @FXML
    private Button addButton,searchButton,editButton,templateButton,batchUploadButton,folderScanButton;
    
    @FXML
    private ChoiceBox regionChoiceBox,metricChoiceBox,timeChoiceBox;
   
    
    @FXML
    private void handleSearchButton(ActionEvent event) {
     
    }
    
    AppHandler appHandler;
    
    @FXML
    private void handleAddButton(ActionEvent event)
    {
        //System.out.println("This takes us to add screen");
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addEntity.fxml")); 
        AddEntityController addControl = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            addControl = loader.<AddEntityController>getController();
            fxmlFound = true; 
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Fact/Belief");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            addControl.setAppHandler(appHandler);
            stage.showAndWait();
            
            //System.out.println("BUTS");
        }
        
    }
    
    @FXML
    private void handleEditButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editEntity.fxml")); 
        EditEntityController editControl = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            editControl = loader.<EditEntityController>getController();
            fxmlFound = true; 
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Fact/Belief");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            editControl.setAppHandler(appHandler);
            stage.showAndWait();
            
        }
        
    }
    
    @FXML
    private void handleBatchUploadButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("batchUploadResults.fxml")); 
        BatchUploadResultsController batchControl = null;
        
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        chooser.getExtensionFilters().add(extFilter);
        
        File file = chooser.showOpenDialog(new Stage());
        
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            batchControl = loader.<BatchUploadResultsController>getController();
            fxmlFound = true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Batch Upload Results");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(batchUploadButton.getScene().getWindow());
            batchControl.setAppHandler(appHandler);
            stage.showAndWait();
        }
    }
    
    @FXML
    private void handleTemplateButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateView.fxml")); 
        TemplateController templateControl = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            templateControl = loader.<TemplateController>getController();
            fxmlFound = true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Template Viewer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(templateButton.getScene().getWindow());
            templateControl.setAppHandler(appHandler);
            stage.showAndWait();
        }
    }
    
    @FXML
    private void handleFolderScanButton(ActionEvent event)
    {   
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("folderScanResults.fxml")); 
        FolderScanResultsController scanControl = null;
        
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(new Stage());
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            scanControl = loader.<FolderScanResultsController>getController();
            fxmlFound = true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Folder Scan Results");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(folderScanButton.getScene().getWindow());
            scanControl.setAppHandler(appHandler);
            stage.showAndWait();
        }
    }
        
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
}
