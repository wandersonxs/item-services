package com.auctionall.itemservices.infrastructure.adapter.in.rest;

import com.auctionall.itemservices.application.in.FetchingItem;
import com.auctionall.itemservices.application.in.RegisteringItem;
import com.auctionall.itemservices.application.in.UpdatingItem;
import com.auctionall.itemservices.infrastructure.adapter.in.rest.resource.ItemRequest;
import com.auctionall.itemservices.infrastructure.adapter.in.rest.resource.ItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final RegisteringItem registeringItem;
    private final UpdatingItem updatingItem;
    private final FetchingItem fetchItem;

    @PostMapping("/items")
    Mono<ResponseEntity<ItemResponse>> createItem(@RequestBody @Valid ItemRequest request, UriComponentsBuilder uriComponentsBuilder) {

        return this.registeringItem.saveItem(request.toDomain())
                .map(n -> ResponseEntity.created(null).body(ItemResponse.fromDomain(n))).toMono()
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @PutMapping("/items/{id}")
    Mono<ResponseEntity<ItemResponse>> updateOrder(@PathVariable Integer id, @Valid @RequestBody ItemRequest request) {
        return this.updatingItem.updateItem(id, request.toDomain(id))
                .map(n -> ResponseEntity.created(null).body(ItemResponse.fromDomain(n))).toMono()
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("/items/{id}")
    Mono<ResponseEntity<ItemResponse>> fetchItemById(@PathVariable Integer id) {
        return this.fetchItem.findItemById(id).toMono()
                .map(n -> ResponseEntity.ok(ItemResponse.fromDomain(n)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/items")
    Flux<ItemResponse> findAll() {
        return this.fetchItem.findAll().map(ItemResponse::fromDomain).toFlux();
    }

    @GetMapping(value = "/items/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ItemResponse> findAllStream() {
        return this.fetchItem.findAll().map(ItemResponse::fromDomain).toFlux();
    }
}
