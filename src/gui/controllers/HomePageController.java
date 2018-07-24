package gui.controllers;

import gui.MainWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class for gui's home page.
 *
 * @author Tomasz Strzoda
 */
public class HomePageController implements Initializable {

    @FXML
    private Button einstein;
    
    @FXML
    private Button museum;
    
    @FXML
    private Button mapColoring;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }  
    
    @FXML
    public void menuAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        if (e.getSource().equals(einstein)) 
            loader.setLocation(getClass().getResource("/gui/FXML/einstein.fxml"));
        else if (e.getSource().equals(museum)) 
            loader.setLocation(getClass().getResource("/gui/FXML/museum.fxml"));
        else if (e.getSource().equals(mapColoring)) 
            loader.setLocation(getClass().getResource("/gui/FXML/mapColoring.fxml"));
        
        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getInstance();
        newWindow.setScene(scene);
        newWindow.show();
    }
    
}
