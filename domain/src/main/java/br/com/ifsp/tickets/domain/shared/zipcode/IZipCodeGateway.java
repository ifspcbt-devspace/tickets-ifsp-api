package br.com.ifsp.tickets.domain.shared.zipcode;

import java.util.Optional;

public interface IZipCodeGateway {

    Optional<ZipCode> findByValue(String value);
}
