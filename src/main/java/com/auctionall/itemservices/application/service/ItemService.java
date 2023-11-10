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
import com.auctionall.itemservices.infrastructure.reactive.Collectionx;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

@UseCase
public class ItemService implements RegisteringItem, UpdatingItem, FetchingItem {

    private final Items items;
    private final Users users;

    public ItemService(Items items, Users users) {
        this.items = items;
        this.users = users;
    }

    @Override
    public Unitx<Item> saveItem(Item item) {

        ItemUser itemUser = new ItemUser(new User(item.user().id()), item);

        return Unitx.of(Unitx.just(itemUser).toMono()
                .flatMap(n -> {
                    return this.users.findUserById(n.getUser().id()).toMono()
                            .doOnNext(itemUser::setUser)
                            .thenReturn(itemUser);
                })
                .flatMap(k -> {
                    return items.existsItemByName(itemUser.getItem())
                            .flatMap(itemExists -> itemExists ?
                                    Unitx.error(new BusinessException("Item already registered...")) :
                                    items.save(itemUser.getItem())).toMono();
                }));
    }

    @Override
    public Unitx<Item> updateItem(Integer itemId, Item item) {

        ItemUser itemUser = new ItemUser(new User(item.user().id()), item);

        return Unitx.of(Unitx.just(itemUser).toMono()
                .flatMap(n -> {
                    return this.users.findUserById(n.getUser().id()).toMono()
                            .doOnNext(itemUser::setUser)
                            .thenReturn(itemUser);
                })
                .flatMap(k -> {
                    return items.existsItemById(itemId)
                            .flatMap(itemExists -> itemExists ?
                                    items.save(itemUser.getItem()) :
                                    Unitx.error(new BusinessException("Item already registered..."))).toMono();
                }));
    }

    @Override
    public Unitx<Item> findItemById(Integer itemId) {
        return items.findItemById(itemId);
    }

    @Override
    public Unitx<Boolean> existsItemById(Integer id) {
        return items.existsItemById(id);
    }

    @Override
    public Collectionx<Item> findAll() {
        return items.findAll();
    }

}
