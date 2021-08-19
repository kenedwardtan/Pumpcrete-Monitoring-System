package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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


public class EmployeesEditController extends Controller implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql = new Postgresql();
    public static Connection con;

    @FXML
    private TextArea edit_fn_tf;
    @FXML
    private TextArea edit_mn_tf;
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
    @FXML
    private JOptionPane optionPane;
    private String oldUname;

    private static ObservableList<User> u;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        u = FXCollections.observableArrayList();
        u = postgresql.getUser(Controller.con, EmployeesController.getEditUser());

        edit_fn_tf.setText(u.get(0).first_name.get());
        edit_mn_tf.setText(u.get(0).middle_name.get());
        edit_ln_tf.setText(u.get(0).first_name.get());
        edit_email_tf.setText(u.get(0).email.get());
        edit_user_tf.setText(u.get(0).username.get());
        this.oldUname = u.get(0).username.get();
        edit_role_dd.setValue(u.get(0).role.get());

    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        con = Controller.con;

        if (e.getSource() == edit_submit_btn) {
            String fname = edit_fn_tf.getText().trim();
            String lname = edit_ln_tf.getText().trim();
            String mname = edit_mn_tf.getText().trim();
            String email = edit_email_tf.getText().trim();
            String uname = edit_user_tf.getText().trim();
            String role = edit_role_dd.getValue().trim();

            if (verifyEditFields()) {
                if (!postgresql.checkUsername(con, uname) || uname.equals(this.oldUname)) {
                    //checks the format of the email
                    if (EmailVerification()) {
                        //creates the user and inserts into database
                        postgresql.editUser(con, uname, fname, mname, lname, email, role);

                        stage = (Stage) edit_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                } else
                    optionPane.showMessageDialog(null, "This username is already taken, please choose another one", "Username Failed", 2);
            }
        }

        if (e.getSource() == edit_cancel_btn) {
            System.out.print("hi");
            stage = (Stage) edit_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == edit_generate_btn ) {
            String newPW = postgresql.resetPassword(con, this.u.get(0).username.get(), this.u.get(0).password.get());
            optionPane.showMessageDialog(null, "New Password: "+newPW, "Password Generated", 1);
            stage = (Stage) edit_generate_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployeesEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public boolean verifyEditFields() {
        String fname = edit_fn_tf.getText();
        String lname = edit_ln_tf.getText();
        String mname = edit_mn_tf.getText();
        String email = edit_email_tf.getText();
        String uname = edit_user_tf.getText();
        String role = edit_role_dd.getValue();

        // check empty fields
        if (fname.trim().equals("") || mname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
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
    public boolean EmailVerification() {
        String regex = "^(.+)@(.+).com$";
        String email = edit_email_tf.getText();

        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Email format is correct.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Make sure you're email format is correct!\n(e.g. name@brand.com)", "Invalid Email", 2);
            return false;
        }
    }
}
