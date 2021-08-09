package sample.controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import sample.model.Postgresql;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;
    public Postgresql postgresql;
    @FXML
    private Button login_btn;
    @FXML
    private Button create_btn;
    @FXML
    private MenuButton create_role_btn;
    @FXML
    private MenuItem role_admin_btn;
    @FXML
    private MenuItem role_staff_btn;
    @FXML
    private TextField create_fn_tf;
    @FXML
    private TextField create_ln_tf;
    @FXML
    private TextField create_email_tf;
    @FXML
    private TextField create_user_tf;
    @FXML
    private TextField create_pass_tf;
    @FXML
    private Button create_submit_btn;
    @FXML
    private Button create_cancel_btn;
    @FXML
    private TextField login_user_tf;
    @FXML
    private TextField login_pass_tf;

    @FXML
    private void handleAction(ActionEvent e) throws IOException {

        if (e.getSource() == login_btn) { // if (buttonpressedis == login button)
            String username = login_user_tf.getText();
            String password = login_pass_tf.getText();
            String role = postgresql.loginUser(username,password);

            switch(role){
                case "Staff": //is there a separate homepage for staff and admin?
                case "staff":
                case "Admin":
                case "admin":
                default:
                try {
                        home_controller controller = new home_controller();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                        loader.setController(controller);
                        home_controller display =  loader.getController();
                        display.setRole(role);
                        display.setUsername(username);

                        root = loader.load();
                        stage = (Stage) login_btn.getScene().getWindow(); // get Scene (screen) associated with the login button
                        scene = new Scene(root); // set new Scene (screen) as the indicated javafx file (home.fxml)
                        stage.setScene(scene); // place Scene (screen) which is home.fxml as the Scene of the Stage (the empty 1280x988 window)
                        stage.show(); // then show the Scene (screen) which is home.fxml

                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null, ex);
                    }
            }



        }

        if (e.getSource() == create_btn) { // same ^
            stage = (Stage) create_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == create_submit_btn) { // same ^
            stage = (Stage) create_submit_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // retrieve inputs
            System.out.print(create_fn_tf.getText()); // once submit button is pressed, getText from create_fn_tf (first name textfield) -> print
        }

        if (e.getSource() == create_cancel_btn) { // same ^
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
