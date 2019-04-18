package gui.controllers;

import com.jfoenix.controls.JFXButton;
import core.Museum;
import gui.MuseumGanttChart;
import gui.MainWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class MuseumController extends BasicOptions implements Initializable {

    @FXML private ImageView closeApp;
    @FXML private ImageView minimizeApp;
    @FXML private ImageView aboutApp;
    @FXML private ImageView einsteinShortcut;
    @FXML private ImageView homeShortcut;
    @FXML private ImageView mapShortcut;
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
    void navBarAction(MouseEvent e) throws IOException {
        if (e.getSource().equals(closeApp))
            Platform.exit();
        else if (e.getSource().equals(minimizeApp)) {
            MainWindow mainWindow = new MainWindow();
            mainWindow.getStage().setIconified(true);
        }
        else if (e.getSource().equals(aboutApp))
            createNewStage("/gui/FXML/about.fxml");
    }

    @FXML
    void transparentOn(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(0.5);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(0.5);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(0.5);
        else if (e.getSource().equals(einsteinShortcut))einsteinShortcut.setOpacity(0.5);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(0.5);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(0.5);
    }

    @FXML
    void transparentOff(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(1);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(1);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(1);
        else if (e.getSource().equals(einsteinShortcut))einsteinShortcut.setOpacity(1);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(1);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(1);
    }

    @FXML
    void bottomBarAction(MouseEvent e) throws IOException {
        if (e.getSource().equals(einsteinShortcut))
            loadNewScene("/gui/FXML/einstein.fxml");
        else if (e.getSource().equals(homeShortcut))
            loadNewScene("/gui/FXML/homePage.fxml");
        else if (e.getSource().equals(mapShortcut))
            loadNewScene("/gui/FXML/mapColoring.fxml");
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
        Text time = new Text(round(activeModule.getTime(),6) + "s");
        time.setFont(Font.font ("Berlin Sans FB Demi", 20));
        time.setFill(Color.valueOf("#eda647"));
        museumTime.getChildren().add(time);

        museumSolve.setDisable(true);
    }

    @FXML
    void museumCleanAction(ActionEvent event) {
        museumTime.getChildren().clear();
        museumTableView.getItems().clear();

        museumSolve.setDisable(false);
    }

}