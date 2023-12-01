package wtf.ewsc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String returnHashed(String initial) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(initial.getBytes());
        return new String(messageDigest.digest());
    }
}
