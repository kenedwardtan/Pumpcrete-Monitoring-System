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
import sample.model.Client;
import sample.model.Postgresql;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ClientsController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public long selectedID =0;
    public static long editClient;

    @FXML
    private Button clients_create_btn;

    @FXML
    private TableView clients_tb;
    @FXML
    private TableColumn<Client, Long> IDColumn;
    @FXML
    private TableColumn<Client, LocalDate> dateColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableView clients_details_tb;
    @FXML
    private TableColumn<Client, Long> IDColumn1;
    @FXML
    private TableColumn<Client, LocalDate> dateColumn1;
    @FXML
    private TableColumn<Client, String> nameColumn1;
    @FXML
    private TableColumn<Client, String> emailColumn1;
    @FXML
    private TableColumn<Client, Long> cellphoneColumn1;
    @FXML
    private TableColumn<Client, Integer> landlineColumn1;
    @FXML
    private TableColumn<Client, String> addressColumn1;
    @FXML
    private TableColumn<Client, String> positionColumn1;

    @FXML
    private Button clients_remove_btn;
    @FXML
    private Button clients_back_btn;
    @FXML
    private Button clients_edit_btn;

    @FXML
    private Label date_lbl;
    @FXML
    private Label name_lbl;
    @FXML
    private Label project_lbl;
    @FXML
    private Label billnum_lbl;
    @FXML
    private Label prnum_lbl;
    @FXML
    private Label amount_lbl;
    @FXML
    private Label status_lbl;
    @FXML
    private Label cmnum_lbl;
    @FXML
    private Label paid_lbl;
    @FXML
    private Label balance_lbl;
    @FXML
    private Label overpaid_lbl;
    @FXML
    private Label total_lbl;

    @FXML
    private ImageView clients_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clients_tb.setItems(postgresql.getAllClients(Controller.con));

        IDColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("latest_date"));
        positionColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("position"));

        clients_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        //create
        if (e.getSource() == clients_create_btn) {
            //clear fields
            stage = (Stage) clients_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clientsCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //edit
        if (e.getSource() == clients_edit_btn) {
            ObservableList<Client> c = FXCollections.observableArrayList();
            c = clients_tb.getSelectionModel().getSelectedItems();
            editClient = c.get(0).id.get();

            stage = (Stage) clients_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clientsEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //delete selected rows
        if (e.getSource() == clients_remove_btn) {
            ObservableList<Client> c = FXCollections.observableArrayList();
            c = clients_tb.getSelectionModel().getSelectedItems();
            int i = 0;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete selected items?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                //do stuff
                while (i < c.size()) {
                    postgresql.deleteClient(Controller.con, c.get(i++).id.get());
                }
                clients_tb.getItems().removeAll(clients_tb.getSelectionModel().getSelectedItems());
            }
        }


        //back to dashboard
        if (e.getSource() == clients_back_btn) {
            //switch roles?
            //temp
            stage = (Stage) clients_back_btn.getScene().getWindow();
            switch(postgresql.getRole(Controller.con)){
                case"admin":
                case "Admin":
                    loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    break;
                default:
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    break;
            }

            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        clients_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Client c = (Client) clients_tb.getSelectionModel().getSelectedItem();
                selectedID = c.getId();
                System.out.println(selectedID); //test
                clients_2img.setVisible(true);
                clients_remove_btn.setVisible(true);
                clients_edit_btn.setVisible(true);

                //set details table

                clients_details_tb.setItems(clients_tb.getSelectionModel().getSelectedItems());
                IDColumn1.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
                emailColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
                nameColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
//                dateColumn1.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("latest_date"));
                landlineColumn1.setCellValueFactory(new PropertyValueFactory<Client, Integer>("landline"));
                cellphoneColumn1.setCellValueFactory(new PropertyValueFactory<Client, Long>("cpnum"));
                addressColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));

            }
        });

        clients_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Client c = (Client) clients_tb.getSelectionModel().getSelectedItem();
                selectedID = 0;
                System.out.println(clients_tb.getSelectionModel().getSelectedItem()); //test
                clients_details_tb.getItems().removeAll();
                clients_2img.setVisible(false);
                clients_remove_btn.setVisible(false);
                clients_edit_btn.setVisible(false);
            }
        });
    }

    public static long getEditClient() {
        return editClient;
    }
}


