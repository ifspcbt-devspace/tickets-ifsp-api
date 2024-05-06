package br.com.ifsp.tickets.infra.contexts.user.presenters;

import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.infra.contexts.user.models.login.LoginResponse;

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

}
