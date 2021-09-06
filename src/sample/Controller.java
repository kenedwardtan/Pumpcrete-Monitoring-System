package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Postgresql;
import sample.model.User;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
    private Label profile_name_lbl;
    @FXML
    private Label profile_user_lbl;
    @FXML
    private Label profile_email_lbl;
    @FXML
    private Label profile_role_lbl;
    @FXML
    private Label settings_oPass_lbl;
    @FXML
    private Label settings_roPass_lbl;
    @FXML
    private Label settings_nPass_lbl;
    @FXML
    private Button settings_change_btn;
    @FXML
    private ImageView settings_change_btn1;
    @FXML
    private PasswordField settings_oPass_tf;
    @FXML
    private PasswordField settings_roPass_tf;
    @FXML
    private PasswordField settings_nPass_tf;
    @FXML
    private Button settings_save_btn;
    @FXML
    private Button settings_cancel_btn;
    @FXML
    private ImageView settings_2btn;

    //clients - create
    @FXML
    private DatePicker clients_date;
    @FXML
    private TextField clients_fn_tf;
    @FXML
    private TextField clients_ln_tf;
    @FXML
    private TextField clients_position_tf;
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

    //billings - create
    @FXML
    private ChoiceBox<String> billings_client;
    @FXML
    private TextField billings_psc_tf;
    @FXML
    private TextField billings_padd_tf;
    @FXML
    private TextField billings_pname_tf;
    @FXML
    private TextField billings_struct_tf;
    @FXML
    private TextField billings_flr_tf;
    @FXML
    private TextField billings_qty_tf;
    @FXML
    private TextField billings_price_tf;
    @FXML
    private DatePicker billings_date;
    @FXML
    private Button billings_submit_btn;
    @FXML
    private Button billings_cancel_btn;



    //employees - create
    @FXML
    private TextField create_fn_tf;
    @FXML
    private TextField create_mn_tf;
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
            con = postgresql.loginUser(username.trim(), password.trim());

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
                        stage.setResizable(false);
                        stage.show();
                        break;
                    case "Admin":
                    case "admin":
                        stage = (Stage) login_btn.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        break;
                    case "Supervisor":
                    case "supervisor":
                        stage = (Stage) login_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
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

        //profile
        if (e.getSource() == settings_change_btn) {
            settings_change_btn.setVisible(false);
            settings_change_btn1.setVisible(false);

            settings_oPass_lbl.setVisible(true);
            settings_roPass_lbl.setVisible(true);
            settings_nPass_lbl.setVisible(true);
            settings_oPass_tf.setVisible(true);
            settings_roPass_tf.setVisible(true);
            settings_nPass_tf.setVisible(true);
            settings_save_btn.setVisible(true);
            settings_cancel_btn.setVisible(true);
            settings_2btn.setVisible(true);
        }

        if (e.getSource() == settings_cancel_btn) {
            settings_change_btn.setVisible(true);
            settings_change_btn1.setVisible(true);

            settings_oPass_lbl.setVisible(false);
            settings_roPass_lbl.setVisible(false);
            settings_nPass_lbl.setVisible(false);
            settings_oPass_tf.setVisible(false);
            settings_roPass_tf.setVisible(false);
            settings_nPass_tf.setVisible(false);
            settings_save_btn.setVisible(false);
            settings_cancel_btn.setVisible(false);
            settings_2btn.setVisible(false);

            String role = postgresql.getRole(con);
            switch (role) {
                case "Staff":
                case "staff":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Supervisor":
                case "supervisor":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                default:
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Invalid Role.");
                    errorAlert.showAndWait();
                    break;
            }
        }

        if (e.getSource() == settings_save_btn) {
            settings_change_btn.setVisible(true);
            settings_change_btn1.setVisible(true);

            settings_oPass_lbl.setVisible(false);
            settings_roPass_lbl.setVisible(false);
            settings_nPass_lbl.setVisible(false);
            settings_oPass_tf.setVisible(false);
            settings_roPass_tf.setVisible(false);
            settings_nPass_tf.setVisible(false);
            settings_save_btn.setVisible(false);
            settings_cancel_btn.setVisible(false);
            settings_2btn.setVisible(false);

            String oldpw = settings_oPass_tf.getText().trim();
            String newpw = settings_nPass_tf.getText().trim();
            String repeat = settings_roPass_tf.getText().trim();

            if (!newpw.equals("") && !oldpw.equals("") && !repeat.equals("")) {//(oldpw.equals(repeat))
                if (verifyNewPw(newpw, repeat)) {
                    boolean changed = postgresql.editPassword(con, oldpw, newpw);
                    if (changed){
                        JOptionPane.showMessageDialog(null, "Successfully changed password!\nNew password: " + newpw, "Password Changed!", 1);

                        String role = postgresql.getRole(con);
                        switch (role) {
                            case "Staff":
                            case "staff":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
                                loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                                root = loader.load();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                                break;
                            case "Admin":
                            case "admin":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                                root = loader.load();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                                break;
                            case "Supervisor":
                            case "supervisor":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
                                loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                                root = loader.load();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setResizable(false);
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
                else
                    JOptionPane.showMessageDialog(null, "Passwords do not match, make sure you confirm your new passwords match!", "Password mismatch!", 2);
            }
            else
                JOptionPane.showMessageDialog(null, "Fill up new password, blank spaces are not allowed", "New Password Failed", 2);
        }

        //clients
        if (e.getSource() == clients_submit_btn) {
            if (verifyCreateClient()) {
                if (verifyClientNumbers()) {
                    // retrieve inputs
                    String fname = clients_fn_tf.getText();
                    String lname = clients_ln_tf.getText();
                    String position = clients_position_tf.getText();
                    String address = clients_address_tf.getText();
                    String email = clients_email_tf.getText();
                    String landline = clients_landline_tf.getText();
                    String cellphone =  clients_cellphone_tf.getText();

                    String fullname =  fname.trim() + " " + lname.trim();

                    //checks the format of the email
                    if (EmailVerification(email)) {

                        //creates the user and inserts into database
                        postgresql.createClient(con, fname.trim(), lname.trim(), position.trim(), address.trim(), landline.trim(), cellphone.trim(), email.trim());
                        String message = "Name: " + fullname;
                        optionPane.showMessageDialog(null, message, "Client Created!", 1);
                        //clear fields

                        stage = (Stage) clients_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("clients.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }
                }else
                    optionPane.showMessageDialog(null, "Please check the format of your landline and cellphone number!\n" +
                            "Landline must only contain exactly 8 digits.\n Cellphone Number must contain exactly 11 digits and must start with 09.", "Contact number error!", 2);
            }
        }

        if (e.getSource() == clients_cancel_btn) {
            //clear fields code here
            stage = (Stage) clients_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clients.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //employees
        if (e.getSource() == create_submit_btn) {
            // check if the data are empty
            if (verifyCreateFields()) {
                // retrieve inputs
                String fname = create_fn_tf.getText();
                String lname = create_ln_tf.getText();
                String mname = create_mn_tf.getText();
                String email = create_email_tf.getText();
                String uname = create_user_tf.getText();
                String role = create_role_dd.getValue();

                // check if the username already exists
                if (!postgresql.checkUsername(con, uname)) {

                    //checks the format of the email
                    if (EmailVerification(email)) {
                        //creates the user and inserts into database
                        String passsword = postgresql.createUser(con, fname.trim(), mname.trim(), lname.trim(), email.trim(), uname.trim(), role.trim());
                        String message ="Username: " + uname.trim() +"\nPassword: " + passsword.trim();
                        optionPane.showMessageDialog(null, message, "User Created!", 1);
                        //clear fields

                        stage = (Stage) create_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }
                } else
                    optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
            }
            System.out.print(create_fn_tf.getText());
        }

        if (e.getSource() == create_cancel_btn) {
            //clear fields code here
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }



    }

    public boolean verifyNewPw(String newpw, String repeat) {
        if(newpw.equals(repeat))
            return true;
        else
            return false;
    }

    public boolean verifyCreateClient () {
        String fname = clients_fn_tf.getText();
        String lname = clients_ln_tf.getText();
        String position = clients_position_tf.getText();
        String email = clients_email_tf.getText();
        String address = clients_address_tf.getText();
        String landline = clients_landline_tf.getText();
        String cpnum = clients_cellphone_tf.getText();
        // check empty fields
        if (fname.trim().equals("") || position.trim().equals("") || lname.trim().equals("") || email.trim().equals("")
                || address.trim().equals("") || landline.trim().equals("") || cpnum.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }

        // if everything is ok
        else {
            return true;
        }
    }

    public boolean verifyClientNumbers () {
        if (clients_landline_tf.getText().matches("\\d{8}") && clients_cellphone_tf.getText().matches("09\\d{9}")){
            System.out.println("Its Valid Number");
            return true;
        } else {

            System.out.println("Invalid Input..!");
            return false;
        }
    }

    public boolean verifyCreateFields () {
        String fname = create_fn_tf.getText();
        String lname = create_ln_tf.getText();
        String mname = create_mn_tf.getText();
        String email = create_email_tf.getText();
        String uname = create_user_tf.getText();
        String role = create_role_dd.getValue();

        // check empty fields
        if (fname.trim().equals("") || mname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
                || email.trim().equals("") || role.trim().equals("Select Role")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }

        // if everything is ok
        else {
            if (this.checkFormat(uname.trim()) && this.checkFormat(role.trim()))
                return true;
            else
                return false;

        }
    }

    //verify email format
    public boolean EmailVerification (String email) {
//        String first = "";
//        String last = "^[a-zA-Z0-9!@#$%^&*?(){}]*$";
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.)([a-zA-Z0-9-]+)*$";
                //"^([a-zA-Z0-9!@#$%^&*?(){}]+\\S)@([a-zA-Z0-9!@#$%^&*?(){}]+\\S)(?:\\.)com$";

        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(email.trim());

        if (matcher.matches()) {
            System.out.println("Email format is correct.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Make sure you're email format is correct!\n(e.g. name@brand.com)", "Invalid Email", 2);
            return false;
        }
    }



    public boolean checkFormat(String uname){
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+$";
        //"^([a-zA-Z0-9!@#$%^&*?(){}]+\\S)@([a-zA-Z0-9!@#$%^&*?(){}]+\\S)(?:\\.)com$";

        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(uname.trim());
        if (matcher.matches()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Make sure you don't have white spaces!\n", "Invalid fields", 2);
            return false;
        }
    }
}
