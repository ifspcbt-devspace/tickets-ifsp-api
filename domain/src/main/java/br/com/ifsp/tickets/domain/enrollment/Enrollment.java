package br.com.ifsp.tickets.domain.enrollment;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.UserID;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Enrollment extends Entity<EnrollmentID> {

    private final UserID userID;
    private final EventID eventID;
    private final LocalDateTime createdAt;
    private EnrollmentStatus status;
    private LocalDateTime updatedAt;

    public Enrollment(EnrollmentID enrollmentID, UserID userID, EventID eventID, EnrollmentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(enrollmentID);
        this.userID = userID;
        this.eventID = eventID;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Enrollment with(EnrollmentID enrollmentID, UserID userID, EventID eventID, EnrollmentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Enrollment(enrollmentID, userID, eventID, status, createdAt, updatedAt);
    }

    public static Enrollment newEnrollment(UserID userID, EventID eventID) {
        return new Enrollment(EnrollmentID.unique(), userID, eventID, EnrollmentStatus.CONFIRMED, LocalDateTime.now(), null);
    }

    public void confirm() {
        this.status = EnrollmentStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void deny() {
        this.status = EnrollmentStatus.DENIED;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new EnrollmentValidator(handler, this).validate();
    }
}
