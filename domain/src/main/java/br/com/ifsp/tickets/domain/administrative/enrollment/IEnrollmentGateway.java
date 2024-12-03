package br.com.ifsp.tickets.domain.administrative.enrollment;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

import java.util.Optional;

public interface IEnrollmentGateway {

    Enrollment create(Enrollment enrollment);

    Optional<Enrollment> findById(EnrollmentID id);

    Pagination<Enrollment> findAllByUserID(UserID id, SearchQuery sq);

    Pagination<Enrollment> findAllByEventID(EventID eventID, SearchQuery sq);

    Enrollment findByUserIDAndEventID(UserID userID, EventID eventID);

    boolean existsByUserIDAndEventID(UserID userID, EventID eventID);
    boolean existsByDocumentAndEventID(String document, EventID eventID);
    boolean existsByEmailAndEventID(String document, EventID eventID);

    Enrollment update(Enrollment enrollment);

    void delete(EnrollmentID id);

    boolean exists(EnrollmentID id);

}
