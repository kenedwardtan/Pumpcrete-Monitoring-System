package sample.model;

import javafx.beans.property.*;

public class Client {
    public SimpleStringProperty position;
    public SimpleStringProperty fname;
    public SimpleStringProperty lname;
    public SimpleIntegerProperty cpnum;
    public SimpleStringProperty email;
    public SimpleStringProperty address;
    public SimpleIntegerProperty landline;

    public Client(String position, String fname, String lname, int cpnum, String email, String address, int landline){
        this.position = new SimpleStringProperty(position);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.cpnum = new SimpleIntegerProperty(cpnum);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleIntegerProperty(landline);
    }

    public String getPosition() {
        return position.get();
    }

    public String getFname() {
        return fname.get();
    }

    public String getLname() {
        return lname.get();
    }

    public int getCpnum() {
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
}