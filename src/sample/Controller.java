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

    //profile
    @FXML
    private TextArea profile_fn_ta;
    @FXML
    private TextArea profile_ln_ta;
    @FXML
    private TextArea profile_email_ta;
    @FXML
    private TextArea profile_user_ta;

    //clients - create
    @FXML
    private DatePicker clients_date;
    @FXML
    private TextField clients_fn_tf;
    @FXML
    private TextField clients_ln_tf;
    @FXML
    private TextField clients_email_tf;
    @FXML
    private TextField clients_landline_tf;
    @FXML
    private TextField clients_cellphone_tf;
    @FXML
    private TextField clients_address_tf;
    @FXML
    private Button clients_submit_btn;
    @FXML
    private Button clients_cancel_btn;

    //clients - edit
    @FXML
    private DatePicker edit_clients_date;
    @FXML
    private TextArea edit_clients_fn_tf;
    @FXML
    private TextArea edit_clients_ln_tf;
    @FXML
    private TextArea edit_clients_email_tf;
    @FXML
    private TextArea edit_clients_landline_tf;
    @FXML
    private TextArea edit_clients_cellphone_tf;
    @FXML
    private TextArea edit_clients_address_tf;
    @FXML
    private Button edit_clients_submit_btn;
    @FXML
    private Button edit_clients_cancel_btn;

    //billings - create
    @FXML
    private DatePicker billings_date;
    @FXML
    private TextField billings_cn_tf;
    @FXML
    private Button billings_submit_btn;
    @FXML
    private Button billings_cancel_btn;

    //billings - edit
    @FXML
    private DatePicker edit_billings_date;
    @FXML
    private TextArea edit_billings_cn_tf;
    @FXML
    private Button edit_billings_submit_btn;
    @FXML
    private Button edit_billings_cancel_btn;

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
    private TextArea edit_fn_tf;
    @FXML
    private TextArea edit_ln_tf;
    @FXML
    private TextArea edit_email_tf;
    @FXML
    private TextArea edit_user_tf;
    @FXML
    private Button edit_submit_btn;
    @FXML
    private Button edit_cancel_btn;
    @FXML
    private ChoiceBox<String> edit_role_dd;
    @FXML
    private Button edit_generate_btn;

    // settings
    @FXML
    private PasswordField settings_oPass_tf;
    @FXML
    private PasswordField settings_nPass_tf;
    @FXML
    private Button settings_save_btn;
    @FXML
    private Button settings_cancel_btn;

	@FXML
	private JOptionPane optionPane;

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //login
        if (e.getSource() == login_btn) {
            String username = "";
            username = login_user_tf.getText();
            String password = login_pass_tf.getText();
            con = postgresql.loginUser(username, password);

            if (con != null) {
                System.out.println("Successful Login");
                String role = postgresql.getRole(con);
                switch (role) {
                    case "Staff":
                    case "staff":
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case "Supervisor":
                    case "supervisor":
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
            } else {
                errorAlert.setHeaderText("Login Failed");
                errorAlert.setContentText("Wrong Username/Password");
                errorAlert.showAndWait();
            }
        }

        //clients
        if (e.getSource() == edit_clients_submit_btn) {
            stage = (Stage) edit_clients_submit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clients.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == edit_clients_cancel_btn) {
            //clear fields
            stage = (Stage) edit_clients_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clients.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //billings
        if (e.getSource() == edit_billings_submit_btn) {
            stage = (Stage) edit_billings_submit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == edit_billings_cancel_btn) {
            //clear fields
            stage = (Stage) edit_billings_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //employees
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
                        String passsword = postgresql.createUser(con, fname, lname, email, uname, role);
                        String message ="Username: " + uname +"\nPassword: " + passsword;
                        optionPane.showMessageDialog(null, message, "User Created!", 1);
                        //clear fields

                        stage = (Stage) create_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
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

        //settings
        if (e.getSource() == settings_cancel_btn) {
            String role = postgresql.getRole(con);
            switch (role) {
                case "Staff":
                case "staff":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case "Supervisor":
                case "supervisor":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
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

        }

        if(e.getSource() == settings_save_btn) {
            String oldpw = settings_oPass_tf.getText();
            String newpw = settings_nPass_tf.getText();

            postgresql.editPassword(con, oldpw, newpw);
            String role = postgresql.getRole(con);
            switch (role) {
                case "Staff":
                case "staff":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case "Supervisor":
                case "supervisor":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
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
        }
    }

    public boolean verifyFields () {
        String fname = create_fn_tf.getText();
        String lname = create_ln_tf.getText();
        String email = create_email_tf.getText();
        String uname = create_user_tf.getText();
        String role = create_role_dd.getValue();

        // check empty fields
        if (fname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
                || email.trim().equals("") || role.trim().equals("Select Role")) {
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
