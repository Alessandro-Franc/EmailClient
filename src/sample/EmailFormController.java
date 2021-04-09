package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmailFormController extends ConnectionController{
    private EasyEmail email;

    private Model model;

    private int EmailType;

    @FXML
    private TextField DestEmail;

    @FXML
    private TextField ObjEmail;

    @FXML
    private TextArea EmailText;

    @FXML
    private void sendEmailHandler(ActionEvent e) {
        boolean problem = false;
        String[] destinatari;
        if(DestEmail.getText().contains(";")) {
            destinatari = DestEmail.getText().replace(" ", "").split(";");
        }
        else {
            destinatari = new String[1];
            destinatari[0] = DestEmail.getText();
        }

        for(int i = 0; i<destinatari.length; i++) {
            if (!destinatari[i].matches(".*.@..*")) {
                System.out.println(destinatari[i] + " e' scritto male");
                problem = true;
            }//setto true se almeno un destinatario è scritto male
        }

        if(!problem) {
            email = new EasyEmail(destinatari, model.getId(), ObjEmail.getText(), EmailText.getText());
            sendEmail(email);
            final Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else {
            //chiamare popup di errore coi destinatari mal scritti
        }
    }

    public EmailFormController(int i) {
        this.EmailType = i;
    }

    public void start(Model m) {
        this.model=m;
        String obj,dest,mitt;
        try {
            Stage secondStage = new Stage();
            FXMLLoader formLoader = new FXMLLoader(getClass().getResource("NewMail.fxml"));
            formLoader.setController(this);
            GridPane root = (GridPane) formLoader.load();
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.start(this.model);
        try {
        switch(EmailType) {
            case 0: //new mail
                break;
            case 1: //reply
                dest = m.getCurrentEmail().getMitt();
                obj = m.getCurrentEmail().getObject();
                DestEmail.setText(dest);
                ObjEmail.setText("RE: " + obj);
                break;
            case 2: //reply all
                mitt = m.getCurrentEmail().getMitt();
                dest = m.getCurrentEmail().getDestination();
                dest = dest.replaceAll(model.getId() + ";", "");
                dest = dest.replaceAll(mitt + ";", "");
                String obj2 = m.getCurrentEmail().getObject();
                DestEmail.setText(mitt + "; " + dest);
                ObjEmail.setText("RE: " + obj2);
                break;
            case 3: //inoltra
                obj = m.getCurrentEmail().getObject();
                String eText = m.getCurrentEmail().geteText();
                ObjEmail.setText(obj);
                EmailText.setText(eText);
            default:
                break;
        }
        } catch (NullPointerException e) {System.out.println("Nessuna email selezionata, verrà creata una nuova mail vuota");}

    }

}
