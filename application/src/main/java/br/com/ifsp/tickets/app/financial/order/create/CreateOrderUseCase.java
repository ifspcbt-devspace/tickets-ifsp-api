package br.com.ifsp.tickets.app.financial.order.create;

import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventStatus;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.administrative.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.administrative.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.administrative.user.vo.RG;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.IPaymentURLGenerator;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateOrderUseCase implements ICreateOrderUseCase {

    private final IOrderGateway orderGateway;
    private final IEventGateway eventGateway;
    private final IPaymentURLGenerator paymentURLGenerator;
    private final ITicketSaleGateway ticketSaleGateway;

    public CreateOrderUseCase(IOrderGateway orderGateway, IEventGateway eventGateway, IPaymentURLGenerator paymentURLGenerator, ITicketSaleGateway ticketSaleGateway) {
        this.orderGateway = orderGateway;
        this.eventGateway = eventGateway;
        this.paymentURLGenerator = paymentURLGenerator;
        this.ticketSaleGateway = ticketSaleGateway;
    }

    @Override
    public CreateOrderOutput execute(CreateOrderInput anIn) {
        final Optional<User> authenticatedUser = Optional.ofNullable(anIn.authenticatedUser());

        final String name;
        final Document document;
        final EmailAddress emailAddress;
        final PhoneNumber phoneNumber;
        final LocalDate birthDate;

        if (authenticatedUser.isEmpty()) {
            name = anIn.name();
            emailAddress = new EmailAddress(anIn.email());
            phoneNumber = new PhoneNumber(anIn.phone());
            document = new RG(anIn.document());
            birthDate = anIn.birthDate();
        } else {
            final User user = authenticatedUser.get();
            name = user.getName();
            emailAddress = user.getEmail();
            phoneNumber = user.getPhoneNumber();
            document = user.getDocument();
            birthDate = user.getBirthDate();
        }

        final Notification notification = Notification.create("Create Order Validation Exception");

        Order createdOrder = this.orderGateway.create(Order.newOrderTicket(
                authenticatedUser.orElse(null),
                new ArrayList<>(),
                name,
                emailAddress,
                phoneNumber,
                document,
                birthDate
        ));

        try {
            createdOrder.validate(notification);
            notification.throwAnyErrors();

            final List<OrderItem> items = new ArrayList<>();

            for (CreateOrderInput.OrderItemInput item : anIn.items()) {
                final TicketSaleID ticketSaleID = TicketSaleID.with(item.ticketSaleId());
                final TicketSale ticketSale = this.ticketSaleGateway.findById(ticketSaleID).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticketSaleID));
                final Event event = this.eventGateway.findById(ticketSale.getEventID()).orElseThrow(() -> NotFoundException.with(Event.class, ticketSale.getEventID()));

                if (!event.getStatus().equals(EventStatus.OPENED))
                    Notification.create("Event is not opened").append("Event is not open for enrollment").throwAnyErrors();

                if (ticketSale.getStock() < 1)
                    notification.append("Ticket #%s has insufficient stock".formatted(ticketSale.getId().toString())).throwAnyErrors();

                ticketSale.removeStock(1);

                final OrderItem orderItem = OrderItem.newOrderItem(createdOrder.getId(), ticketSale, 1);
                orderItem.validate(notification);

                items.add(orderItem);
            }

            createdOrder.addItems(items);
            createdOrder.generatePaymentUrl(this.paymentURLGenerator);
            createdOrder = this.orderGateway.update(createdOrder);

            return CreateOrderOutput.from(createdOrder);
        } catch (Exception e) {
            this.orderGateway.delete(createdOrder);
            throw e;
        }
    }
}
