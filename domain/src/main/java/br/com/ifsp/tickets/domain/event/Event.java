package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.exceptions.ChangeEventStatusException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class Event extends AggregateRoot<EventID> {

    private final CompanyID companyID;
    private final List<String> attachmentPaths;
    private final List<EventConfig> configuration;
    private String name;
    private String description;
    private Date initDate;
    private Date endDate;
    private Address address;
    private EventStatus status;

    public Event(EventID eventID, String name, String description, Date initDate, Date endDate, Address address, CompanyID companyID, EventStatus status, List<String> attachmentPaths, List<EventConfig> configuration) {
        super(eventID);
        this.name = name;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.address = address;
        this.companyID = companyID;
        this.status = status;
        this.attachmentPaths = attachmentPaths == null ? new ArrayList<>() : attachmentPaths;
        this.configuration = configuration == null ? new ArrayList<>() : configuration;
    }

    public static Event with(EventID eventID, String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, EventStatus status, List<String> attachmentPaths, List<EventConfig> configuration) {
        return new Event(eventID, name, description, initialDate, endDate, address, companyID, status, attachmentPaths, configuration);
    }

    public static Event newEvent(String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, List<EventConfig> configuration) {
        return new Event(EventID.unique(), name, description, initialDate, endDate, address, companyID, EventStatus.SCHEDULED, null, configuration);
    }

    public void update(String name, String description, Date initialDate, Date endDate, List<EventConfig> configuration) {
        this.name = name;
        this.description = description;
        this.initDate = initialDate;
        this.endDate = endDate;
        this.changeConfiguration(configuration);
    }

    public EventConfig getConfiguration(EventConfigKey key) {
        return this.configuration.stream()
                .filter(c -> c.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public void changeConfiguration(List<EventConfig> configuration) {
        for (EventConfig config : configuration) {
            this.configuration.stream()
                    .filter(c -> c.getKey().equals(config.getKey()))
                    .findFirst()
                    .ifPresentOrElse(
                            c -> c.update(config.getValue()),
                            () -> this.configuration.add(config)
                    );
        }
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

    public void changeStatus(EventStatus status) {
        switch (status) {
            case SCHEDULED -> unpublish();
            case PUBLISHED -> publish();
            case OPENED -> open();
            case CANCELED -> cancel();
            case FINISHED -> finish();
        }
    }

    public void unpublish() {
        if (this.status.isFinished())
            throw new ChangeEventStatusException("Event is finished");
        if (this.status.isCanceled())
            throw new ChangeEventStatusException("Event is canceled");
        if (this.status.isOpened())
            throw new ChangeEventStatusException("Event is opened and cannot be unpublished");
        if (this.status.isScheduled())
            throw new ChangeEventStatusException("Event is already scheduled to be published");
        this.status = EventStatus.SCHEDULED;
    }

    public void open() {
        if (this.status.isFinished())
            throw new ChangeEventStatusException("Event is finished");
        if (this.status.isCanceled())
            throw new ChangeEventStatusException("Event is canceled");
        if (this.status.isOpened())
            throw new ChangeEventStatusException("Event is already opened");
        if (this.status.isScheduled())
            throw new ChangeEventStatusException("Event is scheduled and cannot be opened before being published");
        this.status = EventStatus.OPENED;
    }

    public void publish() {
        if (this.status.isFinished())
            throw new ChangeEventStatusException("Event is finished");
        if (this.status.isCanceled())
            throw new ChangeEventStatusException("Event is canceled");
        if (this.status.isOpened())
            throw new ChangeEventStatusException("Event is opened and cannot need to be closed to be published");
        if (this.status.isPublished())
            throw new ChangeEventStatusException("Event is already published");
        this.status = EventStatus.PUBLISHED;
    }

    public void cancel() {
        if (this.status.isOpened())
            throw new ChangeEventStatusException("Event is opened and cannot be canceled, it only can be finished");
        if (this.status.isFinished())
            throw new ChangeEventStatusException("Event is finished");
        if (this.status.isCanceled())
            throw new ChangeEventStatusException("Event is already canceled");
        this.status = EventStatus.CANCELED;
    }


    public void addAttachment(String path) {
        this.attachmentPaths.add(path);
    }

    public void removeAttachment(String path) {
        this.attachmentPaths.remove(path);
    }

    public void finish() {
        if (!this.status.isOpened())
            throw new ChangeEventStatusException("Event is not opened, it needs to be opened to be finished");
        this.status = EventStatus.FINISHED;
    }


    @Override
    public void validate(IValidationHandler handler) {
        new EventValidator(handler, this).validate();
    }
}
