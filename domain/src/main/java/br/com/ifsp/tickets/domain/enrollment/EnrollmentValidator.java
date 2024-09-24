package br.com.ifsp.tickets.domain.enrollment;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class EnrollmentValidator extends Validator {

    private final Enrollment enrollment;

    public EnrollmentValidator(IValidationHandler aHandler, Enrollment enrollment) {
        super(aHandler);
        this.enrollment = enrollment;
    }

    @Override
    public void validate() {
        if (this.enrollment.getEventID() == null || this.enrollment.getEventID().getValue() == null)
            error("EventID is required");
        if (this.enrollment.getDocument() == null)
            error("Document is required");
        if (this.enrollment.getStatus() == null) error("Status is required");
        if (this.enrollment.getCreatedAt() == null) error("CreatedAt is required");
    }
}
