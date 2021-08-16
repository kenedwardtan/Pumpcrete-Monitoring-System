package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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

public class StaffHomeController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;
    public static Connection con;


    @FXML
    private Text home_name_txt;
    @FXML
    private Button home_master_btn;
    @FXML
    private Button home_transactions_btn;
    @FXML
    private Button home_reports_btn;
    //settings
    @FXML
    private Button settings_btn;
    //logout
    @FXML
    private Button logout_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home_name_txt.setText(postgresql.getCurrUser(Controller.con));

    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);


        //settings
        if (e.getSource() == settings_btn) {
            stage = (Stage) settings_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //logout
        if (e.getSource() == logout_btn) {
            postgresql.endConnection(Controller.con);

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

