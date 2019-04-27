package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for gui's home page.
 *
 * @author Tomasz Strzoda
 */
public class HomePageController extends BasicController {

    @FXML private AnchorPane einstein;
    @FXML private AnchorPane museum;
    @FXML private AnchorPane mapColoring;

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