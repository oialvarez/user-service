package org.alviel.user.adapter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomCipher implements org.alviel.user.adapter.Cipher {

    public static final String ALGORITHM = "DES";
    private Cipher cipher;

    public CustomCipher(@Value("${secret.key:'abcde67890'}") String key) {
        try {
            initCipher(key);
        } catch (Exception e) {
            throw new RuntimeException("Problem found initializing cipher", e);
        }
    }

    private void initCipher(String key) throws Exception {
        if (cipher == null) {
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            cipher = Cipher.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
    }

    public String encrypt(String input) {
        try {
            byte[] cleartext = input.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt data with " + ALGORITHM + " algorithm");
        }
    }
}
