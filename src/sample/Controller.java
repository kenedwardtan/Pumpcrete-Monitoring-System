package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;

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
    private void handleAction(ActionEvent e) throws IOException {
        if (e.getSource() == login_btn) { // if (buttonpressedis == login button)
            stage = (Stage) login_btn.getScene().getWindow(); // get Scene (screen) associated with the login button
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml")); // load javafx file (home.fxml)
            root = loader.load();
            scene = new Scene(root); // set new Scene (screen) as the indicated javafx file (home.fxml)
            stage.setScene(scene); // place Scene (screen) which is home.fxml as the Scene of the Stage (the empty 1280x988 window)
            stage.show(); // then show the Scene (screen) which is home.fxml
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
