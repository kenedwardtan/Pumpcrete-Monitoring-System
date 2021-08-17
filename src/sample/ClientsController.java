package sample;

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
import sample.model.Postgresql;
import sample.model.User;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientsController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;

    @FXML
    private TableView clients_tb;
    @FXML
    private TableColumn<User, String> IDColumn;
    @FXML
    private TableColumn<User, String> dateColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableView clients_details_tb;
    @FXML
    private TableColumn<User, String> IDColumn1;
    @FXML
    private TableColumn<User, String> dateColumn1;
    @FXML
    private TableColumn<User, String> firstNameColumn1;
    @FXML
    private TableColumn<User, String> lastNameColumn1;
    @FXML
    private TableColumn<User, String> emailColumn1;
    @FXML
    private TableColumn<User, String> cellphoneColumn1;
    @FXML
    private TableColumn<User, String> landlineColumn1;
    @FXML
    private TableColumn<User, String> addressColumn1;
    @FXML
    private TableColumn<User, String> positionColumn1;

    @FXML
    private Button clients_remove_btn;
    @FXML
    private Button clients_create_btn;
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
        clients_tb.setItems(postgresql.getAllUsers());

        IDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last_name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<User, String>("date"));

        clients_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

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
            clients_tb.getItems().removeAll(clients_tb.getSelectionModel().getSelectedItems());
            //*code to delete from db as well*
        }

        //add client
        if (e.getSource() == clients_create_btn) {
            stage = (Stage) clients_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("clientsCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == clients_back_btn) {
            //switch roles?
            //temp
            stage = (Stage) clients_back_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
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
                System.out.println(clients_tb.getSelectionModel().getSelectedItem()); //test
                clients_2img.setVisible(true);
                clients_remove_btn.setVisible(true);
                clients_edit_btn.setVisible(true);
            }
        });

        clients_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(clients_tb.getSelectionModel().getSelectedItem()); //test
                clients_2img.setVisible(false);
                clients_remove_btn.setVisible(false);
                clients_edit_btn.setVisible(false);
            }
        });
    }
}

