package sample;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Collection;

public class Model{
    ObservableList<Email> eMailList = FXCollections.observableArrayList(email ->
            new Observable[] {email.objectProperty()});
    ObjectProperty<Email> currentEmail = new SimpleObjectProperty<>();

    private static String id; //reso static

    public void setId(String i){
        id=i;
    }

    public static String getId(){ //reso static
        return id;
    }

    public ObservableList<Email> geteMailList() {
        return eMailList;
    }

    public Email getCurrentEmail() {
        return currentEmail.get();
    }

    public ObjectProperty<Email> currentEmailProperty() {
        return currentEmail;
    }

    public void setCurrentEmail(Email currentEmail) {
        this.currentEmail.set(currentEmail);
    }

    public void deleteCurrentemail(){
        this.geteMailList().remove(getCurrentEmail());
    }
}
