package gui.controllers;

import com.jfoenix.controls.JFXButton;
import gui.MainWindow;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class for gui's home page.
 *
 * @author Tomasz Strzoda
 */
public class HomePageController implements Initializable {

    @FXML private ImageView closeApp;
    @FXML private ImageView minimizeApp;
    @FXML private ImageView aboutApp;
    @FXML private AnchorPane einstein;
    @FXML private AnchorPane museum;
    @FXML private AnchorPane mapColoring;

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
        else if (e.getSource().equals(aboutApp)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/FXML/about.fxml"));
            Scene secondScene = new Scene(loader.load());

            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            newWindow.initStyle(StageStyle.UNDECORATED);
            newWindow.show();
        }

    }



    @FXML
    void menuAction(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        if (e.getSource().equals(einstein))
            loader.setLocation(getClass().getResource("/gui/FXML/einstein.fxml"));
        else if (e.getSource().equals(museum))
            loader.setLocation(getClass().getResource("/gui/FXML/museum.fxml"));
        else if (e.getSource().equals(mapColoring))
            loader.setLocation(getClass().getResource("/gui/FXML/mapColoring.fxml"));

        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();
        newWindow.setScene(scene);
        newWindow.show();
    }


    @FXML
    void transparentOn(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(0.5);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(0.5);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(0.5);
        else if (e.getSource().equals(einstein))        einstein.setOpacity(0.7);
        else if (e.getSource().equals(museum))          museum.setOpacity(0.7);
        else if (e.getSource().equals(mapColoring))     mapColoring.setOpacity(0.7);
    }

    @FXML
    void transparentOff(MouseEvent e) {
        if      (e.getSource().equals(closeApp))        closeApp.setOpacity(1);
        else if (e.getSource().equals(minimizeApp))     minimizeApp.setOpacity(1);
        else if (e.getSource().equals(aboutApp))        aboutApp.setOpacity(1);
        else if (e.getSource().equals(einstein))        einstein.setOpacity(1);
        else if (e.getSource().equals(museum))          museum.setOpacity(1);
        else if (e.getSource().equals(mapColoring))     mapColoring.setOpacity(1);
    }

}
