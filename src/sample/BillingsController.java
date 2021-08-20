package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
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

public class BillingsController extends Controller implements Initializable {
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
    private TableColumn<Billing, Integer> billNumColumn;
    @FXML
    private TableColumn<Billing, LocalDate> dateColumn;
    @FXML
    private TableColumn<Billing, String> clientNameColumn;
    @FXML
    private TableColumn<Billing, Boolean> postedColumn;

    @FXML
    private Button billings_unpost_btn;
    @FXML
    private Button billings_create_btn;
    @FXML
    private Button billings_back_btn;
    @FXML
    private Button billings_edit_btn;

    @FXML
    private ImageView billings_2img;

    @FXML
    private JOptionPane optionPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        postgresql = new Postgresql();

        billings_tb.setItems(postgresql.getAllBillings(Controller.con));

        billNumColumn.setCellValueFactory(new PropertyValueFactory<Billing, Integer>("bill_no"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Billing, String>("client_name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Billing, LocalDate>("date_doc"));
        postedColumn.setCellValueFactory(new PropertyValueFactory<Billing, Boolean>("posted"));
        billings_tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        //edit
        if (e.getSource() == billings_edit_btn) {
            stage = (Stage) billings_edit_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billingsEdit.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //delete selected rows
        if (e.getSource() == billings_unpost_btn) {
            billings_tb.getItems().removeAll(billings_tb.getSelectionModel().getSelectedItems());
            //*code to delete from db as well*
        }

        //add billing
        if (e.getSource() == billings_create_btn) {
            stage = (Stage) billings_create_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("billingsCreate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //back to dashboard
        if (e.getSource() == billings_back_btn) {
            //switch roles?
            //temp
            stage = (Stage) billings_back_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent e) throws IOException, SQLException {

        billings_tb.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(billings_tb.getSelectionModel().getSelectedItem()); //test
                billings_2img.setVisible(true);
                billings_unpost_btn.setVisible(true);
                billings_edit_btn.setVisible(true);
            }
        });

        billings_tb.setOnMouseExited((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(billings_tb.getSelectionModel().getSelectedItem()); //test
                billings_2img.setVisible(false);
                billings_unpost_btn.setVisible(false);
                billings_edit_btn.setVisible(false);
            }
        });
    }
}

