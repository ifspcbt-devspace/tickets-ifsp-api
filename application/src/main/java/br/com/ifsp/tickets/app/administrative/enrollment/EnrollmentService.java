package br.com.ifsp.tickets.app.administrative.enrollment;

import br.com.ifsp.tickets.app.administrative.enrollment.core.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.administrative.enrollment.core.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.administrative.enrollment.core.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class EnrollmentService {

    private final ICreateEnrollmentUseCase createEnrollmentUseCase;
    private final IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase;

    public EnrollmentService(ICreateEnrollmentUseCase createEnrollmentUseCase, IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
        this.listEnrollmentsByUserUseCase = listEnrollmentsByUserUseCase;
    }

    public CreateEnrollmentOutput create(CreateEnrollmentInput input) {
        return this.createEnrollmentUseCase.execute(input);
    }

    public Pagination<EnrollmentOutput> listEnrollmentsByUser(User authenticatedUser) {
        return this.listEnrollmentsByUserUseCase.execute(authenticatedUser);
    }

}
