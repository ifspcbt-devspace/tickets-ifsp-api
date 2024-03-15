package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.vo.Address;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.AlreadyFinishedEventException;
import br.com.ifsp.tickets.domain.shared.validation.ValidationHandler;
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
    private boolean published;
    private boolean active;
    private int maxTickets;

    public Event(EventID eventID, String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, boolean published, boolean active, int maxTickets, List<String> attachmentPaths) {
        super(eventID);
        this.name = name;
        this.description = description;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.address = address;
        this.companyID = companyID;
        this.published = published;
        this.active = active;
        this.maxTickets = maxTickets;
        this.attachmentPaths = attachmentPaths == null ? List.of() : attachmentPaths;
    }

    public static Event with(EventID eventID, String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, boolean published, boolean active, int maxTickets, List<String> attachmentPaths) {
        return new Event(eventID, name, description, initialDate, endDate, address, companyID, published, active, maxTickets, attachmentPaths);
    }

    public static Event newEvent(String name, String description, Date initialDate, Date endDate, Address address, CompanyID companyID, int maxTickets) {
        return new Event(EventID.unique(), name, description, initialDate, endDate, address, companyID, false, true, maxTickets, null);
    }

    public void update(String name, String description, Date initialDate, Date endDate, Address address, int maxTickets) {
        this.name = name;
        this.description = description;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.address = address;
        this.maxTickets = maxTickets;
    }

    public void togglePublished() {
        this.published = !this.published;
    }

    public void toggleActive() {
        if (this.endDate.before(new Date()))
            throw new AlreadyFinishedEventException(this.getId());
        this.active = !this.active;
    }

    public void addAttachment(String path) {
        this.attachmentPaths.add(path);
    }

    public void removeAttachment(String path) {
        this.attachmentPaths.remove(path);
    }

    public void finish() {
        if (!this.active && this.endDate.before(new Date()))
            throw new AlreadyFinishedEventException(this.getId());
        this.active = false;
    }


    @Override
    public void validate(ValidationHandler handler) {
        new EventValidator(handler, this).validate();
    }
}
