package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MessageController{
    private Model model;
    @FXML
    private TextArea MailText;

    public void start(Model m){
        this.model = m;
        model.currentEmailProperty().addListener((obs, oldEmail, newEmail) -> {
            if (oldEmail != null) {
                MailText.textProperty().unbindBidirectional(oldEmail.eTextProperty());
            }
            if (newEmail== null) {
                MailText.setText("");
            } else {
                MailText.textProperty().bindBidirectional(newEmail.eTextProperty());
            }
        });

    }
}
