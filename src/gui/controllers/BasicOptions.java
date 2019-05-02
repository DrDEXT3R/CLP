package gui.controllers;

import core.Base;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Locale;

/**
 * Abstract class template for controllers.
 * It contains all basic elements other than "@FXML".
 *
 * @author Tomasz Strzoda
 */
public abstract class BasicOptions {

    /** The variable used to make movable application. */
    protected double xOffset = 0;

    /** The variable used to make movable application. */
    protected double yOffset = 0;

    /**
     * This method is responsible for ensuring movable.
     * @param stage stage to move.
     * @param page  current scene.
     */
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

    /**
     * This method is responsible for loading the new scene onto the stage.
     * @param location      location of the fxml file.
     * @throws IOException  can be thrown by load().
     */
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

    /**
     * This method is responsible for creating a new stage.
     * @param location      location of the fxml file.
     * @param width         stage width.
     * @param height        stage height.
     * @throws IOException  can be thrown by load().
     */
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

    /**
     * This method fills the TableView in which solutions of CLP problems are presented.
     * @param solution  array of solution.
     * @param tableView specific TableView to fill.
     * @param tableCols array of columns.
     */
    protected void fillTableView(String[][] solution, TableView tableView, TableColumn[] tableCols) {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        tableView.setItems(data);

        for (int i = 0; i < tableCols.length ; i++)
            this.setColumn(tableCols[i], i);
    }

    /**
     * This method is responsible for filling the column of TableView which is the solution of the CLP problem.
     * @param column    specific column to fill.
     * @param colNo     column number from the TableView.
     */
    protected void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");
        });
    }

    /**
     * This method sets the time label which is the time to look for a solution of the CLP problem.
     * @param activeModule  type of CLP problem.
     * @param timeLabel     specific label to fill.
     */
    protected void setTimeLabel(Base activeModule, TextFlow timeLabel) {
        Text time = new Text(String.format(Locale.US,"%.6f", round(activeModule.getTime(),6)) + "s");
        time.setFont(Font.font ("Berlin Sans FB Demi", 20));
        time.setFill(Color.valueOf("#eda647"));
        timeLabel.getChildren().add(time);
    }

    /**
     * This method rounds the floating point number (solve time).
     * @param value     number for rounding.
     * @param places    number of decimal places.
     * @return          rounded number.
     */
    protected static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
