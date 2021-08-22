package sample.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Pumpcrete {
    public SimpleLongProperty pumpcrete_id;
    public SimpleStringProperty description;
    public SimpleStringProperty plate_no;
    public SimpleStringProperty fuel_type;
    public LocalDate purchase_date;
    public SimpleLongProperty cr_no;
    public SimpleLongProperty or_no;
    public SimpleIntegerProperty tires;
    public SimpleBooleanProperty rented;
    public SimpleStringProperty client_name;


    public Pumpcrete(long id, String desc, String plate, String fuel, LocalDate purchase_date, long cr, long or,
                     int tires, boolean rented, String name){
        this.pumpcrete_id = new SimpleLongProperty(id);
        this.description = new SimpleStringProperty(desc);
        this.plate_no = new SimpleStringProperty(plate);
        this.fuel_type = new SimpleStringProperty(fuel);
        this.purchase_date = purchase_date;
        this.cr_no = new SimpleLongProperty(cr);
        this.or_no = new SimpleLongProperty(or);
        this.tires = new SimpleIntegerProperty(tires);
        this.rented = new SimpleBooleanProperty(rented);
        this.client_name = new SimpleStringProperty(name);
    }

    public long getPumpcreteID() {return pumpcrete_id.get();}
    public String getPumpcreteDesc(){return description.get();}
    public String getPumpcretePlateNo() {return plate_no.get();}
    public String getPumpcreteFuel() {return fuel_type.get();}
    public LocalDate getPurchase_date() {return purchase_date;}
    public long getPumpcreteCR(){return cr_no.get();}
    public long getPumpcreteOR(){ return or_no.get();}
    public int getPumpcreteTires() {return tires.get();}
    public boolean getPumpcreteRented() {return rented.get();}
    public String getPumpcreteClientName() {return client_name.getName();}
}
