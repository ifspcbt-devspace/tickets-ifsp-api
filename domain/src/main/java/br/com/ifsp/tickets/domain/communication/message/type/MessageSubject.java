package br.com.ifsp.tickets.domain.communication.message.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageSubject {

    PASSWORD_RECOVERY(1, "Recuperação de Senha"),
    ACCOUNT_ACTIVATION(2, "Ativação de Conta"),
    EVENT_TICKET(3, "Ingresso de Evento"),
    PAYMENT_EMAIL(4, "Pagamento do ingresso");

    private final int key;
    private final String description;

    MessageSubject(Integer key, String description) {
        this.key = key;
        this.description = description;
    }

    public static MessageSubject from(int key) {
        return Arrays.stream(values()).filter(subject -> subject.key == key).findFirst().orElse(null);
    }

}
