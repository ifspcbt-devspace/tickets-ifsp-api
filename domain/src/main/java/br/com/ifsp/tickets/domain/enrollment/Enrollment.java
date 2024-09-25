package br.com.ifsp.tickets.domain.enrollment;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.UserID;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class Enrollment extends Entity<EnrollmentID> {

    private final UserID userID;
    private final String name;
    private final String email;
    private final LocalDate birthDate;
    private final String document;
    private final EventID eventID;
    private final LocalDateTime createdAt;
    private EnrollmentStatus status;
    private LocalDateTime updatedAt;

    public Enrollment(EnrollmentID enrollmentID, String name, String email, LocalDate birthDate, String document, EventID eventID, EnrollmentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, UserID userID) {
        super(enrollmentID);
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.document = document;
        this.eventID = eventID;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Enrollment with(EnrollmentID enrollmentID, String name, String email, LocalDate birthDate, String document, EventID eventID, EnrollmentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, UserID userID) {
        return new Enrollment(enrollmentID, name, email, birthDate, document, eventID, status, createdAt, updatedAt, userID);
    }

    public static Enrollment newEnrollment(String name, String email, String document, LocalDate birthDate, UserID userID, EventID eventID) {
        return new Enrollment(EnrollmentID.unique(), name, email, birthDate, document, eventID, EnrollmentStatus.CONFIRMED, LocalDateTime.now(), null, userID);
    }

    public Optional<UserID> getUserID() {
        return Optional.ofNullable(userID);
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
