package br.com.ifsp.tickets.app.financial.order.retrieve.list;

import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;

public record ListCustomerOrdersInput(
        User authenticatedUser,
        AdvancedSearchQuery advancedSearchQuery
) {

    public static ListCustomerOrdersInput of(User authenticatedUser, AdvancedSearchQuery advancedSearchQuery) {
        return new ListCustomerOrdersInput(authenticatedUser, advancedSearchQuery);
    }
}
