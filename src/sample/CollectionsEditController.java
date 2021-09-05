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
import jdk.nashorn.internal.scripts.JO;
import sample.model.Billing;
import sample.model.Client;
import sample.model.Postgresql;
import sample.model.Pumpcrete;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CollectionsEditController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;

    //collections - edit
    @FXML
    private ChoiceBox<String> edit_collections_client;
    @FXML
    private TextField edit_collections_billNum_tf;
    @FXML
    private TextField edit_collections_pid_tf;
    @FXML
    private TextField edit_collections_amount_tf;
    @FXML
    private TextField edit_collections_bank_tf;
    @FXML
    private TextField edit_collections_checkNum_tf;
    @FXML
    private TextField edit_collections_total_tf;
    @FXML
    private TextField collections_price_tf;
    @FXML
    private DatePicker edit_collections_date;
    @FXML
    private DatePicker edit_collections_checkDate;
    @FXML
    private Button edit_collections_submit_btn;
    @FXML
    private Button edit_collections_cancel_btn;

    //private static Billing b;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();
        //System.out.println("bill no="+collectionsController.getEditBilling());
        //b = postgresql.getBilling(Controller.con, collectionsController.getEditBilling());

        ObservableList<String> test = postgresql.getAllClientNames(Controller.con);
        edit_collections_client.setItems(test);
        edit_collections_client.setValue(b.getClient_name());
        edit_collections_pname_tf.setText(b.getProject_name());
        edit_collections_padd_tf.setText(b.getProject_add());
        edit_collections_date.setValue(b.getDate_doc());
        edit_collections_flr_tf.setText(Long.toString(b.getFloor_level()));
        edit_collections_price_tf.setText(Float.toString(b.getUnit_price()));
        edit_collections_struct_tf.setText(b.getConc_structure());
        edit_collections_qty_tf.setText(Float.toString(b.getQty()));
        edit_collections_psc_tf.setText(Long.toString(b.getPSC_id()));

    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if (e.getSource() == edit_collections_submit_btn) {
            //collections
            if (checkFields()) {
                String client_name = edit_collections_client.getValue().toString();
                int psc = Integer.parseInt(edit_collections_psc_tf.getText());
                String project_name = edit_collections_pname_tf.getText();
                String project_add = edit_collections_padd_tf.getText();
                Date date_used = Date.valueOf(edit_collections_date.getValue());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date_doc = formatter.format(LocalDate.now());
                String struct = edit_collections_struct_tf.getText();
                int floor_level = Integer.parseInt(edit_collections_flr_tf.getText());
                float qty_temp = Float.parseFloat(edit_collections_qty_tf.getText());
                float unit_price = Float.parseFloat(edit_collections_price_tf.getText());
                float qty_added = 0;
                float qty_final = 0;
                //additional if less than 50
                if (qty_temp < 50.00) {
                    qty_added = (float) (50.00 - qty_temp);
                    qty_final = qty_temp + qty_added;
                    JOptionPane.showMessageDialog(null, "We added " + qty_added + " to the original qty. Final qty(in cubic meters): " + qty_final, "Qty. did not meet minimum", 2);

                }

                float total = (float) qty_final * unit_price;
                //checks if billing has unique psc and clientb.get(0).getPSC_id() == psc ||
                if (b.getPSC_id() == psc || !postgresql.checkBillingPSC(Controller.con, psc)) {
                    postgresql.editBilling(Controller.con, client_name, project_name, project_add,
                            Date.valueOf(date_doc), psc, date_used, floor_level, qty_final, unit_price,
                            struct, total, b.getBill_no());
                    stage = (Stage) edit_collections_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("collections.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else
                    JOptionPane.showMessageDialog(null, "A Similar bill already exists. (Repeating PSC No.)", "Unique Billing Violation", 2);
            }
        }

        if (e.getSource() == edit_collections_cancel_btn) {
            //clear fields
            stage = (Stage) edit_collections_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("collections.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    public boolean checkFields() {
        String client_name = edit_collections_client.getValue().toString();
        String psc = edit_collections_psc_tf.getText();
        String project_name = edit_collections_pname_tf.getText();
        String project_add = edit_collections_padd_tf.getText();
        String date_used = String.valueOf(edit_collections_date.getValue());
        String floor_level = edit_collections_flr_tf.getText();
        String qty_temp = edit_collections_qty_tf.getText();
        String struct = edit_collections_struct_tf.getText();
        String unit = edit_collections_price_tf.getText();


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

        if (!edit_collections_psc_tf.getText().matches("[0-9]+") || !edit_collections_flr_tf.getText().matches("[0-9]+") ||
                !edit_collections_price_tf.getText().matches("[0-9]+.[0-9]++") || !edit_collections_qty_tf.getText().matches("[0-9]+.[0-9]++")) {
            JOptionPane.showMessageDialog(null, "PSC and Floor fields must only contain whole numbers\n" +
                    "Price and Quantity fields must only be in the format of a whole number with decimal values", "Invalid Number Inputs", 2);
            return false;
        }
        else {
            System.out.println("All fields are filled!");
            return true;
        }
    }
}
