package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

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
            String email,
            String username,
            RoleOutputData role,
            LocalDate birthDate,
            String cpfInitials,
            String phoneNumberInitials,
            String companyID
    ) {
        public static UserOutputData from(User user) {
            return new UserOutputData(
                    user.getId().getValue().toString(),
                    user.getName(),
                    user.getEmail().getValue(),
                    user.getUsername(),
                    RoleOutputData.from(user.getRole()),
                    user.getBirthDate(),
                    user.getCpf().getInitials(),
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
