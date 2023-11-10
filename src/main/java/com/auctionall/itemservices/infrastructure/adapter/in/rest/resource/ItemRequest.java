package com.auctionall.itemservices.infrastructure.adapter.in.rest.resource;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.domain.shared.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public record ItemRequest(Integer id, Integer userId, @NotEmpty(message = "Item's name mandatory.") String name, @NotNull BigDecimal estimatedValue, Status status) {

     public Item toDomain() {
        return new Item(id, new User(userId), name, estimatedValue, Objects.isNull(status) ? Status.ENABLED : status);
    }

    public Item toDomain(Integer id) {
        return new Item(id, new User(userId), name, estimatedValue, Objects.isNull(status) ? Status.ENABLED : status);
    }

}
