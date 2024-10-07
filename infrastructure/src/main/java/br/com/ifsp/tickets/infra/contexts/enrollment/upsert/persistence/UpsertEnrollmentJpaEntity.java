package br.com.ifsp.tickets.infra.contexts.enrollment.upsert.persistence;

import br.com.ifsp.tickets.domain.enrollment.upsert.UpsertEnrollment;
import br.com.ifsp.tickets.domain.enrollment.upsert.UpsertEnrollmentID;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
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
public class UpsertEnrollmentJpaEntity implements Serializable {
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
    @Column(name = "ticket_sale_id", nullable = false)
    private UUID ticketSaleID;
    @Column(name = "ticket_id", nullable = false)
    private UUID ticketID;
    @Column(name = "user_id")
    private UUID userID;

    public UpsertEnrollmentJpaEntity(UUID id, UUID eventID, String name, String email, LocalDate birthDate, String document, UUID ticketSaleID, UUID ticketID, UUID userID) {
        this.id = id;
        this.eventID = eventID;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.document = document;
        this.ticketSaleID = ticketSaleID;
        this.ticketID = ticketID;
        this.userID = userID;
    }

    public static UpsertEnrollmentJpaEntity from(UpsertEnrollment enrollment){
        return new UpsertEnrollmentJpaEntity(
                enrollment.getId().getValue(),
                enrollment.getEventId().getValue(),
                enrollment.getName(),
                enrollment.getEmail(),
                enrollment.getBirthDate(),
                enrollment.getDocument(),
                enrollment.getTicketSaleId().getValue(),
                enrollment.getTicketID().getValue(),
                enrollment.getUserID().getValue()
                );
    }

    public UpsertEnrollment toAggregate(){
        return new UpsertEnrollment(
                UpsertEnrollmentID.with(this.getId()),
                UserID.with(this.getUserID()),
                this.getName(),
                this.getEmail(),
                this.getDocument(),
                this.getBirthDate(),
                EventID.with(this.getEventID()),
                TicketID.with(this.getTicketID()),
                TicketSaleID.with(this.getTicketSaleID())
        );
    }
}
