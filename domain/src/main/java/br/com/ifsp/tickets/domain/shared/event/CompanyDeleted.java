package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.administrative.user.User;

import java.time.Instant;
import java.util.UUID;

public class CompanyDeleted implements IDomainEvent {

    private final String targetId;
    private final String name;
    private final String authorId;

    public CompanyDeleted(Company company, User author) {
        this.targetId = company.getId().getValue().toString();
        this.name = company.getName();
        this.authorId = author.getId().getValue().toString();
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }

    @Override
    public String subject() {
        return "CompanyDeleted";
    }

    @Override
    public String message() {
        return "Company has been deleted";
    }

    @Override
    public String reason() {
        return "User " + authorId + " deleted the company '" + name + "'";
    }

    @Override
    public String source() {
        return Company.class.getName();
    }

    @Override
    public DomainEventType type() {
        return DomainEventType.INFO;
    }

    @Override
    public String targetId() {
        return this.targetId;
    }

    @Override
    public String id() {
        return UUID.randomUUID().toString();
    }
}
