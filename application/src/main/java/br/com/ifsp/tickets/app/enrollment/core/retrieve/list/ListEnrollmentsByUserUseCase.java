package br.com.ifsp.tickets.app.enrollment.core.retrieve.list;

import br.com.ifsp.tickets.app.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.user.User;

public class ListEnrollmentsByUserUseCase implements IListEnrollmentsByUserUseCase {

    private final IEnrollmentGateway enrollmentGateway;

    public ListEnrollmentsByUserUseCase(IEnrollmentGateway enrollmentGateway) {
        this.enrollmentGateway = enrollmentGateway;
    }

    @Override
    public Pagination<EnrollmentOutput> execute(User authenticatedUser) {
        return this.enrollmentGateway.findAllByUserID(authenticatedUser.getId(), new SearchQuery(0, 10, null, "createdAt", "desc")).map(EnrollmentOutput::from);
    }
}
