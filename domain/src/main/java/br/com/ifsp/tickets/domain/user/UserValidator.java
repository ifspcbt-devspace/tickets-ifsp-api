package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.shared.validation.ValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class UserValidator extends Validator {

    private final User user;

    protected UserValidator(ValidationHandler aHandler, User user) {
        super(aHandler);
        this.user = user;
    }

    @Override
    public void validate() {
        this.validateEmail();
        this.validatePassword();
        this.validateName();
        this.validateUsername();
        this.validatePhoneNumber();
        this.validateCPF();
        this.validateBirthDate();
        this.validateRoles();
    }

    private void validateEmail() {
        if (user.getEmail() == null)
            error("Email is required");
    }

    private void validatePassword() {
        if (user.getPassword() == null || user.getPassword().isBlank())
            error("Password is required");
    }

    private void validateName() {
        if (user.getName() == null || user.getName().isBlank())
            error("Name is required");
    }

    private void validateUsername() {
        if (user.getUsername() == null || user.getUsername().isBlank())
            error("Username is required");
    }

    private void validatePhoneNumber() {
        if (user.getPhoneNumber() == null)
            error("Phone number is required");
    }

    private void validateCPF() {
        if (user.getCpf() == null)
            error("CPF is required");
    }

    private void validateBirthDate() {
        if (user.getBirthDate() == null)
            error("Birth date is required");
    }

    private void validateRoles() {
        if (user.getRoles() == null || user.getRoles().isEmpty())
            error("Roles are required");
    }


}
