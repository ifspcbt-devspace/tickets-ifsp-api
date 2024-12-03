package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.administrative.event.EventService;
import br.com.ifsp.tickets.app.administrative.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.administrative.event.create.CreateEventOutput;
import br.com.ifsp.tickets.app.administrative.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellInput;
import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellOutput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.download.DownloadThumbnailOutput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.reset.ResetThumbnailInput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.upload.UploadThumbnailInput;
import br.com.ifsp.tickets.domain.shared.exceptions.NoStacktraceException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.EventAPI;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.CreateEventRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.event.presenters.EventApiPresenter;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.CreateTicketSaleRequest;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import br.com.ifsp.tickets.infra.shared.search.SearchFilterRequest;
import br.com.ifsp.tickets.infra.shared.search.SortSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController implements EventAPI {

    private final EventService eventService;

    @Override
    public ResponseEntity<Void> create(CreateEventRequest request) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateEventInput input = CreateEventInput.with(
                user.toAggregate(),
                request.companyId(),
                request.name(),
                request.description(),
                request.initDate(),
                request.endDate(),
                request.configuration()
        );
        final CreateEventOutput output = this.eventService.create(input);
        return ResponseEntity.created(URI.create("/v1/event/" + output.id())).build();
    }

    @Override
    public ResponseEntity<Pagination<SearchEventResponse>> search(Integer page, Integer perPage, AdvancedSearchRequest request) {
        final AdvancedSearchQuery advancedSearchQuery = AdvancedSearchQuery.of(
                page,
                perPage,
                request.sorts().stream().map(SortSearchRequest::toSortSearch).toList(),
                request.filters().stream().map(SearchFilterRequest::toSearchFilter).toList()
        );
        final Pagination<SearchEventResponse> pagination = this.eventService.search(advancedSearchQuery).map(EventApiPresenter::present);
        return ResponseEntity.ok(pagination);
    }

    @Override
    public ResponseEntity<EventResponse> get(String id) {
        final EventOutput output = this.eventService.get(id);
        return ResponseEntity.ok(EventApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<byte[]> getThumbnail(String id) {
        final DownloadThumbnailOutput output = this.eventService.downloadThumbnail(id);
        return ResponseEntity.ok().contentType(IFileStorage.getExtension(output.fileName()).equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG).body(output.fileContent());
    }

    @Override
    public ResponseEntity<?> deleteThumbnail(String id) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.eventService.resetThumbnail(ResetThumbnailInput.of(user.toAggregate(), id));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> uploadThumbnail(String id, MultipartFile file, String fileName) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UploadThumbnailInput input;
        try {
            input = UploadThumbnailInput.of(user.toAggregate(), id, fileName, file.getBytes());
        } catch (Exception e) {
            throw new NoStacktraceException(e.getMessage(), e);
        }
        this.eventService.uploadThumbnail(input);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> createTicketSale(String id, CreateTicketSaleRequest request) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final CreateTicket2SellInput ticket2SellInput = CreateTicket2SellInput.of(user.toAggregate(), id, request.name(), request.description(), request.price(), request.entries());

        final CreateTicket2SellOutput output = this.eventService.createTicketForSale(ticket2SellInput);

        return ResponseEntity.created(URI.create("/v1/event/" + output.id())).build();
    }

    @Override
    public ResponseEntity<Pagination<TicketSaleResponse>> getTicketSaleByEventId(String id) {
        final Pagination<TicketSaleResponse> list = this.eventService.getTicketSaleByEvent(id).map(EventApiPresenter::present);
        return ResponseEntity.ok(list);
    }
}
