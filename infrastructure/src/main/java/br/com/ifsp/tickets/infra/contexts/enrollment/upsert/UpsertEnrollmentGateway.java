package br.com.ifsp.tickets.infra.contexts.enrollment.upsert;

import br.com.ifsp.tickets.domain.enrollment.upsert.IUpsertEnrollmentGateway;
import br.com.ifsp.tickets.domain.enrollment.upsert.UpsertEnrollment;
import br.com.ifsp.tickets.infra.contexts.enrollment.core.persistence.EnrollmentJpaEntity;
import br.com.ifsp.tickets.infra.contexts.enrollment.upsert.persistence.UpsertEnrollmentJpaEntity;
import br.com.ifsp.tickets.infra.contexts.enrollment.upsert.persistence.UpsertEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class UpsertEnrollmentGateway implements IUpsertEnrollmentGateway {
    private final UpsertEnrollmentRepository enrollmentRepository;

    @Override
    public UpsertEnrollment create(UpsertEnrollment enrollment) {
        return this.enrollmentRepository.save(UpsertEnrollmentJpaEntity.from(enrollment)).toAggregate();
    }

    @Override
    public Optional<UpsertEnrollment> getByTicketId(String ticketId) {
        return this.enrollmentRepository.findByTicketID(UUID.fromString(ticketId)).map(UpsertEnrollmentJpaEntity::toAggregate);
    }
}
