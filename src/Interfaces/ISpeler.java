package Interfaces;

import Components.LobbyServer;
import Toepen.Kaart;
import Toepen.Lobby;
import Toepen.Spel;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Jelle on 12-6-2017.
 */
public interface ISpeler extends Remote, Serializable {
    ArrayList<Kaart> getHand() throws RemoteException;


    void setHand(ArrayList<Kaart> hand);

    ISpel getSpel();

    boolean isPass() throws RemoteException;

    void setPass(boolean pass) throws RemoteException;

    boolean isReady() throws RemoteException;

    void setReady(boolean ready) throws RemoteException;

    void setSpel(ISpel spel) throws RemoteException;

    boolean isAanDeBeurt() throws RemoteException;

    void setAanDeBeurt(boolean aanDeBeurt) throws RemoteException;

    int getAantalKeerGeklopt() throws RemoteException;

    void setAantalKeerGeklopt(int aantalKeerGeklopt) throws RemoteException;

    int getStrafPunten() throws RemoteException;

    void setStrafPunten(int strafPunten) throws RemoteException;

    void addKaart(Kaart k) throws RemoteException;

    void removeKaart(Kaart k) throws RemoteException;

    boolean isKaartGespeeld() throws RemoteException;

    void setKaartGespeeld(boolean kaartGespeeld) throws RemoteException;

    int getId() throws RemoteException;

    void setId(int id) throws RemoteException;

    String getGebruikersnaam() throws RemoteException;

    void setGebruikersnaam(String gebruikersnaam) throws RemoteException;

    String getWachtwoord() throws RemoteException;

    void setWachtwoord(String wachtwoord) throws RemoteException;

    int getGamesWon() throws RemoteException;

    void setGamesWon(int gamesWon) throws RemoteException;

    int getGamesLost() throws RemoteException;

    void setGamesLost(int gamesLost) throws RemoteException;

    boolean kloppen() throws RemoteException;

    void passen() throws RemoteException;

    ILobbyServer getLobbyServer() throws RemoteException;

    void setLobbyServer(ILobbyServer server) throws RemoteException;

    Kaart getLaatsteKaart() throws RemoteException;

    void setLaatsteKaart(Kaart laatsteKaart) throws RemoteException;

    Kaart kaartSpelen(Kaart k) throws RemoteException;
    void selecteerKaart(Kaart k)throws RemoteException;
    Kaart getSelectedKaart()throws RemoteException;

    int calculateAantalStrafPunten(ISpeler winnaar) throws RemoteException;
}
