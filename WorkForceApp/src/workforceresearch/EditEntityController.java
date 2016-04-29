package workforceresearch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javafx.stage.Window;

public class EditEntityController implements Initializable {
	@FXML
	private Button deleteEntityButton, addFileRelationButton, removeRelationButton, addRelationButton, saveEditsButton,
			cancelEditsButton;

	@FXML
	private RadioButton beliefRadioButton;

	@FXML
	private ListView associationsListView;

	@FXML
	private TextField nameField, personField;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private ChoiceBox regionChoiceBox, metricChoiceBox, strengthChoiceBox, timeChoiceBox;

	private AppHandler appHandler;
	private Entity entity;
	private ObservableList<String> relationList;
	private HashMap<Integer, String> relationsMap;
	private List<String> filePathsList;
	private boolean isBelief;

	@FXML
	private void handleBeliefRadioButton(ActionEvent event) {
		isBelief = beliefRadioButton.isSelected();
	}

	@FXML
	private void handleAddFileRelationButton(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File file = chooser.showOpenDialog(new Stage());
		if(file != null)
		{
			String fullPath = file.getAbsolutePath();
			relationList.add(fullPath);
			filePathsList.add(fullPath);
			associationsListView.setItems(relationList);
	
		}
	}

	@FXML
	private void handleRemoveRelationButton(ActionEvent event) {

		String item = associationsListView.getSelectionModel().getSelectedItem().toString();
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
		for (Iterator<Map.Entry<Integer, String>> it = relationsMap.entrySet().iterator(); it.hasNext();) {
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/workforceresearch/associateEntity.fxml"));
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
		// TODO: edit the fact/belief
		if(!nameField.getText().isEmpty())
		{
			Stage stage = (Stage) saveEditsButton.getScene().getWindow();	
			stage.close();
			String region = checkNull(regionChoiceBox);
			String metric = checkNull(metricChoiceBox);
			String time = checkNull(timeChoiceBox);
			String strength = checkNull(strengthChoiceBox);
			appHandler.updateEntity(entity.getId(), nameField.getText(), region, metric, time, filePathsList, relationsMap,
					isBelief, personField.getText(), strength, descriptionTextArea.getText());
		}
	}

	@FXML
	private void handleCancelNewButton(ActionEvent event) {
		Stage stage = (Stage) cancelEditsButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleDeleteEntityButton(ActionEvent event) {
		Stage stage;
		Parent root = null;
		boolean fxmlFound = false;

		stage = new Stage();
//		try {
//			root = FXMLLoader.load(getClass().getResource("deleteEntityVerify.fxml"));
//			fxmlFound = true;
//		} catch (IOException ex) {
//			Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
//		}

		if (fxmlFound) {
			stage.setScene(new Scene(root));
			stage.setTitle("WARNING");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(deleteEntityButton.getScene().getWindow());
			stage.showAndWait();
		}
		appHandler.deleteEntity(entity.getId());
		Stage currentStage = (Stage) deleteEntityButton.getScene().getWindow();
		currentStage.close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		appHandler = new AppHandler();
		entity = new Entity();
		relationsMap = new HashMap<Integer, String>();
		filePathsList = new ArrayList<String>();
		relationList = FXCollections.observableArrayList();
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

		ObservableList<String> strengthList = FXCollections.observableArrayList();
		for (Strength s : appHandler.retrieveEnabledStrengths()) {
			strengthList.add(s.getValue());
		}
		strengthChoiceBox.setItems(strengthList);
	}

	public void setAppHandler(AppHandler ah) {
		appHandler = ah;
	}

	public void setEntity(Entity e) {
		this.entity = e;
		if(e != null){
			regionChoiceBox.setValue(e.getRegion().getValue());
			metricChoiceBox.setValue(e.getMetric().getValue());
			timeChoiceBox.setValue(e.getTimeperiod().getValue());
			strengthChoiceBox.setValue(e.getStrength().getValue());
			nameField.setText(e.getStatement());
			personField.setText(e.getPerson());
			descriptionTextArea.setText(e.getNote());
			if (e.isBelief()) {
				beliefRadioButton.setSelected(true);
			} else {
				beliefRadioButton.setSelected(false);
			}
			for(String s : e.getFilePaths()){
				relationList.add(s);
				filePathsList.add(s);
			}
			relationsMap = e.getRelatedEntities();
			for(Entry<Integer, String> entry : relationsMap.entrySet()) {
				relationList.add(entry.getValue());
			}
			associationsListView.setItems(relationList);
		}
	}
	
	private String checkNull(ChoiceBox c) {
		if (c.getValue() != null) {
			return c.getValue().toString();
		}
		return null;
	}

}