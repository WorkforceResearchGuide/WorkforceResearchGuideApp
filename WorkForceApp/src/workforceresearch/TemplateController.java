package workforceresearch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TemplateController implements Initializable 
{
    @FXML
    private Button addRegionButton, deleteRegionButton, addMetricButton, deleteMetricButton, addTimeButton, deleteTimeButton, addStrengthButton, deleteStrengthButton, confirmButton;
    
    @FXML
    private ListView regionListView, metricListView, timeListView, strengthListView;
    
    private AppHandler appHandler;
    private ObservableList<String> regions,metrics,timeperiods,strengths;
    
    @FXML
    private void handleAddRegionButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addTemplateRegion.fxml")); 
        AddTemplateRegionController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<AddTemplateRegionController>getController();
            fxmlFound = true;
        }  
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Region");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addRegionButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleDeleteRegionButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteRegionVerify.fxml")); 
        DeleteRegionVerifyController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<DeleteRegionVerifyController>getController();
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
            stage.initOwner(deleteRegionButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleAddMetricButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addTemplateMetric.fxml")); 
        AddTemplateMetricController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<AddTemplateMetricController>getController();
            fxmlFound = true;
        }   
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Metric");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMetricButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleDeleteMetricButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteMetricVerify.fxml")); 
        DeleteMetricVerifyController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<DeleteMetricVerifyController>getController();
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
            stage.initOwner(deleteMetricButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleAddTimeButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addTemplateTime.fxml")); 
        AddTemplateTimeController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<AddTemplateTimeController>getController();
            fxmlFound = true;
        }  
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Time Period");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addTimeButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleDeleteTimeButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteTimeVerify.fxml")); 
        DeleteTimeVerifyController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<DeleteTimeVerifyController>getController();
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
            stage.initOwner(deleteTimeButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleAddStrengthButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addTemplateStrength.fxml")); 
        AddTemplateStrengthController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<AddTemplateStrengthController>getController();
            fxmlFound = true;
        }  
        catch (IOException ex) 
        {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fxmlFound)
        {
            stage.setScene(new Scene(root));
            stage.setTitle("Add a Strength Measurement");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addStrengthButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleDeleteStrengthButton(ActionEvent event)
    {
        Stage stage;
        Parent root = null;
        boolean fxmlFound = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteStrengthVerify.fxml")); 
        DeleteStrengthVerifyController control = null;
        
        stage = new Stage();
        try 
        {
            root = loader.load();
            control = loader.<DeleteStrengthVerifyController>getController();
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
            stage.initOwner(deleteStrengthButton.getScene().getWindow());
            control.setAppHandler(appHandler);
            stage.showAndWait();
            updateTemplateLists();
        }
    }
    
    @FXML
    private void handleConfirmButton(ActionEvent event)
    {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    	appHandler = new AppHandler();
    	updateTemplateLists();
    }
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
    //Populates the listviews, called on initialization and whenever an add/delete of an item occurs
    private void updateTemplateLists()
    {
    	regions = FXCollections.observableArrayList();
    	metrics = FXCollections.observableArrayList();
    	timeperiods = FXCollections.observableArrayList();
    	strengths = FXCollections.observableArrayList();
    	
    	for(Region r: appHandler.retrieveAllRegions())
    	{
    		regions.add(r.getValue());
    	}
    	
    	for(Metric m: appHandler.retrieveAllMetrics())
    	{
    		metrics.add(m.getValue());
    	}
    	
    	for(Timeperiod t: appHandler.retrieveAllTimeperiods())
    	{
    		timeperiods.add(t.getValue());
    	}
    	
    	for(Strength s: appHandler.retrieveAllStrengths())
    	{
    		strengths.add(s.getValue());
    	}
    	
    	regionListView.setItems(regions);
    	metricListView.setItems(metrics);
    	timeListView.setItems(timeperiods);
    	strengthListView.setItems(strengths);
    	
    }
}