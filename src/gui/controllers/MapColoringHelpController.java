package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class for map coloring help window.
 *
 * @author Tomasz Strzoda
 */
public class MapColoringHelpController extends BasicController{

    @FXML
    void linkAction(ActionEvent e) {
        openBrowser("https://en.wikipedia.org/wiki/Four_color_theorem");
    }

}
