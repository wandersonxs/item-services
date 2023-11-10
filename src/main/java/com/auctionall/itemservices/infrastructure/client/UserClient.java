package com.auctionall.itemservices.infrastructure.client;

import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.infrastructure.client.resource.UserResponse;
import com.auctionall.itemservices.infrastructure.reactive.UnitReactive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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

//
//    public Flux<UserDto> getAllUsers(){
//        return this.webClient
//                .get()
//                .uri("all")
//                .retrieve()
//                .bodyToFlux(UserDto.class);
//    }


}
