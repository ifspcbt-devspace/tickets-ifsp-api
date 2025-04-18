package br.com.ifsp.tickets.infra.contexts.administrative.event.presenters;

import br.com.ifsp.tickets.app.administrative.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.app.administrative.event.retrieve.search.SearchEventOutput;
import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import br.com.ifsp.tickets.infra.shared.address.AddressResponse;

import java.util.HashMap;

public interface EventApiPresenter {

    static EventResponse present(EventOutput output) {
        final HashMap<String, String> configuration = new HashMap<>();
        output.configuration().forEach(cfg -> configuration.put(cfg.key(), cfg.value()));
        return new EventResponse(
                output.id(),
                output.name(),
                output.description(),
                output.companyId(),
                output.initDate(),
                output.endDate(),
                output.status(),
                new AddressResponse(
                        output.address().street(),
                        output.address().number(),
                        output.address().complement(),
                        output.address().neighborhood(),
                        output.address().city(),
                        output.address().state(),
                        output.address().country(),
                        output.address().zipCode()
                ),
                configuration
        );
    }

    static SearchEventResponse present(SearchEventOutput output) {
        final HashMap<String, String> configuration = new HashMap<>();
        output.configuration().forEach(cfg -> configuration.put(cfg.key(), cfg.value()));
        return new SearchEventResponse(
                output.id(),
                output.name(),
                output.description(),
                output.companyId(),
                output.initDate(),
                output.endDate(),
                output.status(),
                new AddressResponse(
                        output.address().street(),
                        output.address().number(),
                        output.address().complement(),
                        output.address().neighborhood(),
                        output.address().city(),
                        output.address().state(),
                        output.address().country(),
                        output.address().zipCode()
                ),
                configuration
        );
    }

    static TicketSaleResponse present(Ticket2SellOutput output) {
        return new TicketSaleResponse(
                output.id(),
                output.eventId(),
                output.name(),
                output.description(),
                output.price(),
                output.entries(),
                output.active()
        );
    }
}
