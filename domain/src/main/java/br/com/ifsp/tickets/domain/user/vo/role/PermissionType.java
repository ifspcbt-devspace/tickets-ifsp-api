package br.com.ifsp.tickets.domain.user.vo.role;

import lombok.Getter;

@Getter
public enum PermissionType {

    MANAGE_USERS("Gerenciar usuários"),
    MANAGE_COMPANIES("Gerenciar companhias"),
    MANAGE_TICKETS("Gerenciar ingressos"),
    MANAGE_ENROLLMENTS("Gerenciar inscrições"),
    MANAGE_EVENTS("Gerenciar eventos"),
    MANAGE_A_COMPANY("Gerenciar uma companhia"),
    MANAGE_COMPANY_TICKETS("Gerenciar ingressos de sua companhia"),
    MANAGE_COMPANY_EVENTS("Gerenciar eventos de sua companhia"),
    MANAGE_COMPANY_ENROLLMENTS("Gerenciar inscrições de sua companhia"),
    BASIC("Básica");

    private final String description;

    PermissionType(String description) {
        this.description = description;
    }
}
