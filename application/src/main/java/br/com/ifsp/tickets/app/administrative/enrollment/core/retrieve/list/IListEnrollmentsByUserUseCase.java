package br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.list;

import br.com.ifsp.tickets.app.IUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.administrative.user.User;

public interface IListEnrollmentsByUserUseCase extends IUseCase<User, Pagination<EnrollmentOutput>> {
}
