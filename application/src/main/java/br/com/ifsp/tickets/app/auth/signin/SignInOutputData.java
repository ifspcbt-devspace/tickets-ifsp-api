package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import java.util.Date;
import java.util.List;

public record SignInOutputData(
        String token,
        String name,
        String email,
        String username,
        List<RoleOutputData> roles,
        Date birthDate,
        String cpfInitials,
        String phoneNumberInitials,
        String companyID

) {

    public static SignInOutputData from(User user, String token) {
        return new SignInOutputData(
                token,
                user.getName(),
                user.getEmail().getValue(),
                user.getUsername(),
                user.getRoles().stream().map(RoleOutputData::from).toList(),
                user.getBirthDate(),
                user.getCpf().getInitials(),
                user.getPhoneNumber().getInitials(),
                user.getCompanyID().getValue().toString()
        );
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
