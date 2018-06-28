package sample;

import Components.GameServer;
import Interfaces.ILobby;
import Interfaces.ISpel;
import Interfaces.ISpeler;
import Toepen.Kaart;
import Toepen.Session;
import Toepen.Spel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jelle on 7-6-2017.
 */
public class PlayController implements Initializable {


    private ISpel spel;


@FXML
private Button btnSpeelKaart;
    @FXML
    private Label lbRonde;
    @FXML
    private Label lbAanZet;
    @FXML
    private Label lbCard1;
    @FXML
    private Label lbCard2;
    @FXML
    private Label lbCard3;
    @FXML
    private Label lbCard4;
    @FXML
    private Label lbCard5;
    @FXML
    private Label lbCard6;
    @FXML
    private Label lbCard7;
    @FXML
    private Label lbCard8;
    @FXML
    private Label lbCard9;
    @FXML
    private Label lbCard10;
    @FXML
    private Label lbCard11;
    @FXML
    private Label lbCard12;
    @FXML
    private Label lbCard13;
    @FXML
    private Label lbCard14;
    @FXML
    private Label lbCard15;
    @FXML
    private Label lbCard16;
    @FXML
    private Label lbLaatsteKaart1;
    @FXML
    private Label lbLaatsteKaart2;
    @FXML
    private Rectangle card1;
    @FXML
    private Rectangle card2;
    @FXML
    private Rectangle card3;
    @FXML
    private Rectangle card4;
    @FXML
    private Rectangle card5;
    @FXML
    private Rectangle card6;
    @FXML
    private Rectangle card7;
    @FXML
    private Rectangle card8;
    @FXML
    private Rectangle card9;
    @FXML
    private Rectangle card10;
    @FXML
    private Rectangle card11;
    @FXML
    private Rectangle card12;
    @FXML
    private Rectangle card13;
    @FXML
    private Rectangle card14;
    @FXML
    private Rectangle card15;
    @FXML
    private Rectangle card16;
    @FXML
    private Rectangle laasteKaart1;
    @FXML
    private Rectangle laatsteKaart2;
    @FXML
    private Label lbScore1;
    @FXML
    private Label lbScore2;
    @FXML
    private Label lbNaamSpeler1;
    @FXML
    private Label lbNaamSpeler2;;
    @FXML
    private Label lbSelecteerEenKaart;
    @FXML
    private Button btnTerugNaarLobby;
    @FXML
    private Label lbVoorbij;
    @FXML
    private Label lbSelectedCard;
    @FXML
    private Label lbBekenSoort;

@FXML
private Rectangle selectedCard;
    private Kaart selectedKaart=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateTimer();
    }

    public void update() throws RemoteException {
        spel = Session.getClient().getGame();
        if(!spel.checkEnd()) {
            spel.nieuweRonde();
            updateButton();
            updateKaarten();
            updateKaartenTegenstander();
            spel.checkAanZet(spel.getSpelers());
            boundRectanglesToCards();
            lbAanZet.setText("Aan zet: " + spel.checkAanZet(spel.getSpelers()).getGebruikersnaam());
            lbRonde.setText("Ronde: " + Integer.toString(spel.getRonde()));
            for (ISpeler s : spel.getSpelers()) {
                if (s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam())) {
                    lbNaamSpeler1.setText(s.getGebruikersnaam());
                    lbScore1.setText("strafpunten " + s.getGebruikersnaam() + ": " + Integer.toString(s.getStrafPunten())+" /"+ Spel.maxStrafpunten);
                } else {
                    lbNaamSpeler2.setText(s.getGebruikersnaam());
                    lbScore2.setText("strafpunten: " + s.getGebruikersnaam() + ": " + Integer.toString(s.getStrafPunten())+" /"+Spel.maxStrafpunten);
                }

            }
        }
        else {
            btnTerugNaarLobby.setVisible(true);
            lbVoorbij.setVisible(true);

        }
        if(selectedKaart!= null)
        {
            lbSelectedCard.setVisible(true);
            lbSelectedCard.setText(selectedKaart.toString());
            selectedCard.setVisible(true);
        }
        else{
            selectedCard.setVisible(false);
            lbSelectedCard.setVisible(false);
            lbSelectedCard.setText("");
        }

    }

