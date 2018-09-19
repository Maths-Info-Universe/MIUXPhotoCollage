/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import miu.xphotocollage.util.exception.MIUCryptoException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUEncryptor {
     /**
     * Forme courte de l'algorithme de cryptage par défaut
     */
    public static final String DEFAULT_ENCRYPTION_ALGO_SHORT = "AES";

    /**
     * Forme longue de l'algorithme de cryptage par défaut
     */
    public static final String DEFAULT_ENCRYPTION_ALGO_LONG = "AES/CBC/PKCS5Padding";

    /**
     * Clé privée de cryptage (<i>ne utiliser qu'en cas de besoin réel</i>)
     */
    public static final String DEFAULT_ENCRYPTION_SECRET_KEY = "#1_%$M-(>=*/@]{+";

    /**
     * Vecteur d'initialisation de cryptage (<i>ne utiliser qu'en cas de besoin réel</i>) 
     */
    public static final String DEFAULT_ENCRYPTION_INIT_VECTOR = "<~r2&-I|^.q[}9s:";
    
    
    /**
     * Cette méthode permet de crypter des messages suivant un algorithme donnée. Les messages cryptés
     * peuvent par la suite être décryptés. Il est alors important de savoir que la clé et le vecteur
     * d'initialisation (chaines de taille 16) doivent être identiques lors du cryptage et du décryptage.
     * L'algorithme utilisé est fourni en argument à cette méthode. Nous attendons une courte et une longue
     * forme de l'algorithme.
     * 
     * @param message
     * Le message à crypter
     * @param secretKey
     * La clé privée de cryptage (16 caractères)
     * @param initVector
     * Le vecteur d'initialisation de cryptage (16 caractères)
     * @param encryptionAlgoShort
     * La forme courte de l'algorithme de cryptage (ex: "AES")
     * @param encryptionAlgoLong
     * La forme longue de l'algorithme de cryptage (ex: "AES/CBC/PKCS5Padding")
     * @return
     * Le message crypté.
     * @throws MIUCryptoException
     */
    public static String encryptMessage(String message, String secretKey, String initVector, 
                String encryptionAlgoShort, String encryptionAlgoLong) throws MIUCryptoException{
        try {
            // Premier cryptage avec les paramètres passés en argument.
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), encryptionAlgoShort);
            IvParameterSpec ivParamSpec = new IvParameterSpec(initVector.getBytes());

            Cipher cipher = Cipher.getInstance(encryptionAlgoLong);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParamSpec);

            byte[] messageBytes = message.getBytes("UTF8");
            byte[] encryptedMessageBytes;

            encryptedMessageBytes = cipher.doFinal(messageBytes);
            BASE64Encoder base64Encoder = new BASE64Encoder();

            String firstResult = base64Encoder.encode(encryptedMessageBytes);

            // Second cryptage avec les paramètres de cryptage par défaut.
            secretKeySpec = new SecretKeySpec(DEFAULT_ENCRYPTION_SECRET_KEY.getBytes(), DEFAULT_ENCRYPTION_ALGO_SHORT);
            ivParamSpec = new IvParameterSpec(DEFAULT_ENCRYPTION_INIT_VECTOR.getBytes());

            cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGO_LONG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParamSpec);

            messageBytes = firstResult.getBytes("UTF8");

            encryptedMessageBytes = cipher.doFinal(messageBytes);

            return base64Encoder.encode(encryptedMessageBytes);

        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            throw new MIUCryptoException(e);
        }
    }
    
    public static String encryptMessage(String message) throws MIUCryptoException{
        return encryptMessage(message, DEFAULT_ENCRYPTION_SECRET_KEY, DEFAULT_ENCRYPTION_INIT_VECTOR, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
    
    public static String encryptMessage(String message, String secretKey, String initVector) 
            throws MIUCryptoException{
        return encryptMessage(message, secretKey, initVector, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }

    /**
     * Cette méthode permet de décrypter des messages suivant un algorithme donnée.
     * 
     * @param encryptedMessage
     * Le message crypté à décrypter
     * @param secretKey
     * La clé privée utilisée lors du cryptage (16 caractères)
     * @param initVector
     * Le vecteur d'initialisation utilisé lors du cryptage (16 caractères)
     * @param encryptionAlgoShort
     * La forme courte de l'algorithme de cryptage utilisé (ex: "AES")
     * @param encryptionAlgoLong
     * La forme longue de l'algorithme de cryptage utilisé (ex: "AES/CBC/PKCS5Padding")
     * @return
     * Le message décrypté.
     * @throws MIUCryptoException
     */
    public static String decryptMessage(String encryptedMessage, String secretKey, String initVector, 
                String encryptionAlgoShort, String encryptionAlgoLong) throws MIUCryptoException{
        try {
            // Premier décryptage avec les paramètres de cryptage par défaut.
            SecretKeySpec secretKeySpec = new SecretKeySpec(DEFAULT_ENCRYPTION_SECRET_KEY.getBytes(), DEFAULT_ENCRYPTION_ALGO_SHORT);
            IvParameterSpec ivParamSpec = new IvParameterSpec(DEFAULT_ENCRYPTION_INIT_VECTOR.getBytes());

            Cipher cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGO_LONG);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParamSpec);

            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] encryptedMessageBytes = base64Decoder.decodeBuffer(encryptedMessage);
            byte[] messageBytes = cipher.doFinal(encryptedMessageBytes);

            String firstResult = new String(messageBytes, "UTF8");

            // Second décryptage avec les paramètres passés en argument.
            secretKeySpec = new SecretKeySpec(secretKey.getBytes(), encryptionAlgoShort);
            ivParamSpec = new IvParameterSpec(initVector.getBytes());

            cipher = Cipher.getInstance(encryptionAlgoLong);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParamSpec);

            base64Decoder = new BASE64Decoder();
            encryptedMessageBytes = base64Decoder.decodeBuffer(firstResult);
            messageBytes = cipher.doFinal(encryptedMessageBytes);

            return new String(messageBytes, "UTF8");
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new MIUCryptoException(e);
        } catch (UnsupportedEncodingException e) {
            throw new MIUCryptoException(e);
        } catch (IOException e) {
            throw new MIUCryptoException(e);
        }
    }
    
    public static String decryptMessage(String encryptedMessage) throws MIUCryptoException{
        return decryptMessage(encryptedMessage, DEFAULT_ENCRYPTION_SECRET_KEY, DEFAULT_ENCRYPTION_INIT_VECTOR, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
    
    public static String decryptMessage(String encryptedMessage, String secretKey, String initVector) 
            throws MIUCryptoException{
        return decryptMessage(encryptedMessage, secretKey, initVector, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
}
