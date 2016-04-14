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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEntityController implements Initializable {
	private String name, country, metric, timeperiod, person, strength, note;
	private List<String> filePaths;
	private HashMap<Integer, String> relatedEntities;
	private boolean isBelief;

	@FXML
	private Button addRelationButton, addFileRelationButton, removeRelationButton, saveNewFBButton, cancelAddNewButton;

	@FXML
	private ListView associationsListView;

	@FXML
	private RadioButton beliefRadioButton;

	@FXML
	private TextField nameField, personField;

	@FXML
	private ChoiceBox regionChoiceBox, metricChoiceBox, strengthChoiceBox, timeChoiceBox;

	@FXML
	private TextArea descriptionTextArea;

	private AppHandler appHandler;
	private ObservableList<String> relationList;
	private List<String> filePathsList = new ArrayList<String>();

	@FXML
	private void handleBeliefRadioButton(ActionEvent event) {
		isBelief = beliefRadioButton.isSelected();
	}

	@FXML
	private void handleAddFileRelationButton(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		// FileChooser.ExtensionFilter extFilter = new
		// FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		// chooser.getExtensionFilters().add(extFilter);
		File file = chooser.showOpenDialog(new Stage());
		String fullPath = file.getAbsolutePath();
		relationList.add(fullPath);
		filePathsList.add(fullPath);
		associationsListView.setItems(relationList);
		System.out.println(fullPath);
	}

	@FXML
	private void handleRemoveRelationButton(ActionEvent event) {

		String item = associationsListView.getSelectionModel().getSelectedItem().toString();
		for(int i = 0; i < filePathsList.size(); i++){
			if(item.equals(filePathsList.get(i))){
				filePathsList.remove(i);
			}
		}
		for(int i = 0; i < relationList.size(); i++){
			if(item.equals(relationList.get(i))){
				relationList.remove(i);
			}
		}
		associationsListView.getItems().remove(item);
	}

	@FXML
	private void handleAddRelationButton(ActionEvent event) {
		Stage stage;
		Parent root = null;
		boolean fxmlFound = false;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("associateEntity.fxml"));
		AssociateEntityController associateControl = null;

		stage = new Stage();
		try {
			root = loader.load();
			associateControl = loader.<AssociateEntityController> getController();
			fxmlFound = true;
		} catch (IOException ex) {
			Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (fxmlFound) {
			stage.setScene(new Scene(root));
			stage.setTitle("Associate a Fact/Belief");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(addRelationButton.getScene().getWindow());
			associateControl.setAppHandler(appHandler);
			stage.showAndWait();
		}
	}

	@FXML
	private void handleSaveNewFBButton(ActionEvent event) {
		// CHange HashMap
		HashMap<Integer, String> t = new HashMap<Integer, String>();
		t.put(1, "a");
		String region = checkNull(regionChoiceBox);
		String metric = checkNull(metricChoiceBox);
		String time = checkNull(timeChoiceBox);
		String strength = checkNull(strengthChoiceBox);
		appHandler.addEntity(nameField.getText(), region, metric, time, filePathsList, t, isBelief,
				personField.getText(), strength, descriptionTextArea.getText());
		for( Entity e : appHandler.retrieveAllEntities()){
			System.out.println(e.getStatement());
		}
		Stage stage = (Stage) saveNewFBButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleCancelNewButton(ActionEvent event) {
		Stage stage = (Stage) cancelAddNewButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// throw new UnsupportedOperationException("Not supported yet."); //To
		// change body of generated methods, choose Tools | Templates.
		name = "";
		country = "";
		metric = "";
		timeperiod = "";
		person = "";
		strength = "";
		note = "";
		filePaths = new ArrayList<String>();
		relatedEntities = new HashMap<Integer, String>();
		isBelief = false;
		relationList = FXCollections.observableArrayList();
		appHandler = new AppHandler();
		ObservableList<String> regionList = FXCollections.observableArrayList();
		for(Region r : appHandler.retrieveAllRegions()){
			regionList.add(r.getValue());
		}
		regionChoiceBox.setItems(regionList);
		
		ObservableList<String> metricList = FXCollections.observableArrayList();
		for(Metric m : appHandler.retrieveAllMetrics()){
			metricList.add(m.getValue());
		}
		metricChoiceBox.setItems(metricList);
		
		ObservableList<String> timeList = FXCollections.observableArrayList();
		for(Timeperiod t : appHandler.retrieveAllTimeperiods()){
			timeList.add(t.getValue());
		}
		timeChoiceBox.setItems(timeList);
		
		ObservableList<String> strengthList = FXCollections.observableArrayList();
		for(Strength s : appHandler.retrieveAllStrengths()){
			strengthList.add(s.getValue());
		}
		strengthChoiceBox.setItems(strengthList);
	}

	public void setAppHandler(AppHandler ah) {
		appHandler = ah;
	}

	public String checkNull(ChoiceBox c){
		if(c.getValue() != null){
			return c.getValue().toString();
		}
			return null;
	}
}
