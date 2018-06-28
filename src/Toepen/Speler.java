package Toepen;

import Components.LobbyServer;
import Interfaces.ILobbyServer;
import Interfaces.ISpel;
import Interfaces.ISpeler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelle on 6-6-2017.
 */
public class Speler implements ISpeler {
    private int id;
    private String gebruikersnaam;
    private String wachtwoord;
    private int gamesWon;
    private int gamesLost;
    private boolean isAanDeBeurt;
    private int aantalKeerGeklopt;
    private int strafPunten;
    private ArrayList<Kaart> hand;
    private ISpel spel;
    private boolean pass;
    private Kaart laatsteKaart=null;
    private boolean kaartGespeeld;
    private boolean ready;
    private ILobbyServer lobbyServer;
//    private GameServer gameServer;

    public Speler(int id, String gebruikersnaam, String wachtwoord, int gameswon, int gamesLost) {
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.gamesLost = gamesLost;
        this.gamesWon = gameswon;
        this.hand = new ArrayList<>();
    }

    public Speler(int id, String gebruikersnaam, String wachtwoord) {
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.hand = new ArrayList<>();
    }
    public Speler() {
        this.hand = new ArrayList<>();
    }

    public ArrayList<Kaart> getHand() {
        return new ArrayList<>(hand);
    }

    public void setHand(ArrayList<Kaart> hand) {
        this.hand = hand;
    }

    public ISpel getSpel() {
        return spel;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setSpel(ISpel spel) {
        this.spel = spel;
    }

    public boolean isAanDeBeurt() {
        return isAanDeBeurt;
    }

    public void setAanDeBeurt(boolean aanDeBeurt) {
        isAanDeBeurt = aanDeBeurt;
        if (aanDeBeurt) {
            System.out.println(this.gebruikersnaam + " is aan de beurt");
        }
    }

    public int getAantalKeerGeklopt() {
        return aantalKeerGeklopt;
    }

    public void setAantalKeerGeklopt(int aantalKeerGeklopt) {
        this.aantalKeerGeklopt = aantalKeerGeklopt;
    }

    public int getStrafPunten() {
        return strafPunten;
    }

    public void setStrafPunten(int strafPunten) {
        this.strafPunten = strafPunten;
    }

    public void addKaart(Kaart k)throws RemoteException{
        this.hand.add(k);
    }

    public void removeKaart(Kaart k){
        this.hand.remove(k);
    }

    public boolean isKaartGespeeld() {
        return kaartGespeeld;
    }

    public void setKaartGespeeld(boolean kaartGespeeld) {
        this.kaartGespeeld = kaartGespeeld;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public boolean kloppen()
    {
        return true;
    }

    public void passen()
    {
        this.setPass(true);
    }


    public ILobbyServer getLobbyServer() {
        return this.lobbyServer;
    }


    public void setLobbyServer(ILobbyServer server) {

            this.lobbyServer = server;

    }


    public Kaart getLaatsteKaart() {
        return laatsteKaart;
    }

    public void setLaatsteKaart(Kaart laatsteKaart) {
        this.laatsteKaart = laatsteKaart;
    }

    public Kaart getSelectedKaart(){
        Kaart selectedKaart = null;
        for(Kaart k :this.hand)
        {
            if(k.getSelectedKaart())
            {
                selectedKaart = k;
            }
        }
        return selectedKaart;
    }

    public Kaart kaartSpelen(Kaart k)
    {
        Kaart returnKaart=null;
        List<Kaart> toRemove = new ArrayList<>();
        for(Kaart kaart : this.hand)
        {
            if(k.getSoort().equals(kaart.getSoort())&&k.getWaarde()==kaart.getWaarde())
            {
                setLaatsteKaart(kaart);
                toRemove.add(kaart);
                this.setKaartGespeeld(true);
                returnKaart=kaart;
        //        getSelectedKaart().setVisible(true);

            }
        }
        this.hand.removeAll(toRemove);
        return returnKaart;

    }

    public int calculateAantalStrafPunten(ISpeler winnaar) throws RemoteException {
        if(winnaar.getGebruikersnaam().equals(this.gebruikersnaam))
        {
           this.strafPunten +=0;
        }
        else this.strafPunten+= (aantalKeerGeklopt+1);
        return getStrafPunten();
    }

    public void selecteerKaart(Kaart k)
    {
        for(Kaart kaart : this.hand)
        {
            if(kaart.getWaarde()==k.getWaarde()&&k.getSoort()==kaart.getSoort())
            {
                kaart.setSelectedKaart(true);
            }

        }
    }

    @Override
    public String toString() {

        String ready= null;
        if(isReady())
        {
            ready = "ready!";
        }
        else ready ="not ready";
        return this.gebruikersnaam + "  "+ready;

    }
}
