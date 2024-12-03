package br.com.ifsp.tickets.domain.administrative.user.vo;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalDocumentException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class CPF extends Document {

    public CPF(String value) {
        super(value);

        if (!ValidationUtils.isCPF(value))
            throw new IllegalDocumentException(value);
    }

    public String getInitials() {
        return this.getValue().substring(0, this.getValue().length() - 5) + "*****";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CPF cpf = (CPF) o;

        return this.getValue().equals(cpf.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
