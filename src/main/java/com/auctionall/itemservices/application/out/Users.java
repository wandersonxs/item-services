package com.auctionall.itemservices.application.out;

import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;

public interface Users {
    UnitReactive<User> findUserById(Integer userId);

}
