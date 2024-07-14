package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.User;

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
