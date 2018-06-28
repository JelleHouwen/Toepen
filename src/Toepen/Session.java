package Toepen;

import Components.Client;
import Interfaces.ISpeler;
import org.newdawn.slick.SlickException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Created by Jelle on 7-6-2017.
 */
public class Session {
    private static ISpeler speler;
    private static int id;
    private static Client client;


    public static void setSpeler(ISpeler s)
    {
        speler = s;
    }
    public static void setId(int sessionID)
    {
        id = sessionID;
    }
    public static ISpeler getSpeler()
    {
        return speler;
    }
    public static int getId()
    {
        return id;
    }
    public static void setClient() throws RemoteException, NotBoundException, ClassNotFoundException,
            SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        client = new Client();
    }

    //Getters
    public static Client getClient()
    {
        return client;
    }

    public static void reset()
    {
        speler = null;
        id = 0;
    }

}
