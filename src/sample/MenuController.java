package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        new EmailFormController(0).start(this.model);
        //per ora si può modificare questo int per provare gli altri metodi finche mancano i bottoni
    }

    @FXML
    private void onClickReply(ActionEvent e) throws Exception {
        new EmailFormController(1).start(this.model);
    }

    @FXML
    private void onClickReplyAll(ActionEvent e) throws Exception {
        new EmailFormController(2).start(this.model);
    }

    @FXML
    private void onClickForward(ActionEvent e) throws Exception {
        new EmailFormController(3).start(this.model);
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
