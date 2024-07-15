package br.com.ifsp.tickets.infra.contexts.enrollment.persistence;

import br.com.ifsp.tickets.domain.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.enrollment.EnrollmentID;
import br.com.ifsp.tickets.domain.enrollment.EnrollmentStatus;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.user.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@Getter
public class EnrollmentJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userID;
    @Column(name = "event_id", nullable = false)
    private UUID eventID;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EnrollmentJpaEntity(UUID id, UUID userID, UUID eventID, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userID = userID;
        this.eventID = eventID;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EnrollmentJpaEntity from(Enrollment enrollment) {
        return new EnrollmentJpaEntity(
                enrollment.getId().getValue(),
                enrollment.getUserID().getValue(),
                enrollment.getEventID().getValue(),
                enrollment.getStatus().name(),
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt()
        );
    }

    public Enrollment toAggregate() {
        return Enrollment.with(
                EnrollmentID.with(this.id),
                UserID.with(this.userID),
                EventID.with(this.eventID),
                EnrollmentStatus.valueOf(this.status),
                this.createdAt,
                this.updatedAt
        );
    }
}
