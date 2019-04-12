package gui.controllers;

import core.Einstein;
import gui.MainWindow;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import gui.MovableStage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class for Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class EinsteinController extends MovableStage implements Initializable {
    
    @FXML private TextFlow einsteinTime;  
    @FXML private TableView<String[]> einsteinTableView;
    @FXML private TableColumn<String[], String> houseColumn;
    @FXML private TableColumn<String[], String> colourColumn;
    @FXML private TableColumn<String[], String> nationColumn;
    @FXML private TableColumn<String[], String> petColumn;
    @FXML private TableColumn<String[], String> drinkColumn;
    @FXML private TableColumn<String[], String> cigarColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }
    
    @FXML
    public void homeAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();
        makeMovable(newWindow, scene);
        newWindow.setScene(scene);
        newWindow.show();
    }
    
    @FXML
    public void einsteinSolveAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        Einstein activeModule = (Einstein) mainWindow.getModule(0);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        fillTableView(solution);

        // Calculation time.
        Text time = new Text("I calculated it in: " + activeModule.getTime() + "s");
        einsteinTime.getChildren().add(time);
    }

    private void fillTableView(String[][] solution) {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        einsteinTableView.setItems(data);

        this.setColumn(houseColumn, 0);
        this.setColumn(colourColumn, 1);
        this.setColumn(nationColumn, 2);
        this.setColumn(petColumn, 3);
        this.setColumn(drinkColumn, 4);
        this.setColumn(cigarColumn, 5);
    }

    private void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");
        });
    }

    @FXML
    void einsteinCleanAction(ActionEvent event) {
        einsteinTime.getChildren().clear();
        einsteinTableView.getItems().clear();
    }

}