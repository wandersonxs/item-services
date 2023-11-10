package com.auctionall.itemservices.application.service;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.domain.vo.ItemUser;
import com.auctionall.itemservices.application.in.FetchingItem;
import com.auctionall.itemservices.application.in.RegisteringItem;
import com.auctionall.itemservices.application.in.UpdatingItem;
import com.auctionall.itemservices.application.out.Items;
import com.auctionall.itemservices.application.out.Users;
import com.auctionall.itemservices.infrastructure.adapter.exception.BusinessException;
import com.auctionall.itemservices.infrastructure.annotations.UseCase;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import reactor.core.publisher.Mono;

@UseCase
public class ItemService implements RegisteringItem, UpdatingItem, FetchingItem {

    private final Items items;
    private final Users users;

    public ItemService(Items items, Users users) {
        this.items = items;
        this.users = users;
    }

    @Override
    public UnitReactive<Item> saveItem(Item item) {

        ItemUser itemUser = new ItemUser(new User(item.user().id(), null, null, null), item);

        return UnitReactive.of(Mono.just(itemUser)
                .flatMap(n -> {
                    return this.users.findUserById(n.getUser().id()).toMono()
                            .doOnNext(itemUser::setUser)
                            .thenReturn(itemUser);
                })
                .flatMap(k -> {
                    return items.existsItemByName(itemUser.getItem())
                            .flatMap(itemExists -> itemExists ?
                                    UnitReactive.error(new BusinessException("Item already registered...")) :
                                    items.save(itemUser.getItem())).toMono();
                }));
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
        return new Item(itemId, new User(item.user().id(), null, null, null), item.name(), item.estimatedValue(), item.status());
    }
}
