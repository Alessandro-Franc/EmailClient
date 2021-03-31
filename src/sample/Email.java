package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Email{

    private StringProperty object = new SimpleStringProperty();
    private StringProperty eText = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();

    public StringProperty objectProperty() {
        return object;
    }

    public String getObject() {
        return object.get();
    }

    public void setObject(String object) {
        this.object.set(object);
    }

    public StringProperty eTextProperty() {
        return eText;
    }

    public String geteText() {
        return eText.get();
    }

    public void seteText(String eText) {
        this.eText.set(eText);
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public Email(String destination , String obj , String eText){
        setObject(obj);
        seteText(eText);
        setDestination(destination);
    }
}
