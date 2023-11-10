package com.auctionall.itemservices.infrastructure.client.user;

import com.auctionall.itemservices.application.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<User> resource(Integer id){
        return this.webClient
                .get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

}
