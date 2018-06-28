package Interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jelle on 13-6-2017.
 */
public interface ILobbyServer extends Remote, Serializable {
    void update(ILobby lobby) throws RemoteException;
    ILobbyServer getLobbyServer() throws RemoteException;
    ILobby getLobby() throws RemoteException;
}
