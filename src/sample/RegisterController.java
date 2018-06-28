package sample;


import Database.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label lbAlleVelden;
    @FXML
    private Label lbAndereWachtwoorden;

    @FXML
        private void exitToLogin(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(home_page_scene);
        main_stage.show();
    }
    @FXML
        private boolean checkFields()
        {
           if(username.getText().equals("")||password.getText().equals("")||confirmPassword.getText().equals(""))
           {
               System.out.println("velden");
               lbAlleVelden.setVisible(true);
               return false;
           }
           else if(!password.getText().equals(confirmPassword.getText()))
           {
               System.out.println("wachtwoord");
               lbAndereWachtwoorden.setVisible(true);
               return false;

           }
           else {

               lbAndereWachtwoorden.setVisible(false);
               lbAlleVelden.setVisible(false);
               return true;
           }



        }
@FXML
    private void validate(ActionEvent event) {
        try
        {

            if (checkFields()){
                boolean register=Repository.getRepAccount().register(this.username.getText(),this.password.getText());
                if(register)
                {
                        exitToLogin(event);
                }
                else System.out.println("er ging iets fout met inloggen");
                }
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
}


}
