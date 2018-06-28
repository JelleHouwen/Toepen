package Interfaces;

import Toepen.Speler;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Created by Jelle on 6-6-2017.
 */
public interface IDBAccount {
    Speler login(String username, String password) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException;
    boolean register(String username, String password) throws SQLException;
}
