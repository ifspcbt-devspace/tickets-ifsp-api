package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record CreateOrderRequest(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone,
        @JsonProperty("document") String document,
        @JsonProperty("birth_date") LocalDate birthDate,
        @JsonProperty("items") List<OrderItemRequest> items
) {

    public record OrderItemRequest(
            @JsonProperty("ticket_sale_id")
            String ticketSaleId
    ) {

    }
}
