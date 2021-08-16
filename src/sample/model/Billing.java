package sample.model;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Billing {

    public LocalDate date_doc;
    public SimpleIntegerProperty client_id;
    public SimpleStringProperty project_name;
    public SimpleStringProperty project_add;
    public LocalDate date_used;
    public SimpleIntegerProperty PSC_id;
    public SimpleStringProperty concrete_struct;
    public SimpleIntegerProperty floor;
    public SimpleFloatProperty quantity;
    public SimpleFloatProperty unit_price;
    public SimpleFloatProperty total;
    public SimpleFloatProperty grand_total;
    public SimpleBooleanProperty posted;
    public SimpleStringProperty checked_by;
    public SimpleStringProperty approved_by;
    public SimpleStringProperty posted_by;
    public SimpleStringProperty received_by;

    public Billing(int client_id, String project_name, String project_add, LocalDate date_used,
                   int PSC_id, String concrete_struct, int floor, float quantity,
                   float unit_price, float total, float grand_total, boolean posted) {
        this.date_doc = LocalDate.now();
        this.client_id = new SimpleIntegerProperty(client_id);
        this.project_name = new SimpleStringProperty(project_name);
        this.project_add = new SimpleStringProperty(project_add);
        this.date_used = date_used;
        this.PSC_id = new SimpleIntegerProperty(PSC_id);
        this.concrete_struct = new SimpleStringProperty(concrete_struct);
        this.floor = new SimpleIntegerProperty(floor);
        this.quantity = new SimpleFloatProperty(quantity);
        this.unit_price = new SimpleFloatProperty(unit_price);
        this.total = new SimpleFloatProperty(total);
        this.grand_total = new SimpleFloatProperty(grand_total);
        this.posted = new SimpleBooleanProperty(posted);
    }
    public Billing(int client_id, String project_name, String project_add, LocalDate date_used,
                   int PSC_id, String concrete_struct, int floor, float quantity,
                   float unit_price, float total, float grand_total, boolean posted,
                   String checked_by, String approved_by, String posted_by, String received_by){
        this.date_doc = LocalDate.now();
        this.client_id = new SimpleIntegerProperty(client_id);
        this.project_name = new SimpleStringProperty(project_name);
        this.project_add = new SimpleStringProperty(project_add);
        this.date_used = date_used;
        this.PSC_id = new SimpleIntegerProperty(PSC_id);
        this.concrete_struct = new SimpleStringProperty(concrete_struct);
        this.floor = new SimpleIntegerProperty(floor);
        this.quantity = new SimpleFloatProperty(quantity);
        this. unit_price = new SimpleFloatProperty(unit_price);
        this.total = new SimpleFloatProperty(total);
        this.grand_total = new SimpleFloatProperty(grand_total);
        this.posted = new SimpleBooleanProperty(posted);
        if (this.posted.get()){
            this.checked_by = new SimpleStringProperty(checked_by);
            this.approved_by = new SimpleStringProperty(approved_by);
            this.posted_by = new SimpleStringProperty(posted_by);
            this.received_by = new SimpleStringProperty(received_by);
        }

    }

    public LocalDate getDate_doc() {
        return date_doc;
    }

    public LocalDate getDate_used() {
        return date_used;
    }

    public float getGrand_total() {
        return grand_total.get();
    }

    public float getQuantity() {
        return quantity.get();
    }

    public float getTotal() {
        return total.get();
    }

    public float getUnit_price() {
        return unit_price.get();
    }

    public int getClient_id() {
        return client_id.get();
    }

    public int getFloor() {
        return floor.get();
    }

    public int getPSC_id() {
        return PSC_id.get();
    }

    public String getApproved_by() {
        return approved_by.get();
    }

    public String getChecked_by() {
        return checked_by.get();
    }

    public String getConcrete_struct() {
        return concrete_struct.get();
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

    public String getReceived_by() {
        return received_by.get();
    }
    public Boolean getPosted(){
        return posted.get();
    }
}