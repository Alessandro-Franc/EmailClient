package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Email{

    private StringProperty object = new SimpleStringProperty();
    private StringProperty eText = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private StringProperty mitt = new SimpleStringProperty();

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

    public void setDestination(String[] destination) {
        String dest = "";
        for(int i = 0; i<destination.length; i++) {
            dest += destination[i];
            dest += "; ";
        }
        this.destination.set(dest);
    }

    public StringProperty mittProperty() {
        return mitt;
    }

    public String getMitt() {
        return mitt.get();
    }

    public void setMitt(String mitt) {
        this.mitt.set(mitt);
    }

    public Email(String[] destination , String mitt, String obj , String eText){
        setObject(obj);
        seteText(eText);
        setDestination(destination);
        setMitt(mitt);
    }
}
