package com.auctionall.itemservices.infrastructure.adapter.out.persistence.repository;

import com.auctionall.itemservices.infrastructure.adapter.out.persistence.entity.ItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<ItemEntity, Integer> {
    Flux<ItemEntity> findByName(String name);
}
