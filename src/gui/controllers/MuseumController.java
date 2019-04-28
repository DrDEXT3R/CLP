package gui.controllers;

import com.jfoenix.controls.JFXButton;
import core.Museum;
import gui.MuseumGanttChart;
import gui.MainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FXML Controller class for museum time schedule.
 *
 * @author Tomasz Strzoda
 */
public class MuseumController extends BasicController {

    @FXML private TextFlow museumTime;
    @FXML private JFXButton museumSolve;
    @FXML private JFXButton museumGanttChart;
    @FXML private TableView<String[]> museumTableView;
    @FXML private TableColumn<String[], String> nationColumn;
    @FXML private TableColumn<String[], String> drawingsColumn;
    @FXML private TableColumn<String[], String> paintingsColumn;
    @FXML private TableColumn<String[], String> sculpturesColumn;
    @FXML private TableColumn<String[], String> photographsColumn;

    @FXML
    void museumGanttChartAction(ActionEvent e) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Museum Schedule (click to reload)");
        MainWindow mainWindow = new MainWindow();
        Museum activeModule = (Museum) mainWindow.getModule(1);
        MuseumGanttChart ganttChart = new MuseumGanttChart(newWindow, activeModule.getSolutionAsRawArray());
    }

    @FXML
    public void museumSolveAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        Museum activeModule = (Museum) mainWindow.getModule(1);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] museumCols = {nationColumn, drawingsColumn, paintingsColumn, sculpturesColumn, photographsColumn};
        fillTableView(solution, museumTableView, museumCols);

        // Calculation time.
        Text time = new Text(round(activeModule.getTime(),6) + "s");
        time.setFont(Font.font ("Berlin Sans FB Demi", 20));
        time.setFill(Color.valueOf("#eda647"));
        museumTime.getChildren().add(time);

        museumSolve.setDisable(true);
        museumGanttChart.setDisable(false);
    }

    @FXML
    void museumCleanAction(ActionEvent e) {
        museumTime.getChildren().clear();
        museumTableView.getItems().clear();

        museumSolve.setDisable(false);
        museumGanttChart.setDisable(true);
    }

    @FXML
    void museumHelpAction(ActionEvent e) throws IOException {
        createNewStage("/gui/FXML/museumHelp.fxml", 500,400);
    }

}