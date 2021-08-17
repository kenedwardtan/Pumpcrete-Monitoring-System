package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Postgresql;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminHomeController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;

    //dashboard
    @FXML
    private Text dashboard_name_txt;
    @FXML
    private Button dashboard_master_btn;
    @FXML
    private Button dashboard_transactions_btn;
    @FXML
    private Button dashboard_reports_btn;

    @FXML
    private Button profile_btn;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button reports_btn;
    @FXML
    private Button clients_btn;
    @FXML
    private Button inventory_btn;
    @FXML
    private Button receivables_btn;
    @FXML
    private Button billings_btn;
    @FXML
    private Button memos_btn;
    @FXML
    private Button employees_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private Button logout_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboard_name_txt.setText(postgresql.getCurrUser(Controller.con));
    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        if (e.getSource() == profile_btn) {
            stage = (Stage) profile_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == dashboard_btn) {
            stage = (Stage) dashboard_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == dashboard_master_btn) {

        } else if (e.getSource() == dashboard_transactions_btn) {

        } else if (e.getSource() == dashboard_reports_btn) {

        }

        if (e.getSource() == reports_btn) {
            stage = (Stage) reports_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reports.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == clients_btn) {
            stage = (Stage) clients_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clients.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == inventory_btn) {
            stage = (Stage) inventory_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == receivables_btn) {
            stage = (Stage) receivables_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("receivables.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == billings_btn) {
            stage = (Stage) billings_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == memos_btn) {
            stage = (Stage) memos_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("memos.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == employees_btn) {
            stage = (Stage) employees_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminEmployees.fxml"));
            root = loader.load();
            scene = new Scene(root);
            //scene.getStylesheets().add("/sample/resources/css/views.css");
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == settings_btn) {
            stage = (Stage) settings_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == logout_btn) {
            postgresql.endConnection(Controller.con);

//            //Check if user is still there, should print No connected user
//            String getUser = postgresql.getCurrUser(con);
//            System.out.println(getUser);

            //put the login form here again hehe
            stage = (Stage) logout_btn.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}

