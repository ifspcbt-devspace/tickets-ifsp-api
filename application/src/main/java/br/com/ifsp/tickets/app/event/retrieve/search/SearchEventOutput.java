package br.com.ifsp.tickets.app.event.retrieve.search;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventConfig;
import br.com.ifsp.tickets.domain.event.EventStatus;

import java.util.List;

public record SearchEventOutput(
        String id,
        String name,
        String description,
        String companyId,
        String initDate,
        String endDate,
        EventStatus status,
        AddressOutput address,
        List<Configuration> configuration
) {

    public static SearchEventOutput from(Event event) {
        return new SearchEventOutput(
                event.getId().toString(),
                event.getName(),
                event.getDescription(),
                event.getCompanyID().toString(),
                event.getInitDate().toString(),
                event.getEndDate().toString(),
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
