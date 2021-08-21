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
import sample.model.Postgresql;
import sample.model.User;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public static String editUser;

    @FXML
    private TableView employees_tb;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> middleNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private Button employees_remove_btn;
    @FXML
    private Button employees_create_btn;
    @FXML
    private Button employees_back_btn;
    @FXML
    private Button employees_edit_btn;

    @FXML
    private ImageView employees_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employees_tb.setItems(postgresql.getAllUsers(Controller.con));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("first_name"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last_name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        employees_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == employees_edit_btn) {
            ObservableList<User> u = FXCollections.observableArrayList();
            u = employees_tb.getSelectionModel().getSelectedItems();
            editUser = u.get(0).username.get();

            stage = (Stage) employees_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployeesEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //delete selected rows
        if (e.getSource() == employees_remove_btn) {
            ObservableList<User> u = FXCollections.observableArrayList();
            u = employees_tb.getSelectionModel().getSelectedItems();
            int i = 0;
            while (i < u.size()) {
                System.out.println(u.get(i).username.get());
                postgresql.deleteUser(Controller.con, u.get(i++).username.get());
            }

            employees_tb.getItems().removeAll(employees_tb.getSelectionModel().getSelectedItems());
        }

        //create account
        if (e.getSource() == employees_create_btn) {
            stage = (Stage) employees_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("adminEmployeesCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == employees_back_btn) {
            stage = (Stage) employees_back_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        employees_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(employees_tb.getSelectionModel().getSelectedItem()); //test
                employees_2img.setVisible(true);
                employees_remove_btn.setVisible(true);
                employees_edit_btn.setVisible(true);
            }
        });

        employees_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(employees_tb.getSelectionModel().getSelectedItem()); //test
                employees_2img.setVisible(false);
                employees_remove_btn.setVisible(false);
                employees_edit_btn.setVisible(false);
            }
        });
    }

    public static String getEditUser(){
        return editUser;
    }
}

