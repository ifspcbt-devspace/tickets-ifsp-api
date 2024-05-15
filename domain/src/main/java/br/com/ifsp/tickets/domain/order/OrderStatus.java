package br.com.ifsp.tickets.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING(1, "Pendente"),
    CONFIRMED(20, "Confirmado"),
    REFUSED(21, "Recusado"),
    CANCELED(22, "Cancelado"),
    REFUNDED(30, "Reembolsado"),
    ;

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
