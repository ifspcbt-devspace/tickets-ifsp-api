package br.com.ifsp.tickets.domain.user.vo.role;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import lombok.Getter;

@Getter
public class Role extends ValueObject {

    public static final Role ADMIN = new Role(RoleType.ADMIN);
    public static final Role COMPANY_MANAGER = new Role(RoleType.COMPANY_MANAGER);
    public static final Role COMPANY_EMPLOYEE = new Role(RoleType.COMPANY_EMPLOYEE);
    public static final Role CUSTOMER = new Role(RoleType.CONSUMER);

    private final RoleType roleType;

    public Role(RoleType roleType) {
        if (roleType == null)
            throw new IllegalArgumentException("RoleType cannot be null");
        this.roleType = roleType;
    }

    public static Role fromCode(Integer code) {
        if (code == null) {
            return null;
        }

        return switch (code) {
            case 1 -> ADMIN;
            case 2 -> COMPANY_MANAGER;
            case 3 -> COMPANY_EMPLOYEE;
            case 4 -> CUSTOMER;
            default -> null;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return roleType == role.roleType;
    }

    @Override
    public int hashCode() {
        return roleType.hashCode();
    }
}
