package br.com.ifsp.tickets.infra.user.presenters;

import br.com.ifsp.tickets.app.auth.signin.SignInOutputData;
import br.com.ifsp.tickets.infra.user.models.LoginResponse;

public interface AuthApiPresenter {

    static LoginResponse present(SignInOutputData data) {
        return new LoginResponse(data.token(), presentUser(data.user()));
    }

    static LoginResponse.UserResponse presentUser(SignInOutputData.UserOutputData data) {
        return new LoginResponse.UserResponse(
                data.id(),
                data.name(),
                data.email(),
                data.username(),
                presentRole(data.role()),
                data.birthDate(),
                data.cpfInitials(),
                data.phoneNumberInitials(),
                data.companyID()
        );
    }

    static LoginResponse.RoleResponse presentRole(SignInOutputData.RoleOutputData data) {
        return new LoginResponse.RoleResponse(
                data.code(),
                data.description()
        );
    }

}
