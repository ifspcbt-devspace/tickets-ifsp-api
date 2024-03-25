package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.ChangeEventStatusException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class Event extends Entity<EventID> {

    private final CompanyID companyID;
    private final List<String> attachmentPaths;
    private String name;
    private String description;
    private Date initialDate;
    private Date endDate;
    private Address address;
    private EventStatus status;
    private int maxTickets;

    public Event(EventID eventID, String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, EventStatus status, int maxTickets, List<String> attachmentPaths) {
        super(eventID);
        this.name = name;
        this.description = description;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.address = address;
        this.companyID = companyID;
        this.status = status;
        this.maxTickets = maxTickets;
        this.attachmentPaths = attachmentPaths == null ? List.of() : attachmentPaths;
    }

    public static Event with(EventID eventID, String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, EventStatus status, int maxTickets, List<String> attachmentPaths) {
        return new Event(eventID, name, description, initialDate, endDate, address, companyID, status, maxTickets, attachmentPaths);
    }

    public static Event newEvent(String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, int maxTickets) {
        return new Event(EventID.unique(), name, description, initialDate, endDate, address, companyID, EventStatus.SCHEDULED, maxTickets, null);
    }

    public void update(String name, String description, Date initialDate, Date endDate, Address address, int maxTickets) {
        this.name = name;
        this.description = description;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.address = address;
        this.maxTickets = maxTickets;
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
