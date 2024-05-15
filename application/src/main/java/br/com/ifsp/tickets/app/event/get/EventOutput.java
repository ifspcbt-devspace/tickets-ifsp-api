package br.com.ifsp.tickets.app.event.get;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventConfig;
import br.com.ifsp.tickets.domain.event.EventStatus;

import java.util.List;

public record EventOutput(
        String id,
        String name,
        String description,
        String companyId,
        String initDate,
        String endDate,
        EventStatus status,
        Address address,
        List<Configuration> configuration
) {

    public static EventOutput from(Event event) {
        return new EventOutput(
                event.getId().toString(),
                event.getName(),
                event.getDescription(),
                event.getCompanyID().toString(),
                event.getInitDate().toString(),
                event.getEndDate().toString(),
                event.getStatus(),
                Address.from(event.getAddress()),
                event.getConfiguration().stream()
                        .map(Configuration::from)
                        .toList()
        );
    }

    public record Address(
            String street,
            String number,
            String neighborhood,
            String city,
            String state,
            String country,
            String zipCode
    ) {

        public static Address from(br.com.ifsp.tickets.domain.shared.vo.Address address) {
            return new Address(
                    address.getStreet(),
                    address.getNumber(),
                    address.getNeighborhood(),
                    address.getCity(),
                    address.getState(),
                    address.getCountry(),
                    address.getZipCode()
            );
        }

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
