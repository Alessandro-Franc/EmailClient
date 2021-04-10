package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Email{

    private StringProperty object = new SimpleStringProperty();
    private StringProperty eText = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private StringProperty mitt = new SimpleStringProperty();
    private StringProperty data = new SimpleStringProperty();

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

    public void setData(String data) {
        this.data.set(data);
    }

    public String getData() {
        return data.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public StringProperty dataProperty() {
        return data;
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

    public Email(String[] destination , String mitt, String obj , String eText, String data){
        setObject(obj);
        seteText(eText);
        setDestination(destination);
        setMitt(mitt);
        setData(data);
    }

    public EasyEmail toEasyMail() {
        String[] destinatari;
        if(getDestination().contains(";")) {
            destinatari = getDestination().replace(" ", "").split(";");
        }
        else {
            destinatari = new String[1];
            destinatari[0] = getDestination();
        }
        return new EasyEmail(destinatari, getMitt(), getObject(), geteText(), getData());
    }
}
