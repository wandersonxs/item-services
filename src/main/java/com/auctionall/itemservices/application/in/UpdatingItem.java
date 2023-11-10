package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

public interface UpdatingItem {
    Unitx<Item> updateItem(Integer id, Item item);
}

