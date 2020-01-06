package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColunnId;
	
	@FXML
	private TableColumn<Department, String> tableColunnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> objList;
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		creatDialogForm(obj,  "/gui/DepartmentForm.fxml", parentStage);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	private void initializeNodes() {
		tableColunnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
	
		/*Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());*/
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("service was null");
		}
		List<Department> list = service.findAll();
		objList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(objList);
	}
	
	private void creatDialogForm(Department obj,String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartent(obj);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} 
		catch (IOException e) {
			Alerts.showAlert("IO Exception", null, e.getMessage(), AlertType.ERROR);
		}
	}
}
