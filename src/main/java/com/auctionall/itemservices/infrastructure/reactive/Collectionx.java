package com.auctionall.itemservices.infrastructure.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class Collectionx<T> {

    Flux<T> flux;

    public Flux<T> toFlux() {
        return flux;
    }

    public <U> Collectionx<U> map(Function<? super T, ? extends U> mapper) {
        return Collectionx.of(flux.map(mapper));
    }
}

