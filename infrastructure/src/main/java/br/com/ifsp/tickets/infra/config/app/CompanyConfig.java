package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.administrative.company.CompanyService;
import br.com.ifsp.tickets.app.administrative.company.CompanyServiceFactory;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class CompanyConfig {

    private final ICompanyGateway companyGateway;
    private final IUserGateway userGateway;
    private final IDomainEventPublisher eventPublisher;

    @Bean
    public CompanyService companyService() {
        return CompanyServiceFactory.create(companyGateway, userGateway, eventPublisher);
    }

}