@FXML
    public void speelKaart() throws RemoteException {
        if(spel.checkAanZet(spel.getSpelers()).getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
        {
            for(ISpeler s : spel.getSpelers())
            {
                if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
                {

                   if(selectedKaart!=null) {
                       if(!spel.checkKaartBekennen(s,selectedKaart))
                       {
                            lbBekenSoort.setVisible(true);
                       }
                       else{
                           lbBekenSoort.setVisible(false);
                       }
                       spel.speelKaart(s, selectedKaart);
                       selectedKaart= null;
                   }
                    else lbSelecteerEenKaart.setVisible(true);

                }
            }

        }
    }
    private void boundRectanglesToCards() throws RemoteException {
        for(ISpeler s : spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
               for(int i=0;i<s.getHand().size();i++)
               {
                   if(i==0) {
                       s.getHand().get(i).setRectangleKaart(card1);
                   }
                   if(i==1) {
                       s.getHand().get(i).setRectangleKaart(card2);
                   }
                   if(i==2) {
                       s.getHand().get(i).setRectangleKaart(card3);
                   }
                   if(i==3) {

                       s.getHand().get(i).setRectangleKaart(card4);
                   }
               }
            }
        }
    }
    @FXML
    public Kaart selecteerKaart1() throws RemoteException {
        Kaart k = null;
        for(ISpeler s :spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                for(int i =0;i<s.getHand().size();i++){
                    if(i==0) {
                        s.selecteerKaart(s.getHand().get(i));
                    }
                }
                k= s.getSelectedKaart();
                lbSelecteerEenKaart.setVisible(false);
            }
        }
        System.out.println(k);
        selectedKaart =k;
        return k;
    }

    @FXML
    public Kaart selecteerKaart2() throws RemoteException {
        Kaart k = null;
        for(ISpeler s :spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                for(int i =0;i<s.getHand().size();i++){
                    if(i==1) {
                        s.selecteerKaart(s.getHand().get(i));
                    }
                }
                k= s.getSelectedKaart();
                lbSelecteerEenKaart.setVisible(false);
            }
        }
        System.out.println(k);
        selectedKaart =k;
        return k;
    }

    @FXML
    public Kaart selecteerKaart3() throws RemoteException {
        Kaart k = null;
        for(ISpeler s :spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                for(int i =0;i<s.getHand().size();i++){
                    if(i==2) {
                        s.selecteerKaart(s.getHand().get(i));
                    }
                }
                k= s.getSelectedKaart();
                lbSelecteerEenKaart.setVisible(false);
            }
        }
        System.out.println(k);
        selectedKaart =k;
        return k;
    }

    @FXML
    public Kaart selecteerKaart4() throws RemoteException {
        Kaart k = null;
        for(ISpeler s :spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                for(int i =0;i<s.getHand().size();i++){
                    if(i==3) {
                        s.selecteerKaart(s.getHand().get(i));
                    }
                }
                k= s.getSelectedKaart();
                lbSelecteerEenKaart.setVisible(false);
            }

        }
        selectedKaart =k;
        return k;

    }

    private void updateButton() throws RemoteException {
        for(ISpeler s : this.spel.getSpelers())
        {
            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                if(s.isAanDeBeurt())
                {
                    btnSpeelKaart.setDisable(false);
                }
                else btnSpeelKaart.setDisable(true);
            }
        }
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

    public void updateKaarten() throws RemoteException {
        int sizeHand=0;
        for(ISpeler s : spel.getSpelers())
        {

            if(s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                if(s.getLaatsteKaart()!=null)
            {
                laasteKaart1.setVisible(true);
                lbLaatsteKaart1.setVisible(true);
                lbLaatsteKaart1.setText(s.getLaatsteKaart().toString());
            }
                if(s.getLaatsteKaart()==null)
                {
                    laasteKaart1.setVisible(false);
                    lbLaatsteKaart1.setVisible(false);
                }
                if(s.getHand().size()==3)
                {
                    lbCard4.setText("");
                    card4.setVisible(false);
                    card1.setVisible(true);
                    card2.setVisible(true);
                    card3.setVisible(true);

                }
                if(s.getHand().size()==2)
                {
                    lbCard4.setText("");
                    lbCard3.setText("");
                    card4.setVisible(false);
                    card3.setVisible(false);
                    card1.setVisible(true);
                    card2.setVisible(true);
                }
                if(s.getHand().size()==1)
                {
                    lbCard4.setText("");
                    lbCard3.setText("");
                    lbCard2.setText("");
                    card4.setVisible(false);
                    card3.setVisible(false);
                    card2.setVisible(false);
                    card1.setVisible(true);
                }
                if(s.getHand().size()==0)
                {
                    lbCard4.setText("");
                    lbCard3.setText("");
                    lbCard2.setText("");
                    lbCard1.setText("");
                    card4.setVisible(false);
                    card3.setVisible(false);
                    card2.setVisible(false);
                    card1.setVisible(false);
                }
                for(int i=0;i<s.getHand().size();i++)
                {
                    if(i==0) {
                        lbCard1.setText(s.getHand().get(i).toString());
                        card1.setVisible(true);
                    }
                    if(i==1) {
                        lbCard2.setText(s.getHand().get(i).toString());
                        card2.setVisible(true);
                    }
                    if(i==2) {
                        lbCard3.setText(s.getHand().get(i).toString());
                        card3.setVisible(true);
                    }
                    if(i==3) {
                        lbCard4.setText(s.getHand().get(i).toString());
                        card4.setVisible(true);
                    }

                }
            }
        }
    }
    public void updateKaartenTegenstander() throws RemoteException {
        int sizeHand=0;
        for(ISpeler s : spel.getSpelers())
        {

            if(!s.getGebruikersnaam().equals(Session.getSpeler().getGebruikersnaam()))
            {
                if(s.getLaatsteKaart()!=null)
                {
                    laatsteKaart2.setVisible(true);
                    lbLaatsteKaart2.setVisible(true);
                    lbLaatsteKaart2.setText(s.getLaatsteKaart().toString());
                }
                if(s.getLaatsteKaart()==null)
                {
                    laatsteKaart2.setVisible(false);
                    lbLaatsteKaart2.setVisible(false);
                }
                if(s.getHand().size()==3)
                {
                    lbCard8.setText("");
                    card8.setVisible(false);
                    card7.setVisible(true);
                    card6.setVisible(true);
                    card5.setVisible(true);
                }
                if(s.getHand().size()==2)
                {
                    lbCard8.setText("");
                    lbCard7.setText("");
                    card8.setVisible(false);
                    card7.setVisible(false);
                    card6.setVisible(true);
                    card5.setVisible(true);
                }
                if(s.getHand().size()==1)
                {
                    lbCard8.setText("");
                    lbCard7.setText("");
                    lbCard6.setText("");
                    card8.setVisible(false);
                    card7.setVisible(false);
                    card6.setVisible(false);
                    card5.setVisible(true);
                }
                if(s.getHand().size()==0)
                {
                    lbCard8.setText("");
                    lbCard7.setText("");
                    lbCard6.setText("");
                    lbCard5.setText("");
                    card8.setVisible(false);
                    card7.setVisible(false);
                    card5.setVisible(false);
                    card4.setVisible(false);
                }
                for(int i=0;i<s.getHand().size();i++)
                {
                    if(i==0) {

                        card5.setVisible(true);
                    }
                    if(i==1) {

                        card6.setVisible(true);
                    }
                    if(i==2) {

                        card7.setVisible(true);
                    }
                    if(i==3) {

                        card8.setVisible(true);
                    }

                }
            }
        }
    }
    @FXML
    private void toLobby(ActionEvent event) throws IOException {
        Parent home_page_Parent = FXMLLoader.load(getClass().getResource("room.fxml"));
        Scene home_page_scene = new Scene(home_page_Parent);
        Stage register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        register_stage.setScene(home_page_scene);
        register_stage.show();
    }
}
