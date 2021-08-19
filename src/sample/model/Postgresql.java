package sample.model;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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

    public static void editPassword (Connection connection,String old, String npw){

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
        } catch (SQLException throwables) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Edit Password Failed");
            errorAlert.setContentText("Wrong password.");
            errorAlert.showAndWait();
        }
    }


    //Once all information are verified, adds new user to the database.
    public static String createUser (Connection connection, String fname, String mname, String lname, String email, String uname, String role) {

        Random r = new Random();
        String u_password = "password" + Integer.toString(r.nextInt(9999) + 1);

        String DBRole = "";
        String DBPassword = "'" + u_password +"'";

        String query1 = "";
        String query2 = "INSERT INTO users(first_name, last_name, username, password, email, role) VALUES (?,?,?,?,?,?)";
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
                ps.setString(3, uname);
                ps.setString(4, u_password);
                ps.setString(5, email);
                ps.setString(6, role);

                ps.executeUpdate();

                System.out.println("Insert Successful!");
                ps.close();
                p.close();

            } catch (SQLException ex) {
                Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        return u_password;
    }


    public void editUser (Connection con, String uname, String fname, String mname, String lname, String email, String role){

        String query = "UPDATE users SET first_name = ?, middle_name = ?, last_name = ?, email = ?, role = ? WHERE username = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, fname);
            ps.setString(2, mname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, role);
            ps.setString(6, uname);

            ps.executeUpdate();

            System.out.println("Edit success!");

		} catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public void deleteUser (Connection connection, String uname){

        String query = "DELETE FROM users WHERE username = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, uname);

            ps.executeUpdate();

            System.out.println("User deleted!");

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //Once all information are verified, adds new user to the database.
    public static void createClient (Connection connection, String name, String position,
                                     String address,int landline, BigInteger cpnum, String email){


        String query = "INSERT INTO client(client_name, client_position, client_address," +
                "client_landline, client_cellphone, client_email) VALUES (?,?,?,?,?,?)";


        try{
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, position);
            ps.setString(3, address);
            ps.setInt(4, landline);
            ps.setInt(5, cpnum);
            ps.setString(6, email);

            ps.executeUpdate();

            System.out.println("Insert Successful!");
        } catch (SQLException ex){
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editClient (Connection connection, int c_id, String name, String position,
                            String address, int landline, int cpnum, String email){

        String query = "UPDATE client SET position = ? WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try{
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, position);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteClient (Connection connection, int c_id){

        String query = "DELETE FROM client WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, c_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ObservableList<Client> getClient (Connection connection, int c_id){

        String query = "SELECT * FROM client WHERE client_id = ?";
        ObservableList<Client> c_result = FXCollections.observableArrayList();

        try {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, c_id);
                ResultSet results = ps.executeQuery();


                query = "SELECT * FROM contact_details WHERE contact_id = ?";
                System.out.println(query);
                ps.setInt(1, c_id);
                ResultSet result = ps.executeQuery();

                int id = result.getInt("client_id");
                String name = result.getString("client_name");
                String position = result.getString("client_position");
                int cpnum = result.getInt("client_cellphone");
                int landline = result.getInt("client_landline");
                String email = result.getString("client_email");
                String address = result.getString("client_address");
                String date = result.getString("latest_doc_date");

                Client c = new Client(id, position, name, cpnum, email, address, landline, date);
                c_result.add(c);
                return c_result;
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Postgresql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }
    }


    public static ObservableList<Client> getAllClients(Connection con)
    {
        String query = "SELECT * FROM client";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ObservableList<Client> c_result = FXCollections.observableArrayList();
            ResultSet result = pst.executeQuery();
            while (result.next()) {

                int id = result.getInt("client_id");
                String name = result.getString("client_name");
                String position = result.getString("client_position");
                int cpnum = result.getInt("client_cellphone");
                int landline = result.getInt("client_landline");
                String email = result.getString("client_email");
                String address = result.getString("client_address");
                String date = result.getString("latest_doc_date");

                Client c = new Client(id, position, name, cpnum, email, address, landline, date);
                c_result.add(c);
            }

            return c_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }



    //Once all information are verified, adds new user to the database.
    public static void createContact (Connection connection, String fname, String lname, int cpnum, String email, String address, int landline){

        String url = "jdbc:postgresql:Pumpcrete";

        String query = "INSERT INTO contact_details(first_name, last_name, landline_num, cellphone_num, email, c_address) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);



            ps.executeUpdate();

            System.out.println("Insert Successful!");
        } catch (SQLException ex){
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editContact(Connection connection, int u_id, String fname, String lname, int cpnum, String email, String address, int landline){

        String query = "UPDATE contact_details SET first_name = ?, last_name = ?, landline_num = ?, cellphone_num = ?, email = ?, c_address = ? WHERE contact_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setInt(3, landline);
            ps.setInt(4, cpnum);
            ps.setString(5, email);
            ps.setString(6, address);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteContact (int c_id) {

        String query = "DELETE FROM contact_details WHERE contact_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, c_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void addBilling (Connection connection, String client_name, String project_name, String project_add,
                            Date date_used, int PSC_id, String concrete_struct, int floor, float quantity,
                            float unit_price, float total, float grand_total){

        String query = "INSERT INTO billings(date_doc, client_name, project_name, project_add, date_used, PSC_id, concrete_struct, " +
                "floor, quantity, unit_price, total, grand_total, posted) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String url = "jdbc:postgresql:Pumpcrete";

        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDate(1, date);
            ps.setString(2, client_name);
            ps.setString(3, project_name);
            ps.setString(4, project_add);
            ps.setDate(5, date_used);
            ps.setInt(6, PSC_id);
            ps.setString(7, concrete_struct);
            ps.setInt(8, floor);
            ps.setFloat(9, quantity);
            ps.setFloat(10, unit_price);
            ps.setFloat(11, total);
            ps.setFloat(12, grand_total);
            ps.setBoolean(13, false);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public Billing getBilling(Connection conecttion, int billing_id) {
//        String query = "SELECT * FROM billings WHERE billing_id = ?";
//        String url = "jdbc:postgresql:Pumpcrete";
//
//        Billing b;
//
//        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
//             PreparedStatement ps = connection.prepareStatement(query)) {
//
//            ResultSet results = ps.executeQuery();
//
//            int id = results.getInt("bill_no");
//            int client_id = results.getInt("client_id");
//            String project_name = results.getString("project_name");
//            String project_add = results.getString("project_add");
//            Date date_used = results.getDate("date_used");
//            int PSC_id = results.getInt("PSC_id");
//            String concrete_struct = results.getString("concrete_struct");
//            int floor = results.getInt("floor");
//            float quantity = results.getFloat("quantity");
//            float unit_price = results.getFloat("unit_price");
//            float total = results.getFloat("total");
//            float grand_total = results.getFloat("grand_total");
//            boolean posted = results.getBoolean("posted");
//
//            if (posted){
//                String checked_by = results.getString("checked_by");
//                String approved_by = results.getString("approved_by");
//                String posted_by = results.getString("posted_by");
//                String received_by = results.getString("received_by");
//
//                b = new Billing(client_id, project_name, project_add,
//                        date_used.toLocalDate(), PSC_id, concrete_struct,
//                        floor, quantity, unit_price, total, grand_total, posted,
//                        checked_by, approved_by, posted_by, received_by, id);
//            }
//            else{
//                b = new Billing(client_id, project_name, project_add,
//                        date_used.toLocalDate(), PSC_id, concrete_struct,
//                        floor, quantity, unit_price, total, grand_total, posted,id);
//            }
//
//            return b;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }


    public ObservableList<Billing> getAllBillings(Connection con) {
        String query = "SELECT * FROM billings";

        ObservableList<Billing> billings = FXCollections.observableArrayList();
        Billing b ;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()){
               // int id = results.getInt("bill_no");
                int client_id = results.getInt("client_id");
                String project_name = results.getString("project_name");
                String project_add = results.getString("project_add");
                //Date date_doc = results.getDate("date_doc");
                //Date date_used = results.getDate("date_used");
//                int PSC_id = results.getInt("PSC_id");
//                String concrete_struct = results.getString("concrete_struct");
//                int floor = results.getInt("floor");
//                float quantity = results.getFloat("quantity");
//                float unit_price = results.getFloat("unit_price");
//                float total = results.getFloat("total");
//                float grand_total = results.getFloat("grand_total");
//                boolean posted = results.getBoolean("posted");
//                String checked_by = results.getString("checked_by");
//                String approved_by = results.getString("approved_by");
//                String posted_by = results.getString("posted_by");
//                String received_by = results.getString("received_by");

                b = new Billing(client_id, project_name, project_add);
                billings.add(b);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
            return billings;
        }
        return billings;
    }

    public void postBilling (Connection connection, String checked_by, String approved_by, String posted_by, String received_by) {

        String query = "UPDATE billings SET posted = ?, checked_by = ?, approved_by = ?, posted_by = ?, received_by = ? WHERE billing_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setBoolean(1, true);
            ps.setString(2, checked_by);
            ps.setString(3, approved_by);
            ps.setString(4, posted_by);
            ps.setString(5, received_by);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String resetPassword(Connection connection, String uname, String old) {
        Random r = new Random();
        String password = "password" + (r.nextInt(9999)+1);
        String query1 = "ALTER USER "+uname+" WITH PASSWORD "+ "'"+password+"'";
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

            ps.setString(1, uname.trim());
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
}

