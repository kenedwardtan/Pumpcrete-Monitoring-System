package sample.model;

import java.sql.*;
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
            return null;
        }
        return null;
    }




    //Once all information are verified, adds new user to the database.
    public static void createUser (Connection connection, String fname, String lname, String email, String uname, String role) {

        String url = "jdbc:postgresql:Pumpcrete";

        String query1 = "INSERT INTO users(first_name, last_name, username, password, email, role) VALUES (?,?,?,?,?,?)";

        Random r = new Random();
        String u_password = "password" + Integer.toString(r.nextInt(9999) + 1);

        String DBRole = "";
        String DBPassword = "'" + u_password +"'";
        //I had to make the role titles in the db diff bc 'admin' is reserved :/
        switch (role)
        {
            case "staff": DBRole = "staff_role"; break;
            case "supervisor": DBRole = "supervisor_role"; break;
            case "admin": DBRole = "admin_role"; break;
        }

            try {
                //This will ad user as postgres user
                PreparedStatement p = connection.prepareStatement("CREATE USER ? with password ?;\n" +
                        "Grant ? to ?");
                p.setString(1, uname);
                p.setString(2, DBPassword);
                p.setString(3, DBRole);
                p.setString(4, uname);

                p.executeQuery();
                System.out.println(p);

                //This will keep user info in the 'users' table
                PreparedStatement ps = connection.prepareStatement(query1);
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
}
