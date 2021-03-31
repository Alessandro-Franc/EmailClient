package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class EmailFormController extends ConnectionController{
    private EasyEmail email;

    private Model model;

    @FXML
    private TextField DestEmail;

    @FXML
    private TextField ObjEmail;

    @FXML
    private TextArea EmailText;

    @FXML
    private void sendEmailHandler(ActionEvent e){
        email= new EasyEmail(DestEmail.getText(),ObjEmail.getText(),EmailText.getText());
        sendEmail(email);
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void start(Model m){
        this.model=m;
        super.start(this.model);
    }
}
