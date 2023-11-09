package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

public interface FetchingItem {
    UnitReactive<Item> findItemById(Integer id);
    UnitReactive<Boolean> existsItemById(Integer id);
}

