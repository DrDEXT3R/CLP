package gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class.
 * 
 * @author Tomasz Strzoda
 */
public class MainWindow extends Application {
    
    private static Stage mainWindow;
   
    public static void main(String[] args) {
        launch(args); 
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {  
        mainWindow = primaryStage; 
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene homePage = new Scene(loader.load());
        mainWindow.setScene(homePage);
        mainWindow.setTitle("CLP Calculator");
        mainWindow.show();
    }
    
    public Stage getInstance() {
        return mainWindow;
    }
     
}
