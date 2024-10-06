package br.com.ifsp.tickets.app.auth.signup;

import java.time.LocalDate;

public record SignUpInput(
        String name,
        String email,
        String username,
        String password,
        LocalDate birthDate,
        String document,
        String phoneNumber
) {

    public SignUpInput {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("field 'name' cannot be null or empty");

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("field 'email' cannot be null or empty");

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("field 'username' cannot be null or empty");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("field 'password' cannot be null or empty");

        if (birthDate == null)
            throw new IllegalArgumentException("field 'birth_date' cannot be null");

        if (document == null || document.isBlank())
            throw new IllegalArgumentException("field 'document' cannot be null or empty");

        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("field 'phone_number' cannot be null or empty");
    }

    public static SignUpInput of(String name, String email, String username, String password, LocalDate birthDate, String document, String phoneNumber) {
        return new SignUpInput(name, email, username, password, birthDate, document, phoneNumber);
    }
}
