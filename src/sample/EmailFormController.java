package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

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
        String[] destinatari;
        if(DestEmail.getText().contains(";")) {
            destinatari = DestEmail.getText().replace(" ", "").split(";");
        }
        else {
            destinatari = new String[1];
            destinatari[0] = DestEmail.getText();
        }

        email= new EasyEmail(destinatari,Model.getId(),ObjEmail.getText(),EmailText.getText());
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
