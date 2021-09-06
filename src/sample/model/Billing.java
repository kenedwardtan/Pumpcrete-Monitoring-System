package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Billing {
    public SimpleLongProperty bill_no;
    public SimpleStringProperty client_name;
    public SimpleStringProperty project_name;
    public SimpleStringProperty project_add;
    public LocalDate date_doc;
    public LocalDate date_used;
    public SimpleLongProperty PSC_id;
    public SimpleStringProperty conc_structure;
    public SimpleLongProperty floor_level;
    public SimpleFloatProperty quantity;
    public SimpleFloatProperty unit_price;
    public SimpleFloatProperty total;
    public SimpleBooleanProperty posted;
    public SimpleStringProperty edited_by;
    public SimpleStringProperty posted_by;


    public Billing (long bill_no, String client_name, String project_name, String project_add, LocalDate date_doc,
                    LocalDate date_used, long psc_id, String conc_structure, long floor_level, float qty, float unit_price,
                    float total,boolean posted,String edited_by,String posted_by){

        this.bill_no = new SimpleLongProperty(bill_no);
        this.date_doc = date_doc;
        this.date_used =date_used;
        this.client_name = new SimpleStringProperty(client_name);
        this.project_name = new SimpleStringProperty(project_name);
        this.project_add = new SimpleStringProperty(project_add);
        this.PSC_id = new SimpleLongProperty(psc_id);
        this.total = new SimpleFloatProperty(total);
        this.posted = new SimpleBooleanProperty(posted);
        this.edited_by = new SimpleStringProperty(edited_by);
        this.posted_by= new SimpleStringProperty(posted_by);
        this.conc_structure = new SimpleStringProperty(conc_structure);
        this.floor_level = new SimpleLongProperty(floor_level);
        this.quantity = new SimpleFloatProperty(qty);
        this.unit_price = new SimpleFloatProperty(unit_price);
    }

    public String getEdited_by() {
        return edited_by.get();
    }

    public LocalDate getDate_doc() {
        return date_doc;
    }

    public float getTotal() {
        return total.get();
    }

    public long getPSC_id() {
        return PSC_id.get();
    }

    public String getPosted_by() {
        return posted_by.get();
    }

    public String getProject_add() {
        return project_add.get();
    }

    public String getProject_name() {
        return project_name.get();
    }

    public Boolean getPosted(){
        return posted.get();
    }

    public long getBill_no() {return bill_no.get();}

    public String getClientName (){return client_name.get();}

    public String getConc_structure() {return conc_structure.get();}

    public float getQty(){return quantity.get();}

    public float getUnit_price() {return unit_price.get();}

    public long getFloor_level(){return floor_level.get();}

    public String getClient_name(){return client_name.get();}


}