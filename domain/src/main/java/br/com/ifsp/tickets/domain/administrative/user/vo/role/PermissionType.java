package br.com.ifsp.tickets.domain.administrative.user.vo.role;

import lombok.Getter;

@Getter
public enum PermissionType {

    ADMIN("Acesso a recursos restritos"),
    MANAGE_ANY_USER("Gerenciar usuários"),
    MANAGE_ANY_COMPANY("Gerenciar companhias"),
    MANAGE_ANY_TICKET("Gerenciar ingressos"),
    MANAGE_ANY_ENROLLMENT("Gerenciar inscrições"),
    MANAGE_ANY_EVENT("Gerenciar eventos"),
    MANAGE_COMPANY("Gerenciar uma companhia"),
    MANAGE_COMPANY_TICKETS("Gerenciar ingressos de sua companhia"),
    MANAGE_COMPANY_EVENTS("Gerenciar eventos de sua companhia"),
    MANAGE_COMPANY_ENROLLMENTS("Gerenciar inscrições de sua companhia"),
    BASIC("Básica");

    private final String description;

    PermissionType(String description) {
        this.description = description;
    }
}
