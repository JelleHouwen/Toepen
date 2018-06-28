package sample;

import Database.Database;
import Toepen.Session;
import Toepen.Speler;
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
import Database.*;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );;
    @FXML
    private PasswordField wachtwoord;
    @FXML
    private TextField gebruikersnaam;
    @FXML
    private Label errorLogin;
    @FXML
    private Label alleVelden;

    private Speler speler;
    private Session createSession;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.setScene(home_page_scene);
        register_stage.show();
    }

    @FXML
    private void toLobby(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("lobby.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.setScene(home_page_scene);
        register_stage.show();
    }

    @FXML
    private boolean validate(ActionEvent event) throws ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        try {
            if (noEmpty()) {
                Speler s = new Speler(this.gebruikersnaam.getText(),this.wachtwoord.getText());
                //Speler s = Repository.getRepAccount().login(this.gebruikersnaam.getText(),this.wachtwoord.getText());
                if (s != null) {
                    speler = s;
                    Session.setSpeler(s);
                    connectToLobby();
                    toLobby(event);
                    return true;
                } else {
                    this.errorLogin.setVisible(true);
                    return false;

                }
            }
        } catch (SQLException | ClassNotFoundException | InvalidKeySpecException | NoSuchAlgorithmException e) {
             e.getMessage();
        }
        return false;
    }
    private boolean noEmpty()
    {
        if(this.gebruikersnaam.getText().equals("")||this.wachtwoord.getText().equals(""))
        {
            alleVelden.setVisible(true);
            return false;
        }
        else alleVelden.setVisible(false); return true;
    }

    private boolean connectToLobby()  {
        try
        {
            Session.setClient();
            return true;
        } catch (RemoteException |NotBoundException |InvalidKeySpecException |NoSuchAlgorithmException |SQLException |ClassNotFoundException e)
        {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return false;
        }
    }

}
