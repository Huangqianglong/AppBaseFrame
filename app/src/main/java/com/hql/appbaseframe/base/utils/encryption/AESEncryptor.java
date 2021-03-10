/*
 * 文件名：AESUtils.java
 * 版权：Copyright 2000-2100 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描述：订单可视
 * 修改人：  zWX317775
 * 修改时间：2016年5月18日
 * 修改内容：新增
 */
package com.hql.appbaseframe.base.utils.encryption;

import android.util.Log;

import com.hql.appbaseframe.base.utils.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lizihan
 * @version V1.0
 * @description AES双向加密管控
 */
public class AESEncryptor {
    private static final String ENCODING = "UTF-8";

    private static AESEncryptor aesEncryptor;

    public static AESEncryptor getInstance() {
        if (aesEncryptor == null) {
            aesEncryptor = new AESEncryptor();
        }
        return aesEncryptor;
    }

    private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    private static final int HASH_ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private char[] humanPassphrase = {'P', 'e', 'r', ' ', 'v', 'a', 'l', 'l',
            'u', 'm', ' ', 'd', 'u', 'c', 'e', 's', ' ', 'L', 'a', 'b', 'a',
            'n', 't'};

    // char[] humanPassphrase = { 'v', 't', 'i', 'o', 'n','s','f','o','t', '.',
    // 'c', 'o', 'm',
    // 'p'};
    private byte[] salt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD,
            0xE, 0xF}; // must save this for next time we want the key

    private PBEKeySpec myKeyspec = new PBEKeySpec(humanPassphrase, salt,
            HASH_ITERATIONS, KEY_LENGTH);
    private static final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";

    private SecretKeyFactory keyfactory = null;
    private SecretKey sk = null;
    private SecretKeySpec skforAES = null;
    private byte[] iv = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,
            0xD, 91};

    private IvParameterSpec IV;

    public AESEncryptor() {

        try {
            keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
            sk = keyfactory.generateSecret(myKeyspec);

        } catch (NoSuchAlgorithmException nsae) {
            Log.e("AESEncryptor",
                    "no key factory support for PBEWITHSHAANDTWOFISH-CBC");
        } catch (InvalidKeySpecException ikse) {
            Log.e("AESEncryptor", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
        }

        // This is our secret key. We could just save this to a file instead of
        // regenerating it
        // each time it is needed. But that file cannot be on the device (too
        // insecure). It could
        // be secure if we kept it on a server accessible through https.
        byte[] skAsByteArray = sk.getEncoded();
        // LogUtil.d("",
        // "skAsByteArray=" + skAsByteArray.length + ","
        // + Base64Encoder.encode(skAsByteArray));
        skforAES = new SecretKeySpec(skAsByteArray, "AES");
        IV = new IvParameterSpec(iv);
    }

    public String encrypt(byte[] plaintext) {

        byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
        String base64_ciphertext = Base64Encoder.encode(ciphertext);
        return base64_ciphertext;
    }

    public String decrypt(String ciphertext_base64) {
        if (TextUtils.isEmpty(ciphertext_base64)) {
            return ciphertext_base64;
        }
        byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
        String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV,s));
        return decrypted;
    }

    // Use this method if you want to add the padding manually
    // AES deals with messages in blocks of 16 bytes.
    // This method looks at the length of the message, and adds bytes at the navi_end
    // so that the entire message is a multiple of 16 bytes.
    // the padding is a series of bytes, each set to the total bytes added (a
    // number in range 1..16).
    @SuppressWarnings("unused")
    private byte[] addPadding(byte[] plain) {
        byte plainpad[] = null;
        int shortage = 16 - (plain.length % 16);
        // if already an exact multiple of 16, need to add another block of 16
        // bytes
        if (shortage == 0)
            shortage = 16;
        // reallocate array bigger to be exact multiple, adding shortage bits.
        plainpad = new byte[plain.length + shortage];
        for (int i = 0; i < plain.length; i++) {
            plainpad[i] = plain[i];
        }
        for (int i = plain.length; i < plain.length + shortage; i++) {
            plainpad[i] = (byte) shortage;
        }
        return plainpad;
    }

    // Use this method if you want to remove the padding manually
    // This method removes the padding bytes
    @SuppressWarnings("unused")
    private byte[] dropPadding(byte[] plainpad) {
        byte plain[] = null;
        int drop = plainpad[plainpad.length - 1]; // last byte gives number of
        // bytes to drop

        // reallocate array smaller, dropping the pad bytes.
        plain = new byte[plainpad.length - drop];
        for (int i = 0; i < plain.length; i++) {
            plain[i] = plainpad[i];
            plainpad[i] = 0; // don't keep a copy of the decrypt
        }
        return plain;
    }

    public String encrypt(String content) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        try {
            return encrypt(content.getBytes(ENCODING));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                           byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE, sk, IV);
            return c.doFinal(msg);
        } catch (NoSuchAlgorithmException nsae) {
            Log.e("AESEncryptor", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException nspe) {
            Log.e("AESEncryptor", "no cipher getinstance support for padding " + cmp);
        } catch (InvalidKeyException e) {
            Log.e("AESEncryptor", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            Log.e("AESEncryptor", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            Log.e("AESEncryptor", "illegal block size exception");
        } catch (BadPaddingException e) {
            Log.e("AESEncryptor", "bad padding exception");
        }
        return null;
    }

    private byte[] decrypt(String cmpstr, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
        try {
            Cipher c = Cipher.getInstance(cmpstr);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException nsae) {
            Log.e("AESEncryptor", "no cipher getinstance support for " + cmpstr);
        } catch (NoSuchPaddingException nspe) {
            Log.e("AESEncryptor", "no cipher getinstance support for padding " + cmpstr);
        } catch (InvalidKeyException e) {
            Log.e("AESEncryptor", "invalid key exception");
        } catch (InvalidAlgorithmParameterException ee) {
            Log.e("AESEncryptor", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            Log.e("AESEncryptor", "illegal block size exception");
        } catch (BadPaddingException e) {
            Log.e("AESEncryptor", "bad padding exception");
        }
        return null;
    }
}
