package az.zeynalovv.UberEats.util;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtil {

    public static PublicKey loadPublicKey(String key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Base64.Decoder decoder = Base64.getDecoder();
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decoder
                .decode(key));

        return keyFactory.generatePublic(publicKeySpec);
    }

    public static PrivateKey loadPrivateKey(String key) throws Exception {
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Base64.Decoder decoder = Base64.getDecoder();
        final PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoder
                .decode(key));

        return keyFactory.generatePrivate(privateKeySpec);
    }


    public static byte[] encryptText(PrivateKey privateKey, byte[] plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return encryptCipher.doFinal(plainText);
    }

    public static byte[] decryptText(PublicKey publicKey, byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, publicKey);
        return decryptCipher.doFinal(cipherText);
    }
}
