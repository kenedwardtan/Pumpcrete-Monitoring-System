package sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Billing {
    public SimpleIntegerProperty bill_no;
    public LocalDate date_doc;
    public SimpleStringProperty client_name;
    public SimpleStringProperty project_name;
    public SimpleStringProperty project_add;
  //  public LocalDate date_used;
    public SimpleIntegerProperty PSC_id;
    public SimpleFloatProperty total;
    public SimpleBooleanProperty posted;
    public SimpleStringProperty filled_by;
    public SimpleStringProperty posted_by;

    public Billing (int bill_no, String client_name, String project_name, String project_add, LocalDate date_doc,
                    int psc_id, float total,boolean posted,String filled_by,String posted_by){

        this.bill_no = new SimpleIntegerProperty(bill_no);
        this.date_doc = date_doc;
     //   this.date_used =date_used;
        this.client_name = new SimpleStringProperty(client_name);
        this.project_name = new SimpleStringProperty(project_name);
        this.project_add = new SimpleStringProperty(project_add);
        this.PSC_id = new SimpleIntegerProperty(psc_id);
        this.total = new SimpleFloatProperty(total);
        this.posted = new SimpleBooleanProperty(posted);
        this.filled_by = new SimpleStringProperty(filled_by);
        this.posted_by= new SimpleStringProperty(posted_by);
    }


    public LocalDate getDate_doc() {
        return date_doc;
    }


    public float getTotal() {
        return total.get();
    }


    public int getPSC_id() {
        return PSC_id.get();
    }

    public String getFilled_by() {
        return filled_by.get();
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

    public Integer getBillNo() {return bill_no.get();}

    public String getClientName (){return client_name.get();}
}