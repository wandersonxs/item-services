package com.auctionall.itemservices.infrastructure.adapter.in.rest.resource;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.shared.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemResponse(Integer id, Integer userId, @NotEmpty(message = "Item's name mandatory.") String name, @NotNull BigDecimal estimatedValue, Status status) {

    public static ItemResponse fromDomain(Item item) {
        return new ItemResponse(item.id(),item.user().id(), item.name(), item.estimatedValue(), item.status());
    }

}
