package Database;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import static java.lang.String.format;

/**
 * Created by Mark on 6-5-2017.
 */
public class Cryptography
{
    // PBKDF2 (Password-Based Key Derivation Function 2) + Salt

    // Working example :
//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException
//    {
//        String  originalPassword = "password";
//        String generatedSecuredPasswordHash = generateStrongPasswordHash(originalPassword);
//        System.out.println(generatedSecuredPasswordHash);
//
//        boolean matched = validatePassword("password", generatedSecuredPasswordHash);
//        System.out.println(matched);
//
//        matched = validatePassword("password1", generatedSecuredPasswordHash);
//        System.out.println(matched);
//    }

    private Cryptography()
    {

    }
    public static String generatePasswordHash(String password) throws NoSuchAlgorithmException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = new byte[0];

        try
        {
            hash = skf.generateSecret(spec).getEncoded();
        }
        catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = new byte[0];

        try
        {
            testHash = skf.generateSecret(spec).getEncoded();
        }
        catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }

        return diff == 0;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();

        if(paddingLength > 0)
        {
            return format(String.format("%%0%dd", paddingLength), 0) + hex;
        }
        else
        {
            return hex;
        }
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
