package gui.controllers;

import gui.MainWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Abstract class template for controllers.
 * It contains all the basic elements related to "@FXML".
 *
 * @author Tomasz Strzoda
 */
public abstract class BasicController extends BasicOptions {

    @FXML private ImageView closeApp;
    @FXML private ImageView minimizeApp;
    @FXML private ImageView aboutApp;
    @FXML private ImageView einsteinShortcut;
    @FXML private ImageView museumShortcut;
    @FXML private ImageView mapShortcut;
    @FXML private ImageView homeShortcut;
    @FXML private AnchorPane einstein;
    @FXML private AnchorPane museum;
    @FXML private AnchorPane mapColoring;

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
        else if (e.getSource().equals(museumShortcut))  museumShortcut.setOpacity(0.5);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(0.5);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(0.5);
        else if (e.getSource().equals(einstein))        einstein.setOpacity(0.7);
        else if (e.getSource().equals(museum))          museum.setOpacity(0.7);
        else if (e.getSource().equals(mapColoring))     mapColoring.setOpacity(0.7);
    }

    @FXML
    void transparentOff(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(1);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(1);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(1);
        else if (e.getSource().equals(einsteinShortcut))einsteinShortcut.setOpacity(1);
        else if (e.getSource().equals(museumShortcut))  museumShortcut.setOpacity(1);
        else if (e.getSource().equals(mapShortcut))     mapShortcut.setOpacity(1);
        else if (e.getSource().equals(homeShortcut))    homeShortcut.setOpacity(1);
        else if (e.getSource().equals(einstein))        einstein.setOpacity(1);
        else if (e.getSource().equals(museum))          museum.setOpacity(1);
        else if (e.getSource().equals(mapColoring))     mapColoring.setOpacity(1);
    }

    @FXML
    void bottomBarAction(MouseEvent e) throws IOException {
        if (e.getSource().equals(einsteinShortcut))
            loadNewScene("/gui/FXML/einstein.fxml");
        else if (e.getSource().equals(museumShortcut))
            loadNewScene("/gui/FXML/museum.fxml");
        else if (e.getSource().equals(mapShortcut))
            loadNewScene("/gui/FXML/mapColoring.fxml");
        else if (e.getSource().equals(homeShortcut))
            loadNewScene("/gui/FXML/homePage.fxml");
    }

}
