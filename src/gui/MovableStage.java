package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class MovableStage {

    protected double xOffset = 0;
    protected double yOffset = 0;

    protected void makeMovable(Stage stage, Scene page) {
        page.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        page.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
    }

}
