package sample.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Postgresql {
    public static String username = "postgres";
    public static String password = "swengt3y2";

    public static String loginUser(String u_username, String u_password)
    {
        String url = "jdbc:postgresql:Pumpcrete";

       String query = "SELECT password,role FROM users WHERE username = ?";

        try (Connection con = DriverManager.getConnection(url, "postgres","swengt3y2");
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, u_username);

            ResultSet result = pst.executeQuery();
            while (result.next()) {
               String pass_Res = result.getString("password");
               String role_Res = result.getString("role");

               //Prints the password from the query
               System.out.println("Password Found: " + pass_Res);
                if(pass_Res.contentEquals(u_password)){
                    System.out.println("Login Successful.");
                   return role_Res;
                }
                else{
                    System.out.println("Password did not match.");
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


}
