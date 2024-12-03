package br.com.ifsp.tickets.domain.financial.order;

import lombok.Getter;

@Getter
public enum OrderStatus {

    RESERVED(10, "Reservado"),
    PENDING(11, "Pendente"),
    APPROVED(20, "Aprovado"),
    FAILED(21, "Recusado"),
    CANCELED(22, "Cancelado"),
    FORCED_CANCELED(23, "Cancelado Forçadamente");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
