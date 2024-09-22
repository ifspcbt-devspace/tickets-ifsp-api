package br.com.ifsp.tickets.infra.contexts.event.core.persistence;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.event.*;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.infra.contexts.event.core.persistence.converter.EventStatusConverter;
import br.com.ifsp.tickets.infra.shared.address.AddressJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
public class EventJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "company_id", nullable = false)
    private UUID companyId;
    @Column(name = "init_date", nullable = false)
    private LocalDate initDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressJpaEntity address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_thumbnail_id", referencedColumnName = "id")
    private EventThumbnailJpaEntity thumbnail;
    @Column(name = "status", nullable = false)
    @Convert(converter = EventStatusConverter.class)
    private EventStatus status;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_attachments", joinColumns = @JoinColumn(name = "event_id"))
    private List<String> attachmentPaths;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_configurations",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> configuration;

    public EventJpaEntity(UUID id, String name, String description, UUID companyId, LocalDate initDate, LocalDate endDate, AddressJpaEntity address, EventThumbnailJpaEntity thumbnail, EventStatus status, List<String> attachmentPaths, HashMap<String, String> configuration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companyId = companyId;
        this.initDate = initDate;
        this.endDate = endDate;
        this.address = address;
        this.thumbnail = thumbnail;
        this.status = status;
        this.attachmentPaths = attachmentPaths == null ? List.of() : attachmentPaths;
        this.configuration = configuration == null ? new HashMap<>() : configuration;
    }

    public static EventJpaEntity from(Event event) {
        final HashMap<String, String> configuration = event.getConfiguration().stream()
                .collect(Collectors.toMap(c -> c.getKey().name(), EventConfig::getValue, (a, b) -> b, HashMap::new));
        return new EventJpaEntity(
                event.getId().getValue(),
                event.getName(),
                event.getDescription(),
                event.getCompanyID().getValue(),
                event.getInitDate(),
                event.getEndDate(),
                AddressJpaEntity.from(event.getId(), event.getAddress()),
                event.getThumbnail().isEmpty() ? null : EventThumbnailJpaEntity.from(event.getId(), event.getThumbnail()),
                event.getStatus(),
                event.getAttachmentPaths(),
                configuration.isEmpty() ? null : configuration);
    }

    public Event toAggregate(IFileStorage fileStorage) {
        final List<EventConfig> eventConfigs = this.configuration.entrySet().stream()
                .map(e -> new EventConfig(EventConfigKey.valueOf(e.getKey()), e.getValue()))
                .collect(Collectors.toList());
        return Event.with(
                new EventID(this.id),
                this.name,
                this.description,
                this.initDate,
                this.endDate,
                this.address.toAggregate(),
                new CompanyID(this.companyId),
                this.status,
                this.attachmentPaths,
                eventConfigs,
                this.thumbnail.toVo(fileStorage, new EventID(this.id))
        );
    }

}
