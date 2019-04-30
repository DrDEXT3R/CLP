package gui;

import core.Base;
import core.Einstein;
import core.MapColoring;
import core.Museum;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.stage.StageStyle;

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
        loadModules();
        
        mainWindow = primaryStage; 
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene homePage = new Scene(loader.load());

        mainWindow.initStyle(StageStyle.UNDECORATED);
        mainWindow.setTitle("CLP Calculator");
        mainWindow.setScene(homePage);
        makeMovable(homePage);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        mainWindow.setX((primaryScreenBounds.getWidth() - 600) / 2);
        mainWindow.setY((primaryScreenBounds.getHeight() - 400) / 2 - 50);
        mainWindow.show();
    }

    private void loadModules() {
        modules = new ArrayList<>();
        modules.add(new Einstein());
        modules.add(new Museum());
        modules.add(new MapColoring());
    }

    private void makeMovable(Scene page) {
        AnchorPane navBar = (AnchorPane) page.lookup("#navBar");

        navBar.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        navBar.setOnMouseDragged(e -> {
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
