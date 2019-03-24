package gui.controllers;

import core.Museum;
import gui.MuseumGanttChart;
import gui.MainWindow;
import gui.MuseumGanttChart;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.jfree.ui.RefineryUtilities;

/**
 * FXML Controller class for museum time schedule.
 *
 * @author Tomasz Strzoda
 */
public class MuseumController implements Initializable {

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

    void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");
        });
    }

    @FXML
    public void homeAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();
        newWindow.setScene(scene);
        newWindow.show();
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

        // Filling in the TableView.
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        museumTableView.setItems(data);

        this.setColumn(nationColumn, 0);
        this.setColumn(drawingsColumn, 1);
        this.setColumn(paintingsColumn, 2);
        this.setColumn(sculpturesColumn, 3);
        this.setColumn(photographsColumn, 4);

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
