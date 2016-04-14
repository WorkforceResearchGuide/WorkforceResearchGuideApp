/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workforceresearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
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
    private ChoiceBox regionDropdown, metricDropdown, timeDropdown;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private ListView relationListView;

    private AppHandler appHandler = new AppHandler();
    private List<Entity> selectedEntityList = new ArrayList<Entity>();
    private List<Entity> tempEntityList = new ArrayList<Entity>(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	ObservableList<String> regionList = FXCollections.observableArrayList();
		for(Region r : appHandler.retrieveAllRegions()){
			regionList.add(r.getValue());
		}
		regionDropdown.setItems(regionList);
		
		ObservableList<String> metricList = FXCollections.observableArrayList();
		for(Metric m : appHandler.retrieveAllMetrics()){
			metricList.add(m.getValue());
		}
		metricDropdown.setItems(metricList);
		
		ObservableList<String> timeList = FXCollections.observableArrayList();
		for(Timeperiod t : appHandler.retrieveAllTimeperiods()){
			timeList.add(t.getValue());
		}
		timeDropdown.setItems(timeList);

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
        String statement = relationListView.getSelectionModel().getSelectedItem().toString();
        for(Entity e : tempEntityList){
        	if(e.getStatement().equals(statement)){
        		selectedEntityList.add(e);
        	}
        }
    }
    
    @FXML
    private void handleSearchButton(ActionEvent event){
    	String region = checkNull(regionDropdown);
    	String metric = checkNull(metricDropdown);
    	String timeperiod = checkNull(timeDropdown);
    	ObservableList<String> entityList = FXCollections.observableArrayList();
    	tempEntityList = appHandler.searchEntity(searchField.getText(), region, metric, timeperiod);
    	for(Entity e : tempEntityList){
    		entityList.add(e.getStatement());
    	}
    	relationListView.setItems(entityList);
    }
    
    public void setAppHandler(AppHandler ah)
    {
        appHandler = ah;
    }
    
	public String checkNull(ChoiceBox c){
		if(c.getValue() != null){
			return c.getValue().toString();
		}
			return null;
	}
	
	public List<Entity> getEntityList(){
		return this.selectedEntityList;
	}
    
}
