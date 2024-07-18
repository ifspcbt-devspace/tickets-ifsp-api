package br.com.ifsp.tickets.infra.shared.encryption;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class EncryptionService {

    private static final StrongTextEncryptor encryptor;
    @Value("${encryptor-value}")
    private static String encryptorValue;

    static {
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
