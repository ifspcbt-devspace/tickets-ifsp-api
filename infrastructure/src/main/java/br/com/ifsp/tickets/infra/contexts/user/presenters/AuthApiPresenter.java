package br.com.ifsp.tickets.infra.contexts.user.presenters;

import br.com.ifsp.tickets.app.auth.get.UserOutput;
import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.infra.contexts.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.contexts.user.models.user.UserResponse;

import java.time.format.DateTimeFormatter;

public interface AuthApiPresenter {

    static LoginResponse present(SignInOutput data) {
        return new LoginResponse(data.token(), presentUser(data.user()));
    }

    static UserResponse present(UserOutput output) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new UserResponse(
                output.id(),
                output.name(),
                output.email(),
                output.username(),
                presentRole(output.role()),
                output.birthDate() == null ? null : formatter.format(output.birthDate()),
                output.cpfInitials(),
                output.phoneNumberInitials(),
                output.companyID()
        );
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

    static UserResponse.RoleResponse presentRole(UserOutput.RoleOutputData data) {
        return new UserResponse.RoleResponse(
                data.code(),
                data.description()
        );
    }

    static LoginResponse.RoleResponse presentRole(SignInOutput.RoleOutputData data) {
        return new LoginResponse.RoleResponse(
                data.code(),
                data.description()
        );
    }

}
