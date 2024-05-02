package br.com.ifsp.tickets.app.auth.signup;

import java.time.LocalDate;

public record SignUpInput(
        String name,
        String email,
        String username,
        String password,
        LocalDate birthDate,
        String cpf,
        String phoneNumber
) {

    public static SignUpInput of(String name, String email, String username, String password, LocalDate birthDate, String cpf, String phoneNumber) {
        return new SignUpInput(name, email, username, password, birthDate, cpf, phoneNumber);
    }
}
