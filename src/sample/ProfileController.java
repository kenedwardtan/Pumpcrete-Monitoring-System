package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.model.Postgresql;
import sample.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController extends Controller implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private FXMLLoader loader;
    public Postgresql postgresql;

    @FXML
    private javafx.scene.control.Label big_name;
    //profile
    @FXML
    private javafx.scene.control.Label profile_name_lbl;
    @FXML
    private javafx.scene.control.Label profile_user_lbl;
    @FXML
    private javafx.scene.control.Label profile_email_lbl;
    @FXML
    private javafx.scene.control.Label profile_role_lbl;
    @FXML
    private javafx.scene.control.Label settings_oPass_lbl;
    @FXML
    private javafx.scene.control.Label settings_roPass_lbl;
    @FXML
    private javafx.scene.control.Label settings_nPass_lbl;
    @FXML
    private javafx.scene.control.Button profile_change_btn;
    @FXML
    private ImageView settings_change_btn1;
    @FXML
    private PasswordField settings_oPass_tf;
    @FXML
    private PasswordField settings_roPass_tf;
    @FXML
    private PasswordField settings_nPass_tf;
    @FXML
    private javafx.scene.control.Button settings_save_btn;
    @FXML
    private javafx.scene.control.Button profile_back_btn;
    @FXML
    private Button settings_cancel_btn;
    @FXML
    private ImageView settings_2btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        postgresql = new Postgresql();
        ObservableList<User> user = postgresql.getUser(Controller.con, postgresql.getCurrUser(Controller.con));
        big_name.setText(user.get(0).getUsername()+"'s Profile");
        profile_name_lbl.setText(user.get(0).getFirst_name() + " " +
                user.get(0).getMiddle_name() + " " + user.get(0).getLast_name());
        profile_email_lbl.setText(user.get(0).getEmail());
        profile_role_lbl.setText(user.get(0).getRole());
        profile_user_lbl.setText(user.get(0).getUsername());

    }

    @FXML
    private void handleAction(ActionEvent e) throws IOException, SQLException {
        postgresql = new Postgresql();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        if(e.getSource() == profile_back_btn){
            String role = postgresql.getRole(con);
            switch (role) {
                case "Staff":
                case "staff":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    break;
                case "Supervisor":
                case "supervisor":
                    stage = (Stage) settings_cancel_btn.getScene().getWindow();
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
        //profile
        if (e.getSource() == profile_change_btn) {
            settings_change_btn1.setVisible(false);

            settings_oPass_lbl.setVisible(true);
            settings_roPass_lbl.setVisible(true);
            settings_nPass_lbl.setVisible(true);
            settings_oPass_tf.setVisible(true);
            settings_roPass_tf.setVisible(true);
            settings_nPass_tf.setVisible(true);
            settings_save_btn.setVisible(true);
            settings_cancel_btn.setVisible(true);
            settings_2btn.setVisible(true);
        }

        if (e.getSource() == settings_cancel_btn) {
            settings_change_btn1.setVisible(true);

            settings_oPass_lbl.setVisible(false);
            settings_roPass_lbl.setVisible(false);
            settings_nPass_lbl.setVisible(false);
            settings_oPass_tf.setVisible(false);
            settings_roPass_tf.setVisible(false);
            settings_nPass_tf.setVisible(false);
            settings_save_btn.setVisible(false);
            settings_cancel_btn.setVisible(false);
            settings_2btn.setVisible(false);

        }

        if (e.getSource() == settings_save_btn) {
            settings_change_btn1.setVisible(true);

            settings_oPass_lbl.setVisible(false);
            settings_roPass_lbl.setVisible(false);
            settings_nPass_lbl.setVisible(false);
            settings_oPass_tf.setVisible(false);
            settings_roPass_tf.setVisible(false);
            settings_nPass_tf.setVisible(false);
            settings_save_btn.setVisible(false);
            settings_cancel_btn.setVisible(false);
            settings_2btn.setVisible(false);

            String oldpw = settings_oPass_tf.getText().trim();
            String newpw = settings_nPass_tf.getText().trim();
            String repeat = settings_roPass_tf.getText().trim();

            if (!newpw.equals("") && !oldpw.equals("") && !repeat.equals("")) {//(oldpw.equals(repeat))
                if (verifyNewPw(newpw, repeat)) {
                    boolean changed = postgresql.editPassword(con, oldpw, newpw);
                    if (changed){
                        JOptionPane.showMessageDialog(null, "Successfully changed password!\nNew password: " + newpw, "Password Changed!", 1);

                        String role = postgresql.getRole(con);
                        switch (role) {
                            case "Staff":
                            case "staff":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
                                loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                                root = loader.load();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                                break;
                            case "Admin":
                            case "admin":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                                root = loader.load();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                                break;
                            case "Supervisor":
                            case "supervisor":
                                stage = (Stage) settings_cancel_btn.getScene().getWindow();
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
                else
                    JOptionPane.showMessageDialog(null, "Passwords do not match, make sure you confirm your new passwords match!", "Password mismatch!", 2);
            }
            else
                JOptionPane.showMessageDialog(null, "Fill up new password, blank spaces are not allowed", "New Password Failed", 2);
        }
    }
}
