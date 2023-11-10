package com.auctionall.itemservices.infrastructure.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class Unitx<T> {

    Mono<T> mono;

    public Mono<T> toMono() {
        return mono;
    }

    public static <T> Unitx<T> just(T data){
        return Unitx.of(Mono.just(data));
    }

    public <U> Unitx<U> map(Function<? super T, ? extends U> mapper) {
        return Unitx.of(mono.map(mapper));
    }

    public <U> Unitx<U> flatMap(Function<? super T, ? extends Unitx<? extends U>> transformer) {
        Function<? super T, ? extends Mono<? extends U>> monoFunction = transformer.andThen(Unitx::toMono);
        return Unitx.of(mono.flatMap(monoFunction));
    }

    public static <U> Unitx<U> error(Throwable t) {
        return Unitx.of(Mono.error(t));
    }

}