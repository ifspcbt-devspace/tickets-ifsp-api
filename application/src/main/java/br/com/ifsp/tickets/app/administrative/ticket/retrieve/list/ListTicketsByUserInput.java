package br.com.ifsp.tickets.app.administrative.ticket.retrieve.list;

import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.administrative.user.User;

public record ListTicketsByUserInput(
        User user,
        String userID,
        SearchQuery searchQuery
) {

    public static ListTicketsByUserInput of(User user, String userID, SearchQuery searchQuery) {
        return new ListTicketsByUserInput(user, userID, searchQuery);
    }
}
