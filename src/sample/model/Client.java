package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    public SimpleLongProperty id;
    public SimpleStringProperty position;
    public SimpleStringProperty name;
    public SimpleStringProperty cpnum;
    public SimpleStringProperty email;
    public SimpleStringProperty address;
    public SimpleIntegerProperty landline;
    public LocalDate latest_date;

    public Client(long id, String position, String name,long cpnum, String email, String address, int landline,LocalDate date){
        this.id = new SimpleLongProperty(id);
        this.position = new SimpleStringProperty(position);
        this.name = new SimpleStringProperty(name);
        this.cpnum = new SimpleStringProperty("0"+Long.toString(cpnum));
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleIntegerProperty(landline);
        this.latest_date =  date;
    }
    public Client(long id, String position, String name,long cpnum, String email, String address, int landline){
        this.id = new SimpleLongProperty(id);
        this.position = new SimpleStringProperty(position);
        this.name = new SimpleStringProperty(name);
        this.cpnum = new SimpleStringProperty("0"+Long.toString(cpnum));
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleIntegerProperty(landline);
    }

    public String getPosition() {
        return position.get();
    }

    public String getName() {return name.get();}

    public long getId() { return  id.get();}

    public String getCpnum() {
        return cpnum.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAddress() {
        return address.get();
    }

    public int getLandline() {
        return landline.get();
    }

    public LocalDate getLatest_date(){return latest_date;}
}