package br.com.ifsp.tickets.app.event.create;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventConfig;
import br.com.ifsp.tickets.domain.event.EventConfigKey;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NoCompanyException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CreateEventUseCase implements ICreateEventUseCase {

    private final ICompanyGateway companyGateway;
    private final IEventGateway eventGateway;

    public CreateEventUseCase(ICompanyGateway companyGateway, IEventGateway eventGateway) {
        this.companyGateway = companyGateway;
        this.eventGateway = eventGateway;
    }

    @Override
    public CreateEventOutput execute(CreateEventInput anIn) {
        final User user = anIn.user();
        if (!user.canManageCompany()) throw new IllegalResourceAccessException();
        if (!user.hasCompany()) throw new NoCompanyException();
        final CompanyID companyID = user.getCompanyID();
        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));
        final String name = anIn.name();
        final String description = anIn.description();
        final Date initialDate = anIn.initialDate();
        final Date endDate = anIn.endDate();
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

        final Notification notification = Notification.create();
        event.validate(notification);
        notification.throwPossibleErrors();

        final Event eventCreated = this.eventGateway.create(event);
        return CreateEventOutput.from(eventCreated);
    }


}
