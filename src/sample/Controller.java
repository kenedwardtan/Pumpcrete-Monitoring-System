package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Postgresql;

import javax.swing.*;
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
	private JComboBox role_dropdown;
    @FXML
    private TextField create_fn_tf;
    @FXML
    private TextField create_ln_tf;
    @FXML
    private TextField create_email_tf;
    @FXML
    private TextField create_user_tf;
    @FXML
    private Button create_submit_btn;
    @FXML
    private Button create_cancel_btn;
    @FXML
    private Button settings_save_btn;
    @FXML
    private Button settings_cancel_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private TextField login_user_tf;
    @FXML
    private TextField login_pass_tf;
    @FXML
    private TextField settings_oPass_tf;
    @FXML
    private TextField settings_nPass_tf;
	@FXML
	private JOptionPane optionPane;

    @FXML
    private void handleAction(ActionEvent e) throws IOException {
        String[] roles = {"Role", "Staff", "Supervisor", "Admin"};

        if (e.getSource() == login_btn) { // if (buttonpressedis == login button)
            String username = login_user_tf.getText();
            String password = login_pass_tf.getText();
            String role = postgresql.loginUser(username,password);

            switch(role) {
                case "Staff":
                case "staff":
                    stage = (Stage) create_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homeStaff.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case "Admin":
                case "admin":
                    stage = (Stage) create_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    break;
				case "Supervisor":
				case "supervisor":
                    stage = (Stage) create_btn.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("homeSuperv.fxml"));
                    root = loader.load();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
				    break;
                case "Password did not match.":
                default:
                    try {
                            stage = (Stage) login_btn.getScene().getWindow(); // get Scene (screen) associated with the login button
                            loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
                            root = loader.load();
                            scene = new Scene(root); // set new Scene (screen) as the indicated javafx file (homeAdmin.fxml)
                            stage.setScene(scene); // place Scene (screen) which is homeAdmin.fxml as the Scene of the Stage (the empty 1280x988 window)
                            stage.show(); // then show the Scene (screen) which is homeAdmin.fxml
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null, ex);
                    }
                    break;
            }
        }

        if (e.getSource() == create_btn) {
            stage = (Stage) create_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            role_dropdown = new JComboBox(roles);
        }

        if (e.getSource() == create_submit_btn) {
            stage = (Stage) create_submit_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // retrieve inputs
			String fname = create_fn_tf.getText();
            String lname = create_ln_tf.getText();
            String email = create_email_tf.getText();
            String uname = create_user_tf.getText();
            String role = roles[role_dropdown.getSelectedIndex()];

            // check if the data are empty
            if (verifyFields())
            {
            // check if the username already exists
                if (!postgresql.checkUsername(uname)) {
                    postgresql.createUser(fname,lname,email,uname,role);
                }
				else
					optionPane.showMessageDialog(null, "This Username is Already Taken, Choose Another One", "Username Failed", 2);
            }
			
            System.out.print(create_fn_tf.getText()); // once submit button is pressed, getText from create_fn_tf (first name textfield) -> print
        }

        if (e.getSource() == create_cancel_btn) {
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeAdmin.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if (e.getSource() == settings_btn) {
            stage = (Stage) create_cancel_btn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

/*      //Profile button - idea how to display?
        if (e.getSource() == profile_btn) { // if (buttonpressedis == login button)
            String username = profile_username_label.gettext();
            User user = postgresql.getUser(username);

           }



        //How to display arraylist of Staff
        ArrayList<User> users = postgres.getAllStaff()



 */
    }
	
	// create a function to verify the empty fields
    public boolean verifyFields()
    {
        String fname = create_fn_tf.getText();
        String lname = create_ln_tf.getText();
        String email = create_email_tf.getText();
        String uname = create_user_tf.getText();

        // check empty fields
        if(fname.trim().equals("") || lname.trim().equals("") || uname.trim().equals("")
                || email.trim().equals("") || role_dropdown.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty","Empty Fields",2);
            return false;
        }

        // if everything is ok
        else{
            return true;
        }
    }

	
}
