package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Postgresql;
import sample.model.Pumpcrete;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryEditController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql = new Postgresql();
    public static Connection con;

    // inventory - edit
    @FXML
    private TextField edit_inventory_desc_tf;
    @FXML
    private TextField edit_inventory_plate_tf;
    @FXML
    private TextField edit_inventory_fuel_tf;
    @FXML
    private TextField edit_inventory_cr_tf;
    @FXML
    private TextField edit_inventory_or_tf;
    @FXML
    private TextField edit_inventory_tires_tf;
    @FXML
    private DatePicker edit_inventory_date;
    @FXML
    private Button edit_inventory_submit_btn;
    @FXML
    private Button edit_inventory_cancel_btn;

    private static ObservableList<Pumpcrete> p;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p = FXCollections.observableArrayList();
        p = postgresql.getPumpcrete(Controller.con, InventoryController.getSelectedID());

        edit_inventory_date.setValue(p.get(0).getPurchase_date());
        edit_inventory_desc_tf.setText(p.get(0).getDescription());
        edit_inventory_plate_tf.setText(p.get(0).getPlate_no());
        edit_inventory_fuel_tf.setText(p.get(0).getFuel_type());
        edit_inventory_cr_tf.setText(Long.toString(p.get(0).getCr_no()));
        edit_inventory_or_tf.setText(Long.toString(p.get(0).getOr_no()));
        edit_inventory_tires_tf.setText(Integer.toString(p.get(0).getTires()));
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        con = Controller.con;

        if (e.getSource() == edit_inventory_submit_btn) {
            System.out.println("edit");
            if (checkInventoryFields()) {
                String desc = edit_inventory_desc_tf.getText();
                int tires = Integer.parseInt(edit_inventory_tires_tf.getText());
                String plate = edit_inventory_plate_tf.getText();
                Date date = Date.valueOf(edit_inventory_date.getValue());
                String fuel = edit_inventory_fuel_tf.getText();
                long cr = Long.parseLong(edit_inventory_cr_tf.getText());
                long or = Long.parseLong(edit_inventory_or_tf.getText());

                if ((plate.equals(p.get(0).getPlate_no()) && cr == (p.get(0).getCr_no()) && or ==  p.get(0).getOr_no())
                        || !postgresql.checkPumpcrete(Controller.con, plate, cr, or)) {
                    System.out.println(date);

                    postgresql.editPumpcrete(Controller.con, InventoryController.getSelectedID(), desc, plate, fuel,
                            date, cr, or, tires);
                    stage = (Stage) edit_inventory_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }else
                    JOptionPane.showMessageDialog(null, "Pumpcrete already exist! Please recheck the details! (OR, CR, Plate number)\n", "Invalid input", 2);
            }
        }

        if (e.getSource() == edit_inventory_cancel_btn) {
            System.out.println("cancel");
            stage = (Stage) edit_inventory_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    public boolean checkInventoryFields() {

        String desc = edit_inventory_desc_tf.getText();
        String tires = edit_inventory_tires_tf.getText();
        String plate = edit_inventory_plate_tf.getText();
        String date = String.valueOf(edit_inventory_date.getValue());
        String fuel = edit_inventory_fuel_tf.getText();
        String cr = edit_inventory_cr_tf.getText();
        String or = edit_inventory_or_tf.getText();
        Boolean check = false;


        //check if theyre null
        if (desc == null || tires == null || plate == null
                || date == null || fuel == null
                || cr == null || or == null) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            check = false;
        }
        // check empty fields
        else if (desc.trim().equals("") || tires.trim().equals("") || plate.trim().equals("")
                || date.trim().equals("") || fuel.trim().equals("")
                || cr.trim().equals("") || or.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            check = false;
        }
        // if everything is filled
        else {
            System.out.println("All fields are filled!");
            check = true;
            if (edit_inventory_tires_tf.getText().matches("[0-9]+") && edit_inventory_cr_tf.getText().matches("[0-9]+") && edit_inventory_or_tf.getText().matches("[0-9]+")) {

                check = true;
            }
            else {
                JOptionPane.showMessageDialog(null, "Number of tires, OR, and CR fields must only contain numbers!", "Input Error!", JOptionPane.ERROR_MESSAGE);
                check = false;
            }
        }


        //check field types


        return check;
    }
}
