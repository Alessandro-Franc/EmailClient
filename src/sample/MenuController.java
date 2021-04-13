package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.ConnectException;

public class MenuController extends ConnectionController{
    private Model model;

    @FXML
    private void onClickDelete(ActionEvent e){
        deleteEmail(model.getCurrentEmail());
        model.deleteCurrentemail();
    }

    @FXML
    private void onClickUpdate(ActionEvent e){
        AggiornaLista();
    }

    @FXML
    private TextField UserName;

    @FXML
    private void onClickAddEmail(ActionEvent e) throws Exception {
        new EmailFormController(0).start(this.model);
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
        UserName.setText(model.getId());
    }
}
