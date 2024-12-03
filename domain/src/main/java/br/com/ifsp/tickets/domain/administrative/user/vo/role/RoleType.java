package br.com.ifsp.tickets.domain.administrative.user.vo.role;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN(1, "Administrador do sistema"),
    COMPANY_MANAGER(2, "Diretor da companhia"),
    COMPANY_EMPLOYEE(3, "Funcionário da companhia"),
    CONSUMER(4, "Consumidor");

    private final Integer code;
    private final String description;

    RoleType(final Integer code, final String description) {
        this.code = code;
        this.description = description;
    }

    public static RoleType fromCode(Integer code) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.code.equals(code)) {
                return roleType;
            }
        }
        return null;
    }

}
