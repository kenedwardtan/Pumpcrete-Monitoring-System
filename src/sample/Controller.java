package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Postgresql;
import sample.model.User;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;

    //login
    @FXML
    private Button login_btn;
    @FXML
    private TextField login_user_tf;
    @FXML
    private PasswordField login_pass_tf;

    //home
    @FXML
    private Text home_name_txt;
    @FXML
    private Button home_master_btn;
    @FXML
    private Button home_transactions_btn;
    @FXML
    private Button home_reports_btn;

    //profile
    @FXML
    private Button profile_btn;
    @FXML
    private TextArea profile_fn_ta;
    @FXML
    private TextArea profile_ln_ta;
    @FXML
    private TextArea profile_email_ta;
    @FXML
    private TextArea profile_user_ta;

    //dashboard
    @FXML
    private Button dashboard_btn;

    //reports
    @FXML
    private Button reports_btn;

    //clients
    @FXML
    private Button clients_btn;

    //inventory
    @FXML
    private Button inventory_btn;

    //receivables
    @FXML
    private Button receivables_btn;

    //billings
    @FXML
    private Button billings_btn;

    //credit memos
    @FXML
    private Button memos_btn;

    //employees (admin)
    @FXML
    private Button employees_btn;

    //employees - create
    @FXML
    private TextField create_fn_tf;
    @FXML
    private TextField create_ln_tf;
    @FXML
    private TextField create_email_tf;
    @FXML
    private TextField create_user_tf;
    @FXML
    private ChoiceBox<String> create_role_dd;
    @FXML
    private Button create_submit_btn;
    @FXML
    private Button create_cancel_btn;
    //employees - edit
    @FXML
    private Button employees_edit_btn;
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
    private ChoiceBox<String> edit_role_dd;
    //settings
    @FXML
    private Button settings_btn;
    // settings - pass
    @FXML
    private TextField settings_oPass_tf;
    @FXML
    private TextField settings_nPass_tf;
    @FXML
    private Button settings_save_btn;
    @FXML
    private Button settings_cancel_btn;

    //logout
    @FXML
    private Button logout_btn;

	@FXML
	private JOptionPane optionPane;

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        home_name_txt = new Text();
        //login
        if (e.getSource() == login_btn) {
            String username = login_user_tf.getText();
            String password = login_pass_tf.getText();
            con = postgresql.loginUser(username, password);

            if (con != null) {
                String role = postgresql.getRole(con);
                switch (role) {
                    case "Staff":
                    case "staff":
                        System.out.println("Staff role");
                        home_name_txt.setText(username);
                        stage = (Stage) login_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case "Admin":
                    case "admin":
                        home_name_txt.setText(username);
                        stage = (Stage) login_btn.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case "Supervisor":
                    case "supervisor":
                        home_name_txt.setText(username);
                        stage = (Stage) login_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
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
            } else
                System.out.println("Connection Failed \n Wrong Username/Password");
        }
        //employees (admin)
        if (e.getSource() == employees_btn) {
            stage = (Stage) employees_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == create_submit_btn) {
            // check if the data are empty
            if (verifyFields()) {
                // retrieve inputs
                String fname = create_fn_tf.getText();
                String lname = create_ln_tf.getText();
                String email = create_email_tf.getText();
                String uname = create_user_tf.getText();
                String role = create_role_dd.getValue();

                // check if the username already exists
                if (!postgresql.checkUsername(uname)) {

                    //checks the format of the email
                    if (EmailVerification()) {
                        //creates the user and inserts into database
                        postgresql.createUser(con, fname, lname, email, uname, role);
                        //clear fields

                        stage = (Stage) create_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                } else
                    optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
            }
            System.out.print(create_fn_tf.getText());
        }

        if (e.getSource() == create_cancel_btn) {
            //clear fields
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


        //edit account
        if (e.getSource() == employees_edit_btn) {
            stage = (Stage) employees_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployeesEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            String fname = edit_fn_tf.getText();
            String lname = edit_ln_tf.getText();
            String email = edit_email_tf.getText();
            String uname = edit_user_tf.getText();
            String role = edit_role_dd.getValue();
            if (!postgresql.checkUsername(uname)) {
                //checks the format of the email
                if (EmailVerification()) {
                    //creates the user and inserts into database
                    postgresql.editUser(fname, lname, email, uname, role);

                    stage = (Stage) create_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else
                optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
        }

        //home
        if (e.getSource() == home_master_btn) {

        } else if (e.getSource() == home_transactions_btn) {

        } else if (e.getSource() == home_reports_btn) {

        }

        //profile
        /*
        if (e.getSource() == profile_btn) {
            String username = profile_username_label.gettext();
            User user = postgresql.getUser(username);

           }

        //How to display arraylist of Staff
        ArrayList<User> users = postgres.getAllStaff()
        */

        //dashboard

        //reports

        //clients

        //inventory

        //receivables

        //billings
        if (e.getSource() == billings_btn) {
            stage = (Stage) billings_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //credit memos
        if (e.getSource() == memos_btn) {
            stage = (Stage) memos_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("memos.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


        //settings
        if (e.getSource() == settings_btn) {
            stage = (Stage) settings_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //logout
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
    }

    public boolean verifyFields () {

        String fname = create_fn_tf.getText();
        String lname = create_ln_tf.getText();
        String email = create_email_tf.getText();
        String uname = create_user_tf.getText();

        // check empty fields
        if (fname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
                || email.trim().equals("") || create_role_dd.getValue() == "Select Role") {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }

        // if everything is ok
        else {
            System.out.println("All fields are field!");
            return true;
        }
    }

    //verify email format
    public boolean EmailVerification () {
        String regex = "^(.+)@(.+)$";
        String email = create_email_tf.getText();

        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Email format is correct.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Make sure you're email format is correct!", "Invalid Email", 2);
            return false;
        }


    }


}
