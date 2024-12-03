package br.com.ifsp.tickets.app.administrative.auth.signin;

import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.role.Role;

import java.time.LocalDate;

public record SignInOutput(
        String token,
        UserOutputData user
) {

    public static SignInOutput from(User user, String token) {
        return new SignInOutput(
                token,
                UserOutputData.from(user)
        );
    }

    public record UserOutputData(
            String id,
            String name,
            String bio,
            String email,
            String username,
            RoleOutputData role,
            LocalDate birthDate,
            String documentInitials,
            String phoneNumberInitials,
            String companyID
    ) {
        public static UserOutputData from(User user) {
            return new UserOutputData(
                    user.getId().getValue().toString(),
                    user.getName(),
                    user.getBio(),
                    user.getEmail().getValue(),
                    user.getUsername(),
                    RoleOutputData.from(user.getRole()),
                    user.getBirthDate(),
                    user.getDocument() == null ? null : user.getDocument().getInitials(),
                    user.getPhoneNumber().getInitials(),
                    user.getCompanyID().getValue() == null ? null : user.getCompanyID().getValue().toString()
            );
        }
    }


    public record RoleOutputData(
            Integer code,
            String description
    ) {

        public static RoleOutputData from(Role role) {
            return new RoleOutputData(role.getRoleType().getCode(), role.getRoleType().getDescription());
        }

    }

}
