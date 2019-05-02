package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * FXML Controller class for map coloring help window.
 *
 * @author Tomasz Strzoda
 */
public class MapColoringHelpController extends BasicController{

    @FXML
    void linkAction(ActionEvent e) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Four_color_theorem"));
    }

}
