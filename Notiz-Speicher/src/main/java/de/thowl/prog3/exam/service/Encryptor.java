package de.thowl.prog3.exam.service;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Encryptor {

    private static final int SALT_LENGTH = 16; // Länge des Salts in Bytes
    private static final int HASH_LENGTH = 256; // Länge des Hashes in Bits
    private static final int ITERATIONS = 600000; // Anzahl der Iterationen

    /**
     * Erzeugt einen kombinierten String aus Salt und Hash.
     * @param password Das zu hashende Passwort.
     * @return Ein String im Format "salt:hash", wobei beide Base64-kodiert sind.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        byte[] hash = pbkdf2(password, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Überprüft, ob das eingegebene Passwort zum gespeicherten (kombinierten) Wert passt.
     * @param password Das eingegebene Passwort.
     * @param storedPassword Der gespeicherte String im Format "salt:hash".
     * @return true, wenn das Passwort übereinstimmt, sonst false.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean verifyPassword(String password, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Das gespeicherte Passwort hat nicht das erwartete Format.");
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);
        byte[] computedHash = pbkdf2(password, salt);
        return Arrays.equals(storedHash, computedHash);
    }

    // Erzeugt ein zufälliges Salt
    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Wendet PBKDF2 mit HMAC-SHA256 an
    private static byte[] pbkdf2(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }
}
