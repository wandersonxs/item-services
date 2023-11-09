package com.auctionall.itemservices.application.service;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.in.FetchingItem;
import com.auctionall.itemservices.application.in.RegisteringItem;
import com.auctionall.itemservices.application.in.UpdatingItem;
import com.auctionall.itemservices.application.out.Items;
import com.auctionall.itemservices.infrastructure.adapter.exception.BusinessException;
import com.auctionall.itemservices.infrastructure.annotations.UseCase;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

@UseCase
public class ItemService implements RegisteringItem, UpdatingItem, FetchingItem {

    private final Items items;

    public ItemService(Items items) {
        this.items = items;
    }

    @Override
    public UnitReactive<Item> saveItem(Item item) {
        return items.existsItemByName(item)
                .flatMap(itemExists -> itemExists ?
                        UnitReactive.error(new BusinessException("Item already registered...")) :
                        items.save(item));
    }

    @Override
    public UnitReactive<Item> updateItem(Integer itemId, Item item) {
        return items.existsItemById(itemId)
                .flatMap(itemExists -> itemExists ?
                        items.save(buildItem(itemId, item)) :
                        UnitReactive.error(new BusinessException("Item missed on the repository.")));
    }

    @Override
    public UnitReactive<Item> findItemById(Integer itemId) {
        return items.findItemById(itemId);
    }

    @Override
    public UnitReactive<Boolean> existsItemById(Integer id) {
        return items.existsItemById(id);
    }

    private Item buildItem(Integer itemId, Item item) {
        return new Item(itemId, item.userId(), item.name(), item.estimatedValue(), item.status());
    }
}
