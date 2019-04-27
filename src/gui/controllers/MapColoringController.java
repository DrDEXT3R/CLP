package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.MapColoring;
import gui.MainWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    void addGroupAction(ActionEvent e) {
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
    void mapColoringSolveAction(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.search();
        String[][] solution = activeModule.getSolutionAsArray();

        TableColumn[] mapColoringCols = {regionsColumn, colorIndexColumn, colorNameColumn};
        fillTableView(solution, mapColoringTableView, mapColoringCols);

        // Calculation time.
        Text time = new Text(round(activeModule.getTime(),6) + "s");
        time.setFont(Font.font ("Berlin Sans FB Demi", 20));
        time.setFill(Color.valueOf("#eda647"));
        mapColoringTime.getChildren().add(time);

        mapColoringSolve.setDisable(true);
    }

    @FXML
    void noOfRegionsComboBoxAction(ActionEvent e) {
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
    void mapColoringCleanAction(ActionEvent e) {
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