package Interfaces;

import Components.LobbyServer;
import Toepen.Spel;
import Toepen.Speler;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jelle on 12-6-2017.
 */
public interface ILobby extends Remote, Serializable  {
     boolean addPlayer(ISpeler s) throws RemoteException;

    void removePlayer(ISpeler s)throws RemoteException;

    ArrayList<ISpeler> getSpelers()throws RemoteException;
     void setReady(ISpeler speler)throws RemoteException;
    boolean getReady() throws RemoteException;
    ILobbyServer getLobbyServer()throws RemoteException;

    void startGame() throws RemoteException, NoSuchAlgorithmException, SQLException, ClassNotFoundException, InvalidKeySpecException, UnknownHostException;



}
