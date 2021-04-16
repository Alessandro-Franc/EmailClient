package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ListController extends ConnectionController{

    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    @FXML
    private ListView<Email> emailList;

    @FXML
    private TextField MailType;

    private Model model;

    public void start(Model model) {
        this.model = model;

        super.start(this.model);

        AggiornaLista();


        model.seteMaillistR();

        MailType.textProperty().bindBidirectional(model.displayTypeProperty());

        emailList.setItems(model.geteMailList());

        emailList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                model.setCurrentEmail(newSelection));

        emailList.setCellFactory(lv -> new ListCell<Email>() {
            @Override
            public void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);
                if (empty) {
                    setText(null);
                } else {
                    if(model.getEmailVisual()==0){
                        setText(email.getMitt() + " : " + email.getObject() + " ");
                    }else{
                        setText(email.getObject());
                    }
                }
            }
        });


        executor.scheduleWithFixedDelay(() -> {
            Platform.runLater(new UpdateTask(this.model));
        }, 15, 15, TimeUnit.SECONDS);


    }

    public static void stop() {
        executor.shutdown();
        System.out.println("shutdown");
    }
}

