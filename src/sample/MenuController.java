package sample;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends ConnectionController{
    private Model model;

    @FXML
    private void onClickDelete(ActionEvent e){
        deleteEmail(model.getCurrentEmail());
    }

    @FXML
    private void onClickUpdate(ActionEvent e){
        AggiornaLista();
    }

    @FXML
    private void onClickAddEmail(ActionEvent e) throws Exception {
        Stage secondStage = new Stage();
        FXMLLoader formLoader = new FXMLLoader(getClass().getResource("NewMail.fxml"));
        GridPane root = (GridPane) formLoader.load();
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void onClickViewEmailR(ActionEvent e){
        model.seteMaillistR();
    }

    @FXML
    private void onClickViewEmailS(ActionEvent e){
        model.seteMaillistI();
    }

    public void start(Model m){
        this.model=m;
        super.start(this.model);
    }
}
