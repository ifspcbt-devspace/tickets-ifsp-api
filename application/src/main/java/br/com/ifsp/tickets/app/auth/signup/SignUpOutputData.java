package br.com.ifsp.tickets.app.auth.signup;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import java.util.Date;

public record SignUpOutputData(
        String token,
        UserOutputData user
) {

    public static SignUpOutputData from(String token, User user) {
        return new SignUpOutputData(token, UserOutputData.from(user));
    }

    public record UserOutputData(
            String id,
            String name,
            String email,
            String username,
            RoleOutputData role,
            Date birthDate,
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
                    user.getCompanyID().getValue().toString()
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
