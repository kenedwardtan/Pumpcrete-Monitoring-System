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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Billing;
import sample.model.Client;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BillingsController extends Controller implements Initializable {
    private static long editBilling;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public Controller Controller;

    @FXML
    private TableView billings_tb;
    @FXML
    private TableColumn<Billing, Long> billNumColumn;
    @FXML
    private TableColumn<Billing, LocalDate> dateColumn;
    @FXML
    private TableColumn<Billing, String> clientNameColumn;
    @FXML
    private TableColumn<Billing, Boolean> postedColumn;
    @FXML
    private TableColumn<Billing, String> editedColumn;
    @FXML
    private TableColumn<Billing, String> postedByColumn;
    @FXML
    private Button billings_create_btn;
    @FXML
    private Button billings_back_btn;
    @FXML
    private Button billings_edit_btn;
    @FXML
    private Button billings_post_btn;

    @FXML
    private ImageView billings_2img;
    @FXML
    private ImageView billings_3img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        billings_tb.setItems(postgresql.getAllBillings(Controller.con));

        billNumColumn.setCellValueFactory(new PropertyValueFactory<>("bill_no"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_doc"));
        editedColumn.setCellValueFactory(new PropertyValueFactory<>("edited_by"));
        postedByColumn.setCellValueFactory(new PropertyValueFactory<>("posted_by"));
        postedColumn.setCellValueFactory(new PropertyValueFactory<>("posted"));
        billings_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == billings_edit_btn) {
            ObservableList<Billing> b = FXCollections.observableArrayList();
            b = billings_tb.getSelectionModel().getSelectedItems();
            if (b.get(0).getPosted() == false) {
                this.editBilling = b.get(0).getBill_no();
                System.out.println(editBilling);

                stage = (Stage) billings_edit_btn.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("billingsEdit.fxml"));
                root = loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }else{
                JOptionPane.showMessageDialog(null,"Billing is already posted and cannot be edited!",
                        "Billing not editable", JOptionPane.ERROR_MESSAGE);
            }
        }

        //delete selected rows
        //if (e.getSource() == billings_unpost_btn) {
            //billings_tb.getItems().removeAll(billings_tb.getSelectionModel().getSelectedItems());
            //*code to delete from db as well*
        //}

        //add billing
        if (e.getSource() == billings_create_btn) {
            stage = (Stage) billings_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billingsCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == billings_back_btn) {
            //switch roles?
            //temp
            stage = (Stage) billings_back_btn.getScene().getWindow();
            System.out.println(postgresql.getCurrUser(Controller.con));
            switch (postgresql.getRole(Controller.con)) {
                    case "Staff":
                    case "staff":
                        stage = (Stage) billings_back_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        break;
                    case "Admin":
                    case "admin":
                        stage = (Stage) billings_back_btn.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        break;
                    case "Supervisor":
                    case "supervisor":
                        stage = (Stage) billings_back_btn.getScene().getWindow();
                        loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        break;
                    default:
                        errorAlert.setHeaderText("Input not valid");
                        errorAlert.setContentText("Invalid Role.");
                        errorAlert.showAndWait();
                        break;

                }
            }
            if (e.getSource() == billings_post_btn){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Post selected item?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if(alert.getResult() == ButtonType.YES) {
                    ObservableList<Billing> b = FXCollections.observableArrayList();
                    b = billings_tb.getSelectionModel().getSelectedItems();
                    postgresql.postBilling(Controller.con, b.get(0).getBill_no(), postgresql.getCurrUser(Controller.con),
                            b.get(0).getClient_name(), String.valueOf(b.get(0).getDate_doc()));
                }
                stage = (Stage) billings_post_btn.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("billings.fxml"));
                root = loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }

    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        billings_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Billing b = (Billing) billings_tb.getSelectionModel().getSelectedItem();

                if (postgresql.getRole(Controller.con).equals("Supervisor")) {
                    billings_post_btn.setVisible(true);
                    billings_3img.setVisible(true);

                }

                if (postgresql.getRole(Controller.con).equals("Staff")) {
                    billings_2img.setVisible(true);
                    billings_edit_btn.setVisible(true);
                }
            }
        });

        billings_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(billings_tb.getSelectionModel().getSelectedItem()); //test

                if (postgresql.getRole(Controller.con).equals("Staff")) {
                    billings_2img.setVisible(false);
                    billings_edit_btn.setVisible(false);
                }

                if (postgresql.getRole(Controller.con).equals("Supervisor")) {
                    billings_post_btn.setVisible(false);
                    billings_3img.setVisible(false);

                }
            }
        });
    }

    public static long getEditBilling() {
        return editBilling;
    }
}

