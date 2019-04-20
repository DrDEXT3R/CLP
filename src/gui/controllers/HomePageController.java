package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for gui's home page.
 *
 * @author Tomasz Strzoda
 */
public class HomePageController extends BasicController implements Initializable {

    @FXML private AnchorPane einstein;
    @FXML private AnchorPane museum;
    @FXML private AnchorPane mapColoring;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void menuAction(MouseEvent e) throws IOException {
        if (e.getSource().equals(einstein))
            loadNewScene("/gui/FXML/einstein.fxml");
        else if (e.getSource().equals(museum))
            loadNewScene("/gui/FXML/museum.fxml");
        else if (e.getSource().equals(mapColoring))
            loadNewScene("/gui/FXML/mapColoring.fxml");
    }

}