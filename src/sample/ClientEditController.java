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
import sample.model.Client;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientEditController extends Controller implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql = new Postgresql();
    public static Connection con;

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
    @FXML
    private TextArea edit_clients_position_tf;
    @FXML
    private JOptionPane optionPane;

    private static ObservableList<Client> c;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        c = FXCollections.observableArrayList();
        c = postgresql.getClient(Controller.con, ClientsController.getEditClient());
        String name = c.get(0).name.get();
        String[] names = name.split(" ");
        String fname = names[0];
        String lname = names[1];

        System.out.println(fname + " " + lname + " " + c.get(0).getAddress());

        edit_clients_fn_tf.setText(fname);
        edit_clients_ln_tf.setText(lname);
        edit_clients_position_tf.setText(c.get(0).getPosition());
        edit_clients_address_tf.setText(c.get(0).getAddress());
        edit_clients_email_tf.setText(c.get(0).getEmail());
        edit_clients_cellphone_tf.setText(c.get(0).getCpnum());
        edit_clients_landline_tf.setText(Integer.toString(c.get(0).getLandline()));
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        con = Controller.con;
        if (e.getSource() == edit_clients_submit_btn) {

            if (verifyEditFields()) {
                if (verifyClientNumbers()) {

                    String fname = edit_clients_fn_tf.getText().trim();
                    String lname = edit_clients_ln_tf.getText().trim();
                    String position = edit_clients_position_tf.getText().trim();
                    String address = edit_clients_address_tf.getText().trim();
                    String email = edit_clients_email_tf.getText().trim();
                    Long cp = Long.parseLong(edit_clients_cellphone_tf.getText().trim());
                    int landline = Integer.parseInt(edit_clients_landline_tf.getText().trim());

                    String fullname = fname.trim() + " " + lname.trim();

                    //checks the format of the email
                    if (this.EmailVerification(email)) {
                        //creates the user and inserts into database
                        postgresql.editClient(con, ClientsController.getEditClient(), fullname.trim(), position.trim(), address.trim(), landline, cp, email.trim());
                        String message = "Name: " + fullname;
                        optionPane.showMessageDialog(null, message, "Client edited!", 1);
                        //clear fields

                        stage = (Stage) edit_clients_submit_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("clients.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }
                    else
                        optionPane.showMessageDialog(null, "Please check the format of your landline and cellphone number! It must only contain 8 or 11 digits.", "Contact number error!", 2);
                }
            }
        }

        if (e.getSource() == edit_clients_cancel_btn) {
            //clear fields
            stage = (Stage) edit_clients_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clients.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    public boolean verifyEditFields () {
        String fname = edit_clients_fn_tf.getText();
        String lname = edit_clients_ln_tf.getText();
        String position = edit_clients_position_tf.getText();
        String email = edit_clients_email_tf.getText();
        String address = edit_clients_address_tf.getText();
        String landline = edit_clients_landline_tf.getText();
        String cpnum = edit_clients_cellphone_tf.getText();
        // check empty fields
        if (fname.trim().equals("") || position.trim().equals("") || lname.trim().equals("") || email.trim().equals("")
                || address.trim().equals("") || landline.trim().equals("") || cpnum.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }

        // if everything is ok
        else {
            if (this.checkFormat(fname.trim()) && this.checkFormat(position.trim()) && this.checkFormat(lname.trim()))
                return true;
            else
                return false;
        }
    }

    public boolean verifyClientNumbers () {
        if (edit_clients_landline_tf.getText().matches("\\d{8}") && edit_clients_cellphone_tf.getText().matches("\\d{8}|\\d{11}")){

            System.out.println("Its Valid Number");
            return true;
        }else {

            System.out.println("Invalid Input..!");
            return false;
        }
    }
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
