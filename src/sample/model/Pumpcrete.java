package sample.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.Controller;

import java.time.LocalDate;

public class Pumpcrete {
    public SimpleLongProperty id;
    public SimpleStringProperty description;
    public SimpleStringProperty plate_no;
    public SimpleStringProperty fuel_type;
    public LocalDate purchase_date;
    public SimpleLongProperty cr_no;
    public SimpleLongProperty or_no;
    public SimpleIntegerProperty tires;
    public Postgresql p = new Postgresql();


    public Pumpcrete(long id, String desc, String plate, String fuel, LocalDate purchase_date, long cr, long or, int tires){
        this.id = new SimpleLongProperty(id);
        this.description = new SimpleStringProperty(desc);
        this.plate_no = new SimpleStringProperty(plate);
        this.fuel_type = new SimpleStringProperty(fuel);
        this.purchase_date = purchase_date;
        this.cr_no = new SimpleLongProperty(cr);
        this.or_no = new SimpleLongProperty(or);
        this.tires = new SimpleIntegerProperty(tires);
    }

    public long getId() {return id.get();}
    public String getDescription(){return description.get();}
    public String getPlate_no() {return plate_no.get();}
    public String getFuel_type() {return fuel_type.get();}
    public LocalDate getPurchase_date() {return purchase_date;}
    public long getCr_no(){return cr_no.get();}
    public long getOr_no(){ return or_no.get();}
    public int getTires() {return tires.get();}
}
