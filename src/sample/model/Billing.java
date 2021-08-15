package sample.model;

import java.sql.Date;

public class Billing {

    public int bill_no;
    public Date date_doc;
    public String client_name;
    public String project_name;
    public String project_add;
    public Date date_used;
    public int PSC_id;
    public String concrete_struct;
    public int floor;
    public float quantity;
    public float unit_price;
    public float total;
    public float grand_total;
    public boolean posted;
    public String checked_by;
    public String approved_by;
    public String posted_by;
    public String received_by;

    public Billing(){
    }
}