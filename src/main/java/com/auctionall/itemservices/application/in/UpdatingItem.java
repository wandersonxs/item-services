package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

public interface UpdatingItem {
    UnitReactive<Item> updateItem(Integer id, Item item);
}
