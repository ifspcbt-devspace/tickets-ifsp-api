package br.com.ifsp.tickets.domain.shared.exceptions;

public class AlreadyJoinedACompany extends DomainException {

    public AlreadyJoinedACompany() {
        super("User already joined a company.");
    }
}
