package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class UserValidator extends Validator {

    private final User user;

    protected UserValidator(IValidationHandler aHandler, User user) {
        super(aHandler);
        this.user = user;
    }

    @Override
    public void validate() {
        this.validateEmail();
        this.validatePassword();
        this.validateName();
        this.validateBio();
        this.validateUsername();
        this.validatePhoneNumber();
        this.validateCPF();
        this.validateBirthDate();
        this.validateRole();
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
        if (user.getName() != null && user.getName().length() < 3)
            error("Name must have at least 3 characters");
        if (user.getName() != null && user.getName().length() > 100)
            error("Name must have at most 100 characters");
    }

    private void validateUsername() {
        if (user.getUsername() == null || user.getUsername().isBlank())
            error("Username is required");
        if (user.getUsername() != null && user.getUsername().length() < 3)
            error("Username must have at least 3 characters");
        if (user.getUsername() != null && user.getUsername().length() > 16)
            error("Username must have at most 16 characters");
    }

    private void validateBio() {
        if (user.getBio() != null && user.getBio().length() > 255)
            error("Bio must have at most 255 characters");
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

    private void validateRole() {
        if (user.getRole() == null)
            error("Role are required");
    }


}
