package br.com.ifsp.tickets.app.financial.order.retrieve.list;

import br.com.ifsp.tickets.app.IUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public interface IListCustomerOrdersUseCase extends IUseCase<ListCustomerOrdersInput, Pagination<OrderOutput>> {
}
