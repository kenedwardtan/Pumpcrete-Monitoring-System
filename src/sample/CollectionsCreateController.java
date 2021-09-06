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
    private TextField create_pr_number_tf;
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
    private ObservableList<String> names;
    private ObservableList<Client> test;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_pr_number_tf.setText("a");
        create_collections_date.setValue(LocalDate.now());
        create_collections_date.setEditable(false);
        this.names = FXCollections.observableArrayList();
        this.test= postgresql.getAllClientNames(Controller.con);
        for(int i=0; i<test.size(); i++){
            names.add(test.get(i).getName());
        }
        create_collections_client.setItems(names);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        if (e.getSource() == create_collections_submit_btn) {
            if (checkFields()) {
                String client_name = create_collections_client.getValue();
                long bill_no = Long.parseLong(create_collections_billNum_tf.getText());
                long p_id = Long.parseLong(create_collections_pid_tf.getText());
                long amount = Long.parseLong(create_collections_amount_tf.getText());
                String bank = create_collections_bank_tf.getText();
                long c_no = Long.parseLong(create_collections_checkNum_tf.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String c_date = formatter.format(create_collections_checkDate.getValue());
                long total = Long.parseLong(create_collections_total_tf.getText());

//                //checks if billing has unique psc and client
//                if (!postgresql.checkBillingPSC(Controller.con, psc)) {
//                    postgresql.addBilling(Controller.con, client_name, project_name, project_add,
//                            Date.valueOf(date_doc), psc, date_used, floor_level, qty_final, unit_price, struct, total);
//                    stage = (Stage) create_collections_submit_btn.getScene().getWindow();
//                    loader = new FXMLLoader(getClass().getResource("collections.fxml"));
//                    root = loader.load();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.setResizable(false);
//                    stage.show();
//                } else {
//                    //clear
//                    //show error message
//                    JOptionPane.showMessageDialog(null, "A Similar bill already exists. (Repeating PSC No.)", "Unique Billing Violation", 2);
//                    stage = (Stage) create_collections_submit_btn.getScene().getWindow();
//                    loader = new FXMLLoader(getClass().getResource("collectionsCreate.fxml"));
//                    root = loader.load();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.setResizable(false);
//                    stage.show();
//
//                }
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
        String client_name = create_collections_client.getValue();
        String bill_no = create_collections_billNum_tf.getText();
        String p_id = create_collections_pid_tf.getText();
        String amount = create_collections_amount_tf.getText();
        String bank = create_collections_bank_tf.getText();
        String c_no = create_collections_checkNum_tf.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String c_date = formatter.format(create_collections_checkDate.getValue());
        String total = create_collections_total_tf.getText();

        //check if theyre null
        if (client_name == null || bill_no == null || p_id == null
                || amount == null || bank == null || c_no == null
                || c_date == null || total == null) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        // check empty fields
        else if (client_name.trim().equals("") || bill_no.trim().equals("") || p_id.trim().equals("")
                || amount.trim().equals("") || bank.trim().equals("") || c_no.trim().equals("")
                || c_date.trim().equals("") || total.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        if (!bill_no.matches("[0-9]+") || !p_id.matches("[0-9]+") || !c_no.matches("[0-9]+") ||
                !amount.matches("[0-9]+.[0-9]++") || !total.matches("[0-9]+.[0-9]++")) {
            JOptionPane.showMessageDialog(null, "Bill Number, Pumpcrete ID and Check Number fields must only contain whole numbers\n" +
                    "Amount and Total Amount fields must only be in the format of a whole number with decimal values", "Invalid Number Inputs", 2);
            return false;
        }


        // if everything is ok
        else {
            System.out.println("All fields are filled!");
            return true;
        }
    }
}
