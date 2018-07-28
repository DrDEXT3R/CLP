package gui;

import core.Base;
import core.Einstein;
import core.MapColoring;
import core.Museum;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import static javafx.application.Application.launch;

/**
 * Main class.
 * 
 * @author Tomasz Strzoda
 */
public class MainWindow extends Application {
    
    private static Stage mainWindow;
    private static ArrayList<Base> modules;
       
    public static void main(String[] args) {
        launch(args); 
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {  
        modules = new ArrayList<>();
        modules.add(new Einstein());
        modules.add(new Museum());
        modules.add(new MapColoring());
        
        mainWindow = primaryStage; 
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene homePage = new Scene(loader.load());
        mainWindow.setScene(homePage);
        mainWindow.setTitle("CLP Calculator");
        mainWindow.show();
        
        
//        Einstein activeModule = (Einstein) modules.get(0);
//        activeModule.model();
//        System.out.println(activeModule.search());
//        activeModule.getSolutionAsArray();
    }
    
    public Stage getStage() {
        return mainWindow;
    }
    
    public Base getModule(int module) {
        return modules.get(module);
    }
     
}
