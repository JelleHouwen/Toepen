package Components;

import Interfaces.ILobby;
import Interfaces.ISpel;
import Interfaces.ISpeler;
import Toepen.Session;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import org.newdawn.slick.SlickException;

import java.beans.PropertyChangeEvent;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelle on 12-6-2017.
 */

public class Client extends UnicastRemoteObject implements IRemotePropertyListener
{
    private final static int REGISTRY_PORT_LOBBY = 1099;
    private final static int REGISTRY_PORT_GAME = 1100;
    private static final String PUBLISHER_BINDING_NAME_LOBBY = "publisher";
    private static final String PUBLISHER_BINDING_NAME_GAME = "publisherGame";

    private IRemotePublisherForListener publisher;
    private Registry registry = null;
    private boolean start = true;
    private ILobby lobby;
    private ISpel game;
    private ISpeler speler;
    private String propertyLobby;
    private String propertyGame;
    private static String IpAdress ="192.168.2.20";
    public Client() throws RemoteException, NotBoundException, ClassNotFoundException,
            SQLException, InvalidKeySpecException, NoSuchAlgorithmException
    {

        propertyGame = "propertyGame";
        propertyLobby = "propertyLobby";
        connectToLobbyServer();
        start = false;

    }
    //Getters
    public ILobby getLobby()
    {
        return lobby;
    }

    public ISpel getGame()
    {
        return game;
    }

    //Methods
    public void connectToLobbyServer() throws RemoteException, NotBoundException, ClassNotFoundException,
            SQLException, InvalidKeySpecException, NoSuchAlgorithmException
    {
//        if(!start)
//        {
//            publisher.unsubscribeRemoteListener(this, "game");
//            System.out.println("Client: Unsubscribed from game. \n");
//        }

        registry = LocateRegistry.getRegistry(IpAdress, REGISTRY_PORT_LOBBY);
        System.out.println("located registry");
        publisher = (IRemotePublisherForListener)registry.lookup(PUBLISHER_BINDING_NAME_LOBBY);
        System.out.println("Found publisher");

        publisher.subscribeRemoteListener(this, propertyLobby);
        System.out.println("subscribed");
        lobby = (ILobby)registry.lookup("registerLobby");
        System.out.println("Found lobby");

    }
    public void connectToGameServer() throws RemoteException, NotBoundException
    {
        registry = LocateRegistry.getRegistry(IpAdress, 1099);
        System.out.println("located registry");
        publisher = (IRemotePublisherForListener)registry.lookup(PUBLISHER_BINDING_NAME_GAME);
        System.out.println("Found publisher");

        publisher.subscribeRemoteListener(this, propertyGame);
        System.out.println("subscribed");
        game = (ISpel)registry.lookup("registerGame");
        System.out.println("Found game");
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException
    {
        if(propertyChangeEvent.getPropertyName().equals(propertyLobby))
        {
            System.out.println("propertyChanged");
            lobby = (ILobby)propertyChangeEvent.getNewValue();

        }
        else if(propertyChangeEvent.getPropertyName().equals(propertyGame))
        {
            game = (ISpel)propertyChangeEvent.getNewValue();
        }

    }
    public static void main(String[] arg)
            throws RemoteException, UnknownHostException, ClassNotFoundException, SQLException,
            InvalidKeySpecException, NoSuchAlgorithmException, NotBoundException, SlickException {
        new Client();
    }
}
