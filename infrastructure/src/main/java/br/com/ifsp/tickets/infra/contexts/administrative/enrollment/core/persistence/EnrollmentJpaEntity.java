package br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.persistence;

import br.com.ifsp.tickets.domain.administrative.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.administrative.enrollment.EnrollmentID;
import br.com.ifsp.tickets.domain.administrative.enrollment.EnrollmentStatus;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import br.com.ifsp.tickets.infra.shared.encryption.EncryptionService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@Getter
public class EnrollmentJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "event_id", nullable = false)
    private UUID eventID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "document", nullable = false)
    private String document;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "user_id")
    private UUID userID;

    public EnrollmentJpaEntity(UUID id, String name, String email, String document, LocalDate birthDate, UUID eventID, String status, LocalDateTime createdAt, LocalDateTime updatedAt, UUID userID) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = EncryptionService.encrypt(document);
        this.birthDate = birthDate;
        this.userID = userID;
        this.eventID = eventID;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EnrollmentJpaEntity from(Enrollment enrollment) {
        return new EnrollmentJpaEntity(
                enrollment.getId().getValue(),
                enrollment.getName(),
                enrollment.getEmail(),
                enrollment.getDocument(),
                enrollment.getBirthDate(),
                enrollment.getEventID().getValue(),
                enrollment.getStatus().name(),
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt(),
                enrollment.getUserID().isPresent() ? enrollment.getUserID().get().getValue() : null
        );
    }

    public Enrollment toAggregate() {
        return Enrollment.with(
                EnrollmentID.with(this.id),
                this.name,
                this.email,
                this.birthDate,
                this.getDecryptedDocument(),
                EventID.with(this.eventID),
                EnrollmentStatus.valueOf(this.status),
                this.createdAt,
                this.updatedAt,
                UserID.with(this.userID)
        );
    }

    public String getDecryptedDocument() {
        return EncryptionService.decrypt(this.document);
    }
}
