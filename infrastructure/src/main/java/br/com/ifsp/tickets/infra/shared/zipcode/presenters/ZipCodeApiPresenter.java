package br.com.ifsp.tickets.infra.shared.zipcode.presenters;

import br.com.ifsp.tickets.domain.shared.zipcode.ZipCode;
import br.com.ifsp.tickets.infra.shared.zipcode.models.ZipCodeResponse;

public interface ZipCodeApiPresenter {

    static ZipCodeResponse present(ZipCode zipCode) {
        return new ZipCodeResponse(zipCode.value(), zipCode.street(), zipCode.neighborhood(), zipCode.city(), zipCode.state());
    }

}
