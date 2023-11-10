package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.Collectionx;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

public interface FetchingItem {
    Unitx<Item> findItemById(Integer id);
    Unitx<Boolean> existsItemById(Integer id);

    Collectionx<Item> findAll();
}

