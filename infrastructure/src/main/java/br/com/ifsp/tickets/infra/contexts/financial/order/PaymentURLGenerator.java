package br.com.ifsp.tickets.infra.contexts.financial.order;

import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventConfigKey;
import br.com.ifsp.tickets.domain.financial.order.IPaymentURLGenerator;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.exceptions.ValidationException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PaymentURLGenerator implements IPaymentURLGenerator {

    private final IEventGateway eventGateway;

    @Override
    public String generateURL(Order order) {
        final OrderID orderId = order.getId();
        final String name = order.getName();
        final String email = order.getEmail().getValue();
        final String phone = order.getPhoneNumber().getValue();
        final String document = order.getDocument().getValue();

        final Event event = order.getItems().stream().map(OrderItem::getTicket)
                .map(ts -> this.eventGateway.findById(ts.getEventID()).orElseThrow(() -> NotFoundException.with(Event.class, ts.getEventID())))
                .min(Comparator.comparing(Event::getInitDate))
                .orElseThrow(() -> new ValidationException("No event found for order", Notification.create("No event found for order")));

        final LocalDateTime expirationTime = event.getConfiguration(EventConfigKey.END_SELLING_DATE).getLocalDateTime();

        final PreferenceClient client = new PreferenceClient();

        final List<PreferenceItemRequest> items = order.getItems().stream().map(orderItem -> PreferenceItemRequest.builder()
                .id(orderItem.getId().getValue().toString())
                .title(orderItem.getTicket().getName())
                .description(orderItem.getTicket().getDescription())
                .quantity(orderItem.getQuantity())
                .currencyId("BRL")
                .unitPrice(orderItem.getTicket().getPrice())
                .build()).toList();

        final List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("credit_card").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("debit_card").build());

        final PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .expires(true)
                .dateOfExpiration(OffsetDateTime.from(expirationTime))
                .items(items)
                .payer(
                        PreferencePayerRequest.builder()
                                .name(name)
                                .email(email)
                                .phone(PhoneRequest.builder().number(phone).build())
                                .identification(
                                        IdentificationRequest.builder().type("RG").number(document).build())
                                .build())
                .externalReference(orderId.getValue().toString())
                .paymentMethods(
                        PreferencePaymentMethodsRequest.builder()
                                .defaultPaymentMethodId("pix")
                                .excludedPaymentTypes(excludedPaymentTypes)
                                .installments(1)
                                .defaultInstallments(1)
                                .build())
                .build();

        final Preference preference;
        try {
            preference = client.create(preferenceRequest);
        } catch (Exception e) {
            log.error("Error creating preference", e);
            throw new ValidationException("Error creating preference", Notification.create(e.getMessage()));
        }

        return preference.getInitPoint();
    }
}
