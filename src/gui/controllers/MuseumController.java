package gui.controllers;

import core.Museum;
import gui.MuseumGanttChart;
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
import javafx.stage.Stage;

/**
 * FXML Controller class for museum time schedule.
 *
 * @author Tomasz Strzoda
 */
public class MuseumController extends BasicOptions implements Initializable {

    @FXML private TextFlow museumTime;
    @FXML private TableView<String[]> museumTableView;
    @FXML private TableColumn<String[], String> nationColumn;
    @FXML private TableColumn<String[], String> drawingsColumn;
    @FXML private TableColumn<String[], String> paintingsColumn;
    @FXML private TableColumn<String[], String> sculpturesColumn;
    @FXML private TableColumn<String[], String> photographsColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void homeAction(ActionEvent e) throws IOException {
        loadNewScene("/gui/FXML/homePage.fxml");
    }

    @FXML
    void museumGanttChartAction(ActionEvent event) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Museum Schedule");
        MainWindow mainWindow = new MainWindow();
        Museum activeModule = (Museum) mainWindow.getModule(1);
        MuseumGanttChart ganttChart = new MuseumGanttChart(newWindow, activeModule.getSolutionAsRawArray());
    }

    @FXML
    public void museumSolveAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        Museum activeModule = (Museum) mainWindow.getModule(1);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] museumCols = {nationColumn, drawingsColumn, paintingsColumn, sculpturesColumn, photographsColumn};
        fillTableView(solution, museumTableView, museumCols);

        // Calculation time.
        Text time = new Text("I calculated it in: " + activeModule.getTime() + "s");
        museumTime.getChildren().add(time);
    }

    @FXML
    void museumCleanAction(ActionEvent event) {
        museumTime.getChildren().clear();
        museumTableView.getItems().clear();
    }

}