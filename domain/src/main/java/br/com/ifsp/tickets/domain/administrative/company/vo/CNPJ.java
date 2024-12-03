package br.com.ifsp.tickets.domain.administrative.company.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalCNPJException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class CNPJ extends ValueObject {

    private final String value;

    public CNPJ(String value) {
        if (value == null || value.isBlank())
            throw new IllegalCNPJException(value);

        if (!ValidationUtils.isCNPJ(value))
            throw new IllegalCNPJException(value);

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CNPJ cnpj = (CNPJ) o;

        return value.equals(cnpj.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
