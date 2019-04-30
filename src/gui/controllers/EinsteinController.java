package gui.controllers;

import com.jfoenix.controls.JFXButton;
import core.Einstein;
import gui.MainWindow;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class for Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class EinsteinController extends BasicController{

    @FXML private TextFlow einsteinTime;
    @FXML private JFXButton einsteinSolve;
    @FXML private TableView<String[]> einsteinTableView;
    @FXML private TableColumn<String[], String> houseColumn;
    @FXML private TableColumn<String[], String> colourColumn;
    @FXML private TableColumn<String[], String> nationColumn;
    @FXML private TableColumn<String[], String> petColumn;
    @FXML private TableColumn<String[], String> drinkColumn;
    @FXML private TableColumn<String[], String> cigarColumn;

    @FXML
    public void einsteinSolveAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        Einstein activeModule = (Einstein) mainWindow.getModule(0);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] einsteinCols = {houseColumn, colourColumn, nationColumn, petColumn, drinkColumn, cigarColumn};
        fillTableView(solution, einsteinTableView, einsteinCols);

        setTimeLabel(activeModule, einsteinTime);
        einsteinSolve.setDisable(true);
    }

    @FXML
    void einsteinCleanAction(ActionEvent e) {
        einsteinTime.getChildren().clear();
        einsteinTableView.getItems().clear();

        einsteinSolve.setDisable(false);
    }

    @FXML
    void einsteinHelpAction(ActionEvent e) throws IOException {
        createNewStage("/gui/FXML/einsteinHelp.fxml", 500,400);
    }

}
