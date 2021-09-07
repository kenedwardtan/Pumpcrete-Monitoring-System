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

public class CollectionsEditController extends Controller implements  Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;

    //FXML ELEMENTS
    //collections - create
    @FXML
    private ChoiceBox<String> edit_collections_client;
    @FXML
    private ChoiceBox<Long> edit_collections_billings;
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
    private TextField edit_pr_number_tf;
    @FXML
    private TextField collections_price_tf;
    @FXML
    private DatePicker edit_collections_date;
    @FXML
    private DatePicker edit_collections_checkDate;

    @FXML
    private TableView edit_collections_added_bills_tb;
    @FXML
    private TableColumn<Billing, Long> tb_bill_no_column;
    @FXML
    private TableColumn<Billing, Long> tb_PSC_id;
    @FXML
    private TableColumn<Billing, Float> tb_billing_total;

    @FXML
    private Button edit_collections_submit_btn;
    @FXML
    private Button edit_collections_cancel_btn;
    @FXML
    private Button edit_collections_add_bill_btn;
    @FXML
    private Button edit_collections_remove_bill_btn;

    private ArrayList<String> added_bills = new ArrayList<>();
    private ObservableList<String> names;
    private Long selectedBill;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();
        System.out.println("Collections no="+CollectionsController.getEditCollection());
        ObservableList<Collection> c = FXCollections.observableArrayList();
        c = postgresql.getCollection(Controller.con, CollectionsController.getEditCollection());
        this.names = FXCollections.observableArrayList();
        this.names= postgresql.getAllClientNames(Controller.con);

        edit_collections_client.setItems(names);

        edit_pr_number_tf.setText(String.valueOf(c.get(0).getCollection_no()));
        edit_collections_date.setValue(c.get(0).getDate());
        edit_collections_client.setValue(c.get(0).getClient_name());
        edit_collections_total_tf.setText(String.valueOf(c.get(0).getGrand_total()));
        edit_collections_bank_tf.setText(c.get(0).getBank());
        edit_collections_checkDate.setValue(c.get(0).getCheck_date());
        edit_collections_checkNum_tf.setText(String.valueOf(c.get(0).getCheck_number()));

        ObservableList<Billing> b = FXCollections.observableArrayList();
        for(int i=0; i<c.get(0).getBilling_id().size(); i++) {
            b.add(postgresql.getBilling(Controller.con, Long.parseLong(c.get(0).getBilling_id().get(i))));
        }
        edit_collections_added_bills_tb.setItems(b);
        tb_bill_no_column.setCellValueFactory(new PropertyValueFactory<Billing, Long>("billing_no"));
        tb_billing_total.setCellValueFactory(new PropertyValueFactory<Billing, Float>("total"));
        tb_PSC_id.setCellValueFactory(new PropertyValueFactory<Billing, Long>("psc_id"));
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        if (e.getSource() == edit_collections_submit_btn) {
            if (checkFields()) {
                String client_name = edit_collections_client.getValue();
                String bill_no = "";
                for(int i=0; i<this.added_bills.size(); i++){
                    if(i != this.added_bills.size()-1)
                        bill_no += (added_bills.get(i) +",");
                    else
                        bill_no += added_bills;
                }
                String bank = edit_collections_bank_tf.getText();
                long c_no = Long.parseLong(edit_collections_checkNum_tf.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = formatter.format(edit_collections_date.getValue());
                String c_date = formatter.format(edit_collections_checkDate.getValue());
                long total = Long.parseLong(edit_collections_total_tf.getText());

                postgresql.addCollection(Controller.con, date, client_name, bill_no, false,
                        total, bank, c_no, c_date);
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

        if (e.getSource() == edit_collections_add_bill_btn) {
            long id = edit_collections_billings.getValue();
            if (added_bills.indexOf(String.valueOf(id)) >= 0) {
                this.added_bills.add(String.valueOf(id));
                float total = 0;

                ObservableList<Billing> b = FXCollections.observableArrayList();
                for (int i = 0; i < this.added_bills.size(); i++) {
                    b.add(postgresql.getBilling(Controller.con, Long.parseLong(added_bills.get(i))));
                    total += b.get(i).getTotal();
                }
                edit_collections_added_bills_tb.setItems(b);

                tb_bill_no_column.setCellValueFactory(new PropertyValueFactory<Billing, Long>("bill_no"));
                tb_PSC_id.setCellValueFactory(new PropertyValueFactory<Billing, Long>("psc_id"));
                tb_billing_total.setCellValueFactory(new PropertyValueFactory<Billing, Float>("total"));

                edit_collections_total_tf.setText(String.valueOf(total));
            } else {
                JOptionPane.showMessageDialog(null, "Bill is already in the list!", "Failed to add bill",JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == edit_collections_remove_bill_btn) {
            ObservableList<Billing> b = FXCollections.observableArrayList();
            b = edit_collections_added_bills_tb.getSelectionModel().getSelectedItems();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove selected billing?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                //do stuff
                for(int i=0; i<b.size(); i++) {
                    this.added_bills.remove(this.added_bills.indexOf(b.get(i).bill_no));
                    edit_collections_added_bills_tb.getItems().removeAll(edit_collections_added_bills_tb.getSelectionModel().getSelectedItems());
                }
            }
        }
    }

    public boolean checkFields() {
        String client_name = edit_collections_client.getValue();
        String amount = edit_collections_amount_tf.getText();
        String bank = edit_collections_bank_tf.getText();
        String c_no = edit_collections_checkNum_tf.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String c_date = formatter.format(edit_collections_checkDate.getValue());
        String total = edit_collections_total_tf.getText();

        //check if theyre null
        if (client_name == null || bank == null || c_no == null
                || c_date == null || total == null) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        // check empty fields
        else if (client_name.trim().equals("") || amount.trim().equals("") ||
                bank.trim().equals("") || c_no.trim().equals("")
                || c_date.trim().equals("") || total.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", 2);
            return false;
        }
        if (!c_no.matches("[0-9]+") ||
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

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        edit_collections_client.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        ObservableList<Long> b = FXCollections.observableArrayList();
                        b = postgresql.getBillNosByName(Controller.con, names.get(newValue.intValue()));
                        edit_collections_billings.setItems(b);
                    }
                }
        );

        edit_collections_added_bills_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Collection c = (Collection) edit_collections_added_bills_tb.getSelectionModel().getSelectedItem();
                this.selectedBill = c.getCollection_no(); //test
                edit_collections_remove_bill_btn.setVisible(true);
            }
        });

        edit_collections_added_bills_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(edit_collections_added_bills_tb.getSelectionModel().getSelectedItem()); //test
                edit_collections_remove_bill_btn.setVisible(false);
            }
        });

    }

}
