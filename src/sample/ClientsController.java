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
import sample.model.User;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientsController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public int selectedID =0;

    @FXML
    private Button clients_create_btn;

    @FXML
    private TableView clients_tb;
    @FXML
    private TableColumn<Client, Integer> IDColumn;
    @FXML
    private TableColumn<Client, String> dateColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableView clients_details_tb;
    @FXML
    private TableColumn<Client, Integer> IDColumn1;
    @FXML
    private TableColumn<Client, String> dateColumn1;
    @FXML
    private TableColumn<Client, String> nameColumn1;
    @FXML
    private TableColumn<Client, String> emailColumn1;
    @FXML
    private TableColumn<Client, Integer> cellphoneColumn1;
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
    private ImageView clients_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clients_tb.setItems(postgresql.getAllClients(Controller.con));

        IDColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("latest_date"));

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
            stage.show();
        }

        //edit
        if (e.getSource() == clients_edit_btn) {
            stage = (Stage) clients_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clientsEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //delete selected rows
        if (e.getSource() == clients_remove_btn) {
            ObservableList<Client> c = FXCollections.observableArrayList();
            c = clients_tb.getSelectionModel().getSelectedItems();
            int i = 0;
            while (i < c.size()) {
                postgresql.deleteClient(Controller.con, c.get(i++).id.get());
            }

            clients_tb.getItems().removeAll(clients_tb.getSelectionModel().getSelectedItems());

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
            stage.show();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        clients_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Client c = (Client) clients_tb.getSelectionModel().getSelectedItem();
                selectedID = c.getID();
                System.out.println(selectedID); //test
                clients_2img.setVisible(true);
                clients_remove_btn.setVisible(true);
                clients_edit_btn.setVisible(true);

                //set details table

                clients_details_tb.setItems(clients_tb.getSelectionModel().getSelectedItems());
                IDColumn1.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
                emailColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
                nameColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
                dateColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("latest_date"));
                positionColumn1.setCellValueFactory(new PropertyValueFactory<Client, String>("position"));
                landlineColumn1.setCellValueFactory(new PropertyValueFactory<Client, Integer>("landline"));
                cellphoneColumn1.setCellValueFactory(new PropertyValueFactory<Client, Integer>("cpnum"));
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
}


