package sample;

import Components.GameServer;
import Interfaces.ILobby;
import Interfaces.ISpeler;
import Toepen.Session;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jelle on 12-6-2017.
 */
public class RoomController implements Initializable {
    @FXML
    private ListView lvSpelers;
    private ILobby lobby;
    private boolean gameStarted;
    @FXML
    private AnchorPane anchor;


    public void update() throws IOException {
        if(!gameStarted) {
            lobby = Session.getClient().getLobby();
            ObservableList<ISpeler> items = FXCollections.observableArrayList(
            lobby.getSpelers());
            lvSpelers.setItems(items);
        }

    }

    public void Ready(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, NotBoundException {
       for(ISpeler s : lobby.getSpelers())
       {
           if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam())){
              s.setLobbyServer(Session.getSpeler().getLobbyServer());
             lobby.setReady(s);

           }
           System.out.println(s.isReady());
       }
        if(lobby.getReady())
        {
            lobby.startGame();
            Session.getClient().connectToGameServer();
            toGame(event);
        }

    }

    @FXML
    private void leaveRoom(ActionEvent event) throws IOException {
        for(ISpeler s : this.lobby.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                this.lobby.removePlayer(s);
                toLobby(event);
            }
        }

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
    private void toGame(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("play.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);

        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(home_page_scene);
        main_stage.show();
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
