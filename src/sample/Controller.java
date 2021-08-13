package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    @FXML
    private Button login_btn;
    @FXML
    private Button create_btn;
    @FXML
    private ChoiceBox<String> role_dropdown;
    @FXML
    private TextField create_fn_tf;
    @FXML
    private TextArea profile_fn_ta;
    @FXML
    private TextArea profile_ln_ta;
    @FXML
    private TextArea profile_email_ta;
    @FXML
    private TextArea profile_user_ta;
    @FXML
    private TextField create_ln_tf;
    @FXML
    private TextField create_email_tf;
    @FXML
    private TextField create_user_tf;
    @FXML
    private Button create_submit_btn;
    @FXML
    private Button create_cancel_btn;
    @FXML
    private Button edit_btn;

    @FXML
    private TextField edit_fn_tf;
    @FXML
    private TextField edit_ln_tf;
    @FXML
    private TextField edit_email_tf;
    @FXML
    private TextField edit_user_tf;
    @FXML
    private Button edit_submit_btn;
    @FXML
    private Button edit_cancel_btn;
    @FXML
    private ChoiceBox<String> edit_role;

    @FXML
    private Button settings_save_btn;
    @FXML
    private Button settings_cancel_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private Button profile_btn;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button reports_btn;
    @FXML
    private Button clients_btn;
    @FXML
    private Button memos_btn;
    @FXML
    private Button logout_btn;
    @FXML
    private TextField login_user_tf;
    @FXML
    private TextField login_pass_tf;
    @FXML
    private TextField settings_oPass_tf;
    @FXML
    private TextField settings_nPass_tf;
	@FXML
	private JOptionPane optionPane;

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {

        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        if (e.getSource() == login_btn) { // if (buttonpressedis == login button)
            String username = login_user_tf.getText();
            String password = login_pass_tf.getText();
            con =  postgresql.loginUser(username,password);

            if (con != null)
            {
                String role = postgresql.getRole(con);
                switch(role) {
                    case "Staff":
                    case "staff":
                        System.out.println("Staff role");
                        stage = (Stage) login_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case "Admin":
                    case "admin":
                        stage = (Stage) login_btn.getScene().getWindow();
                        FXMLLoader  loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;

                    case "Supervisor":
                    case "supervisor":
                        stage = (Stage) login_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeSuperv.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    default:
                        errorAlert.setHeaderText("Input not valid");
                        errorAlert.setContentText("Invalid Role.");
                        errorAlert.showAndWait();
                        break;
                }

            }
            else
                System.out.println("Connection Failed \n Wrong Username/Password");





        }
        if (e.getSource() == logout_btn) {
            postgresql.endConnection(con);


//            //Check if user is still there, should print No connected user
//            String getUser = postgresql.getCurrUser(con);
//            System.out.println(getUser);

            //put the login form here again hehe
            stage = (Stage) logout_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == create_btn) {
            stage = (Stage) create_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

        if (e.getSource() == create_submit_btn) {




            // check if the data are empty
            if (verifyFields())
            {
                // retrieve inputs
                String fname = create_fn_tf.getText();
                String lname = create_ln_tf.getText();
                String email = create_email_tf.getText();
                String uname = create_user_tf.getText();
                String role = role_dropdown.getValue();

                // check if the username already exists
                if (!postgresql.checkUsername(uname)) {

                    //checks the format of the email
                    if(EmailVerification()){
                        //creates the user and inserts into database
                        postgresql.createUser(con, fname,lname,email,uname,role);

                        stage = (Stage) create_submit_btn.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
				else
					optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
            }
			
            System.out.print(create_fn_tf.getText()); // once submit button is pressed, getText from create_fn_tf (first name textfield) -> print
        }

        if (e.getSource() == create_cancel_btn) {
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == settings_btn) {
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == edit_btn) {
            //change screen here code here

            String fname = edit_fn_tf.getText();
            String lname = edit_ln_tf.getText();
            String email = edit_email_tf.getText();
            String uname = edit_user_tf.getText();
            String role = edit_role.getValue();
            if (!postgresql.checkUsername(uname)) {

                //checks the format of the email
                if(EmailVerification()){
                    //creates the user and inserts into database
                    postgresql.editUser(fname,lname,email,uname,role);

                    stage = (Stage) create_submit_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else
                optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
        }

/*      //Profile button - idea how to display?
        if (e.getSource() == profile_btn) { // if (buttonpressedis == login button)
            String username = profile_username_label.gettext();
            User user = postgresql.getUser(username);

           }



        //How to display arraylist of Staff
        ArrayList<User> users = postgres.getAllStaff()



 */
    }
	
	// create a function to verify the empty fields
    public boolean verifyFields()
    {
        String fname = create_fn_tf.getText();
        String lname = create_ln_tf.getText();
        String email = create_email_tf.getText();
        String uname = create_user_tf.getText();

        // check empty fields
        if(fname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
                || email.trim().equals("") || role_dropdown.getValue() == "Select Role") {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty","Empty Fields",2);
            return false;
        }

        // if everything is ok
        else{
            System.out.println("All fields are field!");
            return true;
        }
    }

    //verify email format
    public boolean EmailVerification(){
        String regex = "^(.+)@(.+)$";
        String email = create_email_tf.getText();

        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()){
            System.out.println("Email format is correct.");
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Make sure you're email format is correct!","Invalid Email",2);
            return false;
        }


    }

	
}
