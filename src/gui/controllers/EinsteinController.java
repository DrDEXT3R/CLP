package gui.controllers;

import core.Einstein;
import gui.MainWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class for Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class EinsteinController extends BasicOptions implements Initializable {
    
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
        loadNewScene("/gui/FXML/homePage.fxml");
    }
    
    @FXML
    public void einsteinSolveAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        Einstein activeModule = (Einstein) mainWindow.getModule(0);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] einsteinCols = {houseColumn, colourColumn, nationColumn, petColumn, drinkColumn, cigarColumn};
        fillTableView(solution, einsteinTableView, einsteinCols);

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