package gui.controllers;

import core.MapColoring;
import gui.MainWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class for map coloring problem.
 *
 * @author Tomasz Strzoda
 */
public class MapColoringController extends BasicOptions implements Initializable {

    @FXML private Button addGroup;
    @FXML private Button mapColoringSolve;
    @FXML private Label inputRangeLabel;
    @FXML private TextFlow mapColoringTime;
    @FXML private TextField groupTextField;
    @FXML private TextArea allGroups;
    @FXML private ComboBox<Integer> noOfRegionsComboBox;
    @FXML private TableView<String[]> mapColoringTableView;
    @FXML private TableColumn<String[], String> regionsColumn;
    @FXML private TableColumn<String[], String> colorIndexColumn;
    @FXML private TableColumn<String[], String> colorNameColumn;

    ObservableList<Integer> list = FXCollections.observableArrayList();

    String groups;
    boolean mapColoringIsModeled;
    ArrayList<Character> neighboringRegions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int alphabetLength = 26;
        for (int i = 0; i < alphabetLength; i++)
            list.add(i + 1);
        noOfRegionsComboBox.setItems(list);

        groups = new String();
        mapColoringIsModeled = false;
        neighboringRegions = new ArrayList<>();
    }

    @FXML
    public void homeAction(ActionEvent event) throws IOException {
        loadNewScene("/gui/FXML/homePage.fxml");
    }

    @FXML
    void addGroupAction(ActionEvent event) {
        String s = groupTextField.getText().toUpperCase();
        groups += s + "\n";
        allGroups.setText(groups);

        for (int i = 0; i < s.length(); i++) {
            char takenChar = s.charAt(i);
            if(takenChar != ' ')
                neighboringRegions.add(takenChar);
        }

        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        if(!mapColoringIsModeled) {
            activeModule.model();
            mapColoringIsModeled = true;
        }
        activeModule.sendGroup(neighboringRegions);

        neighboringRegions.clear();
        groupTextField.clear();
    }

    @FXML
    void mapColoringSolveAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] mapColoringCols = {regionsColumn, colorIndexColumn, colorNameColumn};
        fillTableView(solution, mapColoringTableView, mapColoringCols);

        // Calculation time.
        Text time = new Text("I calculated it in: " + activeModule.getTime() + "s");
        mapColoringTime.getChildren().add(time);
    }

    @FXML
    void noOfRegionsComboBoxAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.setNoOfRegions(noOfRegionsComboBox.getValue());

        hideUI(false);
        setRangeLabel();
    }

    private void hideUI(boolean hide) {
        groupTextField.setDisable(hide);
        mapColoringSolve.setDisable(hide);
        addGroup.setDisable(hide);
    }

    private void setRangeLabel() {
        char border1 = (char) (noOfRegionsComboBox.getValue() + 64);
        char border2 = (char) (noOfRegionsComboBox.getValue() + 96);
        inputRangeLabel.setText("(A-" + border1 + " or a-" + border2 + ")");
    }

    @FXML
    void mapColoringCleanAction(ActionEvent event) {
        mapColoringTime.getChildren().clear();
        mapColoringTableView.getItems().clear();
        allGroups.clear();
        groupTextField.clear();
        noOfRegionsComboBox.valueProperty().set(null);
        groups = new String();
        mapColoringIsModeled = false;
        hideUI(true);
    }

}