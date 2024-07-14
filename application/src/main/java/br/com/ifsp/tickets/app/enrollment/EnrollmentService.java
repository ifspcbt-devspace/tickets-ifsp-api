package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentInput;

public class EnrollmentService {
    private final ICreateEnrollmentUseCase createEnrollmentUseCase;

    public EnrollmentService(ICreateEnrollmentUseCase createEnrollmentUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
    }

    public CreateEnrollmentOutput create(CreateEnrollmentInput input) {
        return this.createEnrollmentUseCase.execute(input);
    }
}
