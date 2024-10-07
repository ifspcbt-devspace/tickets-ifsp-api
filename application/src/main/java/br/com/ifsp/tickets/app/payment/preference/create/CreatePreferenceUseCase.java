package br.com.ifsp.tickets.app.payment.preference.create;

import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;

import java.util.ArrayList;
import java.util.List;

public class CreatePreferenceUseCase implements ICreatePreferenceUseCase{
    private final ITicketGateway ticketGateway;
    private final ITicketSaleGateway ticketSaleGateway;

    public CreatePreferenceUseCase(ITicketGateway ticketGateway, ITicketSaleGateway ticketSaleGateway) {
        this.ticketGateway = ticketGateway;
        this.ticketSaleGateway = ticketSaleGateway;
    }

    @Override
    public String execute(CreatePreferenceInput anIn) {
        TicketID ticketId = TicketID.with(anIn.ticketId());
        User user = anIn.user();
        final Ticket ticket = this.ticketGateway.findById(ticketId).orElseThrow(() -> NotFoundException.with(Ticket.class, ticketId));
        final TicketSale ticketSale = this.ticketSaleGateway.findById(ticket.getTicketSaleID()).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticket.getTicketSaleID()));

        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id(ticketSale.getId().getValue().toString())
                        .title(ticketSale.getName())
                        .description(ticketSale.getDescription())
                        .quantity(1)
                        .currencyId("BRL")
                        .unitPrice(ticketSale.getPrice())
                        .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("credit_card").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("debit_card").build());

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .expires(false)
                .items(items)
                .payer(
                        PreferencePayerRequest.builder()
                                .name(user.getName())
                                .surname(user.getUsername())
                                .email(user.getEmail().toString())
                                .phone(PhoneRequest.builder().number(user.getPhoneNumber().toString()).build())
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

        Preference preference = null;
        try{
            preference = client.create(preferenceRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return preference.getInitPoint();
    }
}
