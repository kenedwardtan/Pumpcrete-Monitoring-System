package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Billing;
import sample.model.Client;
import sample.model.Collection;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
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
    private ChoiceBox<Long> create_collections_billings;
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
    private TableView create_collections_added_bills_tb;
    @FXML
    private TableColumn<Billing, Long> tb_bill_no_column;
    @FXML
    private TableColumn<Billing, Long> tb_PSC_id;
    @FXML
    private TableColumn<Billing, Float> tb_billing_total;

    @FXML
    private Button create_collections_submit_btn;
    @FXML
    private Button create_collections_cancel_btn;
    @FXML
    private Button create_collections_add_bill_btn;
    @FXML
    private Button create_collections_remove_bill_btn;

    private ObservableList<Long> cb;
    private ArrayList<Long> added_bills = new ArrayList<>();
    private ObservableList<String> names;
    private Long selectedBill;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        create_pr_number_tf.setText(String.valueOf(postgresql.getPRNumber(Controller.con)));
        create_pr_number_tf.setEditable(false);
        create_collections_date.setValue(LocalDate.now());
        create_collections_date.setEditable(false);
        this.names = FXCollections.observableArrayList();
        this.names= postgresql.getClientsWithUnpaid(Controller.con);

        create_collections_client.setItems(names);
        create_collections_client.setOnAction(this::getBills);

    }

    private void getBills(ActionEvent actionEvent) {
        added_bills.clear();
        create_collections_added_bills_tb.getItems().clear();
        ObservableList<Long> b = FXCollections.observableArrayList();
        b = postgresql.getBillNosByName(Controller.con, create_collections_client.getValue().trim());
        System.out.println("Size: "+b.size());
        create_collections_billings.setItems(b);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        if (e.getSource() == create_collections_submit_btn) {
            if (checkFields()) {
                String client_name = create_collections_client.getValue();
                String bill_no = "";
                for(int i=0; i<this.added_bills.size(); i++){
                    if(i != this.added_bills.size()-1) {
                        bill_no += (added_bills.get(i) + ",");
                        postgresql.inPayments(Controller.con, added_bills.get(i));
                    }
                    else {
                        bill_no += added_bills;
                    }
                }
                String bank = create_collections_bank_tf.getText();
                long c_no = Long.parseLong(create_collections_checkNum_tf.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = formatter.format(create_collections_date.getValue());
                String c_date = formatter.format(create_collections_checkDate.getValue());
                long total = Long.parseLong(create_collections_total_tf.getText());
//.substring(0, create_collections_total_tf.getLength()-1))
                postgresql.addCollection(Controller.con, date, client_name, bill_no, false,
                        total, bank, c_no, c_date);
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

        if (e.getSource() == create_collections_add_bill_btn) {
            long id = create_collections_billings.getValue();
            if (added_bills.indexOf(id) < 0) {
                System.out.println(id);
                this.added_bills.add(id);
                float total = 0;

                ObservableList<Billing> b = FXCollections.observableArrayList();
                for (int i = 0; i < this.added_bills.size(); i++) {
                    b.add(postgresql.getBilling(Controller.con, added_bills.get(i)));
                    total += b.get(i).getTotal();
                }
                create_collections_added_bills_tb.setItems(b);

                tb_bill_no_column.setCellValueFactory(new PropertyValueFactory<Billing, Long>("bill_no"));
                tb_PSC_id.setCellValueFactory(new PropertyValueFactory<Billing, Long>("PSC_id"));
                tb_billing_total.setCellValueFactory(new PropertyValueFactory<Billing, Float>("total"));
                tb_billing_total.setEditable(false);

                create_collections_total_tf.setText(String.valueOf(total));
            }else {
                JOptionPane.showMessageDialog(null, "Bill is already in the list!", "Failed to add bill",JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == create_collections_remove_bill_btn) {
            ObservableList<Billing> b = FXCollections.observableArrayList();
            b = create_collections_added_bills_tb.getSelectionModel().getSelectedItems();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove selected billing?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                //do stuff
                for(int i=0; i<b.size(); i++) {
                    this.added_bills.remove(this.added_bills.indexOf(b.get(i).getBill_no()));
                    create_collections_added_bills_tb.getItems().removeAll(create_collections_added_bills_tb.getSelectionModel().getSelectedItems());
                }
            }
        }
    }

    public boolean checkFields() {
        String client_name = create_collections_client.getValue();
        String bank = create_collections_bank_tf.getText();
        String c_no = create_collections_checkNum_tf.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String c_date = create_collections_checkDate.getValue().toString();
        String total = create_collections_total_tf.getText();

        //check if theyre null
        if (client_name == null || bank == null || c_no == null
                || c_date == null || total == null) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        // check empty fields
        else if (client_name.trim().equals("") ||  bank.trim().equals("") || c_no.trim().equals("")
                || c_date.trim().equals("") || total.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        if (!c_no.matches("[0-9]+") || !total.matches("[0-9]+.[0-9]++")) {
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

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

//        create_collections_client.getSelectionModel().selectedIndexProperty().addListener(
//
//                new ChangeListener<Number>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                        System.out.println("Changed option!");
//                        O
//
//                    }
//                }
//        );



        create_collections_added_bills_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Billing b = (Billing) create_collections_added_bills_tb.getSelectionModel().getSelectedItem();
                this.selectedBill = b.getBill_no(); //test
                create_collections_remove_bill_btn.setVisible(true);
            }
        });

        create_collections_added_bills_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(create_collections_added_bills_tb.getSelectionModel().getSelectedItem()); //test
                create_collections_remove_bill_btn.setVisible(false);
            }
        });

    }

}
