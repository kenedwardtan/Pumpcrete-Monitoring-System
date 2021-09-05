package sample;

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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CollectionsCreateController extends Controller implements  Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;

    //FXML ELEMENTS
    //collections - create
    @FXML
    private ChoiceBox<String> create_collections_client;
    @FXML
    private TextField create_collections_billNum_tf;
    @FXML
    private TextField create_collections_pid_tf;
    @FXML
    private TextField create_collections_amount_tf;
    @FXML
    private TextField create_collections_bank_tf;
    @FXML
    private TextField create_collections_checkNum_tf;
    @FXML
    private TextField create_collections_total_tf;
    @FXML
    private TextField collections_price_tf;
    @FXML
    private DatePicker create_collections_date;
    @FXML
    private DatePicker create_collections_checkDate;
    @FXML
    private Button create_collections_submit_btn;
    @FXML
    private Button create_collections_cancel_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> test = postgresql.getAllClientNames(Controller.con);
        //collections_client.setItems(test);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        if (e.getSource() == collections_submit_btn) {
            if (checkFields()) {
                String client_name = collections_client.getValue();
                int psc = Integer.parseInt(collections_psc_tf.getText());
                String project_name = collections_pname_tf.getText();
                String project_add = collections_padd_tf.getText();
                Date date_used = Date.valueOf(collections_date.getValue());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date_doc = formatter.format(LocalDate.now());
                String struct = collections_struct_tf.getText();
                int floor_level = Integer.parseInt(collections_flr_tf.getText());
                float qty_temp = Float.parseFloat(collections_qty_tf.getText());
                float unit_price = Float.parseFloat(collections_price_tf.getText());
                float qty_added = 0;
                float qty_final = 0;
                //additional if less than 50
                if (qty_temp < 50.00) {
                    qty_added = (float) (50.00 - qty_temp);
                    qty_final = qty_temp + qty_added;
                    JOptionPane.showMessageDialog(null, "We added " + qty_added + " to the original qty. Final qty(in cubic meters): " + qty_final, "Qty. did not meet minimum", 2);

                }

                float total = (float) qty_final * unit_price;
                //checks if billing has unique psc and client
                if (!postgresql.checkBillingPSC(Controller.con, psc)) {
                    postgresql.addBilling(Controller.con, client_name, project_name, project_add,
                            Date.valueOf(date_doc), psc, date_used, floor_level, qty_final, unit_price, struct, total);
                    stage = (Stage) create_collections_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("collections.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else {
                    //clear
                    //show error message
                    JOptionPane.showMessageDialog(null, "A Similar bill already exists. (Repeating PSC No.)", "Unique Billing Violation", 2);
                    stage = (Stage) create_collections_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("collectionsCreate.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();

                }
            }

        }

        if (e.getSource() == create_collections_cancel_btn) {
            //clear fields
            stage = (Stage) create_collections_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("collections.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    public boolean checkFields() {
        String client_name = collections_client.getValue();
        String psc = collections_psc_tf.getText();
        String project_name = collections_pname_tf.getText();
        String project_add = collections_padd_tf.getText();
        String date_used = String.valueOf(collections_date.getValue());
        String floor_level = collections_flr_tf.getText();
        String qty_temp = collections_qty_tf.getText();
        String struct = collections_struct_tf.getText();
        String unit = collections_price_tf.getText();

        //check if theyre null
        if (client_name == null || project_name == null || project_add == null
                || psc == null || date_used == null || floor_level == null
                || qty_temp == null || struct == null || unit == null) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        // check empty fields
        else if (client_name.trim().equals("") || project_name.trim().equals("") || project_add.trim().equals("")
                || psc.trim().equals("") || date_used.trim().equals("") || floor_level.trim().equals("")
                || qty_temp.trim().equals("") || struct.trim().equals("") || unit.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        if (!collections_psc_tf.getText().matches("[0-9]+") || !collections_flr_tf.getText().matches("[0-9]+") ||
                !collections_price_tf.getText().matches("[0-9]+.[0-9]++") || !collections_qty_tf.getText().matches("[0-9]+.[0-9]++")) {
            JOptionPane.showMessageDialog(null, "PSC and Floor fields must only contain whole numbers\n" +
                    "Price and Quantity fields must only be in the format of a whole number with decimal values", "Invalid Number Inputs", 2);
            return false;
        }
        // if everything is ok
        else {
            System.out.println("All fields are filled!");
            return true;
        }
    }
}
