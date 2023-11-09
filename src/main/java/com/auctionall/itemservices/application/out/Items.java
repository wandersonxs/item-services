package com.auctionall.itemservices.application.out;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

public interface Items {
    UnitReactive<Item> findItemById(Integer itemId) throws ItemNotFound;

    UnitReactive<Boolean> existsItemById(Integer itemId);

    public UnitReactive<Boolean> existsItemByName(Item item);

    UnitReactive<Item> save(Item item);

}
