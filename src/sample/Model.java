package sample;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Model{
    //array per visualizzazione
    ObservableList<Email> eMailList = FXCollections.observableArrayList(email ->
            new Observable[] {email.objectProperty()});
    ObjectProperty<Email> currentEmail = new SimpleObjectProperty<>();

    //array per email ricevute
    private ArrayList<Email> ReMailList = new ArrayList<>();

    //array per email inviate
    private ArrayList<Email> IeMailList = new ArrayList<>();

    //flag per la visualizzazione
    private int EmailVisual=0;

    public int getEmailVisual(){
        return this.EmailVisual;
    }

    public void setEmailVisual(int i){
        this.EmailVisual=i;
    }

    public ArrayList<Email> getReMailList (){
        return this.ReMailList;
    }

    public ArrayList<Email> getIeMailList (){
        return this.IeMailList;
    }

    public void seteMaillistR(){
        setEmailVisual(0);
        //pulisco la lista view
        if(geteMailList().size()>0){
            geteMailList().remove(0 , geteMailList().size());
        }
        //inserisco i nuovi dati
        for(int i=0; i<getReMailList().size() ; i++){
            geteMailList().add(getReMailList().get(i));
        }
    }

    public void seteMaillistI(){
        setEmailVisual(1);
        //pulisco la lista view
        if(geteMailList().size()>0){
            geteMailList().remove(0 , geteMailList().size());
        }
        //inserisco i nuovi dati
        for(int i=0; i<getIeMailList().size() ; i++){
            geteMailList().add(getIeMailList().get(i));
        }
    }

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
