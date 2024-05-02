package br.com.ifsp.tickets.infra.user.presenters;

import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutput;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterResponse;

import java.time.format.DateTimeFormatter;

public interface AuthApiPresenter {

    static LoginResponse present(SignInOutput data) {
        return new LoginResponse(data.token(), presentUser(data.user()));
    }

    static LoginResponse.UserResponse presentUser(SignInOutput.UserOutputData data) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new LoginResponse.UserResponse(
                data.id(),
                data.name(),
                data.email(),
                data.username(),
                presentRole(data.role()),
                formatter.format(data.birthDate()),
                data.cpfInitials(),
                data.phoneNumberInitials(),
                data.companyID()
        );
    }

    static LoginResponse.RoleResponse presentRole(SignInOutput.RoleOutputData data) {
        return new LoginResponse.RoleResponse(
                data.code(),
                data.description()
        );
    }

    static RegisterResponse present(SignUpOutput data) {
        return new RegisterResponse(data.token(), presentUser(data.user()));
    }

    static RegisterResponse.UserResponse presentUser(SignUpOutput.UserOutputData data) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new RegisterResponse.UserResponse(
                data.id(),
                data.name(),
                data.email(),
                data.username(),
                presentRole(data.role()),
                formatter.format(data.birthDate()),
                data.cpfInitials(),
                data.phoneNumberInitials(),
                data.companyID()
        );
    }

    static RegisterResponse.RoleResponse presentRole(SignUpOutput.RoleOutputData data) {
        return new RegisterResponse.RoleResponse(
                data.code(),
                data.description()
        );
    }

}
