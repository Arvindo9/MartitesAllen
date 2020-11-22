package com.maritesallen.almanac2020.core.util;

import android.util.Base64;

import com.maritesallen.almanac2020.utils.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author : Arvindo Mondal
 * Created on 09-01-2020
 */
public class EncryptDrcypt {
    private static String cryptoPass = "supgh5656gh3rS3xy";
    private static final String TAG = EncryptDrcypt.class.getSimpleName();
//    private static EncryptDrcypt instance;

    private EncryptDrcypt(){

    }
/*

    public static EncryptDrcypt getInstance(){
        if(instance == null){
            instance = new EncryptDrcypt();
        }
        return instance;
    }
*/

    public static String encryptIt1(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes(StandardCharsets.UTF_8);
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
            Logger.e(TAG + " Encrypted: " + value + " -> " + encrypedValue);
            return encrypedValue;

        } catch (InvalidKeyException | InvalidKeySpecException |
                NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    };

    public static String decryptIt1(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            String decrypedValue = new String(decrypedValueBytes);
            Logger.e(TAG + " Decrypted: " + value + " -> " + decrypedValue);
            return decrypedValue;

        } catch (InvalidKeyException | InvalidKeySpecException |
                NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException |IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    }



    public static SecretKey generateKey() {
        SecretKeySpec secret;
        return secret = new SecretKeySpec(cryptoPass.getBytes(), "AES");
    }

    public static byte[] encryptIt(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptIt(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, UnsupportedEncodingException
    {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }
}
