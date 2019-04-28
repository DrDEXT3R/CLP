package gui.controllers;

import gui.MainWindow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Abstract class template for controllers.
 * It contains all basic elements other than "@FXML".
 *
 * @author Tomasz Strzoda
 */
public abstract class BasicOptions {

    // Variables used to make movable application.
    protected double xOffset = 0;
    protected double yOffset = 0;

    protected void makeMovable(Stage stage, Scene page) {
        AnchorPane navBar = (AnchorPane) page.lookup("#navBar");

        navBar.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        navBar.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
    }

    protected void loadNewScene(String location) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        Scene scene = new Scene(loader.load());

        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();

        makeMovable(newWindow, scene);
        newWindow.setScene(scene);
        newWindow.show();
    }

    protected void createNewStage(String location, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        Scene secondScene = new Scene(loader.load());

        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        makeMovable(newWindow, secondScene);
        newWindow.initStyle(StageStyle.UNDECORATED);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        newWindow.setX((primaryScreenBounds.getWidth() - width) / 2);
        newWindow.setY((primaryScreenBounds.getHeight() - height) / 2);
        newWindow.show();
    }

    protected void fillTableView(String[][] solution, TableView tableView, TableColumn[] tableCols) {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        tableView.setItems(data);

        for (int i = 0; i < tableCols.length ; i++)
            this.setColumn(tableCols[i], i);
}

    protected void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}