package gui.controllers;

import core.Einstein;
import core.MapColoring;
import gui.MainWindow;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class for map coloring problem.
 *
 * @author Tomasz Strzoda
 */
public class MapColoringController implements Initializable {

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/FXML/homePage.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindow mainWindow = new MainWindow();
        Stage newWindow = mainWindow.getStage();
        newWindow.setScene(scene);
        newWindow.show();
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

    void setColumn(TableColumn<String[], String> column, int colNo) {
        column.setCellValueFactory(cellData -> {   
            String[] x = cellData.getValue();
            return new SimpleStringProperty(x != null && x.length>1 ? x[colNo] : "<no value>");        
        });
    }
    
    @FXML
    void mapColoringSolveAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2); 
        System.out.println(activeModule.search());
        String[][] solution = activeModule.getSolutionAsArray();
        
        //Filling in the TableView.
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(solution));
        mapColoringTableView.setItems(data);
        
        this.setColumn(regionsColumn, 0);
        this.setColumn(colorIndexColumn, 1);
        this.setColumn(colorNameColumn, 2);
        
        // Calculation time.
        Text time = new Text("I calculated it in: " + activeModule.getTime() + "s");
        mapColoringTime.getChildren().add(time);
        

    }
    
    @FXML
    void noOfRegionsComboBoxAction(ActionEvent event) {
        MainWindow mainWindow = new MainWindow();
        MapColoring activeModule = (MapColoring) mainWindow.getModule(2);
        activeModule.setNoOfRegions(noOfRegionsComboBox.getValue());

        groupTextField.setDisable(false);
        mapColoringSolve.setDisable(false);
        addGroup.setDisable(false);

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
        groupTextField.setDisable(true);
        mapColoringSolve.setDisable(true);
        addGroup.setDisable(true);
    }

    
}
