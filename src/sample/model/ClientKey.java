package sample.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientKey {
    public SimpleStringProperty fname;
    public SimpleStringProperty lname;
    public SimpleStringProperty name;
    public SimpleLongProperty client_id;

    public ClientKey(long id, String fname, String lname){
        String name = fname + " " + lname;
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.name = new SimpleStringProperty(name);
        this.client_id = new SimpleLongProperty(id);

    }

    @Override
    public String toString (){
        return name.get();
    }

    public long getId(){
        return client_id.get();
    }
}
