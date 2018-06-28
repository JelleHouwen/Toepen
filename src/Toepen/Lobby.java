package Toepen;

import Components.GameServer;
import Components.LobbyServer;
import Interfaces.ILobby;
import Interfaces.ILobbyServer;
import Interfaces.ISpeler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jelle on 7-6-2017.
 */
public class Lobby extends UnicastRemoteObject implements ILobby {
    public final static int maxPlayers = 2;
    ArrayList<ISpeler> spelers;
    private int id;
    private LobbyServer server;

    public Lobby(LobbyServer server) throws RemoteException {
        super();
        this.server = server;
        spelers = new ArrayList<>();
    }

    public boolean addPlayer(ISpeler s) throws RemoteException {
        boolean returnValue = false;
        if(spelers.size()<maxPlayers) {

            spelers.add(s);
            returnValue = true;
        }
        return returnValue;
    }

    public void setReady(ISpeler speler) throws RemoteException {
        for(ISpeler s : this.spelers)
        {
            if(s.getGebruikersnaam().equals(speler.getGebruikersnaam()))
            {
                s.setReady(true);
            }
        }

    }

    public void removePlayer(ISpeler s) throws RemoteException {

        ISpeler playerToRemove = null;
        for (ISpeler p : spelers) {
            if (s.getGebruikersnaam().equals(p.getGebruikersnaam()) ) {
                playerToRemove = p;
                break;
            }
        }
        spelers.remove(playerToRemove);

    }

    public ArrayList<ISpeler> getSpelers()
    {
        return new ArrayList<>(spelers);
    }

    public boolean getReady() throws RemoteException {
        boolean b = false;
        int i = 0;
        for(ISpeler s : this.spelers)
        {
            if(s.isReady())
            {
                i++;
            }
        }
        if(i ==2)
        {
            b = true;
        }
        return b;
    }


    public LobbyServer getLobbyServer() throws RemoteException {
        return server;
    }

    public void startGame() throws RemoteException, NoSuchAlgorithmException, SQLException, ClassNotFoundException, InvalidKeySpecException, UnknownHostException {
        getLobbyServer().startGameServer();
    }


    @Override
    public String toString() {
        return "aantal spelers: "+this.spelers.size() +" / "+maxPlayers;
    }
}
