package sample.model;

import javafx.scene.control.Alert;

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
               Connection con = DriverManager.getConnection(url, u_username,u_password);
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
            return "No connected user";
        }
        return null;
    }

    public static void endConnection(Connection con) throws SQLException {
        con.close();
    }


    public static User getUser(String u_username)
    {
        String url = "jdbc:postgresql:Pumpcrete";

        String query = "SELECT current_user";

        try (Connection con = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, u_username);
            User u_result = new User();
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                u_result.username = result.getString("username");
                u_result.email = result.getString("email");
                u_result.first_name = result.getString("first_name");
                u_result.last_name= result.getString("last_name");
                u_result.password = result.getString("password");
                u_result.role = result.getString("role");


                if(u_username.contentEquals(u_result.username)){
                    System.out.println("Loading of Info Successful.");
                    return u_result;
                }
                else{
                    System.out.println("Loading of Info Failed.");
                    return null;
                }

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
                User u = new User();
                u.username = result.getString("username");
                u.email = result.getString("email");
                u.first_name = result.getString("first_name");
                u.last_name= result.getString("last_name");
                u.password = result.getString("password");
                u.role = result.getString("role");

                u_result.add(u);
            }

            return u_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static ArrayList<User> getAllUsers()
    {
        String url = "jdbc:postgresql:Pumpcrete";

        String query = "SELECT * FROM users";

        try (Connection con = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement pst = con.prepareStatement(query)) {

            ArrayList<User> u_result = new ArrayList<User>();
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                User u = new User();
                u.username = result.getString("username");
                u.email = result.getString("email");
                u.first_name = result.getString("first_name");
                u.last_name= result.getString("last_name");
                u.password = result.getString("password");
                u.role = result.getString("role");

                u_result.add(u);
            }

            return u_result;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Postgresql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    //To limit access to editing account details
    public static String getRole(Connection con)
    {
        String url = "jdbc:postgresql:Pumpcrete";

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




    //Once all information are verified, adds new user to the database.
    public static void createUser (Connection connection, String fname, String lname, String email, String uname, String role) {

        Random r = new Random();
        String u_password = "password" + Integer.toString(r.nextInt(9999) + 1);

        String DBRole = "";
        String DBPassword = "'" + u_password +"'";

        String query1 = "";
        String query2 = "INSERT INTO users(first_name, last_name, username, password, email, role) VALUES (?,?,?,?,?,?)";
        switch (role)
        {
            case "Staff":
            case "staff":     query1 = "CREATE ROLE " +uname+ " with login password "+DBPassword+ "; Grant staff_role to " +uname+";"; break;
            case "Supervisor":
            case "supervisor":query1 = "CREATE ROLE " +uname+ " with login password "+DBPassword+ "; Grant supervisor_role to " +uname+";"; break;
            case "Admin":
            case "admin":     query1 = "CREATE ROLE " +uname+ " with login password "+DBPassword+ "; Grant admin_role to " +uname+";"; break;
            default:
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR: Invalid Role");
                errorAlert.setContentText("role is null");
                errorAlert.showAndWait();
        }

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

    }


	
	//checks if there are the same existing username found in the db 
	public boolean checkUsername(String uname){

        ResultSet rs;
        boolean username_exist = false;

        String query = "SELECT * FROM users WHERE username = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, uname);
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
	
	public void editUser (String fname, String lname, String uname, String email, String role){
        boolean username_exist = false;

        String query = "UPDATE users SET first_name = ?, last_name = ?, uname = ?, email = ?, role = ? WHERE u_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, uname);
            ps.setString(4, email);
            ps.setString(5, role);

            ps.executeUpdate();

		} catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public void deleteUser (int u_id){
        boolean username_exist = false;

        String query = "DELETE FROM users WHERE  u_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, u_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //Once all information are verified, adds new user to the database.
    public static void createClient (String position){

        String url = "jdbc:postgresql:Pumpcrete";

        String query = "INSERT INTO client(position) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, position);

            ps.executeUpdate();

            System.out.println("Insert Successful!");
        } catch (SQLException ex){
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editClient (int c_id, String position){

        String query = "UPDATE client SET position = ? WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, position);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteClient (int c_id){

        String query = "DELETE FROM client WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, c_id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Client getClient (int c_id){
        String query = "SELECT * FROM client WHERE client_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {
            Client c = new Client();

            ps.setInt(1, c_id);
            ResultSet results = ps.executeQuery();

            c.position = results.getString("position");

            query = "SELECT * FROM contact_details WHERE contact_id = ?";

            ps.setInt(1, c_id);
            ResultSet result = ps.executeQuery();

            c.fname = results.getString("first_name");
            c.lname = results.getString("last_name");
            c.cpnum = results.getInt("cellphone_num");
            c.email = results.getString("email");
            c.address = results.getString("c_address");
            c.landline = results.getInt(";landline_num");

            return c;

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    //Once all information are verified, adds new user to the database.
    public static void createContact (String fname, String lname, int cpnum, String email, String address, int landline){

        String url = "jdbc:postgresql:Pumpcrete";

        String query = "INSERT INTO contact_details(first_name, last_name, landline_num, cellphone_num, email, c_address) VALUES (?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setInt(3, landline);
            ps.setInt(4, cpnum);
            ps.setString(5, email);
            ps.setString(6, address);

            ps.executeUpdate();

            System.out.println("Insert Successful!");
        } catch (SQLException ex){
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editContact(int u_id, String fname, String lname, int cpnum, String email, String address, int landline){

        String query = "UPDATE contact_details SET first_name = ?, last_name = ?, landline_num = ?, cellphone_num = ?, email = ?, c_address = ? WHERE contact_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

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

    public void deleteContact (int c_id){

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



    public void addBilling (String client_name, String project_name, String project_add, Date date_used,
                            int PSC_id, String concrete_struct, int floor, float quantity, float unit_price,
                            float total, float grand_total){

        String query = "INSERT INTO billings(date_doc, client_name, project_name, project_add, date_used, PSC_id, concrete_struct, " +
                "floor, quantity, unit_price, total, grand_total, posted) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String url = "jdbc:postgresql:Pumpcrete";

        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

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

    public Billing getBilling(int billing_id) {
        String query = "SELECT * FROM billings WHERE billing_id = ?";
        String url = "jdbc:postgresql:Pumpcrete";

        Billing b = new Billing();

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet results = ps.executeQuery();

            b.client_name = results.getString("client_name");
            b.project_name = results.getString("project_name");
            b.project_add = results.getString("project_add");
            b.date_used = results.getDate("date_used");
            b.PSC_id = results.getInt("PSC_id");
            b.concrete_struct = results.getString("concrete_struct");
            b.floor = results.getInt("floor");
            b.quantity = results.getFloat("quantity");
            b.unit_price = results.getFloat("unit_price");
            b.total = results.getFloat("total");
            b.grand_total = results.getFloat("grand_total");
            b.posted = results.getBoolean("posted");

            if (b.posted){
                b.checked_by = results.getString("checked_by");
                b.approved_by = results.getString("approved_by");
                b.posted_by = results.getString("posted_by");
                b.received_by = results.getString("received_by");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public ArrayList<Billing> getAllBillings() {
        String query = "SELECT * FROM billings";

        String url = "jdbc:postgresql:Pumpcrete";
        ArrayList<Billing> billings = new ArrayList<Billing>();

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet results = ps.executeQuery();

            while (results.next()){

                Billing b = new Billing();

                b.client_name = results.getString("client_name");
                b.project_name = results.getString("project_name");
                b.project_add = results.getString("project_add");
                b.date_used = results.getDate("date_used");
                b.PSC_id = results.getInt("PSC_id");
                b.concrete_struct = results.getString("concrete_struct");
                b.floor = results.getInt("floor");
                b.quantity = results.getFloat("quantity");
                b.unit_price = results.getFloat("unit_price");
                b.total = results.getFloat("total");
                b.grand_total = results.getFloat("grand_total");
                b.posted = results.getBoolean("posted");

                if (b.posted){
                    b.checked_by = results.getString("checked_by");
                    b.approved_by = results.getString("approved_by");
                    b.posted_by = results.getString("posted_by");
                    b.received_by = results.getString("received_by");
                }

                billings.add(b);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Postgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return billings;
    }

    public void postBilling (String checked_by, String approved_by, String posted_by, String received_by){

        String query = "UPDATE billings SET posted = ?, checked_by = ?, approved_by = ?, posted_by = ?, received_by = ? WHERE billing_id = ?";

        String url = "jdbc:postgresql:Pumpcrete";

        try (Connection connection = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement ps = connection.prepareStatement(query)) {

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
}
