package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.MapColoring;
import gui.MainWindow;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class for map coloring problem.
 *
 * @author Tomasz Strzoda
 */
public class MapColoringController extends BasicController implements Initializable {

    @FXML private JFXButton addGroup;
    @FXML private JFXButton mapColoringSolve;
    @FXML private Label inputRangeLabel;
    @FXML private TextFlow mapColoringTime;
    @FXML private JFXTextField groupTextField;
    @FXML private JFXTextArea allGroups;
    @FXML private JFXComboBox<Integer> noOfRegionsComboBox;
    @FXML private TableView<String[]> mapColoringTableView;
    @FXML private TableColumn<String[], String> regionsColumn;
    @FXML private TableColumn<String[], String> colorIndexColumn;
    @FXML private TableColumn<String[], String> colorNameColumn;

    ObservableList<Integer> list = FXCollections.observableArrayList();

    String groups;
    boolean mapColoringIsModeled;
    Set<Character> neighboringRegions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int alphabetLength = 26;
        for (int i = 0; i < alphabetLength; i++)
            list.add(i + 1);
        noOfRegionsComboBox.setItems(list);

        groups = new String();
        mapColoringIsModeled = false;
        neighboringRegions = new HashSet<>();
    }

    @FXML
    void addGroupAction(ActionEvent e) {
        readGroup();

        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        if(!mapColoringIsModeled) {
            activeModule.model();
            mapColoringIsModeled = true;
        }
        activeModule.sendGroup(neighboringRegions);

        neighboringRegions.clear();
        groupTextField.clear();
        noOfRegionsComboBox.setDisable(true);
        mapColoringSolve.setDisable(false);
    }

    private void readGroup() {
        String s = groupTextField.getText().toUpperCase();
        groups += s + "\n";
        allGroups.setText(groups);

        for (int i = 0; i < s.length(); i++) {
            char takenChar = s.charAt(i);
            if(takenChar != ' ')
                neighboringRegions.add(takenChar);
        }
    }

    @FXML
    void mapColoringSolveAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] mapColoringCols = {regionsColumn, colorIndexColumn, colorNameColumn};
        fillTableView(solution, mapColoringTableView, mapColoringCols);

        setTimeLabel(activeModule, mapColoringTime);
        solveModeHideUI(true);
    }

    private void solveModeHideUI (boolean hide) {
        noOfRegionsComboBox.setDisable(hide);
        addGroup.setDisable(hide);
        groupTextField.setDisable(hide);
        mapColoringSolve.setDisable(hide);
    }

    @FXML
    void noOfRegionsComboBoxAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.setNoOfRegions(noOfRegionsComboBox.getValue());

        defaultModeHideUI(false);
        mapColoringSolve.setDisable(true);
        setRangeLabel();
        validateGroup();
    }

    private void defaultModeHideUI(boolean hide) {
        groupTextField.setDisable(hide);
        mapColoringSolve.setDisable(hide);
        addGroup.setDisable(hide);
    }

    private void setRangeLabel() {
        char border1 = (char) (noOfRegionsComboBox.getValue() + 64);
        char border2 = (char) (noOfRegionsComboBox.getValue() + 96);
        inputRangeLabel.setText("(A-" + border1 + " or a-" + border2 + ")");
    }

    private void validateGroup() {
        groupTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            char inputKey = event.getCharacter().charAt(0);
            int groupLenght = groupTextField.getText().length();
            int ascii_a = (noOfRegionsComboBox.getValue() + 96);
            int ascii_A = (noOfRegionsComboBox.getValue() + 64);
            int ascii_Z = 90;

            if ( !Character.isAlphabetic(inputKey) || ascii_a < inputKey
                    || (ascii_A < inputKey && ascii_Z > inputKey) || groupLenght > (noOfRegionsComboBox.getValue()-1) )
                event.consume();
        });
    }

    @FXML
    void mapColoringCleanAction(ActionEvent e) {
        mapColoringTime.getChildren().clear();
        mapColoringTableView.getItems().clear();
        allGroups.clear();
        groupTextField.clear();
        noOfRegionsComboBox.setValue(1);
        groups = new String();
        mapColoringIsModeled = false;
        solveModeHideUI(false);
        defaultModeHideUI(true);
    }

    @FXML
    void mapColoringHelpAction(ActionEvent e) throws IOException {
        createNewStage("/gui/FXML/mapColoringHelp.fxml", 500,400);
    }

}
