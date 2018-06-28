package Database;

import Interfaces.IDBAccount;
import Toepen.Speler;
import Database.Cryptography;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

/**
 * Created by Jelle on 6-6-2017.
 */
public class Database implements IDBAccount {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement preparedStatement;

    public Database() throws SQLException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        String dbName = "toepen";
        String ipAdressDatabase = "127.0.0.1:1433;";
        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=toepen;integratedSecurity=true";
        conn = DriverManager.getConnection(url);
    }
    private void close() throws SQLException
    {
        conn.close();
        if (rs != null)
        {
            rs.close();
        }
        preparedStatement.close();
    }


    public Speler login(String username, String password) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        preparedStatement = conn.prepareStatement("SELECT * FROM Speler WHERE gebruikersnaam = ?");

        preparedStatement.setString(1, username);

        rs = preparedStatement.executeQuery();

        Speler speler = null;
        while (rs.next())
        {
            int id = rs.getInt("ID");
            String gebruikersnaam = rs.getString("gebruikersnaam");
            String wachtwoord = rs.getString("wachtwoord");
            int gamesWon = rs.getInt("games_won");
            int gamesLost = rs.getInt("games_lost");

            if (Cryptography.validatePassword(password, wachtwoord))
            {
                speler = new Speler(id, gebruikersnaam, wachtwoord, gamesWon, gamesLost);
            }
        }
        return speler;
    }




    public boolean register(String username, String password) throws SQLException
    {
        preparedStatement = conn.prepareStatement("SELECT * FROM Speler WHERE gebruikersnaam = ?");
        preparedStatement.setString(1, username);
        rs = preparedStatement.executeQuery();
        boolean available = !rs.next();

        if (available){
            try {
                preparedStatement = conn.prepareStatement("INSERT INTO Speler(gebruikersnaam, wachtwoord, games_won, games_lost) VALUES(?, ?, 0, 0)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, Cryptography.generatePasswordHash(password));
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException|NoSuchAlgorithmException e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
