package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class for About Window.
 *
 * @author Tomasz Strzoda
 */
public class AboutController extends BasicController {

    @FXML
    void linkAction(ActionEvent e)  {
        openBrowser("https://github.com/DrDEXT3R");
    }

}
