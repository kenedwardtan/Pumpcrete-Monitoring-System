package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.model.Billing;
import sample.model.Postgresql;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BillingsCreateController extends Controller implements  Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;

    //FXML ELEMENTS
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
    private TextField billings_total_tf;
    @FXML
    private Button billings_submit_btn;
    @FXML
    private Button billings_cancel_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> test = postgresql.getAllClientNames(Controller.con);
        billings_client.setItems(test);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        if (e.getSource() == billings_submit_btn) {

            if (checkFields()){
                String client_name = billings_client.getValue();
                int psc = Integer.parseInt(billings_psc_tf.getText());
                String project_name = billings_pname_tf.getText();
                String project_add = billings_padd_tf.getText();
                float total = Float.parseFloat(billings_total_tf.getText());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString = formatter.format(LocalDate.now());
                System.out.println(dateString);

                //checks if billing has unique psc and client
                if(!postgresql.checkBillingPSC(Controller.con ,psc)) {
                    postgresql.addBilling(Controller.con, client_name, project_name, project_add,
                            dateString, psc ,total);
                    stage = (Stage) billings_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("billings.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    //clear
                    //show error message
                    JOptionPane.showMessageDialog(null, "A Similar bill already exists. (Repeating PSC No.)", "Unique Billing Violation", 2);
                    stage = (Stage) billings_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("billingsCreate.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
            }

        }

        if (e.getSource() == billings_cancel_btn) {
            //clear fields
            stage = (Stage) billings_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    public boolean checkFields() {
        String client_name = billings_client.getValue();
        String psc = billings_psc_tf.getText();
        String project_name = billings_pname_tf.getText();
        String project_add = billings_padd_tf.getText();
        String total = billings_total_tf.getText();
        // check empty fields
        if (client_name.trim().equals("") || project_name.trim().equals("") || project_add.trim().equals("") || total.trim().equals("")
                || psc.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }

        // if everything is ok
        else {
            System.out.println("All fields are filled!");
            return true;
        }
    }
//
//    public boolean checkNumbers(){
//        if (total.getText().matches("\\d{8}") && clients_cellphone_tf.getText().matches("\\d{8}|\\d{11}")){
//            System.out.println("Its Valid Number");
//            return true;
//        } else {
//
//            System.out.println("Invalid Input..!");
//            return false;
//        }
//    }
}