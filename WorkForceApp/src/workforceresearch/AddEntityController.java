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
import java.util.Iterator;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEntityController implements Initializable {

	@FXML
	private Button addRelationButton, addFileRelationButton,
			removeRelationButton, saveNewFBButton, cancelAddNewButton;

	@FXML
	private ListView<String> associationsListView;

	@FXML
	private RadioButton beliefRadioButton;

	@FXML
	private TextField nameField, personField;

	@FXML
	private ChoiceBox<String> regionChoiceBox, metricChoiceBox,
			strengthChoiceBox, timeChoiceBox;

	@FXML
	private TextArea descriptionTextArea;

	private AppHandler appHandler;
	private ObservableList<String> relationList;
	private List<String> filePathsList;
	private boolean isBelief;
	private HashMap<Integer, String> relationsMap;

	@FXML
	private void handleBeliefRadioButton(ActionEvent event) {
		isBelief = beliefRadioButton.isSelected();
	}

	@FXML
	private void handleAddFileRelationButton(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File file = chooser.showOpenDialog(new Stage());
		String fullPath = file.getAbsolutePath();
		relationList.add(fullPath);
		filePathsList.add(fullPath);
		associationsListView.setItems(relationList);
	}

	@FXML
	private void handleRemoveRelationButton(ActionEvent event) {

		String item = associationsListView.getSelectionModel()
				.getSelectedItem().toString();
		for (int i = 0; i < filePathsList.size(); i++) {
			if (item.equals(filePathsList.get(i))) {
				filePathsList.remove(i);
			}
		}
		for (int i = 0; i < relationList.size(); i++) {
			if (item.equals(relationList.get(i))) {
				relationList.remove(i);
			}
		}
		for (Iterator<Map.Entry<Integer, String>> it = relationsMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Integer, String> entry = it.next();
			if (entry.getValue().equals(item)) {
				it.remove();
			}
		}
		associationsListView.getItems().remove(item);
	}

	@FXML
	private void handleAddRelationButton(ActionEvent event) {
		Stage stage;
		Parent root = null;
		boolean fxmlFound = false;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"associateEntity.fxml"));
		AssociateEntityController associateControl = null;

		stage = new Stage();
		try {
			root = loader.load();
			associateControl = loader
					.<AssociateEntityController> getController();
			fxmlFound = true;
		} catch (IOException ex) {
			Logger.getLogger(MainScreenController.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		if (fxmlFound) {
			stage.setScene(new Scene(root));
			stage.setTitle("Associate a Fact/Belief");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(addRelationButton.getScene().getWindow());
			associateControl.setAppHandler(appHandler);
			stage.showAndWait();
			List<Entity> tempRelationList = new ArrayList<Entity>();
			tempRelationList = associateControl.getEntityList();
			for (Entity e : tempRelationList) {
				relationList.add(e.getStatement());
				relationsMap.put(e.getId(), e.getStatement());
			}
			associationsListView.setItems(relationList);
		}
	}

	@FXML
	private void handleSaveNewFBButton(ActionEvent event) {
		String statement = nameField.getText();
		String region = checkNull(regionChoiceBox);
		String metric = checkNull(metricChoiceBox);
		String time = checkNull(timeChoiceBox);
		String strength = checkNull(strengthChoiceBox);
		
		//Check the required fields: statement, region, metric and time 
		if(!(statement.equals(null) || region.equals(null) || metric.equals(null) || time.equals(null)))
		{				
			appHandler.addEntity(statement, region, metric, time,
							filePathsList, relationsMap, isBelief, personField.getText(),
								strength, descriptionTextArea.getText());
			Stage stage = (Stage) saveNewFBButton.getScene().getWindow();
			stage.close();			
		}
	}

	@FXML
	private void handleCancelNewButton(ActionEvent event) {
		Stage stage = (Stage) cancelAddNewButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filePathsList = new ArrayList<String>();
		relationsMap = new HashMap<Integer, String>();
		relationList = FXCollections.observableArrayList();
		appHandler = new AppHandler();
		ObservableList<String> regionList = FXCollections.observableArrayList();
		for (Region r : appHandler.retrieveEnabledRegions()) {
			regionList.add(r.getValue());
		}
		regionChoiceBox.setItems(regionList);

		ObservableList<String> metricList = FXCollections.observableArrayList();
		for (Metric m : appHandler.retrieveEnabledMetrics()) {
			metricList.add(m.getValue());
		}
		metricChoiceBox.setItems(metricList);

		ObservableList<String> timeList = FXCollections.observableArrayList();
		for (Timeperiod t : appHandler.retrieveEnabledTimeperiods()) {
			timeList.add(t.getValue());
		}
		timeChoiceBox.setItems(timeList);

		ObservableList<String> strengthList = FXCollections
				.observableArrayList();
		for (Strength s : appHandler.retrieveEnabledStrengths()) {
			strengthList.add(s.getValue());
		}
		strengthChoiceBox.setItems(strengthList);
	}

	public void setAppHandler(AppHandler ah) {
		appHandler = ah;
	}

	public String checkNull(ChoiceBox<String> c) {
		if (c.getValue() != null) {
			return c.getValue().toString();
		}
		return null;
	}
	
}
