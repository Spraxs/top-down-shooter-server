package nl.jaimyputter.server.modules.network.packets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.logging.Logger;

/**
 * AES Rijndael encryption.
 * @author Pantelis Andrianakis
 */

public class Encryption {

    private static final Logger LOGGER = Logger.getLogger(Encryption.class.getName());

    // Secret keyword.
    private static final String PASSWORD = "SECRET_KEYWORD";
    // 16-byte private password.
    private static final String IV = "0123456789012345";
    // Transformation.
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static SecretKey _key;
    private static IvParameterSpec _ivParameterSpec;

    public static void init()
    {
        try
        {
            _key = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(PASSWORD.getBytes("UTF-8")), "AES");
            _ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        }
        catch (Exception e)
        {
        }

        LOGGER.info("Encryption: Initialized.");
    }

    public static byte[] encrypt(byte[] bytes)
    {
        try
        {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, _key, _ivParameterSpec);
            return cipher.doFinal(bytes);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static byte[] decrypt(byte[] bytes)
    {
        try
        {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, _key, _ivParameterSpec);
            return cipher.doFinal(bytes);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}