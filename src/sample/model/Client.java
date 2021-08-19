package sample.model;

import javafx.beans.property.*;

import java.math.BigInteger;

public class Client {
    public SimpleIntegerProperty id;
    public SimpleStringProperty position;
    public SimpleStringProperty name;
    public BigInteger cpnum;
    public SimpleStringProperty email;
    public SimpleStringProperty address;
    public SimpleIntegerProperty landline;
    public SimpleStringProperty latest_date;

    public Client(int id, String position, String name,BigInteger cpnum, String email, String address, int landline, String date){
        this.id = new SimpleIntegerProperty(id);
        this.position = new SimpleStringProperty(position);
        this.name = new SimpleStringProperty(name);
        this.cpnum = cpnum;
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleIntegerProperty(landline);
        this.latest_date = new SimpleStringProperty(date);
    }

    public String getPosition() {
        return position.get();
    }

    public String getName() {return name.get();}

    public int getID() { return  id.get();}

    public BigInteger getCpnum() {
        return cpnum;
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


    public String getLatestDocDate(){return latest_date.get();}
}