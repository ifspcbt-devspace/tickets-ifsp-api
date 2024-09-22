package br.com.ifsp.tickets.infra.shared.encryption;

import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptionService {

    private static final StrongTextEncryptor encryptor;

    static {
        final String encryptorValue = System.getenv("ENCRYPTOR_VALUE");
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(encryptorValue);
    }

    public static String encrypt(String rawText) {
        return encryptor.encrypt(rawText);
    }

    public static String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
