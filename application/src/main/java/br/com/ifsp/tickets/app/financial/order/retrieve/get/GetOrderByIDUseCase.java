package br.com.ifsp.tickets.app.financial.order.retrieve.get;

import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.administrative.user.vo.RG;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

import java.util.Optional;

public class GetOrderByIDUseCase implements IGetOrderByIDUseCase {

    private final IOrderGateway orderGateway;

    public GetOrderByIDUseCase(IOrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public OrderOutput execute(GetOrderByIDInput anIn) {
        final Optional<User> user = Optional.ofNullable(anIn.authenticatedUser());
        final Document document = user.map(User::getDocument).orElse(new RG(anIn.document()));
        final OrderID id = OrderID.with(anIn.id());

        return this.orderGateway.findByIdAndDocument(id, document)
                .map(OrderOutput::from)
                .orElseThrow(() -> NotFoundException.with(Order.class, id));
    }
}
