package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class home_controller implements Initializable {
    @FXML


    @Override
    public void initialize (URL url, ResourceBundle rb) {

    }

    public void setUsername(String username) {
        System.out.println("Display Username in Home: " + username);

    }

    public void setRole (String role){
        System.out.println("Display Role in Home: " + role);
    }
}
