package com.caitba.caitbaqr;

import android.os.Build;

import androidx.annotation.RequiresApi;


import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;




import java.nio.ByteBuffer;
import java.security.SecureRandom;

import java.util.Arrays;






public class Utils {

    private static final String FACTORY_INSTANCE = "PBKDF2WithHmacSHA256";
    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String ENCRYPTION_TYPE = "AES";
    private static final int IV_LENGTH = 16;
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    private static byte[] getRandomIv() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String password, String salt, String message) throws Exception {
        byte[] iv = getRandomIv();

        SecretKey secret = getSecretKey(password, salt);
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, secret, iv);

        byte[] cipherText = cipher.doFinal(message.getBytes());
        byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();

        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String password, String salt, String encrypted) throws Exception {

        byte[] decoded = Base64.getDecoder().decode(encrypted);
        byte[] iv = Arrays.copyOfRange(decoded, 0, IV_LENGTH);
        byte[] cipherText = Arrays.copyOfRange(decoded, IV_LENGTH, decoded.length);

        SecretKey secret = getSecretKey(password, salt);
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, secret, iv);

        byte[] original = cipher.doFinal(cipherText);
        return new String(original);
    }

    private static SecretKey getSecretKey(String password, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(FACTORY_INSTANCE);

        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ENCRYPTION_TYPE);
    }

    private static Cipher initCipher(final int mode, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, secretKey, new IvParameterSpec(iv));
        return cipher;
    }



    }






