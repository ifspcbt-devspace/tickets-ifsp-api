package br.com.ifsp.tickets.domain.communication.email;

import java.util.List;
import java.util.Optional;

public interface IEmailGateway {

    Email create(Email anEmail);

    Email update(Email anEmail);

    Optional<Email> findById(EmailID anID);

    List<Email> findNotSent();

}
