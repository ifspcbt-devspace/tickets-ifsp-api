package br.com.ifsp.tickets.app.financial.order.cancel;

import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.Document;

import java.util.Optional;

public class CancelOrderUseCase implements ICancelOrderUseCase {

    private final IOrderGateway orderGateway;
    private final ITicketSaleGateway ticketSaleGateway;

    public CancelOrderUseCase(IOrderGateway orderGateway, ITicketSaleGateway ticketSaleGateway) {
        this.orderGateway = orderGateway;
        this.ticketSaleGateway = ticketSaleGateway;
    }

    @Override
    public void execute(CancelOrderInput anIn) {
        final Optional<User> user = Optional.ofNullable(anIn.authenticatedUser());
        final OrderID orderID = OrderID.with(anIn.id());
        final Document document;

        if (user.isEmpty()) {
            document = new Document(anIn.document());
        } else {
            document = user.get().getDocument();
        }

        final Order order = this.orderGateway.findByIdAndDocument(orderID, document).orElseThrow(() -> NotFoundException.with(Order.class, orderID));

        order.cancel();
        this.orderGateway.update(order);

        for (OrderItem item : order.getItems()) {
            final TicketSale ticket = item.getTicket();
            ticket.addStock(item.getQuantity());
            this.ticketSaleGateway.update(ticket);
        }
    }
}
