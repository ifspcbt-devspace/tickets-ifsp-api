package br.com.ifsp.tickets.infra.contexts.company.presenters;

import br.com.ifsp.tickets.app.company.retrieve.get.CompanyOutput;
import br.com.ifsp.tickets.app.company.retrieve.search.SearchCompanyOutput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyOutput;
import br.com.ifsp.tickets.infra.contexts.company.models.CompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.SearchCompanyResponse;
import br.com.ifsp.tickets.infra.shared.address.AddressResponse;

public interface CompanyApiPresenter {

    static CompanyResponse present(CompanyOutput output) {
        return new CompanyResponse(
                output.id(),
                output.name(),
                output.description(),
                output.cnpj(),
                new AddressResponse(
                        output.address().street(),
                        output.address().number(),
                        output.address().complement(),
                        output.address().neighborhood(),
                        output.address().city(),
                        output.address().state(),
                        output.address().country(),
                        output.address().zipCode()
                )
        );
    }

    static CompanyResponse present(UpdateCompanyOutput output) {
        return new CompanyResponse(
                output.id(),
                output.name(),
                output.description(),
                output.cnpj(),
                new AddressResponse(
                        output.address().street(),
                        output.address().number(),
                        output.address().complement(),
                        output.address().neighborhood(),
                        output.address().city(),
                        output.address().state(),
                        output.address().country(),
                        output.address().zipCode()
                )
        );
    }

    static SearchCompanyResponse present(SearchCompanyOutput output) {
        return new SearchCompanyResponse(
                output.id(),
                output.name(),
                output.description(),
                output.cnpj(),
                new AddressResponse(
                        output.address().street(),
                        output.address().number(),
                        output.address().complement(),
                        output.address().neighborhood(),
                        output.address().city(),
                        output.address().state(),
                        output.address().country(),
                        output.address().zipCode()
                )
        );
    }
}
