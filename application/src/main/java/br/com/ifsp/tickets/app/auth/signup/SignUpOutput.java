package br.com.ifsp.tickets.app.auth.signup;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import java.time.LocalDate;

public record SignUpOutput(
        String token,
        UserOutputData user
) {

    public static SignUpOutput from(String token, User user) {
        return new SignUpOutput(token, UserOutputData.from(user));
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
