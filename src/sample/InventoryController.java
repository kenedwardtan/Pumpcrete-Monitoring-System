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
import sample.model.Billing;
import sample.model.Postgresql;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;
    public Controller Controller;

    @FXML
    private TableView inventory_tb;
    /*
    @FXML
    private TableColumn<Inventory, Long> idColumn;
    @FXML
    private TableColumn<Inventory, String> descColumn;
    @FXML
    private TableColumn<Inventory, Boolean> cidColumn;

    @FXML
    private TableColumn<Inventory, Long> idColumn1;
    @FXML
    private TableColumn<Inventory, LocalDate> dateColumn1;
    @FXML
    private TableColumn<Inventory, String> descColumn1;
    @FXML
    private TableColumn<Inventory, Boolean> cidColumn1;
    @FXML
    private TableColumn<Inventory, Long> plateColumn1;
    @FXML
    private TableColumn<Inventory, LocalDate> fuelColumn1;
    @FXML
    private TableColumn<Inventory, Long> crColumn1;
    @FXML
    private TableColumn<Inventory, Long> orColumn1;
    @FXML
    private TableColumn<Inventory, Long> tiresColumn1;
*/
    @FXML
    private Button inventory_create_btn;
    @FXML
    private Button inventory_back_btn;
    @FXML
    private Button inventory_edit_btn;
    @FXML
    private Button inventory_delete_btn;

    @FXML
    private ImageView inventory_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        //inventory_tb.setItems(postgresql.getAllinventory(Controller.con));

        //idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        //dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_doc"));
        //postedColumn.setCellValueFactory(new PropertyValueFactory<>("posted"));

        //inventory_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == inventory_edit_btn) {
            stage = (Stage) inventory_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("inventoryEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //delete selected rows
        if (e.getSource() == inventory_delete_btn) {
            inventory_tb.getItems().removeAll(inventory_tb.getSelectionModel().getSelectedItems());
        }

        //add billing
        if (e.getSource() == inventory_create_btn) {
            stage = (Stage) inventory_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("inventoryCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == inventory_back_btn) {
            //switch roles?
            //temp
            stage = (Stage) inventory_back_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        inventory_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Billing b = (Billing) inventory_tb.getSelectionModel().getSelectedItem();
                System.out.println(b.getBill_no()); //test
                inventory_2img.setVisible(true);
                inventory_edit_btn.setVisible(true);
            }
        });

        inventory_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(inventory_tb.getSelectionModel().getSelectedItem()); //test
                inventory_2img.setVisible(false);
                inventory_edit_btn.setVisible(false);
            }
        });
    }
}

