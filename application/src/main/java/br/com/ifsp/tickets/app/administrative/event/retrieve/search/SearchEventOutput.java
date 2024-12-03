package br.com.ifsp.tickets.app.administrative.event.retrieve.search;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventConfig;
import br.com.ifsp.tickets.domain.administrative.event.EventStatus;

import java.time.LocalDate;
import java.util.List;

public record SearchEventOutput(
        String id,
        String name,
        String description,
        String companyId,
        LocalDate initDate,
        LocalDate endDate,
        EventStatus status,
        AddressOutput address,
        List<Configuration> configuration
) {

    public static SearchEventOutput from(Event event) {
        return new SearchEventOutput(
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
