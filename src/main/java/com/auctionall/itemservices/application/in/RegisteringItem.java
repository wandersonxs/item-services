package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

public interface RegisteringItem {

    Unitx<Item> saveItem(Item item);

}

