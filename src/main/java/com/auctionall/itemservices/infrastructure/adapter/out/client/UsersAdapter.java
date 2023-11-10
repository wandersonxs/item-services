package com.auctionall.itemservices.infrastructure.adapter.out.client;

import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.out.Users;
import com.auctionall.itemservices.infrastructure.client.UserClient;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UsersAdapter implements Users {

    private final UserClient userClient;

    @Override
    public UnitReactive<User> findUserById(Integer userId) {
        Mono<User> user = userClient.resource(userId);
        return UnitReactive.of(user);
    }
}
