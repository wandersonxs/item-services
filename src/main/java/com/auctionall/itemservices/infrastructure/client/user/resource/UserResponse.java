package com.auctionall.itemservices.infrastructure.client.user.resource;


import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.domain.shared.Status;
import java.time.LocalDate;

public record UserResponse(Integer id, String name, LocalDate dateOfBirth, Status status) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(user.id(), user.name(), user.dateOfBirth(), user.status());
    }

}
