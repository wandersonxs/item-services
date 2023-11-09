package com.auctionall.itemservices.infrastructure.adapter.out.persistence.repository;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.out.ItemNotFound;
import com.auctionall.itemservices.application.out.Items;
import com.auctionall.itemservices.infrastructure.adapter.out.persistence.entity.ItemEntity;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemAdapter implements Items {

    private final ItemRepository itemRepository;

    @Override
    public UnitReactive<Item> findItemById(Integer itemId) throws ItemNotFound {
        Mono<ItemEntity> byId = itemRepository.findById(itemId);
        return UnitReactive.of(byId).map(ItemEntity::toDomain);
    }

    @Override
    public UnitReactive<Item> save(Item item) {
        Mono<Item> itemMono = itemRepository.save(ItemEntity.fromDomain(item)).map(ItemEntity::toDomain);
        return UnitReactive.of(itemMono);
    }

    @Override
    public UnitReactive<Boolean> existsItemById(Integer itemId) {
        Mono<Boolean> booleanMono = itemRepository.existsById(itemId);
        return UnitReactive.of(booleanMono);
    }

    @Override
    public UnitReactive<Boolean> existsItemByName(Item item) {
        Mono<Boolean> booleanMono = itemRepository.findByName(item.name()).hasElements();
        return UnitReactive.of(booleanMono);
    }
}
