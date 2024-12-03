package br.com.ifsp.tickets.domain.administrative.user.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.Getter;

@Getter
public class PhoneNumber extends ValueObject {

    private final static PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalPhoneNumberException(value);
        }
        Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = PHONE_NUMBER_UTIL.parse(value, "BR");
            if (!PHONE_NUMBER_UTIL.isValidNumber(phoneNumber)) {
                throw new IllegalPhoneNumberException(value);
            }
        } catch (NumberParseException e) {
            throw new IllegalPhoneNumberException(value);
        }

        this.value = "" + phoneNumber.getNationalNumber();
    }

    public String getInitials() {
        return value.substring(0, this.value.length() - 4) + "****";
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
