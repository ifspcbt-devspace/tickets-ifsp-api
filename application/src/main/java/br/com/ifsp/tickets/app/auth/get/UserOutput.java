package br.com.ifsp.tickets.app.auth.get;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
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
        String documentInitials,
        String phoneNumberInitials,
        String companyID
) {
    public static UserOutput from(User user, UserID applicant, List<PermissionType> authorities) {
        if (authorities.contains(PermissionType.MANAGE_ANY_USER) || authorities.contains(PermissionType.MANAGE_COMPANY_ENROLLMENTS) || applicant.equals(user.getId()))
            return new UserOutput(
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



