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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.stage.StageStyle;

/**
 * The main class.
 * 
 * @author Tomasz Strzoda
 */
public class MainWindow extends Application {

    /** The main stage of the entire application. */
    private static Stage mainWindow;

    /** The ArrayList containing all CLP problems. */
    private static ArrayList<Base> modules;
       
    public static void main(String[] args) {
        launch(args); 
    }

    /** {@link gui.controllers.BasicOptions#xOffset} */
    private double xOffset = 0;
    /** {@link gui.controllers.BasicOptions#yOffset} */
    private double yOffset = 0;

    @Override
    public void init() throws Exception {
        super.init();
        Font.loadFont(MainWindow.class.getResource("fonts/berlin-sans-fb-demi-bold.ttf").toExternalForm(),10);
        Font.loadFont(MainWindow.class.getResource("fonts/arial.ttf").toExternalForm(),10);
    }

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

    /**
     * This method creates instances of CLP problems.
     */
    private void loadModules() {
        modules = new ArrayList<>();
        modules.add(new Einstein());
        modules.add(new Museum());
        modules.add(new MapColoring());
    }

    /**
     * This method is responsible for ensuring movable.
     * @param page current scene.
     */
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

    /**
     * This method returns the instance of the main application.
     * @return main application.
     */
    public Stage getStage() {
        return mainWindow;
    }

    /**
     * This method returns an instance of the CLP problem.
     * @param module    problem number.
     * @return          instance of the CLP problem.
     */
    public Base getModule(int module) {
        return modules.get(module);
    }
     
}
