package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Collection {
    public SimpleLongProperty collection_no;
    public LocalDate date; //date documented
    public SimpleLongProperty client_id;
    public ArrayList<String> billing_id;
    public SimpleBooleanProperty posted;
    public SimpleFloatProperty grand_total;
    public SimpleIntegerProperty check_number;
    public LocalDate check_date;
    public SimpleStringProperty bank;
    public SimpleStringProperty posted_by;
    public SimpleStringProperty edited_by;

    public Collection(long collection, LocalDate date, long client, String billing,
                      boolean posted, float grand_total, int check_no,
                      LocalDate check_date, String bank, String edited_by){
        this.collection_no = new SimpleLongProperty(collection);
        this.date= date;
        this.client_id= new SimpleLongProperty(client);
        for (int i =0; i < billing.split(",").length; i++){
            billing_id.add(billing.split(",")[i]);
        }
        this.posted = new SimpleBooleanProperty(posted);
        this.grand_total = new SimpleFloatProperty(grand_total);
        this.check_number = new SimpleIntegerProperty(check_no);
        this.check_date = check_date;
        this.bank = new SimpleStringProperty(bank);
        this.edited_by = new SimpleStringProperty(edited_by);
    }

    public Collection(long collection, LocalDate date, long client, String billing,
                      boolean posted, float grand_total, int check_no, LocalDate check_date, String bank,
                      String edited_by, String posted_by){
        this.collection_no = new SimpleLongProperty(collection);
        this.date= date;
        this.client_id= new SimpleLongProperty(client);
        for (int i =0; i < billing.split(",").length; i++){
            billing_id.add(billing.split(",")[i]);
        }
        this.posted = new SimpleBooleanProperty(posted);
        this.grand_total = new SimpleFloatProperty(grand_total);
        this.check_number = new SimpleIntegerProperty(check_no);
        this.check_date = check_date;
        this.bank = new SimpleStringProperty(bank);
        this.edited_by = new SimpleStringProperty(edited_by);
        this.posted_by = new SimpleStringProperty(posted_by);
    }



    public long getCollection_no() {
        return collection_no.get();
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<String> getBilling_id() {
        return billing_id;
    }

    public long getClient_id() {
        return client_id.get();
    }

    public boolean getPosted() {
        return posted.get();
    }

    public LocalDate getCheck_date() {
        return check_date;
    }

    public int getCheck_number() {
        return check_number.get();
    }

    public String getBank() {
        return bank.get();
    }

    public float getGrand_total() {
        return grand_total.get();
    }
}
