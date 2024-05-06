package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.domain.zipcode.IZipCodeGateway;
import br.com.ifsp.tickets.infra.api.CepAPI;
import br.com.ifsp.tickets.infra.contexts.zipcode.models.ZipCodeResponse;
import br.com.ifsp.tickets.infra.contexts.zipcode.presenters.ZipCodeApiPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CepController implements CepAPI {

    private final IZipCodeGateway zipCodeGateway;

    @Override
    public ResponseEntity<ZipCodeResponse> get(String cep) {
        return this.zipCodeGateway.findByValue(cep).map(ZipCodeApiPresenter::present).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
