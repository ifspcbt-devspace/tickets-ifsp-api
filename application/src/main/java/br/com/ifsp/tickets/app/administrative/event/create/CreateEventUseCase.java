package br.com.ifsp.tickets.app.administrative.event.create;

import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.CompanyID;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventConfig;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventConfigKey;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.event.EventCreated;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NoCompanyException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.administrative.user.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class CreateEventUseCase implements ICreateEventUseCase {

    private final ICompanyGateway companyGateway;
    private final IEventGateway eventGateway;
    private final IDomainEventPublisher eventPublisher;

    public CreateEventUseCase(ICompanyGateway companyGateway, IEventGateway eventGateway, IDomainEventPublisher eventPublisher) {
        this.companyGateway = companyGateway;
        this.eventGateway = eventGateway;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CreateEventOutput execute(CreateEventInput anIn) {
        final User user = anIn.user();
        final CompanyID companyID = CompanyID.with(anIn.companyId());

        if (!user.canManageEvents() && !user.canManageAnyEvent())
            throw new IllegalResourceAccessException("User does not have permission to create events");

        if (!user.hasCompany() && !user.canManageAnyEvent()) throw new NoCompanyException();

        final CompanyID userCompanyID = user.getCompanyID();

        if (!user.canManageAnyEvent() && !userCompanyID.equals(companyID))
            throw new IllegalResourceAccessException("User does not have permission to create events for this company");

        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));
        final String name = anIn.name();
        final String description = anIn.description();
        final LocalDate initialDate = anIn.initialDate();
        final LocalDate endDate = anIn.endDate();
        final HashMap<EventConfigKey, String> configuration = anIn.configuration();
        final List<EventConfig> config = configuration.entrySet().stream().map(e -> EventConfig.with(e.getKey(), e.getValue())).toList();

        final Event event = Event.newEvent(
                name,
                description,
                initialDate,
                endDate,
                company.getAddress(),
                companyID,
                config
        );

        final Notification notification = Notification.create("An error occurred while validating the event");
        event.validate(notification);
        notification.throwAnyErrors();

        final Event eventCreated = this.eventGateway.create(event);
        eventCreated.registerEvent(new EventCreated(eventCreated, user));
        eventCreated.publishDomainEvents(this.eventPublisher);

        return CreateEventOutput.from(eventCreated);
    }


}
