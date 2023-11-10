package com.auctionall.itemservices.infrastructure.adapter.out.persistence.repository;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.out.ItemNotFound;
import com.auctionall.itemservices.application.out.Items;
import com.auctionall.itemservices.infrastructure.adapter.out.persistence.entity.ItemEntity;
import com.auctionall.itemservices.infrastructure.reactive.Collectionx;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemAdapter implements Items {

    private final ItemRepository itemRepository;

    @Override
    public Unitx<Item> findItemById(Integer itemId) throws ItemNotFound {
        Mono<ItemEntity> byId = itemRepository.findById(itemId);
        return Unitx.of(byId).map(ItemEntity::toDomain);
    }

    @Override
    public Unitx<Item> save(Item item) {
        Mono<Item> itemMono = itemRepository.save(ItemEntity.fromDomain(item)).map(ItemEntity::toDomain);
        return Unitx.of(itemMono);
    }

    @Override
    public Collectionx<Item> findAll() {
        Flux<ItemEntity> all = itemRepository.findAll();
        return Collectionx.of(all).map(ItemEntity::toDomain);
    }

    @Override
    public Unitx<Boolean> existsItemById(Integer itemId) {
        Mono<Boolean> booleanMono = itemRepository.existsById(itemId);
        return Unitx.of(booleanMono);
    }

    @Override
    public Unitx<Boolean> existsItemByName(Item item) {
        Mono<Boolean> booleanMono = itemRepository.findByName(item.name()).hasElements();
        return Unitx.of(booleanMono);
    }
}
