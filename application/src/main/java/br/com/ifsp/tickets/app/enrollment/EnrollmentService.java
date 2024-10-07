package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.app.enrollment.core.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.core.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.core.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.core.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.create.CreateUpsertEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.upsert.create.ICreateUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.GetUpsertEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.GetUpsertEnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.IGetUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.User;

public class EnrollmentService {

    private final ICreateEnrollmentUseCase createEnrollmentUseCase;
    private final IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase;
    private final ICreateUpsertEnrollmentUseCase createUpsertEnrollmentUseCase;
    private final IGetUpsertEnrollmentUseCase getUpsertEnrollmentUseCase;

    public EnrollmentService(ICreateEnrollmentUseCase createEnrollmentUseCase, IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase, ICreateUpsertEnrollmentUseCase createUpsertEnrollmentUseCase, IGetUpsertEnrollmentUseCase getUpsertEnrollmentUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
        this.listEnrollmentsByUserUseCase = listEnrollmentsByUserUseCase;
        this.createUpsertEnrollmentUseCase = createUpsertEnrollmentUseCase;
        this.getUpsertEnrollmentUseCase = getUpsertEnrollmentUseCase;
    }

    public CreateEnrollmentOutput create(CreateEnrollmentInput input) {
        return this.createEnrollmentUseCase.execute(input);
    }

    public Pagination<EnrollmentOutput> listEnrollmentsByUser(User authenticatedUser) {
        return this.listEnrollmentsByUserUseCase.execute(authenticatedUser);
    }

    public String createUpsertEnrollment(CreateUpsertEnrollmentInput input) {
        return this.createUpsertEnrollmentUseCase.execute(input);
    }

    public GetUpsertEnrollmentOutput getUpsertEnrollment(GetUpsertEnrollmentInput in) {
        return this.getUpsertEnrollmentUseCase.execute(in);
    }
}
