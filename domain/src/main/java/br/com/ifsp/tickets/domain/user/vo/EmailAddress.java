package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEmailException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class EmailAddress extends ValueObject {

    private final String value;

    public EmailAddress(String value) {
        if (value == null || value.isBlank()) {
            this.value = null;
            return;
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

        final EmailAddress email = (EmailAddress) o;

        return value.equals(email.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
