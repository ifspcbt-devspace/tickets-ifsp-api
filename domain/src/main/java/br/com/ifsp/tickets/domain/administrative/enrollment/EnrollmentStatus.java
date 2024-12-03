package br.com.ifsp.tickets.domain.administrative.enrollment;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEnumException;
import lombok.Getter;

@Getter
public enum EnrollmentStatus {

    WAITING_CONFIRMATION(1, "Aguardando confirmação"),
    CONFIRMED(2, "Confirmado"),
    DENIED(3, "Negado");

    private final int code;
    private final String description;

    EnrollmentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EnrollmentStatus fromCode(int code) {
        for (EnrollmentStatus status : EnrollmentStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalEnumException(EnrollmentStatus.class, code);
    }

    public boolean isWaitingConfirmation() {
        return this == WAITING_CONFIRMATION;
    }

    public boolean isConfirmed() {
        return this == CONFIRMED;
    }

    public boolean isDenied() {
        return this == DENIED;
    }

}
