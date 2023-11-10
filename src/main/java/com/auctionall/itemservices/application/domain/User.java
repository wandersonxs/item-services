package com.auctionall.itemservices.application.domain;

import com.auctionall.itemservices.application.domain.shared.Status;

import java.time.LocalDate;

public record User(Integer id, String name, LocalDate dateOfBirth, Status status) {

     public User(Integer id) {
        this(id, null, null, null);
    }

}
