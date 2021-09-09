package sample.model;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;





public class Postgresql {

    public Postgresql(){
    }

    public static Connection loginUser(String u_username, String u_password)
    {
        String url = "jdbc:postgresql:Pumpcrete";

        try
           {
               Connection con = DriverManager.getConnection(url,u_username, u_password);
                return con;
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

            return null;

        }
    }


    public static String getCurrUser(Connection con)
    {
        String query = "SELECT current_user";
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String user = result.getString("current_user");
                return user;
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
        return "";
    }

    public static void endConnection(Connection con) throws SQLException {
        con.close();
    }

    //Getting info of current user
    public ObservableList<User> getUser(Connection connection, String uname)
    {
        System.out.println(" Inside get user!");
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            System.out.println("Query executed!");
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, uname);
            ObservableList<User> u_result = FXCollections.observableArrayList();
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                System.out.println("result found!");
                String username = result.getString("username");
                String email = result.getString("email");
                String first_name = result.getString("first_name");
                String middle_name = result.getString("middle_name");
                String last_name= result.getString("last_name");
                String password = result.getString("password");
                String role = result.getString("role");

                User u = new User(username,email,first_name, middle_name, last_name,password,role);
                u_result.add(u);

                return u_result;

            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
        return null;
    }


    public static ArrayList<User> getAllStaff()
    {
        String url = "jdbc:postgresql:Pumpcrete";

        String query = "SELECT * FROM users WHERE role = 'staff'";

        try (Connection con = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement pst = con.prepareStatement(query)) {

            ArrayList<User> u_result = new ArrayList<User>();
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String email = result.getString("email");
                String first_name = result.getString("first_name");
                String middle_name = result.getString("middle_name");
                String last_name= result.getString("last_name");
                String password = result.getString("password");
                String role = result.getString("role");

                User u = new User(username,email,first_name, middle_name, last_name,password,role);
                u_result.add(u);
            }

            return u_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static ObservableList<User> getAllUsers(Connection con)
    {
        String query = "SELECT * FROM users";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ObservableList<User> u_result = FXCollections.observableArrayList();
            ResultSet result = pst.executeQuery();
            while (result.next()) {

                String username = result.getString("username");
                String email = result.getString("email");
                String first_name = result.getString("first_name");
                String middle_name = result.getString("middle_name");
                String last_name= result.getString("last_name");
                String password = result.getString("password");
                String role = result.getString("role");

                User u = new User(username,email,first_name, middle_name, last_name,password,role);
                u_result.add(u);
            }

            return u_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }


    public static String getRole(Connection con)
    {
        String query = "SELECT role FROM users WHERE username = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            String user = getCurrUser(con);
            ps.setString(1, user);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String role_Res = result.getString("role");
                return role_Res;
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
        return "";
    }

    public static boolean editPassword (Connection connection,String old, String npw){

        String DBPassword = "'"+npw+"'";
        String url = "jdbc:postgresql:Pumpcrete";
        String u_username = getCurrUser(connection);
        try (Connection con = DriverManager.getConnection(url, u_username,old)){
            String query = "ALTER USER "+u_username+" WITH PASSWORD "+DBPassword;
                PreparedStatement p = connection.prepareStatement(query);
                p.execute();

                String query2 = "UPDATE users SET password = ? WHERE username = ?";
                PreparedStatement ps = connection.prepareStatement(query2);
                ps.setString(1, DBPassword);
                ps.setString(2, u_username);
                ps.executeUpdate();

                con.close();
                p.close();
                ps.close();
                return  true;
        } catch (SQLException throwables) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Edit Password Failed");
            errorAlert.setContentText("Wrong password.");
            errorAlert.showAndWait();
            return false;
        }
    }


    //Once all information are verified, adds new user to the database.
    public static String createUser (Connection connection, String fname, String mname, String lname, String email, String uname, String role) {

        Random r = new Random();
        String u_password = "password" + Integer.toString(r.nextInt(9999) + 1);

        String DBRole = "";
        String DBPassword = "'" + u_password +"'";

        String query1 = "";
        String query2 = "INSERT INTO users(first_name, last_name, middle_name, username, password, email, role) VALUES (?,?,?,?,?,?,?)";
        switch (role)
        {
            case "Staff":
            case "staff":      DBRole = "staff_role"; break;
            case "Supervisor":
            case "supervisor": DBRole = "supervisor_role"; break;
            case "Admin":
            case "admin":      DBRole = "admin_role"; break;
            default:
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR: Invalid Role");
                errorAlert.setContentText("role is null");
                errorAlert.showAndWait();
        }
        query1 = "CREATE ROLE " +uname+ " with login password "+DBPassword+ "; Grant "+DBRole+" to " +uname+";";

            try {
                //This will ad user as postgres user
                PreparedStatement p = connection.prepareStatement(query1);
                p.execute();

                //This will keep user info in the 'users' table
                PreparedStatement ps = connection.prepareStatement(query2);
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, mname);
                ps.setString(4, uname);
                ps.setString(5, DBPassword);
                ps.setString(6, email);
                ps.setString(7, role);

                ps.executeUpdate();

                System.out.println("Insert Successful!");
                ps.close();
                p.close();

            } catch (SQLException ex) {
                Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        return u_password;
    }


    public void editUser (Connection con, String old, String uname, String fname, String mname, String lname, String email, String role){

        String query = "UPDATE users SET first_name = ?, middle_name = ?, last_name = ?, email = ?, role = ?, username = ? WHERE username = ?";
        String query1= "ALTER ROLE "+old+" RENAME TO " + uname;

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            if (!old.equals(uname)) {
                PreparedStatement p = con.prepareStatement(query1);
                p.execute();
            }
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, fname);
            ps.setString(2, mname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, role);
            ps.setString(6, uname);
            ps.setString(7, old);

            ps.executeUpdate();

            System.out.println("Edit success!");

		} catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public boolean deleteUser (Connection connection, String uname){

        if (uname.equals(getCurrUser(connection).toString())){
            return false;
        }
        else {
            String query1 = "DROP ROLE " + uname;

            String query2 = "DELETE FROM users WHERE username = ?";

            String url = "jdbc:postgresql:Pumpcrete";

            try {
                PreparedStatement ps = connection.prepareStatement(query1);

                ps.execute();

                PreparedStatement p = connection.prepareStatement(query2);

                p.setString(1, uname);

                p.executeUpdate();


                System.out.println("User deleted!");

            } catch (SQLException ex) {
                Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }


    //Once all information are verified, adds new user to the database.
    public static void createClient (Connection connection, String fname, String lname, String position,
                                     String address,String landline, String cpnum, String email){


        String query = "INSERT INTO client(client_first_name, client_last_name, client_position, client_address," +
                "client_landline, client_cellphone, client_email) VALUES (?,?,?,?,?,?,?)";

        //Date date = new Date(20000101);

        try{
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, position);
            ps.setString(4, address);
            ps.setString(5, landline);
            ps.setString(6, cpnum);
            ps.setString(7, email);
            //ps.setDate(7, date);

            ps.executeUpdate();

            System.out.println("Insert Successful!");
        } catch (SQLException ex){
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editClient (Connection connection, long c_id, String fname, String lname, String position,
                            String address, String landline, String cpnum, String email){
        System.out.println("Inside edit");

        String query = "UPDATE client SET client_position = ?, client_first_name = ?, client_last_name = ?, client_landline = ?, client_cellphone = ?, client_email = ?, client_address = ? WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";
        //Date date = new Date(20000101);

        try{
            System.out.println("try");
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, position);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, landline);
            ps.setString(5, cpnum);
            ps.setString(6, email);
            ps.setString(7,address);
            ps.setLong(8, c_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("catch");
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void deleteClient (Connection connection, long c_id){

        String query = "DELETE FROM client WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, c_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ObservableList<Client> getClient (Connection connection, long c_id){

        String query = "SELECT * FROM client WHERE client_id = ?";
        ObservableList<Client> c_result = FXCollections.observableArrayList();
        Client c;

        try {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setLong(1, c_id);
                ResultSet result = ps.executeQuery();


//                query = "SELECT * FROM contact_details WHERE contact_id = ?";
//                System.out.println(query);
//                ps.setInt(1, c_id);
//                ResultSet result = ps.executeQuery();

                result.next();
                long id = result.getLong("client_id");
                String fname = result.getString("client_first_name");
                String lname = result.getString("client_last_name");
                String position = result.getString("client_position");
                String cpnum = result.getString("client_cellphone");
                String landline = result.getString("client_landline");
                String email = result.getString("client_email");
                String address = result.getString("client_address");
                Date date = result.getDate("latest_doc_date");

                if (date != null)
                    c = new Client(id, position, fname, lname, cpnum, email, address, landline, date.toLocalDate());
                else
                    c = new Client(id, position, fname, lname, cpnum, email, address, landline);
                c_result.add(c);
                return c_result;
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Postgresql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }
    }

    public long getClientID (Connection connection, String fName, String lName){

        String query = "SELECT * FROM client WHERE client_first_name= ?, client_last_name = ?";
        ObservableList<Client> c_result = FXCollections.observableArrayList();
        Client c;

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, fName);
            ps.setString(2, lName);

            ResultSet result = ps.executeQuery();

            result.next();
            return result.getLong("c_id");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return -1;
        }
    }


    public static ObservableList<Client> getAllClients(Connection con)
    {
        Client c;

        String query = "SELECT * FROM client ORDER BY client_id";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ObservableList<Client> c_result = FXCollections.observableArrayList();
            ResultSet result = pst.executeQuery();
            while (result.next()) {

                long id = result.getLong("client_id");
                String fname = result.getString("client_first_name");
                String lname = result.getString("client_last_name");
                String position = result.getString("client_position");
                String cpnum = result.getString("client_cellphone");
                String landline = result.getString("client_landline");
                String email = result.getString("client_email");
                String address = result.getString("client_address");
                if(result.getDate("latest_doc_date") != null) {
                    Date date = result.getDate("latest_doc_date");
                    c = new Client(id, position, fname, lname, cpnum, email, address, landline, date.toLocalDate());
                    c_result.add(c);
                }
                else {
                    c = new Client(id, position, fname, lname, cpnum, email, address, landline);
                    c_result.add(c);
                }


            }

            return c_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static ObservableList<String> getAllClientNames(Connection con)
    {
        String query = "SELECT client_first_name,client_last_name,client_id FROM client";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ObservableList<String> cn_result = FXCollections.observableArrayList();
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Long id = result.getLong("client_id");
                String fname = result.getString("client_first_name");
                String lname = result.getString("client_last_name");
                String name = fname+" "+lname;
                System.out.println(name);
                cn_result.add(name);
            }

            return cn_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }



    public void addBilling (Connection connection, String client_name, String project_name, String project_add,
                            Date date_doc, int PSC_id, Date date_used, int floor, float qty, float unit_price, String struct, float total){

        String query = "INSERT INTO billings(date_doc, date_used, client_name, project_name, project_add, PSC_id, posted,edited_by, floor_level, " +
                "qty, unit_price, conc_structure, total, in_payments, is_paid) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String edited_by = getCurrUser(connection);

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDate(1, date_doc);
            ps.setDate(2, date_used);
            ps.setString(3, client_name);
            ps.setString(4, project_name);
            ps.setString(5, project_add);
            ps.setInt(6, PSC_id);
            ps.setBoolean(7, false);
            ps.setString(8, edited_by);
            ps.setInt(9, floor);
            ps.setFloat(10, qty);
            ps.setFloat(11,unit_price);
            ps.setString(12,struct);
            ps.setFloat(13, total);
            ps.setBoolean(14, false);
            ps.setBoolean(15, false);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editBilling (Connection connection, String client_name, String project_name, String project_add,
                            Date date_doc, int PSC_id, Date date_used, int floor, float qty, float unit_price,
                             String struct, float total, long bill_no, String edited_by){

        String query = "UPDATE billings SET date_used=?, client_name=?, project_name=?, project_add=?, PSC_id=?, " +
                "posted=?,edited_by=?, floor_level=?, qty=?, unit_price=?, conc_structure=?, total=?, date_doc =?, " +
                "in_payments=?, is_paid=? WHERE bill_no=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDate(1, date_used);
            ps.setString(2, client_name);
            ps.setString(3, project_name);
            ps.setString(4, project_add);
            ps.setInt(5, PSC_id);
            ps.setBoolean(6, false);
            ps.setString(7, edited_by);
            ps.setInt(8, floor);
            ps.setFloat(9, qty);
            ps.setFloat(10, unit_price);
            ps.setString(11, struct);
            ps.setFloat(12, total);
            ps.setDate(13, date_doc);
            ps.setBoolean(14, false);
            ps.setBoolean(15, false);
            ps.setFloat(16, bill_no);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBillingClient(Connection connection, String ogName, String fullname) {
        String query = "UPDATE billings SET client_name = ? WHERE client_name = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, fullname);
            ps.setString(2, ogName);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Billing getBilling(Connection connection, long billing_id) {
        String query = "SELECT * from billings WHERE bill_no = ?";

        //ObservableList<Billing> b_result = FXCollections.observableArrayList();
        System.out.println("in billing");
        try{
            System.out.println("in try");
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,billing_id);
            ResultSet result = ps. executeQuery();

            result.next();
            long id = result.getLong("bill_no");
            String client_name = result.getString("client_name");
            String project_name = result.getString("project_name");
            String project_add = result.getString("project_add");
            Date date_doc = result.getDate("date_doc");
            Date date_used = result.getDate("date_used");
            long PSC_id = result.getLong("PSC_id");
            float total = result.getFloat("total");
            boolean posted = result.getBoolean("posted");
            String edited_by = result.getString("edited_by");
            String posted_by = result.getString("posted_by");
            long floor_level = result.getLong("floor_level");
            float qty = result.getFloat("qty");
            float unit_price = result.getFloat("unit_price");
            String struct = result.getString("conc_structure");


            Billing b = new Billing(id, client_name,project_name, project_add, date_doc.toLocalDate(), date_used.toLocalDate(),
                    PSC_id,struct, floor_level, qty, unit_price, total, posted, edited_by, posted_by);

            return b;


        } catch (SQLException ex) {
            System.out.println("in catch");
            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public ObservableList<Long> getBillNosByName(Connection connection, String name) {
        String query = "SELECT * from billings WHERE client_name = ? AND posted = true " +
                "AND in_payments = false AND is_paid = false";

        ObservableList<Long> b_result = FXCollections.observableArrayList();
        System.out.println("in billing name");
        try{
            System.out.println("in try name");
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,name);
            ResultSet result = ps. executeQuery();

            while(result.next()) {
                long id = result.getLong("bill_no");
                b_result.add(id);
            }

            return b_result;


        } catch (SQLException ex) {
            System.out.println("in catch");
            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }


    public ObservableList<Billing> getAllBillings(Connection con) {
        String query = "SELECT * FROM billings ORDER BY bill_no";

        ObservableList<Billing> billings = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()){
                long id = results.getLong("bill_no");
                String client_name = results.getString("client_name");
                String project_name = results.getString("project_name");
                String project_add = results.getString("project_add");
                Date date_doc = results.getDate("date_doc");
                Date date_used = results.getDate("date_used");
                long PSC_id = results.getLong("PSC_id");
                float total = results.getFloat("total");
                boolean posted = results.getBoolean("posted");
                String edited_by = results.getString("edited_by");
                String posted_by = results.getString("posted_by");
                long floor_level = results.getLong("floor_level");
                float qty = results.getFloat("qty");
                float unit_price = results.getFloat("unit_price");
                String struct = results.getString("conc_structure");

                Billing b = new Billing(id, client_name, project_name, project_add, date_doc.toLocalDate(), date_used.toLocalDate(),
                        PSC_id,struct, floor_level, qty, unit_price, total, posted, edited_by, posted_by);
                billings.add(b);
                System.out.println(billings.size());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
            return billings;
        }
        return billings;
    }

    public void postBilling (Connection connection, long id, String posted_by) {

        String query = "UPDATE billings SET posted = ?, posted_by = ? WHERE bill_no = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setBoolean(1, true);
            ps.setString(2, posted_by);
            ps.setLong(3,id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public ObservableList<Billing> getClientBillings(ArrayList<String> billings){
//
//    }

    public String resetPassword(Connection connection, String uname, String old) {
        Random r = new Random();
        String password = "'password" + (r.nextInt(9999)+1) +"'";
        String query1 = "ALTER USER "+uname+" WITH PASSWORD "+ password;
        String query2 = "UPDATE users SET password = ? WHERE username = ?";
        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement p = connection.prepareStatement(query1);
            p.execute();

            PreparedStatement ps = connection.prepareStatement(query2);
            ps.setString(1, password);
            ps.setString(2, uname);

            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return password;
    }


    //checks if there are the same existing username found in the db
    public boolean checkUsername(Connection connection, String uname){

        ResultSet rs;
        boolean username_exist = false;

        String query = "SELECT * FROM users WHERE username = ?";

        String url = "jdbc:postgresql:Pumpcrete";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, uname.toLowerCase().trim());
            rs = ps.executeQuery();

            if(rs.next())
            {
                username_exist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(username_exist)
            System.out.println("Username is not unique!");
        else
            System.out.println("Username is unique!");

        return username_exist;
    }

    public boolean checkName(Connection connection, String name){
        ResultSet rs;
        boolean name_exist = false;

        String query = "SELECT * FROM client WHERE client_name = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            rs = ps.executeQuery();

            if(rs.next())
            {
                name_exist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name_exist;
    }

//Checks if billing psc and client
    public boolean checkBillingPSC(Connection connection, long id){
        ResultSet rs;
        boolean billing_exist = false;

        String query = "SELECT * FROM billings WHERE psc_id = ? ";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setLong(1, id);
            rs = ps.executeQuery();

            if(rs.next())
            {
                billing_exist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return billing_exist;
    }


    public boolean checkPumpcrete (Connection connection, String plate, long cr, long or)
    {

        Boolean pumpcrete_exist = false;
        //check plate_no
        try {
            String query = "SELECT * FROM pumpcrete WHERE plate_no = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pumpcrete_exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        //check cr
        try {
            String query = "SELECT * FROM pumpcrete WHERE cr_no = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setLong(1, cr);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pumpcrete_exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        //check or
        try {
            String query = "SELECT * FROM pumpcrete WHERE or_no = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setLong(1, or);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pumpcrete_exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }


        return pumpcrete_exist;
    }
    public void addPumpcrete (Connection connection, String desc, String plate, String fuel, Date date,
                              long cr, long or, int tires) {

        String query = "INSERT INTO pumpcrete( description, plate_no, fuel_type, purchase_date, cr_no, or_no, tires) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, desc);
            ps.setString(2, plate);
            ps.setString(3, fuel);
            ps.setDate(4, date);
            ps.setLong(5,cr);
            ps.setLong(6, or);
            ps.setInt(7, tires);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public ObservableList<Pumpcrete> getAllinventory (Connection connection) {

        ObservableList<Pumpcrete> p = FXCollections.observableArrayList();
        String query = "SELECT * FROM pumpcrete ORDER BY pumpcrete_id";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                long id = rs.getLong("pumpcrete_id");
                System.out.println(id);
                String desc = rs.getString("description");
                String plate = rs.getString("plate_no");
                String fuel = rs.getString("fuel_type");
                LocalDate date = rs.getDate("purchase_date").toLocalDate();
                long cr = rs.getLong("cr_no");
                long or = rs.getLong("or_no");
                int tires = rs.getInt("tires");

                p.add(new Pumpcrete(id, desc, plate, fuel, date, cr, or, tires));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public void deletePumpcrete (Connection con, long id){
        String query = "DELETE FROM pumpcrete WHERE pumpcrete_id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong (1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Pumpcrete> getPumpcrete (Connection connection, long id) {

        ObservableList<Pumpcrete> p = FXCollections.observableArrayList();
        String query = "SELECT * FROM pumpcrete where pumpcrete_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            Long p_id = rs.getLong("pumpcrete_id");
            String desc = rs.getString("description");
            String plate = rs.getString("plate_no");
            String fuel = rs.getString("fuel_type");
            LocalDate date = rs.getDate("purchase_date").toLocalDate();
            long cr = rs.getLong("cr_no");
            long or = rs.getLong("or_no");
            int tires = rs.getInt("tires");

            p.add(new Pumpcrete(p_id, desc, plate, fuel, date, cr, or, tires));

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public void editPumpcrete(Connection con, long id, String desc, String plate, String fuel, Date date, long cr, long or, int tires) {
        try {
            String query = "UPDATE pumpcrete SET description=?, plate_no=?, fuel_type=?,"
                    + "purchase_date=?, cr_no =?, or_no=?, tires=? WHERE pumpcrete_id = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,desc);
            ps.setString(2,plate);
            ps.setString(3,fuel);
            ps.setDate(4,date);
            ps.setLong(5,cr);
            ps.setLong(6,or);
            ps.setInt(7,tires);
            ps.setLong(8,id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Collection> getCollection(Connection con, long editCollection) {
        ObservableList<Collection> c = FXCollections.observableArrayList();
        String query = "SELECT * FROM collections where collection_no = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1,editCollection);
            ResultSet rs = ps.executeQuery();

            rs.next();

            long collection_no = rs.getLong("collection_no");
            LocalDate date = rs.getDate("date").toLocalDate();
            String client_name = rs.getString("client_name");
            String billing_id = rs.getString("billing_id");
            boolean posted = rs.getBoolean("posted");
            float grand_total = rs.getFloat("grand_total");
            int check_number = rs.getInt("check_number");
            LocalDate check_date = rs.getDate("check_date").toLocalDate();
            String bank = rs.getString("bank");;
            String edited_by = rs.getString("posted_by");;
            String posted_by = rs.getString("edited_by");;

            c.add(new Collection(collection_no, date, client_name, billing_id, posted, grand_total,
                    check_number, check_date, bank, edited_by, posted_by));

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public ObservableList getAllCollections(Connection con) {
        ObservableList<Collection> c = FXCollections.observableArrayList();
        String query = "SELECT * FROM collections";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                long collection_no = rs.getLong("collection_no");
                LocalDate date = rs.getDate("date").toLocalDate();
                String client_name = rs.getString("client_name");

                String billing_id = rs.getString("billing_id");
                boolean posted = rs.getBoolean("posted");
                float grand_total = rs.getFloat("grand_total");
                int check_number = rs.getInt("check_number");
                LocalDate check_date = rs.getDate("check_date").toLocalDate();
                String bank = rs.getString("bank");

                String edited_by = rs.getString("posted_by");

                String posted_by = rs.getString("edited_by");


                c.add(new Collection(collection_no, date, client_name, billing_id, posted, grand_total,
                        check_number, check_date, bank, edited_by, posted_by));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public long getPRNumber (Connection con){
        String query = "SELECT MAX(collection_no) FROM collections";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            rs.next();
            long pr_no = rs.getInt("collection_no")+1;

            return pr_no;

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void addCollection(Connection con, String date, String client,
                              String id, boolean posted, float total,
                              String bank, long c_no,  String c_date){
        String query = "INSERT INTO collections (date, client_name, bill_no, "+
                "posted, grand_total, check_number, check_date, bank)" +
                "values(?,?,?,?,?,?,?,?)";
        Date datedoc = Date.valueOf(date);
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDate(1,datedoc);
            ps.setString(2, client);
            ps.setString(3, id);
            ps.setBoolean(4, posted);
            ps.setFloat(5, total);
            ps.setString(7, bank);
            ps.setLong(8, c_no);
            ps.setString(9,c_date);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}





//    //Once all information are verified, adds new user to the database.
//    public static void createContact (Connection connection, String fname, String lname, int cpnum, String email, String address, int landline){
//
//        String url = "jdbc:postgresql:Pumpcrete";
//
//        String query = "INSERT INTO contact_details(first_name, last_name, landline_num, cellphone_num, email, c_address) VALUES (?,?,?,?,?,?)";
//
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//
//
//
//            ps.executeUpdate();
//
//            System.out.println("Insert Successful!");
//        } catch (SQLException ex){
//            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//        }
//    }
//
//    public void editContact(Connection connection, int u_id, String fname, String lname, int cpnum, String email, String address, int landline){
//
//        String query = "UPDATE contact_details SET first_name = ?, last_name = ?, landline_num = ?, cellphone_num = ?, email = ?, c_address = ? WHERE contact_id = ?";
//
//        String url = "jdbc:postgresql:Pumpcrete";
//
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//
//            ps.setString(1, fname);
//            ps.setString(2, lname);
//            ps.setInt(3, landline);
//            ps.setInt(4, cpnum);
//            ps.setString(5, email);
//            ps.setString(6, address);
//
//            ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void deleteContact (int c_id) {
//
//        String query = "DELETE FROM contact_details WHERE contact_id = ?";
//
//        String url = "jdbc:postgresql:Pumpcrete";
//
//        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
//             PreparedStatement ps = connection.prepareStatement(query)) {
//
//            ps.setInt(1, c_id);
//
//            ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
