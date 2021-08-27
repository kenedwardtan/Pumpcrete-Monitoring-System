package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    public SimpleLongProperty id;
    public SimpleStringProperty position;
    public SimpleStringProperty fname;
    public SimpleStringProperty lname;
    public SimpleStringProperty name;
    public SimpleStringProperty cpnum;
    public SimpleStringProperty email;
    public SimpleStringProperty address;
    public SimpleStringProperty landline;
    public LocalDate latest_date;

    public Client(long id, String position, String fname, String lname, String cpnum, String email, String address, String landline,LocalDate date){
        this.id = new SimpleLongProperty(id);
        this.position = new SimpleStringProperty(position);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.cpnum = new SimpleStringProperty(cpnum);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleStringProperty(landline);
        this.latest_date =  date;
        this.name = new SimpleStringProperty(fname +" "+lname);
    }
    public Client(long id, String position, String fname, String lname, String cpnum, String email, String address, String landline){
        this.id = new SimpleLongProperty(id);
        this.position = new SimpleStringProperty(position);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.cpnum = new SimpleStringProperty(cpnum);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.landline = new SimpleStringProperty(landline);
        this.name = new SimpleStringProperty(fname +" "+lname);
    }

    public String getName() {return name.get();}

    public String getPosition() {
        return position.get();
    }

    public String getFName() {return fname.get();}

    public String getLName() {return lname.get();}

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

    public String getLandline() {
        return landline.get();
    }

    public LocalDate getLatest_date(){return latest_date;}
}