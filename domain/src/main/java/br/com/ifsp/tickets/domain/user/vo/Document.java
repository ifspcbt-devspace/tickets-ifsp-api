package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalDocumentException;
import lombok.Getter;

@Getter
public class Document extends ValueObject {

    private final String value;

    public Document(String value) {
        if (value == null || value.isBlank())
            throw new IllegalDocumentException(value);

        this.value = value;
    }

    public String getInitials() {
        return this.value.substring(0, this.value.length() - 5) + "*****";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Document doc = (Document) o;

        return this.value.equals(doc.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
