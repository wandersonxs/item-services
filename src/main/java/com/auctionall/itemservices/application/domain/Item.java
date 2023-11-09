package com.auctionall.itemservices.application.domain;

import com.auctionall.itemservices.application.domain.shared.Status;

import java.math.BigDecimal;

public record Item(Integer id, Integer userId, String name, BigDecimal estimatedValue, Status status) { }
