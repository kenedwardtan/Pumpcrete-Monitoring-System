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
import sample.model.Collection;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CollectionsController extends Controller implements Initializable {
    private static long editCollection;
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
    private TableColumn<Collection, Long> prNumColumn;
    @FXML
    private TableColumn<Collection, String> clientNameColumn;
//    @FXML
//    private TableColumn<Collection, Long> pidColumn;
    @FXML
    private TableColumn<Collection, Long> totalColumn;
//    @FXML
//    private TableColumn<Collection, Long> billNumColumn;
    @FXML
    private TableColumn<Collection, LocalDate> dateColumn;
    @FXML
    private TableColumn<Collection, Boolean> postedColumn;
    @FXML
    private TableColumn<Collection, String> editedColumn;
    @FXML
    private TableColumn<Collection, String> postedByColumn;

    @FXML
    private TableView collections_tb1;
//    @FXML
//    private TableColumn<Collection, Long> amountColumn1;
    @FXML
    private TableColumn<Collection, String> clientNameColumn1;
    @FXML
    private TableColumn<Collection, String> bankColumn1;
    @FXML
    private TableColumn<Collection, Long> checkNumColumn1;
    @FXML
    private TableColumn<Collection, LocalDate> checkDateColumn;
    @FXML
    private TableColumn<Collection, Long> totalColumn1;

    @FXML
    private Button collections_create_btn;
    @FXML
    private Button collections_back_btn;
    @FXML
    private Button collections_edit_btn;
    @FXML
    private Button collections_post_btn;

    @FXML
    private ImageView collections_2img;
    @FXML
    private ImageView collections_3img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        collections_tb.setItems(postgresql.getAllCollections(Controller.con));

        prNumColumn.setCellValueFactory(new PropertyValueFactory<>("collection_no"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        //pidColumn.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("grand_total"));
        //billNumColumn.setCellValueFactory(new PropertyValueFactory<>("bill_no"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        postedColumn.setCellValueFactory(new PropertyValueFactory<>("posted"));
        editedColumn.setCellValueFactory(new PropertyValueFactory<>("edited_by"));
        postedByColumn.setCellValueFactory(new PropertyValueFactory<>("posted_by"));
        collections_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == collections_edit_btn) {
            ObservableList<Collection> col = FXCollections.observableArrayList();
            col = collections_tb.getSelectionModel().getSelectedItems();
            //this.editBilling = b.get(0).bill_no.get();
            if (col.get(0).getPosted() == false) {
                stage = (Stage) collections_edit_btn.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("collectionsEdit.fxml"));
                root = loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }else{
                JOptionPane.showMessageDialog(null,"Collection is already posted and cannot be edited!",
                        "Collection not editable", JOptionPane.ERROR_MESSAGE);
            }
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

        if(e.getSource() == collections_post_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Post selected item?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {
                ObservableList<Collection> col = FXCollections.observableArrayList();
                col = collections_tb.getSelectionModel().getSelectedItems();
                postgresql.postCollection(Controller.con, col.get(0).getCollection_no(), postgresql.getCurrUser(Controller.con),
                        col.get(0).getClient_name(), String.valueOf(LocalDate.now()));
            }
            stage = (Stage) collections_tb.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("collections.fxml"));
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
                Collection c = (Collection) collections_tb.getSelectionModel().getSelectedItem();
                this.editCollection = c.getCollection_no(); //test

                collections_tb1.setItems(collections_tb.getSelectionModel().getSelectedItems());
                clientNameColumn1.setCellValueFactory(new PropertyValueFactory<>("client_name"));
                bankColumn1.setCellValueFactory(new PropertyValueFactory<>("bank"));
                checkNumColumn1.setCellValueFactory(new PropertyValueFactory<>("check_number"));
                totalColumn1.setCellValueFactory(new PropertyValueFactory<>("grand_total"));
                checkDateColumn.setCellValueFactory(new PropertyValueFactory<>("check_date"));
                collections_tb1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                if (postgresql.getRole(Controller.con).equals("Supervisor")) {
                    collections_post_btn.setVisible(true);
                    collections_3img.setVisible(true);

                }

                if (postgresql.getRole(Controller.con).equals("Staff")) {
                    collections_2img.setVisible(true);
                    collections_edit_btn.setVisible(true);
                }

                System.out.println(postgresql.getRole(Controller.con));

            }
        });

        collections_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(collections_tb.getSelectionModel().getSelectedItem()); //test

                if (postgresql.getRole(Controller.con).equals("Staff")) {
                    collections_2img.setVisible(false);
                    collections_edit_btn.setVisible(false);
                }

                if (postgresql.getRole(Controller.con).equals("Supervisor")) {
                    collections_post_btn.setVisible(false);
                    collections_3img.setVisible(false);
                }
            }
        });
    }

    public static long getEditCollection() {
        return editCollection;
    }
}

