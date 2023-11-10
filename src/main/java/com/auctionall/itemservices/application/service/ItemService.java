package com.auctionall.itemservices.application.service;

import com.auctionall.itemservices.application.domain.vo.ItemUser;
import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.in.FetchingItem;
import com.auctionall.itemservices.application.in.RegisteringItem;
import com.auctionall.itemservices.application.in.UpdatingItem;
import com.auctionall.itemservices.application.out.Items;
import com.auctionall.itemservices.application.out.Users;
import com.auctionall.itemservices.infrastructure.adapter.exception.BusinessException;
import com.auctionall.itemservices.infrastructure.annotations.UseCase;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@UseCase
public class ItemService implements RegisteringItem, UpdatingItem, FetchingItem {

    private final Items items;
    private final Users users;

    public ItemService(Items items, Users users) {
        this.items = items;
        this.users = users;
    }


    private Mono<Item> productRequestResponse(Item itemUser){
//        return this.items.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
//                .doOnNext(rc::setProductDto)
//                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
//                .thenReturn(rc);

        return items.existsItemByName(itemUser)
                .flatMap(itemExists -> itemExists ?
                        UnitReactive.error(new BusinessException("Item already registered...")) :
                        items.save(itemUser)).toMono();
    }

    private Mono<ItemUser> userRequestResponse(ItemUser rc){
        return this.users.findUserById(1).toMono()
                .doOnNext(rc::setUser)
                .thenReturn(rc);
    }

    @Override
    public Mono<Item> saveItem(Item item) {

        ItemUser itemUser = new ItemUser();
        User user;

        Mono<Item> itemMono = Mono.just(item);

         return Mono.just(item).map(n ->  new ItemUser(new User(n.user().id(), null, null, null), item))
//                .flatMap(n -> Mono.just(users.findUserById(n.getUser().id()))
                .flatMap(this::userRequestResponse)
//                .doOnNext(k ->  itemUser.setUser(k.getUser()))
                .doOnNext(k -> {
                    itemUser.setUser(k.getUser());
                    itemUser.setItem(item);
                })
                 .flatMap(k -> this.productRequestResponse(item))
//                .flatMap(this::userRequestResponse)
//                .map(EntityDtoUtil::getPurchaseOrder)
//                .map(this.orderRepository::save) // blocking
//                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .publishOn(Schedulers.boundedElastic());

//         return itemMono;

//        item.
//
//        UnitReactive<User> user = users.findUserById(item.user().id());
//
//
//       user.toMono().doOnNext(result -> userBuild(result)).map();
//
//        return items.existsItemByName(item)
//                .flatMap(itemExists -> itemExists ?
//                        UnitReactive.error(new BusinessException("Item already registered...")) :
//                        items.save(item));

    }

    private void userBuild(Mono<User> user){
//        User u = new User(user.id(),null,null,null);
        System.out.println("TESTE");
    }
//
//    @Override
//    public UnitReactive<Item> saveItem(UnitReactive<Item> item) {
//
//        // Verificar se item não é duplicado
//        // Verificar se id user existe
//
//        item.flatMap(i -> {
//            return users.findUserById(i.userId());
//        }).map()
//
//        users.findUserById(item.userId()).toMono()
//                .map(User::new)
//
//        return items.existsItemByName(item)
//                .flatMap(itemExists -> itemExists ?
//                        UnitReactive.error(new BusinessException("Item already registered...")) :
//                        items.save(item));
//
////        return requestDtoMono.map(RequestContext::new)
////                .flatMap(this::productRequestResponse)
////                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
////                .flatMap(this::userRequestResponse)
////                .map(EntityDtoUtil::getPurchaseOrder)
////                .map(this.orderRepository::save) // blocking
////                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
////                .subscribeOn(Schedulers.boundedElastic());
//
//    }
//
////    public UnitReactive<Item> processOrder(UnitReactive<Item> requestDtoMono){
////        return requestDtoMono.map(RequestContext::new)
////                .flatMap(this::productRequestResponse)
////                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
////                .flatMap(this::userRequestResponse)
////                .map(EntityDtoUtil::getPurchaseOrder)
////                .map(this.orderRepository::save) // blocking
////                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
////                .subscribeOn(Schedulers.boundedElastic());
////    }
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
