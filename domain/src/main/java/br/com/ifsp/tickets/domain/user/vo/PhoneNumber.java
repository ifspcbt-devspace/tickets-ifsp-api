package br.com.ifsp.tickets.domain.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.Getter;

@Getter
public class PhoneNumber extends ValueObject {

    private final static PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidPhoneNumberException(value);
        }

        try {
            if (!PHONE_NUMBER_UTIL.isValidNumber(PHONE_NUMBER_UTIL.parse(value, "BR"))) {
                throw new InvalidPhoneNumberException(value);
            }
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException(value);
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
