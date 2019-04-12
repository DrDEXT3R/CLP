package gui.controllers;

import gui.MainWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML private ImageView closeAbout;
    @FXML private ImageView minimizeAbout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    void navBarAction(MouseEvent e) {
        if (e.getSource().equals(closeAbout)) {
            Stage stage = (Stage) closeAbout.getScene().getWindow();
            stage.close();
        }

        else if (e.getSource().equals(minimizeAbout)) {
            Stage stage = (Stage) minimizeAbout.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @FXML
    void transparentOn(MouseEvent e) {
        if      (e.getSource().equals(closeAbout))        closeAbout.setOpacity(0.5);
        else if (e.getSource().equals(minimizeAbout))     minimizeAbout.setOpacity(0.5);
    }

    @FXML
    void transparentOff(MouseEvent e) {
        if      (e.getSource().equals(closeAbout))        closeAbout.setOpacity(1);
        else if (e.getSource().equals(minimizeAbout))     minimizeAbout.setOpacity(1);
    }
}
