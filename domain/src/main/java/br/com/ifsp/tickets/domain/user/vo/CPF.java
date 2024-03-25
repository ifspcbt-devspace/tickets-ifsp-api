package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalCPFException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class CPF extends ValueObject {

    private final String value;

    public CPF(String value) {
        if (value == null || value.isBlank())
            throw new IllegalCPFException(value);

        if (!ValidationUtils.isCPF(value))
            throw new IllegalCPFException(value);

        this.value = value;
    }

    public String getInitials() {
        return value.substring(0, this.value.length() - 5) + "*****";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CPF cpf = (CPF) o;

        return value.equals(cpf.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
