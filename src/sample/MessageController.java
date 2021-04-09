package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessageController{
    private Model model;

    @FXML
    private TextArea MailText;

    @FXML
    private TextField DestinationText;

    @FXML
    private void onClickReply(ActionEvent e){
        new EmailFormController(1).start(this.model);
    }

    @FXML
    private void onClickReplyAll(ActionEvent e) {
        new EmailFormController(2).start(this.model);
    }

    @FXML
    private void onClickForward(ActionEvent e) {
        new EmailFormController(3).start(this.model);
    }

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
