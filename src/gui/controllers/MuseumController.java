package gui.controllers;

import com.jfoenix.controls.JFXButton;
import core.Museum;
import gui.MuseumGanttChart;
import gui.MainWindow;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class for museum time schedule.
 *
 * @author Tomasz Strzoda
 */
public class MuseumController extends BasicController implements Initializable {

    @FXML private TextFlow museumTime;
    @FXML private JFXButton museumSolve;
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
    void museumGanttChartAction(ActionEvent e) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Museum Schedule");
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
    }

    @FXML
    void museumCleanAction(ActionEvent e) {
        museumTime.getChildren().clear();
        museumTableView.getItems().clear();

        museumSolve.setDisable(false);
    }

}