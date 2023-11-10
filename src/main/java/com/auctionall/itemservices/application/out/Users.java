package com.auctionall.itemservices.application.out;

import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.infrastructure.reactive.Unitx;

public interface Users {
    Unitx<User> findUserById(Integer userId);

}
