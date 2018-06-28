package Repository;

import Interfaces.IDBAccount;
import Toepen.Speler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Created by Jelle on 6-6-2017.
 */
public class RepAccount {
    IDBAccount server;
    public RepAccount(IDBAccount server)
    {
        this.server = server;
    }

    public Speler login(String username, String password) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        return server.login(username, password);
    }

    public boolean register(String username, String password) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        return server.register(username, password);
    }
}
