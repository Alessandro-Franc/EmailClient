package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import static java.lang.Thread.sleep;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ListController extends ConnectionController{
    @FXML
    private ListView<Email> emailList;

    private Model model;

    public void start(Model model) {
        this.model = model;
        super.start(this.model);
        AggiornaLista();
        model.seteMaillistR();

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

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.scheduleWithFixedDelay(() -> {
            Platform.runLater(new UpdateTask(this.model));
        }, 5, 60, TimeUnit.SECONDS);


    }
}

