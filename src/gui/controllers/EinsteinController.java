package gui.controllers;

import core.Einstein;
import gui.MainWindow;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
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
public class EinsteinController implements Initializable {
    
    @FXML private TextFlow einsteinTime;  
    @FXML private TableView<String[]> einsteinTableView;
    @FXML private TableColumn<String[], String> colourColumn;
    @FXML private TableColumn<String[], String> nationColumn;
    @FXML private TableColumn<String[], String> petColumn;
    @FXML private TableColumn<String[], String> drinkColumn;
    @FXML private TableColumn<String[], String> cigarColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
    void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {   
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");        
        });
    }
    
    @FXML
    public void homeAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();
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
        
        // Filling in the TableView.
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        einsteinTableView.setItems(data);
        this.setColumn(colourColumn, 0);
        this.setColumn(nationColumn, 1);
        this.setColumn(petColumn, 2);
        this.setColumn(drinkColumn, 3);
        this.setColumn(cigarColumn, 4);
        
        // Calculation time.
        Text time = new Text("I calculated it in: " + activeModule.getTime() + "s");
        einsteinTime.getChildren().add(time);
    }
    
    @FXML
    void einsteinCleanAction(ActionEvent event) {
        einsteinTime.getChildren().clear();
        einsteinTableView.getItems().clear();
    }
}
