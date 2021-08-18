package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class User {
       public SimpleStringProperty username;
       public SimpleStringProperty email;
       public SimpleStringProperty first_name;
       public SimpleStringProperty middle_name;
       public SimpleStringProperty last_name;
       public SimpleStringProperty password;
       public SimpleStringProperty role;

       public User(String username, String email, String first_name, String middle_name, String last_name, String password, String role){
              this.username = new SimpleStringProperty(username);
              this.email = new SimpleStringProperty(email);
              this.first_name = new SimpleStringProperty(first_name);
              this.middle_name = new SimpleStringProperty(middle_name);
              this.last_name = new SimpleStringProperty(last_name);
              this.password = new SimpleStringProperty(password);
              this.role = new SimpleStringProperty(role);
       }

       public String getUsername() {
              return username.get();
       }

       public String getEmail() {
              return email.get();
       }

       public String getFirst_name() {
              return first_name.get();
       }

       public String getMiddle_name() {
              return middle_name.get();
       }

       public String getLast_name() {
              return last_name.get();
       }

       public String getRole() {
              return role.get();
       }

}
