package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
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
}
