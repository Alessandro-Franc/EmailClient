package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
    private TextField UserName;

    @FXML
    private void onClickAddEmail(ActionEvent e) throws Exception {
        new EmailFormController(0).start(this.model);
        //per ora si può modificare questo int per provare gli altri metodi finche mancano i bottoni
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
