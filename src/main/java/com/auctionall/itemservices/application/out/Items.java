package com.auctionall.itemservices.application.out;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.Collectionx;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

public interface Items {
    Unitx<Item> findItemById(Integer itemId) throws ItemNotFound;

    Unitx<Boolean> existsItemById(Integer itemId);

    Unitx<Boolean> existsItemByName(Item item);

    Unitx<Item> save(Item item);

    Collectionx<Item> findAll();
}
