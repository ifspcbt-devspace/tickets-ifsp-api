package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderItemResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("product")
        TicketSaleResponse product,
        @JsonProperty("quantity")
        Integer quantity,
        @JsonProperty("total")
        BigDecimal total
) {
}
