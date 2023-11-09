package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

public interface RegisteringItem {

    UnitReactive<Item> saveItem(Item item);

}

