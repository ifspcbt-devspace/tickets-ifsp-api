package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEmailException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class Email extends ValueObject {

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalEmailException(value);
        }

        if (!ValidationUtils.isValidEmail(value)) {
            throw new IllegalEmailException(value);
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
