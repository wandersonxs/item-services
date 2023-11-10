package com.auctionall.itemservices.application.in;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.vo.ItemUser;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import reactor.core.publisher.Mono;

public interface RegisteringItem {

    UnitReactive<Item> saveItem(Item item);

}

