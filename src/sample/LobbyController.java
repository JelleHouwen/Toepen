package sample;

import Components.LobbyServer;
import Interfaces.ILobby;
import Toepen.Lobby;
import Toepen.Session;
import Toepen.Speler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Database.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jelle on 7-6-2017.
 */
public class LobbyController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private ListView cbLobbies;

    private ILobby lobby;



    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.setScene(home_page_scene);
        register_stage.show();
        Session.reset();
    }

    public void update() throws IOException {
        if(Session.getSpeler()==null)
        {
            Session.reset();
            System.exit(1);
        }
        lobby =Session.getClient().getLobby();

        this.name.setText(Session.getSpeler().getGebruikersnaam());
        ObservableList<String> items = FXCollections.observableArrayList (
                "aantal spelers: "+lobby.getSpelers().size() +"/"+Lobby.maxPlayers );
        cbLobbies.setItems(items);
    }

    public void joinLobby(ActionEvent event)throws IOException
    {
      if(lobby.addPlayer(Session.getSpeler())) {
          Session.getSpeler().setLobbyServer(lobby.getLobbyServer());
          System.out.println(Session.getSpeler().getLobbyServer());
          toRoom(event);
      }
    }


    @FXML
    private void toRoom(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("room.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.setScene(home_page_scene);
        register_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      updateTimer();
    }
    private void updateTimer()
    {
        Timer timer = new Timer();

        TimerTask ttCooldown = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        update();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        timer.schedule(ttCooldown,  0,100);
    }
}
