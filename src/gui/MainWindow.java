package gui;

import core.Base;
import core.Einstein;
import core.MapColoring;
import core.Museum;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

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

    private double xOffset = 0;
    private double yOffset = 0;

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
        //mainWindow.initStyle(StageStyle.UNDECORATED);

        makeMovable(homePage);

        mainWindow.setScene(homePage);
        mainWindow.setTitle("CLP Calculator");
        mainWindow.show();
    }

    private void makeMovable(Scene page) {
        page.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        page.setOnMouseDragged(e -> {
            mainWindow.setX(e.getScreenX() - xOffset);
            mainWindow.setY(e.getScreenY() - yOffset);
        });
    }
    
    public Stage getStage() {
        return mainWindow;
    }
    
    public Base getModule(int module) {
        return modules.get(module);
    }
     
}
