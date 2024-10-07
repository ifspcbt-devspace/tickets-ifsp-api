package br.com.ifsp.tickets.infra.contexts.enrollment.core.presenters;

import br.com.ifsp.tickets.app.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.infra.contexts.enrollment.core.models.EnrollmentResponse;

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
