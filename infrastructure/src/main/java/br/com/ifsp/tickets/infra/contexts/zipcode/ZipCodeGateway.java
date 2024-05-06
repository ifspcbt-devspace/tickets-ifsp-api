package br.com.ifsp.tickets.infra.contexts.zipcode;

import br.com.ifsp.tickets.domain.zipcode.IZipCodeGateway;
import br.com.ifsp.tickets.domain.zipcode.ZipCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class ZipCodeGateway implements IZipCodeGateway {

    private static final String BASE_URL = "https://brasilapi.com.br/api/cep/v1/";
    private final OkHttpClient client;

    @Override
    public Optional<ZipCode> findByValue(String value) {
        final Request request = new Request.Builder().url(BASE_URL + value + "?ticketsifsp=1")
                .get()
                .header("Connection", "close")
                .header("Accept-Encoding", "identity")
                .build();
        final Call call = this.client.newCall(request);
        try (final Response response = call.execute()) {
            if (response.isSuccessful()) {
                final ResponseBody body = response.body();
                if (body != null) {
                    final String json = body.string();
                    final JsonNode node = new ObjectMapper().readTree(json);
                    return Optional.of(new ZipCode(
                            node.get("cep").asText(),
                            node.get("street").asText(),
                            node.get("neighborhood").asText(),
                            node.get("city").asText(),
                            node.get("state").asText()
                    ));
                }
            }
        } catch (IOException e) {
            log.warn("Error fetching zip code %s".formatted(value), e);
        }
        return Optional.empty();
    }
}
