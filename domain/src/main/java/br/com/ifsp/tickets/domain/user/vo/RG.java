package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalDocumentException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class RG extends Document {


    public RG(String value) {
        super(value);

        if (!ValidationUtils.isRG(value))
            throw new IllegalDocumentException(value);
    }

    public String getInitials() {
        return this.getValue().substring(0, this.getValue().length() - 4) + "****";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RG rg = (RG) o;

        return this.getValue().equals(rg.getValue());
    }

}
