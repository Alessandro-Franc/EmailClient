package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.EventObject;

import static java.lang.Thread.sleep;

public class PopUpController {
    private String Error;

    public PopUpController(String i){
        this.Error = i;
    }

    @FXML
    private TextField ErrorLabel;

    public void start() {
        try {
            Stage secondStage = new Stage();
            FXMLLoader formLoader = new FXMLLoader(getClass().getResource("PopUp.fxml"));
            formLoader.setController(this);
            AnchorPane root = (AnchorPane) formLoader.load();
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.show();
            secondStage.setAlwaysOnTop(true);
            ErrorLabel.setText(this.Error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
