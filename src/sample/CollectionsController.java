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

public class CollectionsController extends Controller implements Initializable {
    private static long editBilling;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public Controller Controller;

    @FXML
    private TableView collections_tb;
    @FXML
    private TableColumn<Billing, Long> prNumColumn;
    @FXML
    private TableColumn<Billing, String> clientNameColumn;
    @FXML
    private TableColumn<Billing, Long> pidColumn;
    @FXML
    private TableColumn<Billing, Long> totalColumn;
    @FXML
    private TableColumn<Billing, Long> billNumColumn;
    @FXML
    private TableColumn<Billing, LocalDate> dateColumn;
    @FXML
    private TableColumn<Billing, Boolean> postedColumn;

    @FXML
    private TableView collections_tb1;
    @FXML
    private TableColumn<Billing, Long> amountColumn1;
    @FXML
    private TableColumn<Billing, String> clientNameColumn1;
    @FXML
    private TableColumn<Billing, String> bankColumn1;
    @FXML
    private TableColumn<Billing, Long> checkNumColumn1;
    @FXML
    private TableColumn<Billing, LocalDate> checkDateColumn1;
    @FXML
    private TableColumn<Billing, Long> totalColumn1;

    @FXML
    private Button collections_create_btn;
    @FXML
    private Button collections_back_btn;
    @FXML
    private Button collections_edit_btn;

    @FXML
    private ImageView collections_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        //collections_tb.setItems(postgresql.getAllCollections(Controller.con));

        prNumColumn.setCellValueFactory(new PropertyValueFactory<>("pr_no"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        billNumColumn.setCellValueFactory(new PropertyValueFactory<>("bill_no"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_doc"));
        postedColumn.setCellValueFactory(new PropertyValueFactory<>("posted"));
        collections_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        clientNameColumn1.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        amountColumn1.setCellValueFactory(new PropertyValueFactory<>("amount"));
        bankColumn1.setCellValueFactory(new PropertyValueFactory<>("bank"));
        checkNumColumn1.setCellValueFactory(new PropertyValueFactory<>("posted"));
        totalColumn1.setCellValueFactory(new PropertyValueFactory<>("total"));
        checkDateColumn1.setCellValueFactory(new PropertyValueFactory<>("bill_no"));
        collections_tb1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == collections_edit_btn) {
            //ObservableList<Billing> b = FXCollections.observableArrayList();
            //b = collections_tb.getSelectionModel().getSelectedItems();
            //this.editBilling = b.get(0).bill_no.get();

            stage = (Stage) collections_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("collectionsEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //add collection/payment
        if (e.getSource() == collections_create_btn) {
            stage = (Stage) collections_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("collectionsCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == collections_back_btn) {
            stage = (Stage) collections_back_btn.getScene().getWindow();
            System.out.println(postgresql.getCurrUser(Controller.con));
            switch (postgresql.getRole(Controller.con)) {
                case "Staff":
                case "staff":
                    stage = (Stage) collections_back_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) collections_back_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Supervisor":
                case "supervisor":
                    stage = (Stage) collections_back_btn.getScene().getWindow();
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
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        collections_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //Billing b = (Billing) collections_tb.getSelectionModel().getSelectedItem();
                //System.out.println(b.getBill_no()); //test
                collections_2img.setVisible(true);
                collections_edit_btn.setVisible(true);
            }
        });

        collections_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(collections_tb.getSelectionModel().getSelectedItem()); //test
                collections_2img.setVisible(false);
                collections_edit_btn.setVisible(false);
            }
        });
    }

    /*public static long getEditBilling() {
        return editBilling;
    }*/
}

