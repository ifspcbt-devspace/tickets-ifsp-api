package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.company.CompanyService;
import br.com.ifsp.tickets.app.company.CompanyServiceFactory;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class CompanyConfig {

    private final ICompanyGateway companyGateway;
    private final IUserGateway userGateway;

    @Bean
    public CompanyService companyService() {
        return CompanyServiceFactory.create(companyGateway, userGateway);
    }

}
