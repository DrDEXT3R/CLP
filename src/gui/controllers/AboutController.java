package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * FXML Controller class for About Window.
 *
 * @author Tomasz Strzoda
 */
public class AboutController extends BasicController {

    @FXML
    void linkAction(ActionEvent e) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/DrDEXT3R"));
    }

}