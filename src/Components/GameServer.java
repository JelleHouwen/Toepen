package Components;

import Interfaces.ILobby;
import Interfaces.ILobbyServer;
import Interfaces.ISpel;
import Toepen.Lobby;
import Toepen.Spel;
import fontyspublisher.RemotePublisher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static java.lang.System.out;

/**
 * Created by Jelle on 13-6-2017.
 */
public class GameServer extends UnicastRemoteObject {
    private static final int REGISTRY_PORT = 1099;
    private RemotePublisher publisher;
    private Registry registry = null;
    private ILobbyServer lobbyServer;
    private ISpel propertySpel;
    public GameServer(ILobbyServer lobbyServer)
            throws RemoteException, UnknownHostException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException{
        super();
        this.lobbyServer = lobbyServer;
       // InetAddress ex = InetAddress.getLocalHost();
        //out.print("IP Address: "+ ex.getHostAddress() + " - Port: " + REGISTRY_PORT + "\n");
        //Create instance of lobby and make empty the empty lists
        propertySpel = new Spel(this,lobbyServer.getLobby().getSpelers());
     //   System.out.println(propertySpel.getSpelers().size()+"<-- aantal spelers");

        //Create instance of remote Publisher
        publisher = new RemotePublisher();

        //Create registry

        registry = LocateRegistry.getRegistry("192.168.2.20",1099);
        registry.rebind("publisherGame", publisher);

        publisher.registerProperty("propertyGame");
        registry.rebind("registerGame", propertySpel);

        out.print("game Server Running... \n");
    }


    public synchronized void update(ILobby lobby) throws RemoteException {
        publisher.inform("propertyGame", propertySpel, lobby);
    }


    public static void main(String[] arg,LobbyServer server)
            throws RemoteException, UnknownHostException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        new GameServer(server);
    }

    public ISpel getLobby() {
        return propertySpel;
    }

}
