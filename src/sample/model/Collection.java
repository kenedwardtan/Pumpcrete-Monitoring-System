package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Collection {
    public SimpleLongProperty collection_no;
    public LocalDate date; //date documented
    public SimpleLongProperty client_id;
    public SimpleLongProperty pumpcrete_id;
    public SimpleLongProperty billing_id;
    public SimpleBooleanProperty posted;
    public SimpleFloatProperty grand_total;
    public SimpleIntegerProperty check_number;
    public LocalDate check_date;
    public SimpleStringProperty bank;

    public Collection(long collection, LocalDate date, long client, long pumpcrete, long billing,
                      boolean posted, float grand_total, int check_no, LocalDate check_date, String bank){
        this.collection_no = new SimpleLongProperty(collection);
        this.date= date;
        this.client_id= new SimpleLongProperty(client);
        this.pumpcrete_id = new SimpleLongProperty(pumpcrete);
        this.billing_id = new SimpleLongProperty(billing);
        this.posted = new SimpleBooleanProperty(posted);
        this.grand_total = new SimpleFloatProperty(grand_total);
        this.check_number = new SimpleIntegerProperty(check_no);
        this.check_date = check_date;
        this.bank = new SimpleStringProperty(bank);
    }



    public long getCollection_no() {
        return collection_no.get();
    }

    public LocalDate getDate() {
        return date;
    }

    public long getBilling_id() {
        return billing_id.get();
    }

    public long getPumpcrete_id() {
        return pumpcrete_id.get();
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
