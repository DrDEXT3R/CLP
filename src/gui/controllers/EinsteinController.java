package gui.controllers;

import com.jfoenix.controls.JFXButton;
import core.Einstein;
import gui.MainWindow;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

/**
 * FXML Controller class for Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class EinsteinController extends BasicOptions implements Initializable {

    @FXML private ImageView closeApp;
    @FXML private ImageView minimizeApp;
    @FXML private ImageView aboutApp;
    @FXML private ImageView museumShortcut;
    @FXML private ImageView homeShortcut;
    @FXML private ImageView mapShortcut;
    @FXML private TextFlow einsteinTime;
    @FXML private JFXButton einsteinSolve;
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
        else if (e.getSource().equals(museumShortcut))  museumShortcut.setOpacity(0.5);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(0.5);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(0.5);
    }

    @FXML
    void transparentOff(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(1);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(1);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(1);
        else if (e.getSource().equals(museumShortcut))  museumShortcut.setOpacity(1);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(1);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(1);
    }

    @FXML
    public void einsteinSolveAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        Einstein activeModule = (Einstein) mainWindow.getModule(0);
        activeModule.model();
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] einsteinCols = {houseColumn, colourColumn, nationColumn, petColumn, drinkColumn, cigarColumn};
        fillTableView(solution, einsteinTableView, einsteinCols);

        // Calculation time.
        Text time = new Text(round(activeModule.getTime(),6) + "s");
        time.setFont(Font.font ("Berlin Sans FB Demi", 20));
        time.setFill(Color.valueOf("#eda647"));
        einsteinTime.getChildren().add(time);

        einsteinSolve.setDisable(true);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @FXML
    void einsteinCleanAction(ActionEvent e) {
        einsteinTime.getChildren().clear();
        einsteinTableView.getItems().clear();

        einsteinSolve.setDisable(false);
    }

    @FXML
    void bottomBarAction(MouseEvent e) throws IOException {
        if (e.getSource().equals(museumShortcut))
            loadNewScene("/gui/FXML/museum.fxml");
        else if (e.getSource().equals(homeShortcut))
            loadNewScene("/gui/FXML/homePage.fxml");
        else if (e.getSource().equals(mapShortcut))
            loadNewScene("/gui/FXML/mapColoring.fxml");
    }

}