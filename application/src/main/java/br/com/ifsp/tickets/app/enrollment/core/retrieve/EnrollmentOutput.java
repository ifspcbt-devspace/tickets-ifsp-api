package br.com.ifsp.tickets.app.enrollment.core.retrieve;

import br.com.ifsp.tickets.domain.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.enrollment.EnrollmentStatus;

import java.time.LocalDateTime;

public record EnrollmentOutput(
        String id,
        String userID,
        String eventID,
        LocalDateTime createdAt,
        EnrollmentStatus status,
        LocalDateTime updatedAt
) {

    public static EnrollmentOutput from(Enrollment enrollment) {
        return new EnrollmentOutput(
                enrollment.getId().toString(),
                enrollment.getUserID().toString(),
                enrollment.getEventID().toString(),
                enrollment.getCreatedAt(),
                enrollment.getStatus(),
                enrollment.getUpdatedAt()
        );
    }

}
