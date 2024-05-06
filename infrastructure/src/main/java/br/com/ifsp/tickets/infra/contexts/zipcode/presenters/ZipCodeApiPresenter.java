package br.com.ifsp.tickets.infra.contexts.zipcode.presenters;

import br.com.ifsp.tickets.domain.zipcode.ZipCode;
import br.com.ifsp.tickets.infra.contexts.zipcode.models.ZipCodeResponse;

public interface ZipCodeApiPresenter {

    static ZipCodeResponse present(ZipCode zipCode) {
        return new ZipCodeResponse(zipCode.value(), zipCode.street(), zipCode.neighborhood(), zipCode.city(), zipCode.state());
    }

}
