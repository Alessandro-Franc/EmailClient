package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessageController{
    private Model model;
    @FXML
    private TextArea MailText;

    @FXML
    private TextField DestinationText;

    public void start(Model m){
        this.model = m;
        model.currentEmailProperty().addListener((obs, oldEmail, newEmail) -> {
            if (oldEmail != null) {
                MailText.textProperty().unbindBidirectional(oldEmail.eTextProperty());
                DestinationText.textProperty().unbindBidirectional(oldEmail.destinationProperty());
            }
            if (newEmail== null) {
                MailText.setText("");
                DestinationText.setText("");
            } else {
                MailText.textProperty().bindBidirectional(newEmail.eTextProperty());
                DestinationText.textProperty().bindBidirectional(newEmail.destinationProperty());
            }
        });

    }
}
