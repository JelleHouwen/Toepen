package Components;

import Interfaces.ILobby;
import Interfaces.ILobbyServer;
import Toepen.Lobby;
import fontyspublisher.RemotePublisher;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static java.lang.System.out;

/**
 * Created by Jelle on 12-6-2017.
 */public class LobbyServer extends UnicastRemoteObject implements ILobbyServer {
    private static final int REGISTRY_PORT = 1099;
    private RemotePublisher publisher;
    private Registry registry = null;
    private GameServer gameServer = null;

    private ILobby propertyLobby;

    public LobbyServer()
            throws RemoteException, UnknownHostException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException
    {
        super();

        InetAddress ex = InetAddress.getLocalHost();
        out.print("IP Address: "+ ex.getHostAddress() + " - Port: " + REGISTRY_PORT + "\n");
        //Create instance of lobby and make empty the empty lists
        propertyLobby = new Lobby(this);

        //Create instance of remote Publisher
        publisher = new RemotePublisher();

        //Create registry
        registry = LocateRegistry.createRegistry(REGISTRY_PORT);
        registry.rebind("publisher", publisher);

        publisher.registerProperty("propertyLobby");
        registry.rebind("registerLobby", propertyLobby);

        out.print("Lobby Server Running... \n");
    }

    public synchronized void update(ILobby lobby) throws RemoteException {
        System.out.println("updated lobby1");
        publisher.inform("propertyLobby", propertyLobby, lobby);
        System.out.println("updated lobby2");
    }

    @Override
    public ILobbyServer getLobbyServer() {
        return this;
    }

    public static void main(String[] arg)
            throws RemoteException, UnknownHostException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        new LobbyServer();
    }

    public void startGameServer() throws RemoteException, InvalidKeySpecException, UnknownHostException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {

        System.out.println("starting game...");
        if(gameServer==null) {
            gameServer = new GameServer(this);
            System.out.println("game started!");
        }
    }
    public ILobby getLobby() {
        return propertyLobby;
    }

    @Override
    public String toString()
    {
        String s = null;
        try {
            s="Spelers: "+propertyLobby.getSpelers().size()+"/4";
            return s;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return s;
    }

}