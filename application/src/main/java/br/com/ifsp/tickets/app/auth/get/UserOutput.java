package br.com.ifsp.tickets.app.auth.get;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.PermissionType;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import java.time.LocalDate;
import java.util.List;

public record UserOutput(
        String id,
        String name,
        String bio,
        String email,
        String username,
        RoleOutputData role,
        LocalDate birthDate,
        String cpfInitials,
        String phoneNumberInitials,
        String companyID
) {
    public static UserOutput from(User user, List<PermissionType> authorities) {
        if (authorities.contains(PermissionType.MANAGE_USERS) || authorities.contains(PermissionType.MANAGE_COMPANY_ENROLLMENTS))
            return new UserOutput(
                    user.getId().getValue().toString(),
                    user.getName(),
                    user.getBio(),
                    user.getEmail().getValue(),
                    user.getUsername(),
                    RoleOutputData.from(user.getRole()),
                    user.getBirthDate(),
                    user.getCpf().getInitials(),
                    user.getPhoneNumber().getInitials(),
                    user.getCompanyID().getValue() == null ? null : user.getCompanyID().getValue().toString()
            );
        else return new UserOutput(
                user.getId().getValue().toString(),
                user.getName(),
                user.getBio(),
                null,
                user.getUsername(),
                RoleOutputData.from(user.getRole()),
                null,
                null,
                null,
                user.getCompanyID().getValue() == null ? null : user.getCompanyID().getValue().toString()
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



