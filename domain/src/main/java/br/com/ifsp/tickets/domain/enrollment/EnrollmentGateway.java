package br.com.ifsp.tickets.domain.enrollment;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.UserID;

public interface EnrollmentGateway {

    Enrollment create(Enrollment enrollment);

    Enrollment findById(EnrollmentID id);

    Pagination<Enrollment> findAllByUserID(UserID id);

    Pagination<Enrollment> findAllByEventID(EventID eventID);

    Enrollment findByUserIDAndEventID(UserID userID, EventID eventID);

    Enrollment update(Enrollment enrollment);

    void delete(EnrollmentID id);

    boolean exists(EnrollmentID id);

}
