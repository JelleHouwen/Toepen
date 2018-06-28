package Database;

import Repository.RepAccount;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Created by Jelle on 6-6-2017.
 */
public  class Repository {
    public static RepAccount getRepAccount() throws ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        return new RepAccount(new Database());
    }
}