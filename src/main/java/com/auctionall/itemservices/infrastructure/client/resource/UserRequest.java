package com.auctionall.itemservices.infrastructure.client.resource;

import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.domain.shared.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public record UserRequest(Integer id, @NotEmpty(message = "User's name mandatory.") String name, @NotNull LocalDate dateOfBirth, Status status) {

     public User toDomain() {
        return new User(id, name, dateOfBirth, Objects.isNull(status) ? Status.ENABLED : status);
    }

}
