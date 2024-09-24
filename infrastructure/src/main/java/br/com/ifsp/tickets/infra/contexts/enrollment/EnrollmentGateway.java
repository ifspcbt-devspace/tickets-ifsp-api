package br.com.ifsp.tickets.infra.contexts.enrollment;

import br.com.ifsp.tickets.domain.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.enrollment.EnrollmentID;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.infra.contexts.enrollment.persistence.EnrollmentJpaEntity;
import br.com.ifsp.tickets.infra.contexts.enrollment.persistence.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class EnrollmentGateway implements IEnrollmentGateway {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment create(Enrollment enrollment) {
        return this.enrollmentRepository.save(EnrollmentJpaEntity.from(enrollment)).toAggregate();
    }

    @Override
    public Optional<Enrollment> findById(EnrollmentID id) {
        return this.enrollmentRepository.findById(id.getValue()).map(EnrollmentJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Enrollment> findAllByUserID(UserID id, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<Enrollment> page = this.enrollmentRepository.findAllByUserID(id.getValue(), request).map(EnrollmentJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Pagination<Enrollment> findAllByEventID(EventID eventID, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.sort()), sq.direction())
        );

        final Page<Enrollment> page = this.enrollmentRepository.findAllByEventID(eventID.getValue(), request).map(EnrollmentJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Enrollment findByUserIDAndEventID(UserID userID, EventID eventID) {
        return this.enrollmentRepository.findByUserIDAndEventID(userID.getValue(), eventID.getValue())
                .map(EnrollmentJpaEntity::toAggregate).orElse(null);
    }

    @Override
    public boolean existsByUserIDAndEventID(UserID userID, EventID eventID) {
        return this.enrollmentRepository.existsByUserIDAndEventID(userID.getValue(), eventID.getValue());
    }

    @Override
    public boolean existsByDocumentAndEventID(String document, EventID eventID) {
        return this.enrollmentRepository.existsByDocumentAndEventID(document, eventID.getValue());
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        return this.enrollmentRepository.save(EnrollmentJpaEntity.from(enrollment)).toAggregate();
    }

    @Override
    public void delete(EnrollmentID id) {
        this.enrollmentRepository.deleteById(id.getValue());
    }

    @Override
    public boolean exists(EnrollmentID id) {
        return this.enrollmentRepository.existsById(id.getValue());
    }
}
