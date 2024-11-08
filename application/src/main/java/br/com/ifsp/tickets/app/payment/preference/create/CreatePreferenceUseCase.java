package br.com.ifsp.tickets.app.payment.preference.create;

import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.exceptions.ValidationException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreatePreferenceUseCase implements ICreatePreferenceUseCase {

    private final ITicketSaleGateway ticketSaleGateway;

    public CreatePreferenceUseCase(ITicketSaleGateway ticketSaleGateway) {
        this.ticketSaleGateway = ticketSaleGateway;
    }

    @Override
    public CreatePreferenceOutput execute(CreatePreferenceInput anIn) {
        final TicketID ticketId = TicketID.with(anIn.ticketId());
        final User user = anIn.user();
        final TicketSale ticketSale = this.ticketSaleGateway.findById(TicketSaleID.with(anIn.ticket_sale_id())).orElseThrow(() -> NotFoundException.with(TicketSale.class, TicketSaleID.with(anIn.ticket_sale_id())));

        final PreferenceClient client = new PreferenceClient();

        final PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id(ticketSale.getId().getValue().toString())
                        .title(ticketSale.getName())
                        .description(ticketSale.getDescription())
                        .quantity(1)
                        .currencyId("BRL")
                        .unitPrice(ticketSale.getPrice())
                        .build();

        final List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        final List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("credit_card").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("debit_card").build());

        final PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .expires(false)
                .items(items)
                .payer(
                        PreferencePayerRequest.builder()
                                .name(user.getName())
                                .surname(user.getUsername())
                                .email(user.getEmail().toString())
                                .phone(PhoneRequest.builder().number(user.getPhoneNumber().getValue()).build())
                                .identification(
                                        IdentificationRequest.builder().type("RG").number(user.getDocument().toString()).build())
                                .build())
                .externalReference(ticketId.getValue().toString())
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
        return new CreatePreferenceOutput(preference.getInitPoint(), ticketId.getValue().toString());
    }
}
