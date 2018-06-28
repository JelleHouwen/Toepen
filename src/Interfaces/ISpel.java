package Interfaces;

import Enums.Soort;
import Toepen.Kaart;
import Toepen.Speler;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jelle on 12-6-2017.
 */
public interface ISpel extends Remote, Serializable {
     ArrayList<Kaart> getAlleKaarten() throws RemoteException;

    void setAlleKaarten(ArrayList<Kaart> alleKaarten) throws RemoteException;

    ArrayList<ISpeler> getSpelers() throws RemoteException;

    boolean allCardsOnTable() throws RemoteException;
    void setSpelers(ArrayList<ISpeler> spelers) throws RemoteException;

    int getRonde() throws RemoteException;
    boolean checkKaartBekennen(ISpeler s,Kaart k) throws RemoteException;

    void setRonde(int ronde) throws RemoteException;

    int getId() throws RemoteException;

    void setId(int id) throws RemoteException;

    Kaart getEertseKaart() throws RemoteException;

    void setEertseKaart(Kaart eertseKaart) throws RemoteException;

    void removeKaart(Kaart k) throws RemoteException;


    int getAantalKloppen() throws RemoteException;

    void setAantalKloppen(int aantalKloppen) throws RemoteException;

    ISpeler checkAanZet(ArrayList<ISpeler> spelers) throws RemoteException;

    void deelKaarten(ArrayList<ISpeler> spelers) throws RemoteException;

    void speelKaart(ISpeler s,Kaart k) throws RemoteException;

    void checkWinnaarRonde() throws RemoteException;

    Kaart calculateHighestCard(ArrayList<Kaart> kaarten) throws RemoteException;



     void addKlop() throws RemoteException;

     ArrayList<Kaart> maxKaarten() throws RemoteException;

    boolean nextRound() throws RemoteException;

    void resetRound() throws RemoteException;

    void nieuweRonde()throws RemoteException;
    boolean isGameStarted()throws RemoteException;
    boolean checkEnd()throws RemoteException;
    void setGameStarted(boolean gameStarted)throws RemoteException;
}
