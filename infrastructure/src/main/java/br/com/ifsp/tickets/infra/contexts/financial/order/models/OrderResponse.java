package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import br.com.ifsp.tickets.domain.financial.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("customer_id")
        String customerID,
        @JsonProperty("name")
        String name,
        @JsonProperty("document")
        String document,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("birth_date")
        LocalDate birthDate,
        @JsonProperty("payment_url")
        String paymentUrl,
        @JsonProperty("status")
        OrderStatus status,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt,
        @JsonProperty("items")
        List<OrderItemResponse> items
) {
}
