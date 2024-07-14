package br.com.ifsp.tickets.infra.contexts.enrollment.presenters;

import br.com.ifsp.tickets.app.enrollment.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.infra.contexts.enrollment.models.EnrollmentResponse;

public interface EnrollmentApiPresenter {

    static EnrollmentResponse present(EnrollmentOutput output) {
        return new EnrollmentResponse(
                output.id(),
                output.userID(),
                output.eventID(),
                output.createdAt(),
                output.status(),
                output.updatedAt()
        );
    }

}
