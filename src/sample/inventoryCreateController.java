package sample;

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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class inventoryCreateController extends  Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;

    //inventory (pumpcrete) - create
    @FXML
    private TextField create_inventory_desc_tf;
    @FXML
    private DatePicker create_inventory_date;
    @FXML
    private TextField create_inventory_tires_tf;
    @FXML
    private TextField create_inventory_plate_tf;
    @FXML
    private TextField create_inventory_fuel_tf;
    @FXML
    private TextField create_inventory_or_tf;
    @FXML
    private TextField create_inventory_cr_tf;
    @FXML
    private Button create_inventory_submit_btn;
    @FXML
    private Button create_inventory_cancel_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_inventory_date.setValue(LocalDate.now());
        create_inventory_date.setEditable(false);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //INVENTORY CREATE BUTTONS
        if(e.getSource()==create_inventory_submit_btn)

        {
            if (checkInventoryFields()) {
                String desc = create_inventory_desc_tf.getText();
                int tires = Integer.parseInt(create_inventory_tires_tf.getText());
                String plate = create_inventory_plate_tf.getText();
                Date date = Date.valueOf(create_inventory_date.getValue());
                String fuel = create_inventory_fuel_tf.getText();
                long cr = Long.parseLong(create_inventory_cr_tf.getText());
                long or = Long.parseLong(create_inventory_or_tf.getText());
                System.out.println(!postgresql.checkPumpcrete(Controller.con, plate, cr, or));
                if (!postgresql.checkPumpcrete(Controller.con, plate, cr, or)) {
                    System.out.println(date);

                    postgresql.addPumpcrete(Controller.con, desc, plate, fuel,
                            date, cr, or, tires);
                    stage = (Stage) create_inventory_submit_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else {
                    //clear
                    //show error message
                    JOptionPane.showMessageDialog(null, "A similar pumpcrete already exists.", "Unique Pumpcrete Violation", 2);
                }
            }
        }
        if (e.getSource() == create_inventory_cancel_btn) {
            //clear fields
            stage = (Stage) create_inventory_cancel_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
    public boolean checkInventoryFields() {

        String desc = create_inventory_desc_tf.getText();
        String tires = create_inventory_tires_tf.getText();
        String plate = create_inventory_plate_tf.getText();
        String date = String.valueOf(create_inventory_date.getValue());
        String fuel = create_inventory_fuel_tf.getText();
        String cr = create_inventory_cr_tf.getText();
        String or = create_inventory_or_tf.getText();
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
            //check field types
            if (create_inventory_tires_tf.getText().matches("[0-9]+") && create_inventory_cr_tf.getText().matches("[0-9]+") && create_inventory_or_tf.getText().matches("[0-9]+")) {
                check = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "Number of tires, OR, and CR fields must only contain numbers!", "Input Error!", JOptionPane.ERROR_MESSAGE);
                check = false;
            }
        }



        return check;
    }


}
