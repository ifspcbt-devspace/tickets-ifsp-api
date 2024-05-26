package br.com.ifsp.tickets.app.event.retrieve.get;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventConfig;
import br.com.ifsp.tickets.domain.event.EventStatus;

import java.util.Date;
import java.util.List;

public record EventOutput(
        String id,
        String name,
        String description,
        String companyId,
        Date initDate,
        Date endDate,
        EventStatus status,
        AddressOutput address,
        List<Configuration> configuration
) {

    public static EventOutput from(Event event) {
        return new EventOutput(
                event.getId().getValue().toString(),
                event.getName(),
                event.getDescription(),
                event.getCompanyID().getValue().toString(),
                event.getInitDate(),
                event.getEndDate(),
                event.getStatus(),
                AddressOutput.from(event.getAddress()),
                event.getConfiguration().stream()
                        .map(Configuration::from)
                        .toList()
        );
    }

    public record Configuration(
            String key,
            String value
    ) {

        public static Configuration from(EventConfig eventConfig) {
            return new Configuration(eventConfig.getKey().name(), eventConfig.getValue());
        }
    }
}
