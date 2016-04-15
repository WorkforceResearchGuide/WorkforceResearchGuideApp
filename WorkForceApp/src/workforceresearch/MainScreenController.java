/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workforceresearch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */
public class MainScreenController implements Initializable {
    
	private AppHandler appHandler;
    private boolean entitySelected; //Should return true if the user has selected a fact/belief from the list, false otherwise
    private String currentRegion,currentMetric,currentTime;
    private ObservableList<String> entityStrings,regionStrings,metricStrings,timeperiodStrings,strengthStrings, associationStrings;
    private ObservableList<Entity> currentEntities;
    private Entity tempEntity;
    
    @FXML
    private Label label;
    
    @FXML
    private Button addButton,searchButton,editButton,templateButton,batchUploadButton,folderScanButton;
    
    @FXML
    private ChoiceBox regionChoiceBox,metricChoiceBox,timeChoiceBox;
   
    @FXML
    private ListView<Entity> factbeliefView;
    
    @FXML
    private ListView associationsView;
    
    @FXML
    private TextField searchField, nameField, regionField, metricField, timePeriodField, personField, strengthField;
    
    
    @FXML
    private void handleSearchButton(ActionEvent event) 
    {
    	nameField.setEditable(true);
	    regionField.setEditable(true);
	    metricField.setEditable(true);
	    timePeriodField.setEditable(true);
	    personField.setEditable(true);
	    strengthField.setEditable(true);
	    
	    nameField.clear();
    	regionField.clear();
	   	metricField.clear();
	   	timePeriodField.clear();
	   	personField.clear();
	   	strengthField.clear();
	   	associationStrings = FXCollections.observableArrayList();
	   	associationsView.setItems(associationStrings);
	   	tempEntity = null;
    	
	    nameField.setEditable(false);
	   	regionField.setEditable(false);
	   	metricField.setEditable(false);
	   	timePeriodField.setEditable(false);
	   	personField.setEditable(false);
	    strengthField.setEditable(false);
    		
    	//entityStrings = FXCollections.observableArrayList();
    	currentEntities = FXCollections.observableArrayList();
    		
        for(Entity e: appHandler.searchEntity(searchField.getText(),currentRegion,currentMetric,currentTime))
        {
        	//entityStrings.add(e.getStatement());
        	currentEntities.add(e);
        }
        	
        factbeliefView.setItems(currentEntities);
    	 
    }
    
    
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
            editControl.setEntity(tempEntity);
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
            resetChoiceBoxes();
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
        if(selectedDirectory != null){
            appHandler.addEntityFolderScan(selectedDirectory.getAbsolutePath());
        }
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
    	appHandler = new AppHandler();
    	
    	currentRegion = "";
    	currentMetric = "";
    	currentTime = "";
    	
    	tempEntity = null;
    	
    	resetChoiceBoxes();
    	
    	factbeliefView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Entity>() {

    	    @Override
    	    public void changed(ObservableValue<? extends Entity> observable, Entity oldValue, Entity newValue) 
    	    {
    	    	if(newValue!=null)
    	    	{
    	    		associationStrings = FXCollections.observableArrayList();
    	    		
    	    		nameField.setEditable(true);
    	    		regionField.setEditable(true);
    	    		metricField.setEditable(true);
    	    		timePeriodField.setEditable(true);
    	    		personField.setEditable(true);
    	    		strengthField.setEditable(true);
    	    	
    	    		tempEntity = appHandler.searchEntity(newValue.getId());
    	    		
    	    		nameField.setText(tempEntity.getStatement());
    	    		//if(newValue.getRegion())
    	    		regionField.setText(tempEntity.getRegion().getValue());
    	    		metricField.setText(tempEntity.getMetric().getValue());
    	    		timePeriodField.setText(tempEntity.getTimeperiod().getValue());
    	    		personField.setText(tempEntity.getPerson());
    	    		System.out.println(tempEntity.isBelief());
    	    		if(tempEntity.isBelief())
    	    			strengthField.setText(tempEntity.getStrength().getValue());
    	    		
    	    		for(int e: tempEntity.getRelatedEntities().keySet())
    	    			associationStrings.add(tempEntity.getRelatedEntities().get(e));
    	    		for(String a: tempEntity.getFilePaths())
    	    			associationStrings.add(a);
    	    		
    	    		associationsView.setItems(associationStrings);
    	    	
    	    		nameField.setEditable(false);
    	    		regionField.setEditable(false);
    	    		metricField.setEditable(false);
    	    		timePeriodField.setEditable(false);
    	    		personField.setEditable(false);
    	    		strengthField.setEditable(false);
    	    
    	    	}
    	    }
    	});
    	
    	regionChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
    		
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    		{
    			currentRegion = newValue;
    		}
    	});
    	
    	metricChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
    		
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    		{
    			currentMetric = newValue;
    		}
    	});
    	
    	timeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
    		
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    		{
    			currentTime = newValue;
    		}
    	});
    	
    	
    	
    	//TODO: Do we want to populate the list with items upon initialization?
    	/*entities = FXCollections.observableArrayList();
    	
    	for(Entity e: appHandler.retrieveAllEntities())
    	{
    		entities.add(e.getStatement());
    	}*/
    	
    }    
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
    private void resetChoiceBoxes()
    {
    	regionStrings = FXCollections.observableArrayList();
    	metricStrings = FXCollections.observableArrayList();
    	timeperiodStrings = FXCollections.observableArrayList();
    	regionStrings.add("");
    	metricStrings.add("");
    	timeperiodStrings.add("");
    	
    	for(Region r: appHandler.retrieveAllRegions())
    	{
    		regionStrings.add(r.getValue());
    	}
    	
    	for(Metric m: appHandler.retrieveAllMetrics())
    	{
    		metricStrings.add(m.getValue());
    	}
    	
    	for(Timeperiod t: appHandler.retrieveAllTimeperiods())
    	{
    		timeperiodStrings.add(t.getValue());
    	}
    	
    	regionChoiceBox.setItems(regionStrings);
    	metricChoiceBox.setItems(metricStrings);
    	timeChoiceBox.setItems(timeperiodStrings);
    }
    
}
